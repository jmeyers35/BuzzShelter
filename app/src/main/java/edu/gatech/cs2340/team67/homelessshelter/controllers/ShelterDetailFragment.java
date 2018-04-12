package edu.gatech.cs2340.team67.homelessshelter.controllers;

import android.app.Activity;
import android.support.design.widget.CollapsingToolbarLayout;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import edu.gatech.cs2340.team67.homelessshelter.models.Model;
import edu.gatech.cs2340.team67.homelessshelter.models.Shelter;
import edu.gatech.cs2340.team67.homelessshelter.R;

/**
 * A fragment representing a single Shelter detail screen.
 * This fragment is either contained in a {@link ShelterListActivity}
 * in two-pane mode (on tablets) or a {@link ShelterDetailActivity}
 * on handsets.
 */
public class ShelterDetailFragment extends Fragment {
    /**
     * The fragment argument representing the item ID that this fragment
     * represents.
     */
    public static final String ARG_ITEM_ID = "item_id";
    private final Model _model = Model.getInstance();

    /**
     * The dummy content this fragment is presenting.
     */
    private Shelter mItem;

    /**
     * Mandatory empty constructor for the fragment manager to instantiate the
     * fragment (e.g. upon screen orientation changes).
     */
    public ShelterDetailFragment() {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments().containsKey(ARG_ITEM_ID)) {
            // Load the dummy content specified by the fragment
            // arguments. In a real-world scenario, use a Loader
            // to load content from a content provider.
            mItem = _model.getShelterByName(getArguments().getString(ARG_ITEM_ID)); //GETS THE SHELTER OBJECT WE CLICKED ON

            Activity activity = this.getActivity();
            CollapsingToolbarLayout appBarLayout =  activity.findViewById(R.id.toolbar_layout);
            if (appBarLayout != null) {
                appBarLayout.setTitle(mItem.getName());
            }
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.shelter_detail, container, false);

        // Show the dummy content as text in a TextView.
        if (mItem != null) {
            ((TextView) rootView.findViewById(R.id.name)).setText(mItem.getName());
            ((TextView) rootView.findViewById(R.id.capacity)).setText("Capacity: " + mItem.getCapacity());
            ((TextView) rootView.findViewById(R.id.vacancy)).setText("Vacancy: " + mItem.getVacancy());
            ((TextView) rootView.findViewById(R.id.restrictions)).setText(mItem.getRestrictions());
            ((TextView) rootView.findViewById(R.id.longitudeLatitude)).setText(mItem.getLongitude() + ", " + mItem.getLatitude());
            ((TextView) rootView.findViewById(R.id.address)).setText(mItem.getAddress());
            ((TextView) rootView.findViewById(R.id.phoneNumber)).setText(mItem.getPhoneNumber());


            Spinner numberBedsSpinner = rootView.findViewById(R.id.vacancy_spinner);
            Integer[] spinnerValues = new Integer[Shelter.MAX_RESERVATION + 1];
            for (int i = 0; i <= Shelter.MAX_RESERVATION; i++) {
                spinnerValues[i] = i;
            }
            ArrayAdapter<Integer> spinnerAdapter = new ArrayAdapter<>(getActivity().getApplicationContext(),android.R.layout.simple_spinner_item, spinnerValues);
            spinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            numberBedsSpinner.setAdapter(spinnerAdapter);

            if (_model.getCurrentUser().hasReservation()) {
                if (_model.getCurrentUser().getReservedShelter().equals(mItem)) {
                    numberBedsSpinner.setSelection(spinnerAdapter.getPosition(_model.getCurrentUser().getReservedBedsNumber()));
                }
            }

        }


        return rootView;
    }
}
