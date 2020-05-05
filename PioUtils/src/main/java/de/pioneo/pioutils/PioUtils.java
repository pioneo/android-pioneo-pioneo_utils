package de.pioneo.pioutils;

import android.content.Context;
import android.widget.Toast;

public class PioUtils {

    public static void showTestToast(Context c, String message) {

        Toast.makeText(c, message, Toast.LENGTH_SHORT).show();
    }
}
