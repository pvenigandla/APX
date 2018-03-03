package hackfsu.apx.Activity;

/**
 * Created by pranathi on 3/3/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import java.util.ArrayList;

import hackfsu.apx.R;

import hackfsu.apx.listener.SimpleListValueListener;
import hackfsu.apx.model.PersonModel;
import hackfsu.apx.utils.FirebaseUtils;
import hackfsu.apx.utils.StringUtils;

public class MainActivity extends AppCompatActivity {
    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.hamburger_main);

        final TextView signUpButton     = (TextView) findViewById(R.id.signin_signup_button);
        final TextView signInButton     = (TextView) findViewById(R.id.signin_signin_button);
        final EditText emailEditText    = (EditText) findViewById(R.id.signin_email_field);
        final EditText passwordEditText = (EditText) findViewById(R.id.signin_password_field);



        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            openVisualization();
            Toast.makeText(this, String.format("Signed in as %s", currentUser.getEmail()), Toast.LENGTH_LONG).show();
        }

        signUpButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick (View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
            }
        });
        signInButton.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick (final View v) {
                final String emailValue    = String.valueOf(emailEditText.getText());
                String       passwordValue = String.valueOf(passwordEditText.getText());

                if (StringUtils.isEmptyOrNull(emailValue) || StringUtils.isEmptyOrNull(passwordValue)) {
                    Snackbar.make(v, "Please fill out all of the fields", Snackbar.LENGTH_INDEFINITE).show();
                    return;
                }

                FirebaseAuth.getInstance()
                        .signInWithEmailAndPassword(emailValue, passwordValue)
                        .addOnCompleteListener(MainActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override public void onComplete (@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    openVisualization();
                                }
                                else {
                                    Snackbar.make(v, "An error occurred while signing in. Please verify your credentials.", Snackbar.LENGTH_INDEFINITE)
                                            .setAction("Forgot Password", new View.OnClickListener() {
                                                @Override public void onClick (View v) {
                                                    FirebaseAuth.getInstance().sendPasswordResetEmail(emailValue);
                                                }
                                            })
                                            .show();
                                }
                            }
                        });
            }
        });

        FirebaseUtils.addValueListener("persons", new SimpleListValueListener<PersonModel>(PersonModel.class) {
            @Override public void onValueRetrieved (ArrayList<PersonModel> personModels) {
                Snackbar.make(signUpButton, String.format("Person models received: %d", personModels.size()), Snackbar.LENGTH_INDEFINITE).show();
            }
        });
    }

    private void openVisualization () {startActivity(new Intent(MainActivity.this, HomeActivity.class));}

}