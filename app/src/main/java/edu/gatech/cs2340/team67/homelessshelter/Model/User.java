package edu.gatech.cs2340.team67.homelessshelter.Model;

/**
 * Class to model a User of
 * Created by jacobmeyers on 2/15/18.
 */

public class User {

    private String user;
    private String password;

    public User(String user, String password) {
        this.user = user;
        this.password = password;
    }

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}
