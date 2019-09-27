package com.aut.parkit.Model.DatabaseManagmentSystem;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.aut.parkit.View.Updatable;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class AccountManager {

    private static UserData userData;
    private static Map<Object, UserData> referenceList = new HashMap<>();
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore mFStore = FirebaseFirestore.getInstance();
    private static boolean listening = false;
    private static LinkedList<Updatable> updatableScreens = new LinkedList<>();


    //TODO: need to add the ability to push a parking session and update the records

    public static UserData getUser(Object caller){
        AccountManager.removeOldReference(caller);

        if (AccountManager.userData == null){
            AccountManager.loadUserFromDB();
        }
        UserData newUserData = AccountManager.userData.clone();
        referenceList.put(caller, newUserData);

        return newUserData;
    }

    public static UserData getPartialUser(Object caller){
        AccountManager.removeOldReference(caller);

        if (AccountManager.userData == null){
            AccountManager.loadUserFromDB();
        }

        UserData newUserData = AccountManager.userData.partialClone();
        referenceList.put(caller, newUserData);

        return newUserData;
    }

    private static void removeOldReference(Object caller){
        UserData oldUserData = referenceList.get(caller);

        if (oldUserData != null){
            oldUserData.invalidate();
            referenceList.remove(oldUserData);
        }
    }

    public static void pushModifiedUser(UserData userData) throws Exception {

        if (!userData.isPartialClone()){
            AccountManager.invalidateAll();
            AccountManager.userData = userData;
            AccountManager.storeUserToDB();
        }
        else {
            throw new Exception("Cannot push Partial Clone");
        }
    }

    public static void addUpdatable(Updatable u){
        AccountManager.updatableScreens.add(u);
    }

    private static void invalidateAll(){
        for (UserData u : AccountManager.referenceList.values()) {
            u.invalidate();
            AccountManager.referenceList.remove(u);
        }

        AccountManager.updateScreens();
    }

    private static void updateScreens(){
        for (Updatable u : AccountManager.updatableScreens){
            u.update();
        }
    }

    private static void loadUserFromDB(){
        ThreadLock lock = new ThreadLock();

        DBWorkerGetter dbw = new DBWorkerGetter("Users/" + mAuth.getUid(), lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        UserData newUser = DocumentConverter.toUser(dbw.getDoc().getData());
        userData = newUser;

        if (!listening){
            AccountManager.addListenerToUser();
        }
    }

    public static void createUser(String firstName, String lastName, String emailAddress, String licencePlate){
        UserData newUser = new UserData(mAuth.getUid(), UserData.DRIVER);

        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmailAddress(emailAddress);
        newUser.setExpireWarningNotification(true);
        newUser.setBreachNoticeNotification(true);

        Vehicle defaultVehicle = new Vehicle(licencePlate, "myCar", mAuth.getUid());

        newUser.setDefaultVehicle(defaultVehicle);
        newUser.addVehicleToGarage(defaultVehicle);

        AccountManager.userData = newUser;
        storeUserToDB();

        AccountManager.addVehicle(defaultVehicle);

    }

    private static void storeUserToDB(){
        mFStore.collection("Users").document(mAuth.getUid()).set(userData.toMap())
        .addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.e("FireBase", e.toString());
            }
        });
    }

    public static Vehicle getVehicle(String numberPlate){
        Vehicle v;

        if (AccountManager.userData != null){
            v = AccountManager.userData.getVehicle(numberPlate.toUpperCase());

            if (v != null){
                return v;
            }
        }


        v = AccountManager.getVehicleFromDB(numberPlate.toUpperCase());

        if (v != null && AccountManager.userData != null){
            AccountManager.userData.addVehicleToGarage(v);
            return v;
        }

        return null;
    }

    public static void addVehicle(Vehicle v){
        AccountManager.userData.addVehicleToGarage(v);

        mFStore.collection("Users").document(mAuth.getUid()).collection("Vehicles").document(mAuth.getUid()+"-"+v.getNumberPlate()).set(v.toMap());
    }

    public static LinkedList<ParkingSession> getParkingSession(Date date){
        try {
            LinkedList<ParkingSession> parkingList = AccountManager.getParkingSessionFromBD(date);
            AccountManager.userData.insertParkingSessions(parkingList);
            return AccountManager.userData.getParkingSession(date);
        } catch (Exception e) {
            AccountManager.loadUserFromDB();
        }

        return AccountManager.getParkingSession(date);
    }

    private static ParkingSession getParkingSessionFromDB(String parkingSessionID){
        return null; //TODO:Finish
    }

    public static LinkedList<ParkingSession> getParkingRecord(){

        return AccountManager.userData.getParkingRecord();
    }

    private static Vehicle getVehicleFromDB(String numberPlate){
        ThreadLock lock = new ThreadLock();

        DBWorkerGetter dbw = new DBWorkerGetter("Users/" + mAuth.getUid() + "/" + "Vehicles/" + mAuth.getUid()+"-"+numberPlate, lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        DocumentSnapshot doc = dbw.getDoc();

        if (doc.exists()) {
            return DocumentConverter.toVehicle(doc.getData());
        }

        return null;
    }

    private static LinkedList<ParkingSession> getParkingSessionFromBD(Date date){
        return null; //TODO: need to impliment geting patrkingsessions from the DB and convert it to a parkingSession
    }

    private static void addListenerToUser(){
        DocumentReference docRef = mFStore.collection("Users").document(mAuth.getUid());

        docRef.addSnapshotListener(new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException e) {
                if (e != null){
                    Log.e("addListenerToUser Error",e.toString());
                    return;
                }

                if (documentSnapshot != null && documentSnapshot.exists()) {
                    Log.d("addListenerToUser", "Current data: " + documentSnapshot.getData());
                    AccountManager.userData = DocumentConverter.toUser(documentSnapshot.getData());
                    AccountManager.invalidateAll();
                } else {
                    Log.d("addListenerToUser", "Current data: null");
                }
            }
        });
    }

    public static LinkedList<Vehicle> getGarage() {
        LinkedList<Vehicle> gar = userData.getGarage();
        gar.clear();

        ThreadLock lock = new ThreadLock();

        DBWorkerGetterCollections dbw = new DBWorkerGetterCollections("Users/" + mAuth.getUid() + "/" + "Vehicles", lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        List<DocumentSnapshot> data = dbw.getData();

        if (!data.isEmpty()) {
            for (DocumentSnapshot doc : data) {
                gar.add(DocumentConverter.toVehicle(doc.getData()));
            }
        }

        return gar;
    }

    //TODO: Finish
}
