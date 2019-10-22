package com.aut.parkit.Model.DatabaseManagmentSystem;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class CarparkManager {
    private static LinkedList<CampusData> campusData = new LinkedList<>();
    private static FirebaseFirestore mFStore = FirebaseFirestore.getInstance();
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

        return null;
    }

    private static CampusData getCampusFromDB(String campusID) {
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

        ParkingSpace ps = carPark.getParkingSpace(parkingSpaceID);

        if (ps != null){
            return ps;
        }

        return CarparkManager.getParkingSpaceFromDB(campusID,carParkID,parkingSpaceID);
    }

    public static ParkingSpace getParkingSpace(String parkingSpaceID){
        String campus, carpark;

        StringTokenizer token = new StringTokenizer(parkingSpaceID,"-");

        campus = token.nextToken();

        carpark = campus + "-" + token.nextToken();

        return CarparkManager.getParkingSpace(campus, carpark, parkingSpaceID);
    }

    private static ParkingSpace getParkingSpaceFromDB(String campusID, String carParkID, String parkingSpaceID){


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

    public static void addParkingSessionToDB(ParkingSession parkingSession){
        final String campus, carpark, parkingSpace;
        final ParkingSession parkingSession1 = parkingSession;

        parkingSpace = parkingSession.getParkingSpaceID();

        StringTokenizer token = new StringTokenizer(parkingSpace, "-");

        campus = token.nextToken();

        carpark = campus + "-" + token.nextToken();

        mFStore.collection("Campus").document(campus).collection("Carparks").document(carpark).collection("ParkingSpaces").document(parkingSpace)
                .collection("ParkingRecords").document(parkingSession.getSessionID()).set(parkingSession.toMap()).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                mFStore.collection("Users").document(mAuth.getUid()).collection("ParkingRecords").document(parkingSession1.getSessionID()).set(parkingSession1.toMap());
                  ParkingSpace space = new ParkingSpace(parkingSpace, true);

                mFStore.collection("Campus").document(campus).collection("Carparks").document(carpark).collection("ParkingSpaces").document(parkingSpace)
                        .update(space.toMap());

                CarPark car = CarparkManager.getCarPark(campus, carpark);

                car.setFreeSpaces(car.getFreeSpaces() - 1);
                mFStore.collection("Campus").document(campus).collection("Carparks").document(carpark).update(car.toMap());
            }
        });


    }

    public static LinkedList<CampusData> getAllCampuses(){
        return campusData;
    }

    public static LinkedList<CampusData> getAllCampusesFromDB(){
        LinkedList<CampusData> campuses = new LinkedList<>();

        ThreadLock lock = new ThreadLock();

        DBWorkerGetterCollections dbw = new DBWorkerGetterCollections("Campus", lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        List<DocumentSnapshot> data = dbw.getData();

        if (!data.isEmpty()) {
            for (DocumentSnapshot doc : data) {
                CampusData newCampus = DocumentConverter.toCampus(doc.getData());
                campuses.add(newCampus);

                CampusData oldCampus = CarparkManager.getCampus(newCampus.getCampusID());

                if (oldCampus != null){
                    newCampus.setCarParks(oldCampus.getCarParks());
                }
            }

            campusData = campuses;
        }

        return campusData;
    }

    public static LinkedList<CarPark> getAllCarparks(String campusID){
        CampusData campus = CarparkManager.getCampus(campusID);

        if (campus != null){
            return campus.getCarParks();
        }

        return null;
    }

    public static LinkedList<CarPark> getAllCarparksFromDB(String campusID){
        LinkedList<CarPark> carParks = new LinkedList<>();

        ThreadLock lock = new ThreadLock();

        DBWorkerGetterCollections dbw = new DBWorkerGetterCollections("/Campus/" + campusID + "/Carparks/", lock);

//        DBWorkerGetterCollections dbw = new DBWorkerGetterCollections("/Campus/Manukau/Carparks/", lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        List<DocumentSnapshot> data = dbw.getData();

        if (!data.isEmpty()) {
            for (DocumentSnapshot doc : data) {
                CarPark newCarpark = DocumentConverter.toCarPark(doc.getData());
                carParks.add(newCarpark);

                CarPark oldCarpark = CarparkManager.getCarPark(campusID, newCarpark.getCarParkID());

                if (oldCarpark != null){
                    newCarpark.setParkingSpaces(oldCarpark.getParkingSpaces());
                }
            }
        }

        CampusData campus = CarparkManager.getCampus(campusID);
        campus.setCarParks(carParks);

        return carParks;
    }
}
