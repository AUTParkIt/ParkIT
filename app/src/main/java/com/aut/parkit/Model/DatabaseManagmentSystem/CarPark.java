package com.aut.parkit.Model.DatabaseManagmentSystem;

import android.widget.Space;

import java.util.LinkedList;

public class CarPark {
    private String carParkID;
    private int totalSpaces, freeSpaces;
    private LinkedList<Space> parkingSpaces;
    private String campusData;

    public static String KEY_ID = "ID", KEY_TOTALSPACES = "TotalSpaces", KEY_FREESPACES = "FreeSpaces", KEY_CAMPUSID = "Campus";

    //TODO: Finish class


    public CarPark(String carParkID, int totalSpaces, int freeSpaces, String campusData) {
        this.carParkID = carParkID;
        this.totalSpaces = totalSpaces;
        this.freeSpaces = freeSpaces;
        this.campusData = campusData;
    }


    public void addParkingSpace(Space space){
        this.parkingSpaces.add(space);
    }

    public String getCarParkID() {
        return carParkID;
    }
}
