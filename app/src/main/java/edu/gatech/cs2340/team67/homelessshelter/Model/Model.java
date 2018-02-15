package edu.gatech.cs2340.team67.homelessshelter.Model;

import java.util.ArrayList;

/**
 * edu.gatech.cs2340.team67.homelessshelter.Model class. Using a Singleton design pattern because reasons.
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





}
