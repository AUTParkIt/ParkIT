package com.aut.parkit.Model.DatabaseManagmentSystem;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class ParkingSpace {
    private String spaceID;
    private boolean booked;
    private ParkingSession currentParkingSession;
    private LinkedList<ParkingSession> parkingRecord = new LinkedList<>();

    public static String KEY_SPACEID = "ID", KEY_BOOKED = "Booked";

    public ParkingSpace(String spaceID, boolean booked) {
        this.spaceID = spaceID;
        this.booked = booked;
    }

    public void addCurentSession(ParkingSession parkingSession) {
        this.currentParkingSession = parkingSession;
        this.parkingRecord.add(parkingSession);
    }

    public void addParkingSession(ParkingSession parkingSession) {
        this.parkingRecord.add(parkingSession);
    }

    public String getSpaceID() {
        return spaceID;
    }

    public boolean isBooked() {
        return booked;
    }

    public ParkingSession getCurrentParkingSession() {
        return currentParkingSession;
    }

    public LinkedList<ParkingSession> getParkingRecord() {
        return parkingRecord;
    }

    public Map<String, Object> toMap(){
        Map<String, Object> map = new HashMap<>();

        map.put(ParkingSpace.KEY_SPACEID, this.spaceID);
        map.put(ParkingSpace.KEY_BOOKED, this.booked);

        return map;
    }
}
