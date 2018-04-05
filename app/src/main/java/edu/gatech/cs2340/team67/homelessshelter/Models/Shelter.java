package edu.gatech.cs2340.team67.homelessshelter.Models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Information-holder for shelters.
 *
 * Created by jacobmeyers on 2/22/18.
 */

public class Shelter implements Parcelable {

    private int id;
    private String name;
    private String capacity;
    private String restrictions;
    private double longitude;
    private double latitude;
    private String address;
    private String phoneNumber;
    private String vacancy;

    public static int MAX_RESRERVATION = 10;

    public Shelter(){} //done for firebase database

    public Shelter(int id, String name, String capacity, String restrictions, double longitude, double latitude,
                   String address, String phoneNumber) {
        this.id = id;
        this.name = name;
        this.capacity = capacity;
        this.restrictions = restrictions;
        this.longitude = longitude;
        this.latitude = latitude;
        this.address = address;
        this.phoneNumber = phoneNumber;

        this.vacancy = capacity; //#TODO: CHANGE VACANCY/CAPACITY TO INT BY CLEANING UP DATABASE
    }

    /*
    * Update vacancies upon request
    * @param requestedBeds number of beds to reserve
    * @return true if successful, false if too many beds requested or not enough vacancies available.
    *
    */
    public boolean reserveVacancy(int numBedsRequested) throws NumberFormatException {
        //#TODO: this will not need to throw an exception when vacancy is updated to int
        int numBedsAvailable = Integer.parseInt(vacancy);

        if (numBedsRequested > MAX_RESRERVATION || numBedsAvailable - numBedsRequested < 0) {
            return false; //not enough space or too many beds
        } else {
            //enough space
            vacancy = Integer.toString(numBedsAvailable - numBedsRequested); //#TODO: no need for tostring when capacity is int

            Model.getInstance().updateShelterDatabase(this);// database backup

            return true;
        }

    }

    /*
    * Clear a reservation, opposite of reserve vacancy
    * @param requestedBeds number of beds to be cleared
    *
    */
    public void clearReservation(int numBedsReserved) {
        int numBedsAvailable = Integer.parseInt(vacancy);
        vacancy = Integer.toString(numBedsReserved + numBedsAvailable); //#TODO: remove toString when capacity is int
        Model.getInstance().updateShelterDatabase(this); //update database
    }

    public int getId() { return id; }

    public String getName() {
        return name;
    }

    public String getCapacity() { return capacity; }

    public String getRestrictions() { return  restrictions; }

    public double getLongitude() {
        return longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public String getAddress() {
        return address;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getVacancy() { return vacancy; }


    //Parcelable methods
    public Shelter(Parcel in){

        // the order needs to be the same as in writeToParcel() method
        this.id = in.readInt();
        this.name = in.readString();
        this.capacity = in.readString();
        this.restrictions = in.readString();
        this.longitude = in.readDouble();
        this.latitude = in.readDouble();
        this.address = in.readString();
        this.phoneNumber = in.readString();
        this.vacancy = in.readString();
    }


    public int describeContents(){
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(capacity);
        dest.writeString(restrictions);
        dest.writeDouble(longitude);
        dest.writeDouble(latitude);
        dest.writeString(address);
        dest.writeString(phoneNumber);
        dest.writeString(vacancy);

    }
    public static final Parcelable.Creator CREATOR = new Parcelable.Creator() {
        public Shelter createFromParcel(Parcel in) {
            return new Shelter(in);
        }

        public Shelter[] newArray(int size) {
            return new Shelter[size];
        }
    };

}
