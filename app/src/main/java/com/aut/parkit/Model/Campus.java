package com.aut.parkit.Model;

import java.util.LinkedList;

public class Campus {
    private String campusID;
    private int totalSpaces, freeSpaces;
    private LinkedList<CarPark> carParks;
    //TODO: Finish Class

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
}
