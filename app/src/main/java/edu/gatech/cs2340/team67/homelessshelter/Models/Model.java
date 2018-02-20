package edu.gatech.cs2340.team67.homelessshelter.Models;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by BCochran on 2/20/2018.
 */

public class Model {

    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** holds the list of all users */
    private List<User> _users;

    public Model(){
        _users = new ArrayList<User>();

    }

    public void addUser(User user) {
        _users.add(user);

    }

    public void addUser(String username, String password) {
        _users.add(new User(username, password));

    }

    public List getUsers(){ return _users; }



}
