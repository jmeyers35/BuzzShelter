package edu.gatech.cs2340.team67.homelessshelter.Controllers;


import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.NavUtils;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.SearchView;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.Models.Shelter;
import edu.gatech.cs2340.team67.homelessshelter.R;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


/**
 * An activity representing a map of the Shelters.
 */

public class MapActivity extends ShelterListActivity implements OnMapReadyCallback {

    private GoogleMap map;

    private static final double EARTHRADIUS = 6366198;


    boolean mTwoPane;
    ArrayList<Shelter> filteredValues = null;
    final SimpleItemRecyclerViewAdapter mAdapter =
            new SimpleItemRecyclerViewAdapter(Model.getInstance().getShelters());





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Retrieve the content view that renders the map.
        setContentView(R.layout.activity_map);
        // Get the SupportMapFragment and request notification
        // when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);



        //Grab the Intent and the data passed through

        filteredValues = getIntent().getParcelableArrayListExtra("Shelters");


        SearchView searchView = (SearchView) findViewById(R.id.searchview1);
        // listening to search query text change
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                mAdapter.getFilter().filter(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                mAdapter.getFilter().filter(query);
                return false;
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle(getTitle());


        //Take the values from the filter, then extract those and put those on the map.


    }


    //Where the markers are going to be added
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        //Trying to center the coordinates around atlanta
        LatLng Atlanta = new LatLng(33.74, -84.38);

        //MarkerOptions atlMarker = new MarkerOptions().position(Atlanta).title("Atlanta");

        //googleMap.addMarker(atlMarker);


        //Builds the Boundary Box
        LatLngBounds.Builder builder = new LatLngBounds.Builder();
        builder.include(Atlanta);

        LatLngBounds tmpBounds = builder.build();

        LatLng center = tmpBounds.getCenter();
        LatLng northEast = move(center, 10000, 10000);
        LatLng southWest = move(center, 10000, -10000);
        builder.include(southWest);
        builder.include(northEast);

        LatLngBounds bounds = builder.build();


        map.animateCamera(CameraUpdateFactory.newLatLngBounds(bounds,0));

        //Toolbar
        googleMap.getUiSettings().setMapToolbarEnabled(false);

        //This is where the implementation, you have to update the Markers

        //Gets the current list of shelters
        if (filteredValues == null) {
            ArrayList<Shelter> filteredValues = getMValuesFiltered();
        }



        HashMap <LatLng, Integer> visibilityMap = new HashMap<>();


        //Add places to the HashMap with a visibility of 1

        for (int i = 0; i < filteredValues.size(); i++) {

            LatLng place = new LatLng(filteredValues.get(i).getLatitude(),
                    filteredValues.get(i).getLongitude());

           // visibilityMap.put(place, 1);

            map.addMarker(
                    new MarkerOptions().position(place).title(
                            filteredValues.get(i).getName()).snippet("Under Construction"));

           // map.addMarker(new MarkerOptions().position(place).visible(false));

        }



        //Create a mask that toggles the visibility of select shelters that the filter outputs

        //The method would access the hashmap and checks if the value is 1 - show | 0 - don't show



        //Marker.setVisibile(boolean)

        //Mess with the visibility settings on the


    }



    /**
     * Creates the boundaries for Boundary Box above
     * @param startLL beginning Longitude and Latitude
     * @param toNorth how far north
     * @param toEast how far east
     * @return void
     */
    private static LatLng move(LatLng startLL, double toNorth, double toEast) {
        double lonDiff = meterToLongitude(toEast, startLL.latitude);
        double latDiff = meterToLatitude(toNorth);
        return new LatLng(startLL.latitude + latDiff, startLL.longitude
                + lonDiff);
    }

    /**
     *  Calculates the Latitude Difference
     * @param meterToEast how far east
     * @param latitude latitude we're working with
     * @return calculated longitude
     */
    private static double meterToLongitude(double meterToEast, double latitude) {
        double latArc = Math.toRadians(latitude);
        double radius = Math.cos(latArc) * EARTHRADIUS;
        double rad = meterToEast / radius;
        return Math.toDegrees(rad);
    }


    /**
     * Calculates the Longitude Difference
     * @param meterToNorth
     * @return
     */
    private static double meterToLatitude(double meterToNorth) {
        double rad = meterToNorth / EARTHRADIUS;
        return Math.toDegrees(rad);
    }




}
