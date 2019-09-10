package com.aut.parkit.Model.DatabaseManagmentSystem;

import java.util.LinkedList;

public class ParkingSpace {
    private String spaceID;
    private String carParkID;
    private ParkingSession currentParkingSession;
    private LinkedList<ParkingSession> parkingRecord;

    public ParkingSpace(String spaceID, String carParkID) {
        this.spaceID = spaceID;
        this.carParkID = carParkID;
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

    public String getCarParkID() {
        return carParkID;
    }

    public ParkingSession getCurrentParkingSession() {
        return currentParkingSession;
    }

    public LinkedList<ParkingSession> getParkingRecord() {
        return parkingRecord;
    }
}
