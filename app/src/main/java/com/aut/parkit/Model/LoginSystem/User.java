package com.aut.parkit.Model.LoginSystem;

import androidx.appcompat.app.AppCompatActivity;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.TextHttpResponseHandler;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.auth.FirebaseAuth;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

public class User extends AppCompatActivity {
    //private FirebaseAuth mAuth;
    private String clientToken, userID;
    private AsyncHttpClient client;
    private RequestParams params;
    private String url = "https://us-central1-autparkitnz.cloudfunctions.net/getClientToken";

    public User(){
        /*TODO Get user ID from firebase
        Create FirebaseAuth instance to retrieve User ID
        mAuth = FirebaseAuth.getInstance();
        userID = mAuth.getUid();*/
        userID = "natalie1234";
        client = new AsyncHttpClient();
        params = new RequestParams();
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
