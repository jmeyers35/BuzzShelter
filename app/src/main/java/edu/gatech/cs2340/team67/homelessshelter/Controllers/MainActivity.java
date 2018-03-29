package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;

import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.cs2340.team67.homelessshelter.R;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
    }



    public void buttonLogoutCallBack(View view) {
        mAuth.signOut();
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }

    public void buttonShelterListCallBack(View view) {
        Intent intent = new Intent(this, ShelterListActivity.class);
        startActivity(intent);

    }
    public void mapCallBack(View view) {
        Intent intent = new Intent(this, MapActivity.class);
        startActivity(intent);

    }
}
