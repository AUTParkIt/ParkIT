package com.aut.parkit;

import com.google.firebase.Timestamp;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ParkingSession {
    private String sessionID;
    private User user;
    private ParkingSpace parkingSpace;
    private Date startTime, endTime, refundedTime;
    private CarPark carPark;
    private Campus campus;
    private boolean refunded;
    private Map<String, Object> map;

    public static final String KEY_SESSIONID = "SessionID", KEY_USERID = "UserID", KEY_SPACEID = "SpaceID", KEY_STARTTIME = "StartTime",
                KEY_ENDTIME = "EndTime", KEY_REFUNDTIME = "RefundTime", KEY_CARPARKID = "CarParkID", KEY_REFUNDED = "Refunded",
                KEY_CAMPUSID = "CampusID";

    public ParkingSession(String sessionID, User user, ParkingSpace parkingSpace, Date startTime, Date endTime, CarPark carPark, Campus campus) {
        this.sessionID = sessionID;
        this.user = user;
        this.parkingSpace = parkingSpace;
        this.startTime = startTime;
        this.endTime = endTime;
        this.carPark = carPark;
        this.campus = campus;
        this.refunded = false;
    }

    public Map<String, Object> toMap(){
        this.map = new HashMap<>();

        this.map.put(ParkingSession.KEY_SESSIONID, this.sessionID);
        this.map.put(ParkingSession.KEY_USERID, this.user.getUserID());
        this.map.put(ParkingSession.KEY_CAMPUSID, this.campus.getCampusID());
        this.map.put(ParkingSession.KEY_CARPARKID, this.carPark.getCarParkID());
        this.map.put(ParkingSession.KEY_SPACEID, this.parkingSpace.getSpaceID());
        this.map.put(ParkingSession.KEY_STARTTIME, new Timestamp(this.startTime));
        this.map.put(ParkingSession.KEY_ENDTIME, new Timestamp(this.endTime));
        this.map.put(ParkingSession.KEY_REFUNDED, this.refunded);

        if (this.refunded){
            this.map.put(ParkingSession.KEY_REFUNDTIME, new Timestamp(this.refundedTime));
        }

        return map;
    }

    public void setRefundedTime(Date refundedTime) {
        this.refundedTime = refundedTime;
        this.refunded = true;
    }

    public String getSessionID() {
        return sessionID;
    }

    public User getUser() {
        return user;
    }

    public ParkingSpace getParkingSpace() {
        return parkingSpace;
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

    public CarPark getCarPark() {
        return carPark;
    }

    public Campus getCampus() {
        return campus;
    }

    public boolean isRefunded() {
        return refunded;
    }
}
