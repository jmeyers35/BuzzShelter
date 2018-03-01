package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.app.ListActivity;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import java.io.FileNotFoundException;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.Models.Shelter;
import edu.gatech.cs2340.team67.homelessshelter.R;

public class WelcomeActivity extends ListActivity {

    private final Model model =Model.getInstance();
    private final String TAG = "Welcome_Activity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        try {
            model.readCSV(getResources().openRawResource(R.raw.shelters));
        } catch (FileNotFoundException e) {
            Log.e(TAG, "FNFException", e);
            Context context = getApplicationContext();
            CharSequence text = "There was an error reading the shelters csv!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(context, text, duration);
            toast.show();
        }
//        // TODO fix this
//        ArrayAdapter<Shelter> adapter = new ArrayAdapter<>(getListView().getContext(), android.R.layout.activity_list_item, model.getShelters());
//        getListView().setAdapter(adapter);

    }

    public void buttonLoginCallback(View view) {
        Intent intent = new Intent(this, LoginActivity.class);
        startActivity(intent);

    }

    public void buttonRegisterCallback(View view) {
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }
}
