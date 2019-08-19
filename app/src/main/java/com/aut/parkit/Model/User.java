package com.aut.parkit.Model;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class User implements Serializable {
    private String userID, accountType, firstName, lastName, emailAddress;
    private LinkedList<Vehicle> garage = new LinkedList<>();
    private Vehicle defaultVehicle;
    private boolean expireWarningNotification, breachNoticeNotification, partialClone;
    private ParkingSession currentParkingSession;
    private LinkedList<ParkingSession> parkingRecord = new LinkedList<>();
    private boolean modified;

    private Map<String, Object> map;
    public static final String KEY_USERID = "UserID", KEY_ACCOUNTTYPE = "AccountType", KEY_FIRSTNAME = "FirstName", KEY_LASTNAME = "LastName",
            KEY_EMAILADDRESS = "EmailAddress", KEY_EXPIREWARNINGNOTIFICATION = "ExpireWarningNotification",
            KEY_BREACHNOTICENOTIFICATION = "BreachWarningNotification", KEY_DEFAULTVEHIVLE = "DefaultVehicleID";

    public User(String userID, String accountType) {
        this.userID = userID;
        this.accountType = accountType;
        this.modified = false;
        this.partialClone = false;
    }

    public void addVehicleToGarage(Vehicle vehicle) {
        this.garage.add(vehicle);
    }

    public void removeVehicleFromGarage(Vehicle vehicle) {
        this.garage.remove(vehicle);
    }

    public boolean removeVehicleFromGarage(String numberPlate) {
        for (Vehicle v : this.garage) {
            if (v.getNumberPlate().contentEquals(numberPlate)) {
                this.garage.remove(v);
                return true;
            }
        }

        return false;
    }

    public void addParkingSession(ParkingSession parkingSession){
        this.parkingRecord.add(parkingSession);
    }

    public Map<String, Object> toMap() {
        this.map = new HashMap<>();

        this.map.put(User.KEY_USERID, this.userID);
        this.map.put(User.KEY_ACCOUNTTYPE, this.accountType);
        this.map.put(User.KEY_FIRSTNAME, this.firstName);
        this.map.put(User.KEY_LASTNAME, this.lastName);
        this.map.put(User.KEY_EMAILADDRESS, this.emailAddress);
        this.map.put(User.KEY_BREACHNOTICENOTIFICATION, this.breachNoticeNotification);
        this.map.put(User.KEY_EXPIREWARNINGNOTIFICATION, this.expireWarningNotification);
        this.map.put(User.KEY_DEFAULTVEHIVLE, this.defaultVehicle.getNumberPlate());

        return this.map;
    }

    @Override
    public User clone(){
        User user = this.setUpClone();

        for (Vehicle v: this.garage) {
            user.addVehicleToGarage(v);
        }

        for (ParkingSession p : this.parkingRecord) {
            user.addParkingSession(p.clone());
        }

        return user;
    }

    public User partialClone(){
        User user = this.setUpClone();
        user.setPartialClone();

        return user;
    }

    private User setUpClone(){
        User user = new User(this.userID, this.accountType);

        user.setLastName(this.lastName);
        user.setFirstName(this.firstName);
        user.setExpireWarningNotification(this.expireWarningNotification);
        user.setEmailAddress(this.emailAddress);
        user.setDefaultVehicle(this.defaultVehicle);
        user.setCurrentParkingSession(this.currentParkingSession);
        user.setBreachNoticeNotification(this.breachNoticeNotification);

        return user;
    }

    public void invalidate(){
        this.modified = true;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setDefaultVehicle(Vehicle defaultVehicle) {
        this.defaultVehicle = defaultVehicle;
    }

    public void setExpireWarningNotification(boolean expireWarningNotification) {
        this.expireWarningNotification = expireWarningNotification;
    }

    public void setBreachNoticeNotification(boolean breachNoticeNotification) {
        this.breachNoticeNotification = breachNoticeNotification;
    }

    public void setCurrentParkingSession(ParkingSession currentParkingSession) {
        this.currentParkingSession = currentParkingSession;
    }

    public boolean isPartialClone() {
        return partialClone;
    }

    public void setPartialClone() {
        this.partialClone = true;
    }

    public String getUserID() {
        return userID;
    }

    public String getAccountType() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }
        return accountType;
    }

    public String getFirstName() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }
        return firstName;
    }

    public String getLastName() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }
        return lastName;
    }

    public String getEmailAddress() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }
        return emailAddress;
    }

    public LinkedList<Vehicle> getGarage() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }
        return garage;
    }

    public Vehicle getDefaultVehicle() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }
        return defaultVehicle;
    }

    public boolean isExpireWarningNotification() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }
        return expireWarningNotification;
    }

    public boolean isBreachNoticeNotification() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }
        return breachNoticeNotification;
    }

    public ParkingSession getCurrentParkingSession() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }
        return currentParkingSession;
    }

    public LinkedList<ParkingSession> getParkingRecord() throws Exception {
        if (this.modified){
            throw new Exception("User Object Obsolete");
        }

        return parkingRecord;
    }
}