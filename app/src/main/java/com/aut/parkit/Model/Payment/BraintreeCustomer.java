package com.aut.parkit.Model.Payment;

import com.braintreegateway.*;

public class BraintreeCustomer {

    private BraintreeGateway gateway;

    public BraintreeCustomer(){
        gateway = new BraintreeGateway(
            Environment.SANDBOX,
            "nqhd7msq7bkx89wm",
            "vmp7mmx4b4xp6cyw",
            "3d66224d4559111d2e5303fa5497c2d9"
        );
    }

    public boolean createBraintreeCustomer(String cFirstName, String cLastName, String userID){
        CustomerRequest request = new CustomerRequest()
                .firstName(cFirstName)
                .lastName(cLastName)
                .id(userID);

        Result<Customer> result = gateway.customer().create(request);
        return result.isSuccess();
    }

    public boolean updateBraintreeCustomer(String cFirstName, String cLastName, String userID){
        CustomerRequest request = new CustomerRequest()
                .firstName(cFirstName)
                .lastName(cLastName);

        Result<Customer> updateResult = gateway.customer().update(userID, request);
        return updateResult.isSuccess();
    }
}
