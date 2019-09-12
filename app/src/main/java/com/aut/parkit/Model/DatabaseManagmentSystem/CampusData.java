package com.aut.parkit.Model.DatabaseManagmentSystem;

import java.util.LinkedList;
import java.util.Map;

public class CampusData {
    private String campusID;
    private int totalSpaces, freeSpaces;
    private LinkedList<CarPark> carParks;

    public static final String KEY_ID = "ID";

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
        return carParks;
    }

    public Map<String, Object> toMap(){
        return null;
    }
}
