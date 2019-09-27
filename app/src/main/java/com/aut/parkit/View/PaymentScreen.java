package com.aut.parkit.View;

//Purchasing screen placeholder class

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.Payment.BraintreeClientToken;
import com.aut.parkit.R;

import org.w3c.dom.Text;

public class PaymentScreen extends AppCompatActivity{
    HomeScreen hs = new HomeScreen();
    private BraintreeClientToken token;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_payment_screen);
        token = new BraintreeClientToken();
        token.generateClientTokenfromServer();
        TextView t0 = findViewById(R.id.payTextView);
        String pay = "Payment due: $"+hs.df.format(hs.pay);
        t0.setText(pay);
    }

    public void onPaymentSubmit(View v) {
        Intent intent = new Intent(this, TransactionScreen.class);
        intent.putExtra("token", token.getClientToken());
        //TODO get payment total
        //intent.putExtra("amount", "2.00");
        TextView t1 = findViewById(R.id.payTextView);
        String price = "PAYMENT DUE:\n$"+hs.df.format(hs.pay);
        t1.setText(price);
        intent.putExtra("Amount", t1.getText().toString());
        startActivity(intent);
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
