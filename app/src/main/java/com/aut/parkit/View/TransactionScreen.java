package com.aut.parkit.View;

//Transaction screen that loads Braintree drop-in UI (payment method selection) and displays results of transaction

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.Payment.BraintreeInterface;
import com.aut.parkit.Model.Payment.BraintreeTransaction;
import com.aut.parkit.R;
import com.braintreepayments.api.dropin.DropInActivity;
import com.braintreepayments.api.dropin.DropInRequest;
import com.braintreepayments.api.dropin.DropInResult;
import com.developer.kalert.KAlertDialog;

public class TransactionScreen extends AppCompatActivity {
    private KAlertDialog dialog;
    private String token;
    private String amount;
    private String nonce;
    public static final int REQUEST_CODE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_screen);
        this.token = getIntent().getStringExtra("token");
        this.amount = getIntent().getStringExtra("amount");

        //Load payment drop-in UI
        DropInRequest dropInRequest = new DropInRequest()
                .clientToken(token)
                .vaultManager(true);
        startActivityForResult(dropInRequest.getIntent(this), REQUEST_CODE);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_CODE){
            if (resultCode == RESULT_OK){
                DropInResult result = data.getParcelableExtra(DropInResult.EXTRA_DROP_IN_RESULT);
                nonce = result.getPaymentMethodNonce().getNonce();
                //Send nonce to server to create transaction
                showProcessing();
                BraintreeTransaction transaction = new BraintreeTransaction(nonce, amount, new BraintreeInterface() {
                    @Override
                    public void onSuccess(String responseMain) {
                        showConfirmation(responseMain);
                    }

                    @Override
                    public void onFailure(String responseMain, String responseDesc) {
                        showError(responseMain, responseDesc);
                    }
                });
                transaction.createTransaction();
            } else if (resultCode == RESULT_CANCELED) {
                System.out.println("User cancelled payment");
                finish();
            } else {
                Exception error = (Exception) data.getSerializableExtra(DropInActivity.EXTRA_ERROR);
                System.err.println(error);
            }
        }
    }

    public void showProcessing(){
        dialog = new KAlertDialog(this, KAlertDialog.PROGRESS_TYPE);
        dialog.getProgressHelper().setBarColor(Color.parseColor("#FF7900"));
        dialog.setTitleText("Processing...");
        dialog.setCancelable(false);
        dialog.show();
    }

    public void showConfirmation(String titleText){
        dialog.dismiss();
        new KAlertDialog(this, KAlertDialog.SUCCESS_TYPE)
                .setTitleText(titleText)
                .confirmButtonColor(R.color.colorPrimary)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        /*TODO Load parking status screen:
                        Intent intent = new Intent(this, StatusScreen.class); //Load status screen
                        startActivity(intent);*/
                        finish();
                    }
                })
                .show();
    }

    public void showError(String titleText, String contentText){
        dialog.dismiss();
        new KAlertDialog(this,KAlertDialog.ERROR_TYPE)
                .setTitleText(titleText)
                .setContentText(contentText)
                .confirmButtonColor(R.color.colorPrimary)
                .setConfirmClickListener(new KAlertDialog.KAlertClickListener() {
                    @Override
                    public void onClick(KAlertDialog sDialog) {
                        finish(); //Return to purchasing screen
                    }
                })
                .show();
    }
}
