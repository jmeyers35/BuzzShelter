package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Created by BCochran on 2/20/2018.
 */

public class User {

    private String username;
    private static int uid;
    private boolean isAdmin; //true if admin, false if not


    public User(String username, boolean isAdmin) {
        this.username = username;
        uid++;
        this.isAdmin = isAdmin;

    }

    public String getUsername() { return username; }
    public boolean getAdminStatus() { return isAdmin; }






}
