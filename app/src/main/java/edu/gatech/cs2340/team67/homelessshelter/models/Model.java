package edu.gatech.cs2340.team67.homelessshelter.models;


import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Scanner;




/**
 * Created by BCochran on 2/20/2018.
 */

public final class Model {

    /** Singleton instance */
    private static final Model _instance = new Model();

    /**
     * Getter for the (only) instance of Model.
     *
     * @return The instance of Model.
     */
    public static Model getInstance() { return _instance; }

    /** holds the list of all users */
    private final List<User> _users;
    private User _currentUser;  //This will be changing with future features, so should not be final
    private final List<Shelter> _shelters; //This will be changing with future features, so should not be final
    private final FirebaseDatabase _database;

    private Model(){
        _users = new ArrayList<>();
        _shelters = new ArrayList<>();
        _database = FirebaseDatabase.getInstance();

    }

    /**
     * Adds a new user to the database and the local list of Users.
     *
     * @param user User instance to be added.
     */
    public void addUser(User user) {
        DatabaseReference dbUsers = _database.getReference("users");
        dbUsers.child(Integer.toString(user.getUid())).setValue(user);
        _users.add(user);

    }



    /**
     * Searches for a Shelter in the list of Shelters with a name matching the passed-in name.
     * Throws a NoSuchElementException if there is no Shelter in _shelters with this name.
     *
     * @param name Name of the shelter to search for.
     * @return The Shelter instance with a name matching the
     */
    public Shelter getShelterByName(String name) {
        for(Shelter s : _shelters) {
            if (s.getName().equals(name)){

                return s;
            }
        }
        throw new NoSuchElementException("Shelter: " + name + " not in Model");
    }




    /**
     * Getter for the list of shelters that we have information for.
     *
     * @return A List of shelters we have info for.
     */
    public List<Shelter> getShelters() {return _shelters;}

    /**
     * Sets the current user to the passed-in user. Used at login.
     *
     * @param user User instance to represent the currently logged-in user.
     */
    public void setCurrentUser(User user ) {
        _currentUser = user;
    }

    /**
     * Getter for the currently logged-in user.
     *
     * @return User instance that represents the currently logged-in user.
     */
    public User getCurrentUser() { return _currentUser; }

    /**
     * Searches the list of registered users for a User instance with an email that
     * matches the passed-in email. Throws a NoSuchElementException if there is no user
     * with this email in the list of Users.
     *
     * @param email Email of the user we want to find.
     * @return The User instance with the passed-in email.
     */
    public User findUserByEmail(String email) {
        for (User u: _users) {
            if (u.getUsername() == null) {
                continue;
            }
            if (u.getUsername().equals(email)) {
                return u;
            }
        }
        throw new NoSuchElementException("Cannot find user with this email");
    }

    /**
      * Loads users from database. This is done by taking in a DataSnapshot,
      * iterating over each child, and adding the corresponding User instance to _users.
      * @param ds Iterable representation of the current database.
      */
    public void loadUsers(DataSnapshot ds) {
        if (_users.isEmpty()) {
            for (DataSnapshot data : ds.getChildren()) {
                User user = data.getValue(User.class);
                _users.add(user);
            }
        }
    }

    /**
      * Loads shelters from database. This is done by taking in a DataSnapshot,
      * iterating over each child, and adding the corresponding Shelter instance to _shelters.
      * @param ds Iterable representation of the current database.
      *
      */
    public void loadShelters(DataSnapshot ds) {
        if (_shelters.isEmpty()) {
            for (DataSnapshot data : ds.getChildren()) {
                Shelter shelter = data.getValue(Shelter.class);
                _shelters.add(shelter);
            }
        }
    }

    /**
     * Updates a Shelter in the database. This is called whenever a change is made
     * locally to the state of the Shelter, e.g. a user makes a reservation at a shelter.
     * @param shelter Shelter instance corresponding with the Shelter to be updated in the database.
     *
     */
    public void updateShelterDatabase(Shelter shelter) {
        DatabaseReference shelters = _database.getReference("shelters");
        shelters.child(Integer.toString(shelter.getId())).setValue(shelter);
    }

    /**
     * Updates a User in database. This is called whenever a change is made locally to the state of
     * the User, e.g. the user decides to cancel their reservations.
     * @param user User reference corresponding with the User to be updated in the database.
     *
     */
    public void updateUserDatabase(User user) {
        DatabaseReference users = _database.getReference("users");
        users.child(Integer.toString(user.getUid())).setValue(user);
    }



    /**
     * Attempts to read CSV file via a Scanner instance and create a Shelter instance
     * for each line we find. We store all these Shelters inside _shelters, an
     * ArrayList.
     *
     * @param csv the csv InputStream to read
     */
    public void readCSV(InputStream csv) {

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
