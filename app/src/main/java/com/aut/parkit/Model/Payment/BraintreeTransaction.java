package com.aut.parkit.Model.Payment;

//Class for creating a Braintree payment transaction.
//Payment nonce is sent to Braintree's server via cloud functions to authorise & initialise payment

import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

public class BraintreeTransaction extends AppCompatActivity {

    private final String URL = "https://us-central1-autparkitnz.cloudfunctions.net/createTransaction";

    public BraintreeTransaction(String paymentNonce, final BraintreeInterface bInterface){
        RequestParams params = new RequestParams();
        params.put("payment_method_nonce", paymentNonce);
        //TODO get transaction amount
        params.put("amount", "1.00");
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable error) {
                System.err.println("Error: "+error);
                System.err.println("Response String: "+responseString);
                bInterface.onFailure("Oops!", "Something went wrong... please try again");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString){
                if(responseString.contains("\"success\":false")){
                    System.err.println("Error: "+responseString);
                    System.err.println("Response String: "+responseString);
                    bInterface.onFailure("Payment Failed!", "Please check your payment method and try again");
                }
                else{
                    System.out.println("Transaction successful");
                    System.out.println("Response String: "+responseString);
                    bInterface.onSuccess("Payment Success!");
                }
            }
        });
    }
}
