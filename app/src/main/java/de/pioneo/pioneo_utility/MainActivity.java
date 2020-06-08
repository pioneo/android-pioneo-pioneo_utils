package de.pioneo.pioneo_utility;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import de.pioneo.pioutils.PioUtils;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        PioUtils.showTestToast(getBaseContext(), "hallo");

    }
}