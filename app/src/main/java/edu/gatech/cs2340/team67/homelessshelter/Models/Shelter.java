package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Information-holder for shelters.
 *
 * Created by jacobmeyers on 2/22/18.
 */

public class Shelter {

    private String name;
    private int capacity;
    private double longitude;
    private double latitude;
    private String address;
    private String restrictions;
    private String specialNotes;
    private String phoneNumber;


    public Shelter(String name, int capacity, double longitude, double latitude,
                   String address, String restrictions, String specialNotes, String phoneNumber) {
        this.name = name;
        this.capacity = capacity;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.restrictions = restrictions;
        this.specialNotes = specialNotes;
        this.phoneNumber = phoneNumber;
    }

    public String getName() {
        return name;
    }

    public int getCapacity() {
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

    public String getRestrictions() {
        return restrictions;
    }

    public String getSpecialNotes() {
        return specialNotes;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }
}
