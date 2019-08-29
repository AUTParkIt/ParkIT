package com.aut.parkit.Model.Payment;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;
import static com.braintreepayments.api.GooglePaymentActivity.REQUEST_CODE;

public class ClientToken extends AppCompatActivity {

    private String clientToken;

    public ClientToken(){
        init();
    }

    private void init(){
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("https://us-central1-autparkitnz.cloudfunctions.net/getClientToken", new TextHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, String clientToken) {
                setClientToken(clientToken);
            }
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable error) {
                error.printStackTrace(System.out);
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
