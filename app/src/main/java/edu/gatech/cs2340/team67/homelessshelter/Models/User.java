package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Created by BCochran on 2/20/2018.
 */

public class User {

    private String username;
    private static int uid;
    private boolean isAdmin; //true if admin, false if not
    private boolean hasClaimedBed; //true if user has claimed a bed, false otherwise


    public User(String username, boolean isAdmin, boolean hasClaimedBed) {
        this.username = username;
        uid++;
        this.isAdmin = isAdmin;
        this.hasClaimedBed = hasClaimedBed;

    }

    public String getUsername() { return username; }
    public boolean getAdminStatus() { return isAdmin; }
    public boolean getHasClaimedBed() { return hasClaimedBed; }
    public void setHasClaimedBed(boolean hasClaimedBed) {
        this.hasClaimedBed = hasClaimedBed;
    }
}
