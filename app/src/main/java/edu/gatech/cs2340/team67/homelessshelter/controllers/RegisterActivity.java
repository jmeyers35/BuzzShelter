package edu.gatech.cs2340.team67.homelessshelter.controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;

import edu.gatech.cs2340.team67.homelessshelter.models.Model;
import edu.gatech.cs2340.team67.homelessshelter.models.User;
import edu.gatech.cs2340.team67.homelessshelter.R;

public class RegisterActivity extends AppCompatActivity {
    private final static String TAG = "RegisterActivity";
    private EditText editTextUsername;
    private EditText editTextPassword;
    private CheckBox checkBoxAdminStatus;
    private FirebaseAuth mAuth;
    private final Model model = Model.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        mAuth = FirebaseAuth.getInstance();

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
        checkBoxAdminStatus = findViewById(R.id.checkBoxAdminStatus);
    }


    public void buttonRegisterCallback(View view) {
        String email = editTextUsername.getText().toString();
        String password = editTextPassword.getText().toString();
        boolean isAdmin = checkBoxAdminStatus.isChecked();
        createAccount(email, password, isAdmin);
    }

    public void buttonCancelCallback(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }

    private void createAccount(String email, String password, boolean isAdmin) {
        Log.d(TAG, "createAccount:" + email);
        if (!validateInputs()) {
            return;
        }
        final String femail = email;
        final boolean fisAdmin = isAdmin;

        final Intent intent = new Intent(this, MainActivity.class);

        mAuth.createUserWithEmailAndPassword(email,password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            Log.d(TAG, "createUserWithEmail: success");
                            showSuccessMessage();
                            model.setCurrentUser(new User(femail, fisAdmin));
                            model.addUser(model.getCurrentUser());
                            startActivity(intent);
                        }
                    }
                }).addOnFailureListener(this, new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "createUserWithEmail: fail", e);
                if (e instanceof FirebaseAuthWeakPasswordException) {
                    displayErrorToast(((FirebaseAuthWeakPasswordException) e).getReason());
                } else {
                    displayErrorToast("Sorry, something went wrong " +
                            "during registration. Try again.");
                }
            }
        });


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

    private void showSuccessMessage() {
        Context context = getApplicationContext();
        CharSequence message = "Success! You've been registered. You'll now be logged-in.";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, message, duration);
        toast.show();
    }

    private void displayErrorToast(CharSequence cs) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;

        Toast toast = Toast.makeText(context, cs, duration);
        toast.show();
    }


}
