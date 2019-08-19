package com.aut.parkit.Model;

public class User {

    UserData userData;

    public User() {
        this.userData = AccountManager.getPartialUser(this);
        //userData.get
    }

    public String getUserID(){
        return this.userData.getUserID();
    }

    public String getAccountType(){
        try {
            return this.userData.getAccountType();
        } catch (Exception e) {
            this.updateUser();
            return this.getAccountType();
        }
    }

    public String getFirstName(){
        try {
            return this.userData.getFirstName();
        } catch (Exception e) {
            this.updateUser();
            return this.getAccountType();
        }
    }

    public Vehicle getVehicle(String numberPlate){
        if (this.userData.isPartialClone()){
            this.userData = AccountManager.getUser(this);
        }

        Vehicle v = this.userData.getVehicle(numberPlate);

        if (v != null){
            return v;
        }

        v = AccountManager.getVehicle(numberPlate);

        if (v != null){
            this.userData.addVehicleToGarage(v);
            return v;
        }

        return null;
    }

    private void updateUser(){
        if (!this.userData.isPartialClone()){
            this.userData = AccountManager.getUser(this);
        }
        else {
            this.userData = AccountManager.getPartialUser(this);
        }

    }
}
