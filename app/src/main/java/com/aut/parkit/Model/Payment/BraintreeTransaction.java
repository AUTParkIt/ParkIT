package com.aut.parkit.Model.Payment;

//Class for creating a Braintree payment transaction.
//Payment nonce is sent to Braintree's server via cloud functions to authorise & initialise payment

import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

public class BraintreeTransaction extends AppCompatActivity {

    private String url;
    private String paymentNonce;
    private BraintreeInterface bInterface;
    private AsyncHttpClient client;
    private String amount;
    public static String testAmount = "5.00"; //TEST VALUE FOR USE IN SANDBOX ENVIRONMENT ONLY

    public BraintreeTransaction(String paymentNonce, String amount, BraintreeInterface bInterface){
        this.paymentNonce = paymentNonce;
        this.amount = amount;
        this.bInterface = bInterface;
        client = new AsyncHttpClient();
        url = "https://us-central1-autparkitnz.cloudfunctions.net/createTransaction";
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setClient(AsyncHttpClient client){
        this.client = client;
    }

    public void createTransaction(){
        RequestParams params = new RequestParams();
        params.put("payment_method_nonce", paymentNonce);
        //TODO change to 'testAmount' to 'amount' variable when payment screen complete
        params.put("amount", amount);
        client.post(url, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable error) {
                System.err.println("Error: "+responseString);
                bInterface.onFailure("Oops!", "Something went wrong... please try again");
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString){
                if(responseString.contains("\"success\":false")){
                    System.err.println("Error: "+responseString);
                    bInterface.onFailure("Payment Failed!", "Please check your payment method and try again");
                }
                else{
                    System.out.println("Transaction successful: "+responseString);
                    bInterface.onSuccess("Payment Success!");
                }
            }
        });
    }
}
