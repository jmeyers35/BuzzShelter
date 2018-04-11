package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.app.ActionBar;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;
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
    private Shelter selectedShelter;
    private Model _model = Model.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shelter_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.detail_toolbar);
        setSupportActionBar(toolbar);

        selectedShelter = _model.getShelterByName(getIntent().getStringExtra(ShelterDetailFragment.ARG_ITEM_ID));

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


    /*
 * Callback for reserving beds with spinner and button
 * @param view the view
 *
 */
    public void buttonReserveCallBack(View view) {
        Spinner vacancySpinner = (Spinner) findViewById(R.id.vacancy_spinner);

        int requestedBeds = Integer.parseInt(vacancySpinner.getSelectedItem().toString());
        User currentUser = _model.getCurrentUser();

        Context context = getApplicationContext();
        CharSequence text;
        int duration = Toast.LENGTH_SHORT;

        if(requestedBeds == 0) {
            text = "You must reserve at least 1 bed";
        } else if (requestedBeds > Shelter.MAX_RESERVATION){
            text = "Please request fewer than " + Shelter.MAX_RESERVATION + " beds";
        } else {
            try {
                if (currentUser.reserveBeds(requestedBeds, selectedShelter)) {
                    text = requestedBeds + " beds reserved";
                    //update database
                    _model.updateUserDatabase(currentUser);
                    _model.updateShelterDatabase(selectedShelter);
                    //update vacancy text on screen
                    ((TextView) findViewById(R.id.vacancy)).setText("Vacancy: " + selectedShelter.getVacancy());

                } else {
                    if (currentUser.hasReservation()) {
                        text = "Please clear other reservations first";
                    } else {
                        text = "Not enough vacancy";
                    }
                }
            } catch (NumberFormatException nfe) {
                text = "Online reservations not supported here. Please call the Shelter to make a reservation";
            }
        }
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }


}
