package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.Models.Shelter;
import edu.gatech.cs2340.team67.homelessshelter.Models.User;
import edu.gatech.cs2340.team67.homelessshelter.R;

/**
 * An activity representing a single Shelter detail screen. This
 * activity is only used on narrow width devices. On tablet-size devices,
 * item details are presented side-by-side with a list of items
 * in a {@link ShelterListActivity}.
 */
public class ShelterDetailActivity extends AppCompatActivity {

    /**
     *
     */
    public static final String ARG_ITEM_ID = "item_id";
    private final Model _model = Model.getInstance();

    /**
     * Represents the shelter the user is viewing.
     */
    private Shelter thisShelter;

    /**
     * Represents the user.
     */
    private Model thisUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        Toolbar toolbar = findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);
        thisUser = Model.getInstance();
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own detail action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        // Show the Up button in the action bar.
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            // Create the detail fragment and add it to the activity
            // using a fragment transaction.
            Bundle arguments = new Bundle();
            arguments.putString(ShelterDetailFragment.ARG_ITEM_ID,
                    getIntent().getStringExtra(ShelterDetailFragment.ARG_ITEM_ID));
            ShelterDetailFragment fragment = new ShelterDetailFragment();
            fragment.setArguments(arguments);
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.shelter_detail_container, fragment)
                    .commit();
        }
        Log.d("aaa", "getting spinner");

        Spinner numBeds = findViewById(R.id.numBeds);
        // Create array adapter using string array and a default spinner layout
        ArrayAdapter<CharSequence> bedsAdapter = ArrayAdapter.createFromResource(this,
                R.array.numBeds_array, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the number of beds appears
        bedsAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        numBeds.setAdapter(bedsAdapter);
        Log.d("aaa", "setting the listyrener");
        numBeds.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                // Gets the shelter object that the user clicked on
                // This causes errors -------------------------------------------------------------
                Log.d("aaa", "In spinner handler");
                thisShelter = _model.getShelterByName(getIntent().getStringExtra(ShelterDetailFragment.ARG_ITEM_ID));
                if (thisShelter.getVacancy().equals("none given")) {
                    Toast.makeText(getBaseContext(), "There is no information " +
                            "available for this shelter. Please visit the shelter to" +
                            "stay here.", Toast.LENGTH_LONG).show();
                } else if (thisShelter.getVacancy().split(" ").length > 1) {
                    Toast.makeText(getBaseContext(), "This shelter has different capacites" +
                                    "for different situations. Please visit them to " +
                                    "make a reservation.",
                            Toast.LENGTH_LONG).show();
                } else {


                    int number = Integer.parseInt(adapterView.getItemAtPosition(i).toString());
                    if (number > Integer.parseInt(thisShelter.getVacancy())) {
                        Toast.makeText(getBaseContext(), "Not enough beds at this shelter",
                                Toast.LENGTH_LONG).show();
                    }
                    Log.d("aaa", "model:" + thisUser);
                    Log.d("aaa", "user:" + thisUser.getCurrUserInfo());
                    if (thisUser.getCurrUserInfo().getNumBedsClaimed() != 0) {
                        Toast.makeText(getBaseContext(), "Please cancel your previous "
                                        + "reservations if you wish to reserve a bed at this shelter.",
                                Toast.LENGTH_LONG).show();
                    } else {
                        thisUser.getCurrUserInfo().setHasClaimedBed(true);
                        thisUser.updateUser(thisUser.getCurrUserInfo());
                        int oldVacancy = Integer.parseInt(thisShelter.getVacancy());
                        long newVacancy = oldVacancy - adapterView.getItemIdAtPosition(i);
                        if (Integer.parseInt(thisShelter.getVacancy()) > 0) {
                            thisShelter.setVacancy(Long.toString(newVacancy));
                            thisUser.getCurrUserInfo().setNumBedsClaimed(newVacancy - oldVacancy);
                            Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i)
                                    + " beds reserved", Toast.LENGTH_LONG).show();
                        }
                        thisUser.getCurrUserInfo().setShelterClaimed(thisShelter);
                        thisUser.updateShelter(thisShelter);
                        thisUser.updateUser(thisUser.getCurrUserInfo());
//                    Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i)
//                            + " beds reserved", Toast.LENGTH_LONG).show();
                    }
                    //---------------------------------------------------------------------------------

//                Toast.makeText(getBaseContext(), adapterView.getItemAtPosition(i)
//                        + " beds reserved", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == android.R.id.home) {
            // This ID represents the Home or Up button. In the case of this
            // activity, the Up button is shown. For
            // more details, see the Navigation pattern on Android Design:
            //
            // http://developer.android.com/design/patterns/navigation.html#up-vs-back
            //
            navigateUpTo(new Intent(this, ShelterListActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


}
