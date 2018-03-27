package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Information-holder for shelters.
 *
 * Created by jacobmeyers on 2/22/18.
 */

public class Shelter {

    private int id;
    private String name;
    private String capacity;
    private String vacancy;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;


    public Shelter() {}

    public Shelter(int id, String name, String capacity, String vacancy, String restrictions, double longitude, double latitude,
                   String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.vacancy = vacancy;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getCapacity() {
        return capacity;
    }

    public String getVacancy() {
        return vacancy;
    }

    public void setVacancy(String vacancy) {
        this.vacancy = vacancy;
    }

    public String getRestrictions() { return  restrictions; }

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
