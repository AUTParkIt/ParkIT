package com.aut.parkit.Model;

import java.io.Serializable;
import java.util.*;

public class UserData implements Serializable {
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

    public static final String DRIVER = "Driver";

    public UserData(String userID, String accountType) {
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

    public void addParkingSession(ParkingSession parkingSession) {
        this.parkingRecord.add(parkingSession);
    }

    public void insertParkingSessions(LinkedList<ParkingSession> parkingSessionsList) {
        Collections.sort(this.parkingRecord);
        Collections.sort(parkingSessionsList);

        for (ParkingSession psL : parkingSessionsList) {
            for (ParkingSession pr : this.parkingRecord) {
                if (psL.getSessionID().equalsIgnoreCase(pr.getSessionID())) {
                    this.parkingRecord.remove(pr);
                    break;
                }

                if (Integer.parseInt(pr.getSessionID()) > Integer.parseInt(psL.getSessionID())) {
                    break;
                }
            }
        }

        this.parkingRecord.addAll(parkingSessionsList);
        Collections.sort(this.parkingRecord);
    }

    public Map<String, Object> toMap() {
        this.map = new HashMap<>();

        this.map.put(UserData.KEY_USERID, this.userID);
        this.map.put(UserData.KEY_ACCOUNTTYPE, this.accountType);
        this.map.put(UserData.KEY_FIRSTNAME, this.firstName);
        this.map.put(UserData.KEY_LASTNAME, this.lastName);
        this.map.put(UserData.KEY_EMAILADDRESS, this.emailAddress);
        this.map.put(UserData.KEY_BREACHNOTICENOTIFICATION, this.breachNoticeNotification);
        this.map.put(UserData.KEY_EXPIREWARNINGNOTIFICATION, this.expireWarningNotification);
        this.map.put(UserData.KEY_DEFAULTVEHIVLE, this.defaultVehicle.getNumberPlate());

        return this.map;
    }

    @Override
    public UserData clone() {
        UserData userData = this.setUpClone();

        for (Vehicle v : this.garage) {
            userData.addVehicleToGarage(v);
        }

        for (ParkingSession p : this.parkingRecord) {
            userData.addParkingSession(p.clone());
        }

        return userData;
    }

    public UserData partialClone() {
        UserData userData = this.setUpClone();
        userData.setPartialClone();

        return userData;
    }

    private UserData setUpClone() {
        UserData userData = new UserData(this.userID, this.accountType);

        userData.setLastName(this.lastName);
        userData.setFirstName(this.firstName);
        userData.setExpireWarningNotification(this.expireWarningNotification);
        userData.setEmailAddress(this.emailAddress);
        userData.setDefaultVehicle(this.defaultVehicle);
        userData.setCurrentParkingSession(this.currentParkingSession);
        userData.setBreachNoticeNotification(this.breachNoticeNotification);

        return userData;
    }

    public void invalidate() {
        this.modified = true;
    }

    public Vehicle getVehicle(String numberPlate) {  //TODO: Decide if need to throw exception

        for (Vehicle v : this.garage) {
            if (v.getNumberPlate().contentEquals(numberPlate)) {
                return v;
            }
        }

        return null;
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
        if (this.modified) {
            throw new Exception("UserData Object Obsolete");
        }
        return accountType;
    }

    public String getFirstName() throws Exception {
        if (this.modified) {
            throw new Exception("UserData Object Obsolete");
        }
        return firstName;
    }

    public String getLastName() throws Exception {
        if (this.modified) {
            throw new Exception("UserData Object Obsolete");
        }
        return lastName;
    }

    public String getEmailAddress() throws Exception {
        if (this.modified) {
            throw new Exception("UserData Object Obsolete");
        }
        return emailAddress;
    }

    public LinkedList<Vehicle> getGarage(){
        return garage;
    }

    public Vehicle getDefaultVehicle() throws Exception {
        if (this.modified) {
            throw new Exception("UserData Object Obsolete");
        }
        return defaultVehicle;
    }

    public boolean isExpireWarningNotification() throws Exception {
        if (this.modified) {
            throw new Exception("UserData Object Obsolete");
        }
        return expireWarningNotification;
    }

    public boolean isBreachNoticeNotification() throws Exception {
        if (this.modified) {
            throw new Exception("UserData Object Obsolete");
        }
        return breachNoticeNotification;
    }

    public ParkingSession getCurrentParkingSession() throws Exception {
        if (this.modified) {
            throw new Exception("UserData Object Obsolete");
        }
        return currentParkingSession;
    }

    public LinkedList<ParkingSession> getParkingRecord() {

        return parkingRecord;
    }

    public LinkedList<ParkingSession> getParkingSession(Date date) throws Exception {
        if (this.modified) {
            throw new Exception("UserData Object Obsolete");
        }

        LinkedList<ParkingSession> list = new LinkedList<>();

        for (ParkingSession ps : this.parkingRecord) {
            Date dayAfter = (Date) date.clone();
            dayAfter.setDate(date.getDate() + 1);
            if (ps.getStartTime().after(date) && ps.getStartTime().before(dayAfter)) {
                list.add(ps);
            }
        }

        return list;
    }

    public void setParkingRecord(LinkedList<ParkingSession> record){
        this.parkingRecord = record;
    }

    public void setGarage(LinkedList<Vehicle> garage){
        this.garage = garage;
    }
}
