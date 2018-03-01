package edu.gatech.cs2340.team67.homelessshelter.Models;

import android.util.Log;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.InputStream;
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

    }

    public void addUser(User user) {
        _users.add(user);

    }

    public void addUser(String username, String password, boolean isAdmin) {
        _users.add(new User(username, password, isAdmin));

    }


    /*
   * Looks for Shelter by name
   * @param name Shelter's name
   * @return Shelter object with name, or null if not a shelter //#TODO FIND A BETTER WAY THAN NULL RETURN
   *
   */
    public Shelter getShelterByName(String name) {
        for(Shelter s : _shelters) {
            if (s.getName().equals(name)){
                return s;
            }
        }
        return null;
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
        if (username.length() > 10 ||
                username.length() < 1 ||
                password.length() > 10 ||
                password.length() < 1) {
            return false;
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
     * @throws FileNotFoundException If we can't find the file we want to read.
     */
    public void readCSV(InputStream csv) throws FileNotFoundException {

        Scanner scanner = new Scanner(csv);
        scanner.nextLine(); // Skip header
        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            String[] data = line.split(",");

            // Get data from the split line

            String name = data[1];
            String capacity = data[2];
            String restrictions = data[3];
            double longitude = Double.parseDouble(data[4]);
            double latitude = Double.parseDouble(data[5]);
            String address = data[6];
            String phoneNumber = data[8];

            //Instantiate a new Shelter
            Shelter shelter = new Shelter(name,capacity, restrictions, longitude,
                    latitude,address,phoneNumber);
            _shelters.add(shelter);

        }

    }



}
