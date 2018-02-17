package edu.gatech.cs2340.team67.homelessshelter.Model;

/**
 * Class to model a User of
 * Created by jacobmeyers on 2/15/18.
 */

public class User {

    private String user;
    private String password;
    private static int id;

    public User(String user, String password) {
        this.user = user;
        this.password = password;
        id++;
    }

    public String getUserName() {
        return user;
    }

    public String getPassword() {
        return password;
    }

    public int getId() {
        return id;
    }

    public boolean equals(Object o) {
        if (o == null) {return false;}
        if (o == this) {return true;}
        if (!(o instanceof User)) {return false;}
        User that = (User) o;
        return this.user.equals(that.getUserName()) && password.equals(that.getPassword());
    }
}
