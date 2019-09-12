package com.aut.parkit.Model.DatabaseManagmentSystem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.LinkedList;

public class CarparkManager {
    private LinkedList<CampusData> campusData;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public boolean addParkingSpace(String campusID, String carParkID, ParkingSpace space) {


        //TODO: Finish

        return false;
    }

    public CampusData getCampus(String campusID) {
        for (CampusData c : this.campusData) {
            if (c.getCampusID().contentEquals(campusID)) {
                return c;
            }
        }

        CampusData campusData = this.getCampusFromDB(campusID);

        return campusData;

    }

    private CampusData getCampusFromDB(String campusID) {
        ThreadLock lock = new ThreadLock();

        DBWorkerGetter dbw = new DBWorkerGetter("Users/" + mAuth.getUid() + "/" + "Vehicles/" + mAuth.getUid()+"-", lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        DocumentSnapshot doc = dbw.getDoc();

        if (doc.exists()) {
           // return DocumentConverter.toVehicle(doc.getData());
        }

        return null;
    }

    private CarPark getCarparkFromDB(String carParkID) {
        //TODO: Finish
        return null;
    }
}
