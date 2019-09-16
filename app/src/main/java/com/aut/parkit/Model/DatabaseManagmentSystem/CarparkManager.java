package com.aut.parkit.Model.DatabaseManagmentSystem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.LinkedList;

public class CarparkManager {
    private LinkedList<CampusData> campusData;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public CampusData getCampus(String campusID) {
        for (CampusData c : this.campusData) {
            if (c.getCampusID().contentEquals(campusID)) {
                return c;
            }
        }

        CampusData campus = this.getCampusFromDB(campusID);

        if (campus != null){
            campusData.add(campus);
            return campus;
        }

        return null;
    }

    private CampusData getCampusFromDB(String campusID) {
        ThreadLock lock = new ThreadLock();

        DBWorkerGetter dbw = new DBWorkerGetter("Campus/" + campusID, lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        DocumentSnapshot doc = dbw.getDoc();

        if (doc.exists()) {
           return  DocumentConverter.toCampus(doc.getData());
        }

        return null;
    }

    public CarPark getCarPark(String campusID, String carParkID){
        //TODO: Finish
        return null;
    }

    private CarPark getCarparkFromDB(String carParkID) {
        //TODO: Finish
        return null;
    }

    public ParkingSpace getParkingSpace(String campusID, String carParkID, String parkingSpaceID){
        //TODO: Finish
        return null;
    }

    private ParkingSpace getParkingSpaceFromDB(String campusID, String carParkID, String parkingSpaceID){
        //TODO: Finnish
        return null;
    }
}
