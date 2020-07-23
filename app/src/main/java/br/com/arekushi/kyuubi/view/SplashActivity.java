package br.com.arekushi.kyuubi.view;

import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import br.com.arekushi.kyuubi.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Runnable run = new Runnable() {
            @Override
            public void run() {
                showActivity();
            }
        };

        new Handler().postDelayed(run, 2000);
    }

    private void showActivity() {
        startActivity(returnIntent());
        finish();
    }

    private Intent returnIntent() {
        if(GoogleSignIn.getLastSignedInAccount(this) != null) {
            return new Intent(this, MainActivity.class);
        } else {
            return new Intent(this, LoginActivity.class);
        }
    }
}