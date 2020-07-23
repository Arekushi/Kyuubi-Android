package br.com.arekushi.kyuubi.view;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import br.com.arekushi.kyuubi.GoogleLogin;
import br.com.arekushi.kyuubi.R;

public class LoginActivity extends AppCompatActivity {

    private GoogleLogin login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        login = new GoogleLogin(this);

        findViewById(R.id.btnGoogleSignIn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                googleLogin();
            }
        });
    }

    public void googleLogin() {
        Intent signInIntent = login.getGoogleSignInClient().getSignInIntent();
        startActivityForResult(signInIntent, login.getRC_SIGN_IN());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == login.getRC_SIGN_IN()) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);

            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);
                assert account != null;

                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();

                Toast.makeText(this,
                        this.getString(R.string.success, account.getDisplayName()), Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(this, this.getString(R.string.error), Toast.LENGTH_LONG).show();
            }
        }
    }
}
