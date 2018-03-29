package edu.gatech.cs2340.team67.homelessshelter.Controllers;


import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.android.gms.maps.model.MarkerOptions;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.Models.Shelter;
import edu.gatech.cs2340.team67.homelessshelter.R;

import java.util.ArrayList;


/**
 * An activity representing a map of the Shelters.
 */

public class MapActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap map;
    private static final double EARTHRADIUS = 6366198;


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

    }


    //Where the markers are going to be added
    @Override
    public void onMapReady(GoogleMap googleMap) {
        map = googleMap;

        // Add a marker in Sydney, Australia, and move the camera.
        LatLng Atlanta = new LatLng(33.74, -84.38);

        map.addMarker(new MarkerOptions().position(Atlanta).title("Atlanta"));

        //TODO: Add Boundary Box or initial zoom area

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

        //Gets the current list of shelters
        ArrayList<Shelter> sheltersArray = Model.getInstance().getShelters();

        for (int i = 0; i < sheltersArray.size(); i++) {

            LatLng place = new LatLng(sheltersArray.get(i).getLatitude(),
                    sheltersArray.get(i).getLongitude());
            map.addMarker(
                    new MarkerOptions().position(place).title("Marker in "
                    + sheltersArray.get(i).getName()).snippet("Under Construction"));

        }


    }

    /**
     * Creates the boundaries for Boundary Box above
     * @param startLL
     * @param toNorth how far north
     * @param toEast how far east
     * @return
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
