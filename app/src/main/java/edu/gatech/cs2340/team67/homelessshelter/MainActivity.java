package edu.gatech.cs2340.team67.homelessshelter;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;


public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }



    public void buttonLogoutCallBack(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }
}
