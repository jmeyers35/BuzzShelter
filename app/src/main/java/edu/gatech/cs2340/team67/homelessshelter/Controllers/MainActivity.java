package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.R;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        model = Model.getInstance();

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

         model.getCurrUserInfo().setHasClaimedBed(false);
         model.getCurrUserInfo().setNumBedsClaimed(0);
         String updateVacancy = model.getShelterByName(getIntent().getStringExtra(ShelterDetailFragment.ARG_ITEM_ID)).getVacancy();
         long updateVacancyNum = Integer.parseInt(updateVacancy);
         updateVacancyNum = updateVacancyNum + model.getCurrUserInfo().getNumBedsClaimed();
         model.getShelterByName(getIntent().getStringExtra(ShelterDetailFragment.ARG_ITEM_ID)).setVacancy(Long.toString(updateVacancyNum));

        Toast.makeText(getBaseContext(), "All previous reservations have been cancelled"
                + " beds reserved", Toast.LENGTH_LONG).show(); //This toast doesn't show
    }



}
