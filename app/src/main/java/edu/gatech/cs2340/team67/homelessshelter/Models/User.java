package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Created by BCochran on 2/20/2018.
 */

public class User {

    private String username;
    private String password;
    private boolean isAdmin; //true if admin, false if not

    public User(String username, String password, boolean isAdmin) {
        this.username = username;
        this.password = password;
        this.isAdmin = isAdmin;

    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }
    public boolean getAdminStatus() { return isAdmin; }




}
