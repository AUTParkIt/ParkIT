package com.aut.parkit.Model.Payment;

import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

public class BraintreeClientToken extends AppCompatActivity {

    private String clientToken;
    private final String URL = "https://us-central1-autparkitnz.cloudfunctions.net/getClientToken";

    //Retrieve client token from Braintree server via cloud functions https trigger
    public BraintreeClientToken(){
        RequestParams params = new RequestParams();
        //TODO get userID
        params.put("customerId", "natalie1234");
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(URL, params, new TextHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, String clientToken) {
                System.out.println("Client token successful");
                setClientToken(clientToken);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable error) {
                System.err.println("Error: "+error);
                System.err.println("Response string: "+responseString);
            }
        });
    }

    public String getClientToken(){
        return clientToken;
    }

    public void setClientToken(String clientToken){
        this.clientToken = clientToken;
    }
}
