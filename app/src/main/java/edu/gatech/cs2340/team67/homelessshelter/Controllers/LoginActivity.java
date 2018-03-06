package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.R;

public class LoginActivity extends AppCompatActivity {
    private static final String TAG = "LoginActiviy";
    private EditText editTextUsername;
    private EditText editTextPassword;
    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        mAuth = FirebaseAuth.getInstance();

        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);

    }

    public void buttonLoginCallback(View view) {
        Model _model = Model.getInstance();
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        signIn(email, password);
    }


    public void buttonCancelCallback(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }

    private void signIn(String email, String password) {
        Log.d(TAG, "signIn:" + email);
        if(!validateInputs()) {
            return;
        }
        final Intent intent = new Intent(this, MainActivity.class);

        // START SIGN-IN PROCESS

        mAuth.signInWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()) {
                            Log.d(TAG, "emailLogin: success");
                            startActivity(intent);
                        } else {
                            Log.d(TAG, "emailLogin: fail");
                            displayErrorToast("Authentication failed.");
                        }
                    }
                });
        // END SIGN-IN PROCESS

    }

    private boolean validateInputs() {
        boolean valid = true;
        String email = editTextUsername.getText().toString();
        if (TextUtils.isEmpty(email)) {
            editTextUsername.setError("Required.");
            valid = false;
        } else {
            editTextUsername.setError(null);
        }

        String password = editTextPassword.getText().toString();
        if (TextUtils.isEmpty(password)) {
            editTextPassword.setError("Required.");
            valid = false;
        } else {
            editTextPassword.setError(null);
        }

        return valid;
    }

    private void displayErrorToast(CharSequence cs) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, cs, duration);
        toast.show();
    }

}
