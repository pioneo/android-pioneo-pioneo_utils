package de.pioneo.pioneo_utility;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import java.util.Date;

import lib.pioneo.samsunghealth.SamsungHealthManager;


public class MainActivity2 extends AppCompatActivity {
    TextView textView;
    SamsungHealthManager samsungHealthManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        textView = findViewById(R.id.textviewtest);
        // Create a HealthDataStore instance and set its listener
        samsungHealthManager = SamsungHealthManager.getInstance(this, new SamsungHealthManager.StepSyncListener() {
            @Override
            public void onSync(int steps) {
                textView.setText("Heutige Schritte: " + steps);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        samsungHealthManager.startService(new SamsungHealthManager.ConnectionListener() {
            @Override
            public void onConnected() {
                samsungHealthManager.getStepsFromSamsungHealth(new Date());
            }
        });


    }

    @Override
    public void onDestroy() {
        samsungHealthManager.disconnectService();
        super.onDestroy();
    }


}