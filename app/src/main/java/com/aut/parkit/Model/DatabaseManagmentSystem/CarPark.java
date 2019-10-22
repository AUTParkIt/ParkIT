package com.aut.parkit.Model.DatabaseManagmentSystem;

import com.google.firebase.firestore.GeoPoint;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

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

    public Map<String, Object>  toMap(){

    Map<String, Object> map = new HashMap<>();
    map.put(KEY_ID, carParkID);
    map.put(KEY_TOTALSPACES, totalSpaces);
    map.put(KEY_FREESPACES, freeSpaces);
    map.put(KEY_CAMPUSID, campusData);
    map.put(KEY_TOPLEFT, topLeftCorner);
    map.put(KEY_BOTTOMRIGHT, bottomRightCorner);

    return map;
    }

    public long getTotalSpaces() {
        return totalSpaces;
    }

    public void setTotalSpaces(long totalSpaces) {
        this.totalSpaces = totalSpaces;
    }

    public long getFreeSpaces() {
        return freeSpaces;
    }

    public void setFreeSpaces(long freeSpaces) {
        this.freeSpaces = freeSpaces;
    }
}
