package com.aut.parkit;

import java.util.HashMap;
import java.util.Map;

public class Vehicle {
    private String numberPlate, vehicleName;
    private User owner;
    private Map<String, Object> map;

    public static final String KEY_NUMBERPLATE = "NumberPlate", KEY_VEHICLENAME = "VehicleName", KEY_OWNER = "Owner";

    public Vehicle(String numberPlate, String vehicleName, User owner) {
        this.numberPlate = numberPlate;
        this.vehicleName = vehicleName;
        this.owner = owner;
    }

    public Map<String, Object> toMap(){
        this.map = new HashMap<>();

        this.map.put(Vehicle.KEY_NUMBERPLATE, this.numberPlate);
        this.map.put(Vehicle.KEY_OWNER, this.owner.getUserID());
        this.map.put(Vehicle.KEY_VEHICLENAME, this.vehicleName);

        return this.map;
    }

    public void setVehicleName(String vehicleName) {
        this.vehicleName = vehicleName;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public User getOwner() {
        return owner;
    }
}
