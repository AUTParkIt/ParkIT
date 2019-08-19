package com.aut.parkit.Model;

import java.util.LinkedList;

public class ParkingSpace {
    private String spaceID;
    private CarPark carpark;
    private ParkingSession currentSession;
    private LinkedList<ParkingSession>  parkingRecord;

    //TODO:Finish class

    public String getSpaceID() {
        return spaceID;
    }
}
