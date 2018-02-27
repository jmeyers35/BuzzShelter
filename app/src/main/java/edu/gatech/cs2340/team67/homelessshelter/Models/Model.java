package edu.gatech.cs2340.team67.homelessshelter.Models;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;




/**
 * Created by BCochran on 2/20/2018.
 */

public class Model {

    /** Singleton instance */
    private static final Model _instance = new Model();
    public static Model getInstance() { return _instance; }

    /** holds the list of all users */
    private List<User> _users;

    private User _currentUser; //logged in user

    private ArrayList<Shelter> _shelters;

    public Model(){
        _users = new ArrayList<>();
        _currentUser = null;
        _shelters = new ArrayList<>();
        File shelters = new File("../../../res/raw/shelters.csv");
        try {
            readCSV(shelters);
        } catch (FileNotFoundException e) {
            // Do stuff
            Log.e("FNF", "FileNotFoundException thrown while reading " +
                    "CSV", e);

        }



    }

    public void addUser(User user) {
        _users.add(user);

    }

    public void addUser(String username, String password, boolean isAdmin) {
        _users.add(new User(username, password, isAdmin));

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
    * @param isAdmin administrative status
    * @return true is registration success, false if failure
    *
    */
    public boolean registerUser(String username, String password, boolean isAdmin)  {
        for (User user : _users) {
            if (user.getUsername().equals(username)) {
                return false;
            }
        }
        User newUser = new User(username, password, isAdmin);
        addUser(newUser);
        _currentUser = newUser;

        return true;
    }

    public List getUsers(){ return _users; }
    public User getCurrentUser() {return _currentUser; }
    public ArrayList<Shelter> getShelters() {return _shelters;}


    /**
     * Attempts to read CSV file via a Scanner instance and create a Shelter instance
     * for each line we find. We store all these Shelters inside _shelters, an
     * ArrayList.
     *
     * @param file File to be read.
     * @throws FileNotFoundException If we can't find the file we want to read.
     */
    private void readCSV(File file) throws FileNotFoundException {

        Scanner scanner = new Scanner(file);
        scanner.nextLine(); // Skip header
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");

            // Get data from the split line

            String name = data[1];
            int capacity = Integer.parseInt(data[2]);
            String restrictions = data[3];
            double longitude = Double.parseDouble(data[4]);
            double latitude = Double.parseDouble(data[5]);
            String address = data[6];
            String specialNotes = data[7];
            String phoneNumber = data[8];

            //Instantiate a new Shelter
            Shelter shelter = new Shelter(name,capacity,longitude,
                    latitude,address,restrictions,specialNotes,phoneNumber);
            _shelters.add(shelter);

        }

    }



}
