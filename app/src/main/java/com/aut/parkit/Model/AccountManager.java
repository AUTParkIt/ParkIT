package com.aut.parkit.Model;

import java.util.Map;

public class AccountManager {

    private static UserData userData;
    private static Map<Object, UserData> refrenceList;

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
        //Will need to make it wait;
    }

    private static void storeUserToDB(){

    }

    public static Vehicle getVehicle(String numberPlate){
        Vehicle v = AccountManager.userData.getVehicle(numberPlate);

        if (v != null){
            return v;
        }

        v = AccountManager.getVehicleFromDB();

        if (v != null){
            AccountManager.userData.addVehicleToGarage(v);
            return v;
        }

        return null;
    }

    private static Vehicle getVehicleFromDB(){
        return null;  //TODO: need to get connected to db and find out how to make process wait for date. lock?
    }

    //TODO: Finish
}
