package com.aut.parkit.Model.DatabaseManagmentSystem;

import com.google.firebase.firestore.GeoPoint;

import java.util.LinkedList;

public class CarPark {
    private String carParkID;
    private long totalSpaces, freeSpaces;
    private LinkedList<ParkingSpace> parkingSpaces;
    private String campusData;
    private GeoPoint topLeftCorner, bottomRightCorner;

    public static String KEY_ID = "ID", KEY_TOTALSPACES = "TotalSpaces", KEY_FREESPACES = "FreeSpaces", KEY_CAMPUSID = "Campus",
                KEY_TOPLEFT = "TopLeftCorner", KEY_BOTTOMRIGHT = "BottomRightCorner";

    //TODO: Finish class


    public CarPark(String carParkID, long totalSpaces, long freeSpaces, String campusData, GeoPoint topLeftCorner, GeoPoint bottomRightCorner) {
        this.carParkID = carParkID;
        this.totalSpaces = totalSpaces;
        this.freeSpaces = freeSpaces;
        this.campusData = campusData;
        this.topLeftCorner = topLeftCorner;
        this.bottomRightCorner = bottomRightCorner;
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

    public GeoPoint getTopLeftCorner() {
        return topLeftCorner;
    }

    public void setTopLeftCorner(GeoPoint topLeftCorner) {
        this.topLeftCorner = topLeftCorner;
    }

    public GeoPoint getBottomRightCorner() {
        return bottomRightCorner;
    }

    public void setBottomRightCorner(GeoPoint bottomRightCorner) {
        this.bottomRightCorner = bottomRightCorner;
    }
}
