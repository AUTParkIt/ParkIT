package com.aut.parkit.Model.Payment;

//Class for creating and managing customers on the Braintree payment gateway
//This enables user to store their payment methods in the Braintree vault

import com.braintreegateway.*;

public class BraintreeCustomer {

    private BraintreeGateway gateway;

    public BraintreeCustomer(){
        gateway = new BraintreePaymentGateway().getGateway();
    }

    //TODO need to call this when new user account is created
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

    public boolean deleteBraintreeCustomer(String userID){
        boolean success = false;

        try{
            Result<Customer> deleteResult = gateway.customer().delete(userID);
            success = deleteResult.isSuccess();
        }catch(Exception NotFoundException){
            System.err.println("Unable to delete customer: User ID does not exist");
        }

        return success;
    }

    public BraintreeGateway gateway(){
        return gateway;
    }
}
