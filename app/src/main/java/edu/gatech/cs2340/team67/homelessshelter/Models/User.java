package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Created by BCochran on 2/20/2018.
 */

public class User {

    private String username;
    private String password;

    public User(String username, String password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() { return username; }
    public String getPassword() { return password; }




}
