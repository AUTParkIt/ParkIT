package com.aut.parkit.Model.DatabaseManagmentSystem;

import android.widget.Space;

import java.util.LinkedList;

public class CarPark {
    private String carParkID;
    private int totalSpaces, freeSpaces;
    private LinkedList<Space> parkingSpaces;
    private CampusData campusData;

    //TODO: Finish class

    public String getCarParkID() {
        return carParkID;
    }
}
