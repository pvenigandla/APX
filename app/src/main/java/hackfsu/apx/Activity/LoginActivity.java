package hackfsu.apx.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Patterns;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import hackfsu.apx.R;
import hackfsu.apx.model.PersonModel;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;

/**
 * Created by pranathi on 3/2/18.
 */

public class LoginActivity extends AppCompatActivity {


    @Override protected void onCreate (Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup_form);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        setTitle("");

        final EditText emailEditText = (EditText) findViewById(R.id.person_email);
        final EditText nameEditText = (EditText) findViewById(R.id.person_name);
        final EditText passwordEditText = (EditText) findViewById(R.id.person_password);
        final EditText heightText = (EditText) findViewById(R.id.person_height);
        final EditText weightText = (EditText) findViewById(R.id.weight);



        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick (final View view) {
                Snackbar.make(view, "Registering, please wait...", Snackbar.LENGTH_INDEFINITE).show();
                InputMethodManager imm          = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                View               currentFocus = getCurrentFocus();
                if (currentFocus != null) { imm.hideSoftInputFromWindow(currentFocus.getWindowToken(), 0); }



                String emailValue = String.valueOf(emailEditText.getText());
                String nameValue = String.valueOf(nameEditText.getText());
                String weightValue = String.valueOf(weightText.getText());
                String passwordValue = String.valueOf(passwordEditText.getText());
                String heightValue = String.valueOf(heightText.getText());



                if (!Patterns.EMAIL_ADDRESS.matcher(emailValue).matches()) {
                    Snackbar.make(view, "Please enter a valid email address", Snackbar.LENGTH_INDEFINITE).show();
                    emailEditText.requestFocus();
                    return;
                }

                if (passwordValue.length() < 6) {
                    Snackbar.make(view, "Please provide a password longer than 6 characters", Snackbar.LENGTH_INDEFINITE).show();
                    return;
                }

                final PersonModel person = new PersonModel(nameValue, emailValue, heightValue, weightValue);
                FirebaseAuth.getInstance()
                        .createUserWithEmailAndPassword(emailValue, passwordValue)
                        .addOnCompleteListener(LoginActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override public void onComplete (@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    person.saveToFirebase();
                                    FirebaseAuth.getInstance().signOut();
                                    finish();
                                }
                                else {
                                    Snackbar.make(view, "An error occurred while registering. If you have already registered, please sign in.", Snackbar.LENGTH_INDEFINITE)
                                            .setAction("Sign In", new View.OnClickListener() {
                                                @Override public void onClick (View v) {
                                                    finish();
                                                }
                                            }).show();
                                }
                            }
                        });
            }
        });
    }}
