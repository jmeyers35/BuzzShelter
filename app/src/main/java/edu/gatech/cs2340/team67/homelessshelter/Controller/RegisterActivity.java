package edu.gatech.cs2340.team67.homelessshelter.Controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import edu.gatech.cs2340.team67.homelessshelter.Model.Model;
import edu.gatech.cs2340.team67.homelessshelter.R;

public class RegisterActivity extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private final Model model = Model.getInstance();
    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        username = findViewById(R.id.editTextUsername);
        password = findViewById(R.id.editTextPassword);

    }




    public void buttonRegisterCallback(View view) {
        //#TODO: Code here that would add info from user and pass fields to User model.
        String user = username.getText().toString();
        String pass = password.getText().toString();
        model.addUser(user,pass);
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);


    }

    public void buttonCancelCallback(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }


}
