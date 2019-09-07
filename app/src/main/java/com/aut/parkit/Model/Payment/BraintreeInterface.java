package com.aut.parkit.Model.Payment;

//Interface to send response from server to activities on main thread

public interface BraintreeInterface {
    void onSuccess(String responseMain);
    void onFailure(String responseMain, String responseDesc);
}
