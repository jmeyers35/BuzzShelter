package edu.gatech.cs2340.team67.homelessshelter.Models;

import android.util.Log;

import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

    private FirebaseUser _currentUser; //logged in user

    private User _currUserInfo;

    private FirebaseDatabase _database;

    private ArrayList<Shelter> _shelters;


    private Model(){
        _users = new ArrayList<>();
        _shelters = new ArrayList<>();
        _database = FirebaseDatabase.getInstance();
    }

    public void addUser(String username, boolean isAdmin) {
        User user = new User(username, isAdmin, false);
        DatabaseReference users = _database.getReference("users");
        users.child(Integer.toString(user.getUid())).setValue(user);

    }

    public User findUserByEmail(String email) {
        for (User u: _users) {
            if (u.getUsername().equals(email)) {
                return u;
            }
        }
        throw new NoSuchElementException("Cannot find user with this email");
    }

    public void loadUsers(DataSnapshot ds) {
        if (_users.isEmpty()) {
            for (DataSnapshot data : ds.getChildren()) {
                User user = data.getValue(User.class);
                _users.add(user);
            }
        }
    }

    public void loadShelters(DataSnapshot ds) {
        if (_shelters.isEmpty()) {
            for (DataSnapshot data : ds.getChildren()) {
                Shelter shelter = data.getValue(Shelter.class);
                _shelters.add(shelter);
            }
        }
    }

    public void updateShelter(Shelter shelter) {
        DatabaseReference shelters = _database.getReference("Shelters");
        shelters.child(Integer.toString(shelter.getId())).setValue(shelter);
    }

    public void updateUser(User user) {
        DatabaseReference users = _database.getReference("users");
        users.child(Integer.toString(user.getUid())).setValue(user);
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
        throw new NoSuchElementException();
    }



    public List getUsers(){ return _users; }
    public FirebaseUser getCurrentUser() {return _currentUser; }
    public ArrayList<Shelter> getShelters() {return _shelters;}
    public void setUser(FirebaseUser user ) {
        _currentUser = user;
    }
    public void setUserInfo(User user) {
        _currUserInfo = user;
    }
    public User getCurrUserInfo() {
        return _currUserInfo;
    }


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
            String vacancy = data[2];
            String restrictions = data[3];
            double longitude = Double.parseDouble(data[4]);
            double latitude = Double.parseDouble(data[5]);
            String address = data[6];
            String phoneNumber = data[8];

            //Instantiate a new Shelter
            Shelter shelter = new Shelter(id, name, capacity, vacancy, restrictions, longitude,
                    latitude,address,phoneNumber);
           _shelters.add(shelter);

        }

    }



}
