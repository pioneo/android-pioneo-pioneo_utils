package de.pioneo.pioneo_utility;

import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import java.util.Set;

import de.pioneo.pioutils.PioUtils;
import de.pioneo.pioutils.fitbit.authentication.AuthenticationManager;
import de.pioneo.pioutils.fitbit.authentication.AuthenticationResult;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onResume() {
        super.onResume();

        if (AuthenticationManager.isLoggedIn()) {
            onLoggedIn();
        }
    }

    public void onLoggedIn() {
        Toast.makeText(this, "erfolgreich eingeloggt", Toast.LENGTH_SHORT).show();
    }

    public void onLoginClick(View view) {
        /**
         *  3. Call login to show the login UI
         */
        AuthenticationManager.login(this);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        /**
         *  4. When the Login UI finishes, it will invoke the `onActivityResult` of this activity.
         *  We call `AuthenticationManager.onActivityResult` and set ourselves as a login listener
         *  (via AuthenticationHandler) to check to see if this result was a login result. If the
         *  result code matches login, the AuthenticationManager will process the login request,
         *  and invoke our `onAuthFinished` method.
         *
         *  If the result code was not a login result code, then `onActivityResult` will return
         *  false, and we can handle other onActivityResult result codes.
         *
         */

        if (!AuthenticationManager.onActivityResult(requestCode, resultCode, data, this)) {
            // Handle other activity results, if needed
        }

    }

    public void onAuthFinished(AuthenticationResult authenticationResult) {

        /**
         * 5. Now we can parse the auth response! If the auth was successful, we can continue onto
         *      the next activity. Otherwise, we display a generic error message here
         */
        if (authenticationResult.isSuccessful()) {
            onLoggedIn();
        } else {
            Toast.makeText(this, "Fehler???", Toast.LENGTH_SHORT).show();
        }
    }

}