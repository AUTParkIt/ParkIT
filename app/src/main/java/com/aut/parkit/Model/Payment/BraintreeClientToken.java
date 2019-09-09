package com.aut.parkit.Model.Payment;

//Class to retrieve client token from Braintree server via cloud functions https trigger

import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

public class BraintreeClientToken extends AppCompatActivity {
    //private FirebaseAuth mAuth;
    private String clientToken, userID;
    private AsyncHttpClient client;
    private RequestParams params;
    private String url = "https://us-central1-autparkitnz.cloudfunctions.net/getClientToken";

    public BraintreeClientToken(){
        /*TODO Get user ID from firebase
        Create FirebaseAuth instance to retrieve User ID
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();*/
        userID = "natalie1234";
        client = new AsyncHttpClient();
        params = new RequestParams();
    }

    public void setUrl(String url){
        this.url = url;
    }

    public void setClient(AsyncHttpClient client){
        this.client = client;
    }

    public String getClientToken(){
        return clientToken;
    }

    public void setClientToken(String clientToken){
        this.clientToken = clientToken;
    }

    public void generateClientTokenfromServer(){
        params.put("customerId", userID);
        client.post(url, params, new TextHttpResponseHandler() {
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
}
