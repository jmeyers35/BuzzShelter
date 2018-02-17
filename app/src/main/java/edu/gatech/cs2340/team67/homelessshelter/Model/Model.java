package edu.gatech.cs2340.team67.homelessshelter.Model;

import java.util.ArrayList;

/**
 * edu.gatech.cs2340.team67.homelessshelter
 *
 * Model class. Using a Singleton so all the Controller elements have access.
 *
 * @author Jacob Meyers
 *
 *
 * Created by jacobmeyers on 2/15/18.
 */

public class Model {

    private static final Model instance = new Model();

    private ArrayList<User> users;


    public static Model getInstance() {
        return instance;
    }

    private Model() {
        users = new ArrayList<>();
    }


    public void addUser(String user, String pass) {
        User newUser = new User(user, pass);
        users.add(newUser);
        System.out.println("Users: " + users.size());

    }

    public boolean validateUser(String user, String pass) {
        for (User u : users) {
            if(u.getUserName().equals(user) && u.getPassword().equals(pass)) {
                return true;
            }
        }
        return false;
    }


}
