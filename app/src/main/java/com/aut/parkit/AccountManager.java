package com.aut.parkit;

import java.util.Map;

public class AccountManager {

    private User user, modifiedUser;
    private Map<Object, User> refrenceList;

    public User getUser(Object caller){
        User oldUser  = refrenceList.get(caller);
        //User newUser = this.user.cl

        if (oldUser != null){
            oldUser.invalidate();
            refrenceList.remove(oldUser);
        }

        refrenceList.put(caller, user);

        return user;
    }

    public void pushModifiedUser(User user){
       // modifiedUser = user
    }

    //TODO: Finish


}
