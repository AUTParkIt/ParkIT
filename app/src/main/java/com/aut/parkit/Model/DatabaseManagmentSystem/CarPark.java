package com.aut.parkit.Model.DatabaseManagmentSystem;

import java.util.LinkedList;

public class CarPark {
    private String carParkID;
    private long totalSpaces, freeSpaces;
    private LinkedList<ParkingSpace> parkingSpaces;
    private String campusData;

    public static String KEY_ID = "ID", KEY_TOTALSPACES = "TotalSpaces", KEY_FREESPACES = "FreeSpaces", KEY_CAMPUSID = "Campus";

    //TODO: Finish class


    public CarPark(String carParkID, long totalSpaces, long freeSpaces, String campusData) {
        this.carParkID = carParkID;
        this.totalSpaces = totalSpaces;
        this.freeSpaces = freeSpaces;
        this.campusData = campusData;
    }


    public void addParkingSpace(ParkingSpace space){
        this.parkingSpaces.add(space);
    }

    public String getCarParkID() {
        return carParkID;
    }

    public ParkingSpace getParkingSpace(String id){


        if (this.parkingSpaces == null){
            return null;
        }

        for (ParkingSpace p: this.parkingSpaces){
            if (p.getSpaceID().contentEquals(id)){
                return p;
            }
        }

        return null;
    }

    public LinkedList<ParkingSpace> getParkingSpaces() {
        return parkingSpaces;
    }

    public void setParkingSpaces(LinkedList<ParkingSpace> parkingSpaces) {
        this.parkingSpaces = parkingSpaces;
    }
}
