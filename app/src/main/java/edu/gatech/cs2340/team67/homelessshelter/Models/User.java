package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Created by BCochran on 2/20/2018.
 */

public class User {

    private String username;
    private static int uid;
    private boolean isAdmin; //true if admin, false if not
    private boolean hasClaimedBed; //true if user has claimed a bed, false otherwise
    private long numBedsClaimed;


    public User(String username, boolean isAdmin, boolean hasClaimedBed, long numBedsClaimed) {
        this.username = username;
        uid++;
        this.isAdmin = isAdmin;
        this.hasClaimedBed = hasClaimedBed;
        this.numBedsClaimed = numBedsClaimed;
    }

    public String getUsername() { return username; }
    public boolean getAdminStatus() { return isAdmin; }
    public boolean getHasClaimedBed() { return hasClaimedBed; }
    public void setHasClaimedBed(boolean hasClaimedBed) {
        this.hasClaimedBed = hasClaimedBed;
    }

    public long getNumBedsClaimed() {
        return numBedsClaimed;
    }

    public void setNumBedsClaimed(long numBedsClaimed) {
        this.numBedsClaimed = numBedsClaimed;
    }
}
