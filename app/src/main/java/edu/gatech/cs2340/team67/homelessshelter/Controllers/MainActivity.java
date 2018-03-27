package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.R;

public class MainActivity extends AppCompatActivity {
    FirebaseAuth mAuth;
    Model model;
    FirebaseDatabase db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();
        model = Model.getInstance();
        db = FirebaseDatabase.getInstance();



        DatabaseReference shelters = db.getReference("shelters");
        shelters.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                model.loadShelters(dataSnapshot);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

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
        if (model.getCurrUserInfo().getShelterClaimed() == null) {
            Toast.makeText(getBaseContext(), "You don't currently have any " +
                    "beds reserved", Toast.LENGTH_LONG).show();
        } else {
            model.getCurrUserInfo().setHasClaimedBed(false);
            model.getCurrUserInfo().setNumBedsClaimed(0);
            String updateVacancy = model.getCurrUserInfo().getShelterClaimed().getVacancy();
            long updateVacancyNum = Integer.parseInt(updateVacancy);
            updateVacancyNum = updateVacancyNum + model.getCurrUserInfo().getNumBedsClaimed();
            model.getCurrUserInfo().getShelterClaimed().setVacancy(Long.toString(updateVacancyNum));
            model.updateShelter(model.getCurrUserInfo().getShelterClaimed());
            model.getCurrUserInfo().setShelterClaimed(null);
            model.updateUser(model.getCurrUserInfo());

            Toast.makeText(getBaseContext(), "All previous reservations have been cancelled"
                    + " beds reserved", Toast.LENGTH_LONG).show();
        }
    }



}
