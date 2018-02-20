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

    private User _currentUser; //#TODO: keep track of logged in user for use of his preferences

    public Model(){
        _users = new ArrayList<User>();
        _currentUser = null;

    }

    public void addUser(User user) {
        _users.add(user);

    }

    public void addUser(String username, String password) {
        _users.add(new User(username, password));

    }

    /*
    * Attempts user login
    * @param username username
    * @param password password
    * @return true is login success, false if failure
    *
    */
    public boolean loginUser(String username, String password)  {
        for (User user : _users) {
            if (user.getUsername().equals(username)) {
                if (user.getPassword().equals(password)) {
                    _currentUser = user;
                    return true;
                }
                return false;
            }
        }
        return false;
    }

    /*
    * Attempts user registration
    * @param username username
    * @param password password
    * @return true is registration success, false if failure
    *
    */
    public boolean registerUser(String username, String password)  {
        for (User user : _users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        User newUser = new User(username, password);
        addUser(newUser);
        _currentUser = newUser;

        return true;
    }

    public List getUsers(){ return _users; }
    public User getCurrentUser() {return _currentUser; }



}
