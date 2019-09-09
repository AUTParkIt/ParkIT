package com.aut.parkit.Model.DatabaseManagmentSystem;

import java.util.LinkedList;

public class CarparkManager {
    private LinkedList<Campus> campuses;

    public boolean addParkingSpace(String campusID, String carParkID, ParkingSpace space) {


        //TODO: Finish

        return false;
    }

    public Campus getCampus(String campusID) {
        for (Campus c : this.campuses) {
            if (c.getCampusID().contentEquals(campusID)) {
                return c;
            }
        }

        Campus campus = this.getCampusFromDB(campusID);

        return campus;

    }

    private Campus getCampusFromDB(String campusID) {
        //TODO: Finish
        return null;
    }

    private CarPark getCarparkFromDB(String carParkID) {
        //TODO: Finish
        return null;
    }
}
