package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;

import android.os.Bundle;
import android.content.Intent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

import edu.gatech.cs2340.team67.homelessshelter.Models.Model;
import edu.gatech.cs2340.team67.homelessshelter.Models.Shelter;
import edu.gatech.cs2340.team67.homelessshelter.R;


public class MainActivity extends AppCompatActivity {

    private Model model = Model.getInstance();

    private ArrayList<Shelter> shelters= model.getShelters();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView lv = findViewById(R.id.listView);

        ArrayAdapter<Shelter> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, shelters);
        lv.setAdapter(adapter);
        final Context context = this;

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {


            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Shelter selected = shelters.get(position);
                Intent intent = new Intent(context, SelectedShelterActivity.class);
                intent.putExtra("name", selected.getName());
                intent.putExtra("capacity", selected.getCapacity());
                intent.putExtra("gender", selected.getRestrictions());
                intent.putExtra("latitude", selected.getLatitude());
                intent.putExtra("longitude", selected.getLongitude());
                intent.putExtra("address", selected.getAddress());
                intent.putExtra("phoneNumber", selected.getPhoneNumber());

                startActivity(intent);


            }
        });
    }



    public void buttonLogoutCallBack(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);

    }
}
