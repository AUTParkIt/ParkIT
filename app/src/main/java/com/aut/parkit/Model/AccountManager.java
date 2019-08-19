package com.aut.parkit.Model;

import java.util.Map;

public class AccountManager {

    private static User user;
    private static Map<Object, User> refrenceList;

    public static User getUser(Object caller){
        AccountManager.removeOldReference(caller);

        if (AccountManager.user == null){
            AccountManager.loadUserFromDB();
        }
        User newUser = AccountManager.user.clone();
        refrenceList.put(caller, newUser);

        return newUser;
    }

    public static User getPartialUser(Object caller){
        AccountManager.removeOldReference(caller);

        User newUser = AccountManager.user.partialClone();
        refrenceList.put(caller, newUser);

        return newUser;
    }

    private static void removeOldReference(Object caller){
        User oldUser  = refrenceList.get(caller);

        if (oldUser != null){
            oldUser.invalidate();
            refrenceList.remove(oldUser);
        }
    }

    public static void pushModifiedUser(User user) throws Exception {

        if (!user.isPartialClone()){
            AccountManager.invalidateAll();
            AccountManager.user = user;
        }
        else {
            throw new Exception("Cannot push Partial Clone");
        }
    }

    private static void invalidateAll(){
        for (User u : AccountManager.refrenceList.values()) {
            u.invalidate();
            AccountManager.refrenceList.remove(u);
        }

        AccountManager.user.invalidate();
    }

    private static void loadUserFromDB(){
        //Will need to make it wait;
    }

    private static void storeUserToDB(){

    }

    //TODO: Finish
}
