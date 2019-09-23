package com.aut.parkit.Model.DatabaseManagmentSystem;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;

import java.util.LinkedList;

public class CarparkManager {
    private static LinkedList<CampusData> campusData;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();

    public static CampusData getCampus(String campusID) {
        for (CampusData c : CarparkManager.campusData) {
            if (c.getCampusID().contentEquals(campusID)) {
                return c;
            }
        }

        CampusData campus = CarparkManager.getCampusFromDB(campusID);

        if (campus != null){
            campusData.add(campus);
            return campus;
        }
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

    public static CarPark getCarPark(String campusID, String carParkID){
        CampusData campus = CarparkManager.getCampus(campusID);

        if (campus == null){
            return null;
        }

        CarPark carPark = campus.getCarPark(carParkID);

        if (carPark != null){
            return carPark;
        }

        carPark = CarparkManager.getCarparkFromDB(campusID, carParkID);

        if (carPark != null){
            campus.getCarParks().add(carPark);
            return carPark;
        }

        return null;
    }

    private static CarPark getCarparkFromDB(String campusID, String carParkID) {
        ThreadLock lock = new ThreadLock();

        DBWorkerGetter dbw = new DBWorkerGetter("Campus/" + campusID + "/Carparks/" + carParkID, lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        DocumentSnapshot doc = dbw.getDoc();

        if (doc.exists()) {
            return  DocumentConverter.toCarPark(doc.getData());
        }

        return null;
    }

    public static ParkingSpace getParkingSpace(String campusID, String carParkID, String parkingSpaceID){
        CampusData campus = CarparkManager.getCampus(campusID);

        if (campusData == null){
            return null;
        }

        CarPark carPark = CarparkManager.getCarPark(campus.getCampusID(), carParkID);

        if (carPark == null){
            return null;
        }



        ThreadLock lock = new ThreadLock();

        DBWorkerGetter dbw = new DBWorkerGetter("Campus/" + campusID + "/Carparks/" + carParkID + "/ParkingSpaces/" + parkingSpaceID, lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        DocumentSnapshot doc = dbw.getDoc();

        if (doc.exists()) {
            return  DocumentConverter.toParkingSpace(doc.getData());
        }

        return null;
    }

    private static ParkingSpace getParkingSpaceFromDB(String campusID, String carParkID, String parkingSpaceID){
        //TODO: Finnish
        return null;
    }

    public static void addParkingSessionToDB(ParkingSpace parkingSpace, ParkingSession parkingSession){

    }
}
