package com.aut.parkit.Model.DatabaseManagmentSystem;

import com.google.firebase.Timestamp;
import com.google.firebase.auth.FirebaseAuth;

import java.util.Date;
import java.util.Map;

public class DocumentConverter {

    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static Vehicle toVehicle(Map<String, Object> map) {
        String numberPlate = (String) map.get(Vehicle.KEY_NUMBERPLATE);
        String vehicleName = (String) map.get(Vehicle.KEY_VEHICLENAME);
        String ownerID = (String) map.get(Vehicle.KEY_OWNER);

        return new Vehicle(numberPlate, vehicleName, ownerID);
    }

    public static ParkingSession toParkingSession(Map<String, Object> map) {
        String sessionID;
        String userID;
        String numberPlate;
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
        numberPlate = (String) map.get(ParkingSession.KEY_NUMBERPLATE);
        startTime = ((com.google.firebase.Timestamp) map.get(ParkingSession.KEY_STARTTIME)).toDate();
        endTime = ((com.google.firebase.Timestamp) map.get(ParkingSession.KEY_ENDTIME)).toDate();

        Timestamp ts = (Timestamp) map.get(ParkingSession.KEY_REFUNDTIME);
        if (ts != null) {
            refundedTime = ts.toDate();
        }

        ParkingSession ps = new ParkingSession(sessionID, userID, numberPlate, parkingSpaceID, startTime, endTime, carParkID, campusID);

        if (refundedTime != null) {
            ps.setRefundedTime(refundedTime);
        }

        return ps;
    }

    public static UserData toUser(Map<String, Object> map){
        String userID, accountType, firstName, lastName, emailAddress;
        String defaultVehicleID;
        boolean expireWarningNotification, breachNoticeNotification;

        userID = (String) map.get(UserData.KEY_USERID);
        firstName = (String) map.get(UserData.KEY_FIRSTNAME);
        lastName = (String) map.get(UserData.KEY_LASTNAME);
        accountType = (String) map.get(UserData.KEY_ACCOUNTTYPE);
        emailAddress = (String) map.get(UserData.KEY_EMAILADDRESS);
        defaultVehicleID = (String) map.get(UserData.KEY_DEFAULTVEHIVLE);
        breachNoticeNotification = (boolean) map.get(UserData.KEY_BREACHNOTICENOTIFICATION);
        expireWarningNotification = (boolean) map.get(UserData.KEY_EXPIREWARNINGNOTIFICATION);

        UserData newUser = new UserData(userID, accountType);

        newUser.setBreachNoticeNotification(breachNoticeNotification);
        //newUser.setCurrentParkingSession(AccountManager.getParkingSession("")); //TODO: Need to allow the software to findout if the user has any current parking session that need to be gotten
        newUser.setDefaultVehicle(AccountManager.getVehicle(defaultVehicleID));
        newUser.setLastName(lastName);
        newUser.setFirstName(firstName);
        newUser.setExpireWarningNotification(expireWarningNotification);
        newUser.setEmailAddress(emailAddress);

        return newUser;
    }

    public static CampusData toCampus(Map<String, Object> campus){
        String campusID;
        int totalSpaces, freeSpaces, maxTime;
        float price;

        campusID = (String) campus.get(CampusData.KEY_ID);
        totalSpaces = (int) campus.get(CampusData.KEY_TOTALSPACES);
        freeSpaces = (int) campus.get(CampusData.KEY_FREESPACES);
        maxTime = (int) campus.get(CampusData.KEY_MAXTIME);
        price = (float) campus.get(CampusData.KEY_PRICE);

        return new CampusData(campusID, totalSpaces, freeSpaces, maxTime, price);
    }

    public  static CarPark toCarPark(Map<String, Object> carPark){
        String carParkID, campusID;
        int totalSpaces, freeSpaces;

        carParkID = (String) carPark.get(CarPark.KEY_ID);
        campusID = (String) carPark.get(CarPark.KEY_CAMPUSID);
        totalSpaces = (int) carPark.get(CarPark.KEY_TOTALSPACES);
        freeSpaces = (int) carPark.get(CarPark.KEY_FREESPACES);

        return new CarPark(carParkID, totalSpaces, freeSpaces, campusID);
    }

    public static ParkingSpace toParkingSpace(Map<String, Object> parkingSpace){
        String ID;
        boolean booked;

        ID = (String) parkingSpace.get(ParkingSpace.KEY_SPACEID);
        booked = (boolean) parkingSpace.get(ParkingSpace.KEY_BOOKED);

        return new ParkingSpace(ID, booked);
    }
}
