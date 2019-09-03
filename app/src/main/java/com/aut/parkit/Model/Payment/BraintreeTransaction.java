package com.aut.parkit.Model.Payment;

import androidx.appcompat.app.AppCompatActivity;
import com.loopj.android.http.*;
import cz.msebera.android.httpclient.Header;

public class BraintreeTransaction extends AppCompatActivity {

    private final String URL = "https://us-central1-autparkitnz.cloudfunctions.net/setNonce";

    public BraintreeTransaction(String paymentNonce){
        RequestParams params = new RequestParams();
        params.put("payment_method_nonce", paymentNonce);
        //TODO get transaction amount
        params.put("amount", "3.00");
        AsyncHttpClient client = new AsyncHttpClient();
        client.post(URL, params, new TextHttpResponseHandler() {
            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable error) {
                System.err.println("Error: "+error);
                System.err.println("Response String: "+responseString);
            }

            @Override
            public void onSuccess(int statusCode, Header[] headers, String responseString) {
                System.out.println("Transaction successful");
            }
        });
    }
}
