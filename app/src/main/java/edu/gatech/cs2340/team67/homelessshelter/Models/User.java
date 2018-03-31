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


    public User(String username, boolean isAdmin) {
        this.username = username;
        this.uid = UID++;
        this.isAdmin = isAdmin;

    }

    /*
    * Reserve beds at a shelter
    * @param requestedBeds number of beds to reserve
    * @param shelter Shelter at which to reserve beds
    * @return true if successful, false if user already has reservations
    *
    */
    public boolean reserveBeds(int requestedBeds, Shelter shelter) throws NumberFormatException {
        //#TODO: does not need to throw exception when capacity is set to INTEGER
        if (!hasReservation()) {
            if (shelter.reserveVacancy(requestedBeds)) {
                reservedShelter = shelter;
                reservedBedsNumber = requestedBeds;
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    /*
    * Clear user's reservations
    *
    */
    public void clearReservations() {
        if (hasReservation()) {
            reservedShelter.clearReservation(reservedBedsNumber);
            reservedBedsNumber = 0;
            reservedShelter = null;
        }
    }

    public String getUsername() { return username; }
    public boolean getAdminStatus() { return isAdmin; }
    public Shelter getReservedShelter() { return reservedShelter; }
    public int getReservedBedsNumber() { return reservedBedsNumber; }
    public boolean hasReservation() { return reservedBedsNumber > 0; }
    public int getUid() { return uid; }






}
