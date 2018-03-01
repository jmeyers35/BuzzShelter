package edu.gatech.cs2340.team67.homelessshelter.Controllers;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

import edu.gatech.cs2340.team67.homelessshelter.R;

public class SelectedShelterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_selected_shelter);

        String name = this.getIntent().getExtras().getString("name");
        String capacity = this.getIntent().getExtras().getString("capacity");
        String restrictions = this.getIntent().getExtras().getString("gender");
        Double longitude = this.getIntent().getExtras().getDouble("longitude");
        Double latitude = this.getIntent().getExtras().getDouble("latitude");
        String address = this.getIntent().getExtras().getString("address");
        String phoneNum = this.getIntent().getExtras().getString("phoneNumber");

        TextView nameTV = (TextView)findViewById(R.id.name);
        TextView capTV = (TextView)findViewById(R.id.capacity);
        TextView restrTV = (TextView)findViewById(R.id.restrictions);
        TextView latLongTV = (TextView)findViewById(R.id.latandlong);
        TextView addressTV = (TextView)findViewById(R.id.address);
        TextView phoneNumTV = (TextView)findViewById(R.id.phoneNum);

        nameTV.setText(name);
        capTV.setText("Capacity: " + capacity);
        restrTV.setText("Restrictions: " + restrictions);
        latLongTV.setText("Latitude: " + latitude + ", Longitude: " + longitude);
        addressTV.setText("Address: " + address);
        phoneNumTV.setText("Phone Number: " + phoneNum);

    }
}
