package edu.gatech.cs2340.team67.homelessshelter.Controller;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.gatech.cs2340.team67.homelessshelter.Model.Model;
import edu.gatech.cs2340.team67.homelessshelter.R;

public class LoginActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;
    private final Model model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        editTextUsername = findViewById(R.id.editTextUsername);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void buttonLoginCallback(View view) {
        String user = editTextUsername.getText().toString();
        String pass = editTextPassword.getText().toString();


        if (model.validateUser(user,pass)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            Context context = getApplicationContext();
            CharSequence text = "Failed Login!";
            int duration = Toast.LENGTH_SHORT;

            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }

    }

    public void buttonCancelCallback(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }

}
