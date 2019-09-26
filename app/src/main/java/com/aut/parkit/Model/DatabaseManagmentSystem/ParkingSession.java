package com.aut.parkit.Model.DatabaseManagmentSystem;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParkingSession implements Comparable<ParkingSession> {
    private String sessionID;
    private String userID;
    private String numberPlate;
    private String parkingSpaceID;
    private Date startTime, endTime, refundedTime;
    private String carParkID;
    private String campusID;
    private boolean refunded;
    private Map<String, Object> map;

    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static final String KEY_SESSIONID = "SessionID", KEY_USERID = "UserID", KEY_SPACEID = "SpaceID", KEY_STARTTIME = "StartTime",
            KEY_ENDTIME = "EndTime", KEY_REFUNDTIME = "RefundTime", KEY_CARPARKID = "CarParkID", KEY_REFUNDED = "Refunded",
            KEY_CAMPUSID = "CampusID", KEY_NUMBERPLATE = "NumberPlate";

    public ParkingSession(String numberPlate, String parkingSpaceID, Date startTime, Date endTime, String carParkID, String campusID) {
        this.sessionID = mAuth.getUid() + "-" + numberPlate+ "-"+ parkingSpaceID + "-" + startTime.toString();
        this.userID = mAuth.getUid();
        this.numberPlate = numberPlate;
        this.parkingSpaceID = parkingSpaceID;
        this.startTime = startTime;
        this.endTime = endTime;
        this.carParkID = carParkID;
        this.campusID = campusID;
        this.refunded = false;
    }

    public Map<String, Object> toMap() {
        this.map = new HashMap<>();

        this.map.put(ParkingSession.KEY_SESSIONID, this.sessionID);
        this.map.put(ParkingSession.KEY_USERID, this.userID);
        this.map.put(ParkingSession.KEY_NUMBERPLATE, this.numberPlate);
        this.map.put(ParkingSession.KEY_CAMPUSID, this.campusID);
        this.map.put(ParkingSession.KEY_CARPARKID, this.carParkID);
        this.map.put(ParkingSession.KEY_SPACEID, this.parkingSpaceID);
        this.map.put(ParkingSession.KEY_STARTTIME, new Timestamp(this.startTime));
        this.map.put(ParkingSession.KEY_ENDTIME, new Timestamp(this.endTime));
        this.map.put(ParkingSession.KEY_REFUNDED, this.refunded);
        if (this.refunded) {
            this.map.put(ParkingSession.KEY_REFUNDTIME, new Timestamp(this.refundedTime));
        }

        return map;
    }

    public ParkingSession clone() {
        ParkingSession parking = new ParkingSession(this.numberPlate, this.parkingSpaceID, (Date) this.startTime.clone(), (Date) this.endTime.clone(), this.carParkID, this.campusID);

        if (this.refunded) {
            parking.setRefundedTime((Date) this.refundedTime.clone());
        }

        return parking;
    }

    public void setRefundedTime(Date refundedTime) {
        this.refundedTime = refundedTime;
        this.refunded = true;
    }

    public String getSessionID() {
        return sessionID;
    }

    public String getUserID() {
        return userID;
    }

    public String getParkingSpaceID() {
        return parkingSpaceID;
    }

    public Date getStartTime() {
        return startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public Date getRefundedTime() {
        return refundedTime;
    }

    public String getCarParkID() {
        return carParkID;
    }

    public String getCampusID() {
        return campusID;
    }

    public boolean isRefunded() {
        return refunded;
    }

    @Override
    public int compareTo(ParkingSession parkingSession) {
        return Integer.parseInt(parkingSession.getSessionID()) - Integer.parseInt(this.sessionID);
    }
}
