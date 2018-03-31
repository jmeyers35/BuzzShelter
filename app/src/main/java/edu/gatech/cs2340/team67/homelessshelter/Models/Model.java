package edu.gatech.cs2340.team67.homelessshelter.Models;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;

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


    private User _currentUser;

    private ArrayList<Shelter> _shelters;

    private Model(){
        _users = new ArrayList<>();
        _currentUser = null;
        _shelters = new ArrayList<>();

    }

    public void addUser(User user) {
        _users.add(user);

    }

    public void addUser(String username, boolean isAdmin) {
        _users.add(new User(username,isAdmin));

    }


    /*
   * Looks for Shelter by name
   * @param name Shelter's name
   * @return Shelter object with name, or "error" shelter if it does not exist
   *
   */
    public Shelter getShelterByName(String name) {
        for(Shelter s : _shelters) {
            if (s.getName().equals(name)){

                return s;
            }
        }
        //#TODO: make this throw error if no shelter found!
        return new Shelter(0, "error", "","",0,0,"", "" ); //blank shelter
    }



    public List getUsers(){ return _users; }
    public ArrayList<Shelter> getShelters() {return _shelters;}
    public void setUser(FirebaseUser user ) {
        _currentUser = user;
    }
    public User getCurrentUser() { return _currentUser; }



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
            int id = Integer.parseInt(data[0]);
            String name = data[1];
            String capacity = data[2];
            String restrictions = data[3];
            double longitude = Double.parseDouble(data[4]);
            double latitude = Double.parseDouble(data[5]);
            String address = data[6];
            String phoneNumber = data[8];

            //Instantiate a new Shelter
            Shelter shelter = new Shelter(id, name, capacity, restrictions, longitude,
                    latitude,address,phoneNumber);
            _shelters.add(shelter);

        }

    }



}
