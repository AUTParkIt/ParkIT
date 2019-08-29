package com.aut.parkit.Model;

import android.util.Log;
import android.widget.Toast;
import androidx.annotation.NonNull;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.LinkedList;
import java.util.Map;

public class AccountManager {

    private static UserData userData;
    private static Map<Object, UserData> refrenceList;
    private static FirebaseAuth mAuth = FirebaseAuth.getInstance();
    private static FirebaseFirestore mFStore = FirebaseFirestore.getInstance();


    //TODO: need to add the ability to push a parking session and update the records

    public static UserData getUser(Object caller){
        AccountManager.removeOldReference(caller);

        if (AccountManager.userData == null){
            AccountManager.loadUserFromDB();
        }
        UserData newUserData = AccountManager.userData.clone();
        refrenceList.put(caller, newUserData);

        return newUserData;
    }

    public static UserData getPartialUser(Object caller){
        AccountManager.removeOldReference(caller);

        if (AccountManager.userData == null){
            AccountManager.loadUserFromDB();
        }

        UserData newUserData = AccountManager.userData.partialClone();
        refrenceList.put(caller, newUserData);

        return newUserData;
    }

    private static void removeOldReference(Object caller){
        UserData oldUserData = refrenceList.get(caller);

        if (oldUserData != null){
            oldUserData.invalidate();
            refrenceList.remove(oldUserData);
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

    private static void invalidateAll(){
        for (UserData u : AccountManager.refrenceList.values()) {
            u.invalidate();
            AccountManager.refrenceList.remove(u);
        }

        AccountManager.userData.invalidate();
    }

    private static void loadUserFromDB(){
        ThreadLock lock = new ThreadLock();

        DBWorkerGetter dbw = new DBWorkerGetter("Users/" + mAuth.getUid(), lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        UserData newUser = DocumentConverter.toUser(dbw.getDoc().getData());
        userData = newUser;

    }

    public static void createUser(String FirstName, String LastName, String EmailAddress, String LicencePlate){
        //TODO: create a userData class and a vehicle class
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
        Vehicle v = AccountManager.userData.getVehicle(numberPlate);

        if (v != null){
            return v;
        }

        v = AccountManager.getVehicleFromDB(numberPlate);

        if (v != null){
            AccountManager.userData.addVehicleToGarage(v);
            return v;
        }

        return null;
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

    public static ParkingSession getParkingSession(String parkingSessionID){
        return null; //TODO: Finish
    }

    private static ParkingSession getParkingSessionFromDB(String parkingSessionID){
        return null; //TODO:Finish
    }

    public static LinkedList<ParkingSession> getParkingRecord(){
        return AccountManager.userData.getParkingRecord();
    }

    public static LinkedList<Vehicle> getGarage(){
        return AccountManager.userData.getGarage();
    }

    private static Vehicle getVehicleFromDB(String numberPlate){
        ThreadLock lock = new ThreadLock();
        DBWorkerGetter dbw = new DBWorkerGetter("Users/" + mAuth.getUid() + "/" + "Vehicle/" + numberPlate, lock);
        Thread t = new Thread(dbw);

        t.start();
        lock.lockThread();

        DocumentSnapshot doc = dbw.getDoc();

        return DocumentConverter.toVehicle(doc.getData());
    }

    private static LinkedList<ParkingSession> getParkingSessionFromBD(Date date){
        return null; //TODO: need to impliment geting patrkingsessions from the DB and convert it to a parkingSession
    }

    //TODO: Finish
}
