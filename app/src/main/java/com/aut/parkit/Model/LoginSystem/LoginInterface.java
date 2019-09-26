package com.aut.parkit.Model.LoginSystem;

//Interface to send response from server to login activity on main thread

public interface LoginInterface {
    void onSuccess(String responseMain);
    void onFailure(String responseMain, String responseDesc);
}
