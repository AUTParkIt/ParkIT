package com.aut.parkit.Model.DatabaseManagmentSystem;

import java.util.Date;
import java.util.LinkedList;

public class User {

    UserData userData;

    public User() {
        this.userData = AccountManager.getPartialUser(this);
    }

    public String getUserID() {
        return this.userData.getUserID();
    }

    public String getAccountType() {
        try {
            return this.userData.getAccountType();
        } catch (Exception e) {
            this.updateUser();
            return this.getAccountType();
        }
    }

    public String getFirstName() {
        try {
            return this.userData.getFirstName();
        } catch (Exception e) {
            this.updateUser();
            return this.getAccountType();
        }
    }

    public String getLastName() {
        try {
            return this.userData.getLastName();
        } catch (Exception e) {
            this.updateUser();
            return this.getLastName();
        }
    }

    public String getEmailAddress() {
        try {
            return this.userData.getEmailAddress();
        } catch (Exception e) {
            this.updateUser();
            return this.getEmailAddress();
        }
    }

    public LinkedList<Vehicle> getGarage() {
        try {
            return this.userData.getGarage();
        } catch (Exception e) {
            this.updateUser();
            return this.getGarage();
        }


    }

    public Vehicle getVehicle(String numberPlate) {
        if (this.userData.isPartialClone()) {
            this.userData = AccountManager.getUser(this);
        }

        Vehicle v = this.userData.getVehicle(numberPlate);

        if (v != null) {
            return v;
        }

        v = AccountManager.getVehicle(numberPlate);

        if (v != null) {
            this.userData.addVehicleToGarage(v);
            return v;
        }

        return null;
    }

    public Vehicle getDefaultVehicle() {
        try {
            return this.userData.getDefaultVehicle();
        } catch (Exception e) {
            this.updateUser();
            return this.getDefaultVehicle();
        }
    }

    public boolean isExpireWarningNotification() {
        try {
            return this.userData.isExpireWarningNotification();
        } catch (Exception e) {
            this.updateUser();
            return this.isExpireWarningNotification();
        }
    }

    public boolean isBreachNoticeNotification() {
        try {
            return this.userData.isBreachNoticeNotification();
        } catch (Exception e) {
            this.updateUser();
            return this.isBreachNoticeNotification();
        }
    }

    public ParkingSession getCurrentParkingSession() {
        try {
            return this.userData.getCurrentParkingSession();
        } catch (Exception e) {
            this.updateUser();
            return this.getCurrentParkingSession();
        }
    }

    public LinkedList<ParkingSession> getParkingSession(Date date) { //TODO: Optimise the parking comparison by sorting and checking by date or sessionID
        try {
            LinkedList<ParkingSession> accountManagerSessions = AccountManager.getParkingSession(date);
            userData.insertParkingSessions(accountManagerSessions);

            return this.userData.getParkingSession(date);
        } catch (Exception e) {
            this.updateUser();
            return this.getParkingSession(date);
        }
        //TODO: Setup the ability for the Account manager to get the vehicle by date.
    }

    public LinkedList<ParkingSession> getParkingRecord() {
        try {
            return this.userData.getParkingRecord();
        } catch (Exception e) {
            this.updateUser();
            return this.getParkingRecord();
        }
    }

    private void updateUser() {
        if (!this.userData.isPartialClone()) {
            this.userData = AccountManager.getUser(this);
        } else {
            this.userData = AccountManager.getPartialUser(this);
        }

    }
}