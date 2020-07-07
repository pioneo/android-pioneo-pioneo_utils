
package lib.pioneo.samsunghealth;

import android.util.Log;

import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

public class ExerciseLoader {
    private final HealthDataStore mStore;
    private ExerciseObserver mExerciseObserver;
    private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000L;
    final String APP_TAG = "ExerciseLoader";
    private Date date;

    public ExerciseLoader(HealthDataStore store, Date date) {
        mStore = store;
        this.date = date;
    }

    public void startObserver(ExerciseObserver listener) {
        mExerciseObserver = listener;
        // Register an observer to listen changes of step count and get today step count
        HealthDataObserver.addObserver(mStore, HealthConstants.StepCount.HEALTH_DATA_TYPE, mObserver);
        readExerciseCount();
    }


    private void readExerciseCount() {
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);

        long startTime = getStartTimeOfDate(date);
        long endTime = startTime + ONE_DAY_IN_MILLIS;

        HealthDataResolver.ReadRequest request = new HealthDataResolver.ReadRequest.Builder()
                .setDataType(HealthConstants.Exercise.HEALTH_DATA_TYPE)
                .setProperties(new String[]{HealthConstants.Exercise.COUNT})
                .setLocalTimeRange(HealthConstants.Exercise.START_TIME, HealthConstants.Exercise.TIME_OFFSET,
                        startTime, endTime)
                .build();

        try {
            resolver.read(request).setResultListener(mListener);
        } catch (Exception e) {
            Log.e(APP_TAG, "Getting step count fails.", e);
        }
    }


    private long getStartTimeOfDate(Date date) {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        today.setTime(date);
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTimeInMillis();
    }

    private final HealthResultHolder.ResultListener<HealthDataResolver.ReadResult> mListener = result -> {
        int count = 0;

        try {
            for (HealthData data : result) {
                count += data.getInt(HealthConstants.Exercise.COUNT);
            }
        } finally {
            result.close();
        }

        if (mExerciseObserver != null) {
            mExerciseObserver.onResult(count);
        }
    };

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        // Update the exercise count when a change event is received
        @Override
        public void onChange(String dataTypeName) {
            Log.d(APP_TAG, "Observer receives a data changed event");
            readExerciseCount();
        }
    };

    public interface ExerciseObserver {
        void onResult(int count);
    }
}
