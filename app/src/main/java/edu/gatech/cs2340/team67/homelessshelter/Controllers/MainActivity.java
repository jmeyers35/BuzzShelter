package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.R;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Model modelUser;
    Model modelShelter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        modelUser = Model.getInstance();
        modelShelter = Model.getInstance();
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

    public void buttonResCallBack(View view) {
        //This will crash-----------
        Log.d("aaa", "user: " + modelUser.getCurrUserInfo());
        long oldReservations = modelUser.getCurrUserInfo().getNumBedsClaimed();
        modelUser.getCurrUserInfo().setHasClaimedBed(false);
        modelUser.getCurrUserInfo().setNumBedsClaimed(0);
        Log.d("aaa", "shelter: " + modelShelter.get_currentShelterInfo());
        String updateVacancy = modelShelter.get_currentShelterInfo().getVacancy();
        long updateVacancyNum = Integer.parseInt(updateVacancy);
        updateVacancyNum = updateVacancyNum + Math.abs(oldReservations);
        modelShelter.get_currentShelterInfo().setVacancy(Long.toString(updateVacancyNum));
        Toast.makeText(getBaseContext(), "All previous reservations have been cancelled"
                + " beds reserved", Toast.LENGTH_LONG).show();
    }

}
