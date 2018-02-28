package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Information-holder for shelters.
 *
 * Created by jacobmeyers on 2/22/18.
 */

public class Shelter {

    private String name;
    private String capacity;

    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;


    public Shelter(String name, String capacity, double longitude, double latitude,
                   String address, String phoneNumber) {
        this.name = name;
        this.capacity = capacity;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public String getCapacity() {
        return capacity;
    }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
