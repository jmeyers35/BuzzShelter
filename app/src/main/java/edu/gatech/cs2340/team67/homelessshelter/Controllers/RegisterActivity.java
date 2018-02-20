package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.Models.User;
import edu.gatech.cs2340.team67.homelessshelter.R;

public class RegisterActivity extends AppCompatActivity {
    EditText editTextUsername;
    EditText editTextPassword;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextUsername = (EditText)findViewById(R.id.editTextUsername);
        editTextPassword = (EditText)findViewById(R.id.editTextPassword);
    }


    public void buttonRegisterCallback(View view) {
        Model _model = Model.getInstance();

        String username_input = editTextUsername.getText().toString();
        String password_input = editTextPassword.getText().toString();
        if(_model.registerUser(username_input, password_input)) {
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        } else {
            //failed registration
            Context context = getApplicationContext();
            CharSequence text = "Username already in user";
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
