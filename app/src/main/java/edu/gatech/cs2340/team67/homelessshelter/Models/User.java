package edu.gatech.cs2340.team67.homelessshelter.Models;

/**
 * Created by BCochran on 2/20/2018.
 */

public class User {

    private String username;
    private static int UID;
    private int uid;
    private boolean isAdmin; //true if admin, false if not
    private Shelter reservedShelter;
    private int reservedBedsNumber;

    public User() {} //done for the firebaseDatabase

    public User(String username, boolean isAdmin) {
        this.username = username;
        this.uid = UID++;
        this.isAdmin = isAdmin;

    }

    /**
     * Reserve beds at a shelter
     * @param requestedBeds number of beds to reserve
     * @param shelter Shelter at which to reserve beds
     * @return true if successful, false if user already has reservations
     * @throws java.lang.NumberFormatException throws nfe if shelter doesn't allow reservations
     *
     */
    public boolean reserveBeds(int requestedBeds, Shelter shelter) throws NumberFormatException {
        //#TODO: does not need to throw exception when capacity is set to INTEGER
        if (!hasReservation()) {
            if (shelter.reserveVacancy(requestedBeds)) {
                reservedShelter = shelter;
                reservedBedsNumber = requestedBeds;
                Model.getInstance().updateUserDatabase(this); //update database
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /**
    * Clear user's reservations
    *
    */
    public void clearReservations() {
        if (hasReservation()) {
            reservedShelter.clearReservation(reservedBedsNumber);
            reservedBedsNumber = 0;
            reservedShelter = null; //This assignment to null necessary to clear the User's reservation
            Model.getInstance().updateUserDatabase(this); // update database
        }
    }

    public String getUsername() { return username; }
    public boolean getAdminStatus() { return isAdmin; }
    public Shelter getReservedShelter() { return reservedShelter; }
    public int getReservedBedsNumber() { return reservedBedsNumber; }
    public boolean hasReservation() { return reservedBedsNumber > 0; }
    public int getUid() { return uid; }


    //setters for firebase db
    public void setUsername(String username) { this.username = username; }
    public void setUid(int id) { this.uid = id; }
    public void setAdminStatus(boolean isAdmin) { this.isAdmin = isAdmin; }
    public void setReservedShelter(Shelter shelter) { this.reservedShelter = shelter; }
    public void setReservedBedsNumber(int numberOfBeds) { this.reservedBedsNumber = numberOfBeds; }

    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }
        if (o == this) {
            return true;
        }
        if (!(o instanceof User)) {
            return false;
        }
        User otherUser = (User) o;

        return (this.username.equals(otherUser.username)) && (this.isAdmin == otherUser.isAdmin);
    }




}
