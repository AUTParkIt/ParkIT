package com.aut.parkit.View;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import com.aut.parkit.Model.Payment.ClientToken;
import com.aut.parkit.Model.Payment.BraintreeTransaction;
import com.aut.parkit.R;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;

public class PaymentScreen extends AppCompatActivity{

    public static final int REQUEST_CODE = 1;
    private ClientToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_payment_screen);
        token = new ClientToken();

      /* TODO Check onClickListener with Brad
        Button startParking = (Button)findViewById(R.id.start_parking);
        startParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBraintreeSubmit(view);
            }
        });*/
    }

    public void onBraintreeSubmit(View v) {
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token.getClientToken())
                .vaultManager(true);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

   @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                //Get payment nonce and send to cloud functions
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                BraintreeTransaction transaction = new BraintreeTransaction(result.getPaymentMethodNonce().getNonce());
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("User cancelled payment");
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                System.err.println(error);
            }
        }
    }

    //SETTINGS MENU METHODS:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_myaccount) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
