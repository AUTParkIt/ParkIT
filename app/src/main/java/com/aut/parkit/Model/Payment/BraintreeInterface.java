package com.aut.parkit.Model.Payment;

//Interface to send result of Braintree transaction to TransactionScreen activity

public interface BraintreeInterface {
    void onSuccess(String responseMain);
    void onFailure(String responseMain, String responseDesc);
}
