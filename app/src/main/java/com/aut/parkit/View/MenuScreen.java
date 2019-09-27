package com.aut.parkit.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.R;
import com.google.firebase.auth.FirebaseAuth;

public class MenuScreen extends AppCompatActivity {

    private Button myAcc, myPay, myCars, tAndCs, logOut;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_menu_screen);

        myAcc = findViewById(R.id.myAccountBtn);
        myPay = findViewById(R.id.payMethodBtn);
        myCars = findViewById(R.id.garageBtn);
        tAndCs = findViewById(R.id.termsBtn);
        logOut = findViewById(R.id.logOutBtn);

        myAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "View Account details", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuScreen.this, UpdateDetailsScreen.class));
            }
        });
        myPay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "View payment methods", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuScreen.this, TransactionScreen.class));
            }
        });
        myCars.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "View my garage", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuScreen.this, GaragePopup.class));
            }
        });
        tAndCs.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getApplicationContext(), "View terms and conditions", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(MenuScreen.this, TAndCs.class));
            }
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Intent i = new Intent(MenuScreen.this, LoadingScreen.class);
                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(i);
            }
        });
    }
}
