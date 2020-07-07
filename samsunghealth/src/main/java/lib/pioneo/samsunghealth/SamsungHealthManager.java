package lib.pioneo.samsunghealth;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.util.Log;

import androidx.appcompat.app.AlertDialog;

import com.samsung.android.sdk.healthdata.HealthConnectionErrorResult;
import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthPermissionManager;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import static com.samsung.android.sdk.healthdata.HealthPermissionManager.PermissionKey;
import static com.samsung.android.sdk.healthdata.HealthPermissionManager.PermissionType;

/**
 * Created by pioLay on 06/July/2020
 */
@SuppressLint("StaticFieldLeak")
public class SamsungHealthManager {
    final String APP_TAG = "SamsungHealthManager";

    private static SamsungHealthManager mSamsungHealthManager;
    private static Activity mActivity;
    private static StepSyncListener mSyncCallback;

    private HealthDataStore mStore;
    private boolean isSamsungHealthConnected;

    public boolean isSamsungHealthConnected() {
        return isSamsungHealthConnected;
    }


    //**********************************************************************
    //****                                                              ****
    //****                        GET DATA CALLS                        ****
    //****                                                              ****
    //**********************************************************************

    public void getStepsFromSamsungHealth(Date date) {
        StepLoader mLoader = new StepLoader(mStore, date);
        if (isPermissionAcquired()) {
            mLoader.startObserver(mStepCountObserver);
        } else {
            requestPermission();
        }
    }

    //**********************************************************************
    //**********************************************************************

    public static SamsungHealthManager getInstance(Activity activity, StepSyncListener instance) {
        if (mSamsungHealthManager != null) {
            mActivity = activity;
            mSyncCallback = instance;
            return mSamsungHealthManager;
        }

        mSamsungHealthManager = new SamsungHealthManager(activity, instance);
        return mSamsungHealthManager;

    }

    private SamsungHealthManager(Activity activity, StepSyncListener instance) {
        mActivity = activity;
        mSyncCallback = instance;
    }

    //**********************************************************************
    //****                                                              ****
    //****                       INTERFACE CALLS                        ****
    //****                                                              ****
    //**********************************************************************
    public interface StepSyncListener {
        void onSync(int steps);
    }

    public interface ConnectionListener {
        void onConnected();
    }

    //**********************************************************************
    //**********************************************************************


    /**
     * If we have already a SamsungHealth client, then reconnect it,
     * if not, then create the SamsungHealth client and connect it.
     */
    public void startService(ConnectionListener listener) {
        mStore = new HealthDataStore(mActivity, new HealthDataStore.ConnectionListener() {
            @Override
            public void onConnected() {
                isSamsungHealthConnected = true;
                listener.onConnected();
            }

            @Override
            public void onConnectionFailed(HealthConnectionErrorResult healthConnectionErrorResult) {
                showConnectionFailureDialog(healthConnectionErrorResult);
                isSamsungHealthConnected = false;
            }

            @Override
            public void onDisconnected() {
                isSamsungHealthConnected = false;
                Log.d(APP_TAG, "Health data service is disconnected.");
                if (!mActivity.isFinishing()) {
                    mStore.connectService();
                }
            }
        });
        mStore.connectService();

    }


    public void disconnectService() {
        mStore.disconnectService();
        isSamsungHealthConnected = false;

    }

    private void showPermissionAlarmDialog() {
        if (mActivity.isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);
        alert.setTitle(R.string.notice)
                .setMessage(R.string.msg_perm_acquired)
                .setPositiveButton(R.string.ok, null)
                .show();
    }

    private void showConnectionFailureDialog(final HealthConnectionErrorResult error) {
        if (mActivity.isFinishing()) {
            return;
        }

        AlertDialog.Builder alert = new AlertDialog.Builder(mActivity);

        if (error.hasResolution()) {
            switch (error.getErrorCode()) {
                case HealthConnectionErrorResult.PLATFORM_NOT_INSTALLED:
                    alert.setMessage(R.string.msg_req_install);
                    break;
                case HealthConnectionErrorResult.OLD_VERSION_PLATFORM:
                    alert.setMessage(R.string.msg_req_upgrade);
                    break;
                case HealthConnectionErrorResult.PLATFORM_DISABLED:
                    alert.setMessage(R.string.msg_req_enable);
                    break;
                case HealthConnectionErrorResult.USER_AGREEMENT_NEEDED:
                    alert.setMessage(R.string.msg_req_agree);
                    break;
                default:
                    alert.setMessage(R.string.msg_req_available);
                    break;
            }
        } else {
            alert.setMessage(R.string.msg_conn_not_available);
        }

        alert.setPositiveButton(R.string.ok, (dialog, id) -> {
            if (error.hasResolution()) {
                error.resolve(mActivity);
            }
        });

        if (error.hasResolution()) {
            alert.setNegativeButton(R.string.cancel, null);
        }

        alert.show();
    }

    /**
     * Include all permissions that we want.
     * Look at {@link HealthConstants} to see which permissions we can set.
     * The name of every permission is set in each "HEALTH_DATA_TYPE" string (e.g. com.samsung.health.step_count)
     * Make sure to declare the new permission also in the AndroidManifest.xml File of this library!
     * A new permission must be set at meta-data-value "com.samsung.android.health.permission.read" with a ";" (semicolon)
     *
     * @return the generated Permission set
     * @author pioLay
     */
    private Set<PermissionKey> setPermissions() {
        Set<PermissionKey> pmsKeySet = new HashSet<>();
        pmsKeySet.add(new PermissionKey(HealthConstants.StepCount.HEALTH_DATA_TYPE, PermissionType.READ));
        pmsKeySet.add(new PermissionKey(HealthConstants.Exercise.HEALTH_DATA_TYPE, PermissionType.READ));
        return pmsKeySet;
    }

    private boolean isPermissionAcquired() {
        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
        try {
            // Check whether the permissions that this application needs are acquired
            Map<PermissionKey, Boolean> resultMap = pmsManager.isPermissionAcquired(setPermissions());
            return !resultMap.values().contains(Boolean.FALSE);
        } catch (Exception e) {
            Log.e(APP_TAG, "Permission request fails.", e);
        }
        return false;
    }

    private void requestPermission() {
        HealthPermissionManager pmsManager = new HealthPermissionManager(mStore);
        try {
            // Show user permission UI for allowing user to change options
            pmsManager.requestPermissions(setPermissions(), mActivity)
                    .setResultListener(new HealthResultHolder.ResultListener<HealthPermissionManager.PermissionResult>() {
                        @Override
                        public void onResult(HealthPermissionManager.PermissionResult result) {
                            Log.d(APP_TAG, "Permission callback is received.");
                            Map<PermissionKey, Boolean> resultMap = result.getResultMap();

                            if (resultMap.values().contains(Boolean.FALSE)) {
                                SamsungHealthManager.this.showPermissionAlarmDialog();
                            }
                        }
                    });
        } catch (Exception e) {
            Log.e(APP_TAG, "Permission setting fails.", e);
        }
    }

    private StepLoader.StepCountObserver mStepCountObserver = new StepLoader.StepCountObserver() {
        @Override
        public void onResult(int count) {
            Log.d(APP_TAG, "Step result : " + count);
            mSyncCallback.onSync(count);
        }
    };

    private ExerciseLoader.ExerciseObserver mExerciseObserver = new ExerciseLoader.ExerciseObserver() {
        @Override
        public void onResult(int count) {
            Log.d(APP_TAG, "Exercise count result : " + count);
            mSyncCallback.onSync(count);
        }
    };


}
