package com.aut.parkit.Model;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.Map;

public class DocumentConverter {
    public static Vehicle toVehicle(Map<String, Object> map) {
        String numberPlate = (String) map.get(Vehicle.KEY_NUMBERPLATE);
        String vehicleName = (String) map.get(Vehicle.KEY_VEHICLENAME);
        String ownerID = (String) map.get(Vehicle.KEY_OWNER);

        return new Vehicle(numberPlate, vehicleName, ownerID);
    }

    public static ParkingSession toParkingSession(Map<String, Object> map) {
        String sessionID;
        String userID;
        String parkingSpaceID;
        Date startTime;
        Date endTime;
        Date refundedTime = null;
        String carParkID;
        String campusID;

        sessionID = (String) map.get(ParkingSession.KEY_SESSIONID);
        userID = (String) map.get(ParkingSession.KEY_USERID);
        parkingSpaceID = (String) map.get(ParkingSession.KEY_SPACEID);
        carParkID = (String) map.get(ParkingSession.KEY_CARPARKID);
        campusID = (String) map.get(ParkingSession.KEY_CAMPUSID);

        startTime = ((com.google.firebase.Timestamp) map.get(ParkingSession.KEY_STARTTIME)).toDate();
        endTime = ((com.google.firebase.Timestamp) map.get(ParkingSession.KEY_ENDTIME)).toDate();

        Timestamp ts = (Timestamp) map.get(ParkingSession.KEY_REFUNDTIME);
        if (ts != null) {
            refundedTime = ts.toDate();
        }

        ParkingSession ps = new ParkingSession(sessionID, userID, parkingSpaceID, startTime, endTime, carParkID, campusID);

        if (refundedTime != null) {
            ps.setRefundedTime(refundedTime);
        }

        return ps;
    }
}
