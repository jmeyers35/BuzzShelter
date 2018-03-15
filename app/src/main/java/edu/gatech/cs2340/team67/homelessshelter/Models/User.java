package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Created by BCochran on 2/20/2018.
 */

public class User {

    private String username;
    private boolean isAdmin; //true if admin, false if not
    private int spotsClaimed;

    public User() {}

    public User(String username, boolean isAdmin) {
        this.username = username;
        this.isAdmin = isAdmin;
        this.spotsClaimed = 0;

    }

    public String getUsername() { return username; }
    public boolean getAdminStatus() { return isAdmin; }
    public int getSpotsClaimed() {return spotsClaimed;}

    public void setSpotsClaimed(int claimed) {
        spotsClaimed = claimed;
    }





}
