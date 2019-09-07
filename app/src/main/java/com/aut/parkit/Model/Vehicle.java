package com.aut.parkit.Model;

import java.util.HashMap;
import java.util.Map;

public class Vehicle {
    private String numberPlate, vehicleName, ownerID;
    private Map<String, Object> map;

    public static final String KEY_NUMBERPLATE = "NumberPlate", KEY_VEHICLENAME = "VehicleName", KEY_OWNER = "Owner";

    public Vehicle(String numberPlate, String vehicleName, String ownerID) {
        this.numberPlate = numberPlate;
        this.vehicleName = vehicleName;
        this.ownerID = ownerID;
    }

    public Map<String, Object> toMap(){
        this.map = new HashMap<>();

        this.map.put(Vehicle.KEY_NUMBERPLATE, this.numberPlate);
        this.map.put(Vehicle.KEY_OWNER, this.ownerID);
        this.map.put(Vehicle.KEY_VEHICLENAME, this.vehicleName);

        return this.map;
    }

    public String getNumberPlate() {
        return numberPlate;
    }

    public String getVehicleName() {
        return vehicleName;
    }

    public String getOwnerID() {
        return ownerID;
    }
}
