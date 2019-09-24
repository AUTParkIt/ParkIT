package com.aut.parkit.Model.DatabaseManagmentSystem;

import java.util.LinkedList;

public class CampusData {
    private String campusID;
    private int totalSpaces, freeSpaces, maxTime;
    private float price;
    private LinkedList<CarPark> carParks;

    public static String KEY_ID = "ID", KEY_TOTALSPACES = "TotalSpaces", KEY_FREESPACES = "FreeSpaces", KEY_MAXTIME = "MaxTime",
            KEY_PRICE = "Price";

    public CampusData(String campusID, int totalSpaces, int freeSpaces, int maxTime, float price) {
        this.campusID = campusID;
        this.totalSpaces = totalSpaces;
        this.freeSpaces = freeSpaces;
        this.maxTime = maxTime;
        this.price = price;
        carParks = new LinkedList<>();
    }

    public CarPark getCarPark(String carParkID) {
        for (CarPark c : this.carParks) {
            if (c.getCarParkID().contentEquals(carParkID)) {
                return c;
            }
        }

        return null;
    }

    public String getCampusID() {
        return campusID;
    }

    public int getTotalSpaces() {
        return totalSpaces;
    }

    public int getFreeSpaces() {
        return freeSpaces;
    }

    public LinkedList<CarPark> getCarParks() {
        return (LinkedList<CarPark>) carParks;
    }
}
