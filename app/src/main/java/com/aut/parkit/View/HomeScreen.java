package com.aut.parkit.View;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;
import com.aut.parkit.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;


public class HomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        final HoloCircleSeekBar seekBar = findViewById(R.id.durationSeekbar);
        final long tsLong = System.currentTimeMillis()/1000;
        Button change = findViewById(R.id.changeRegisBtn);
        Button strtPark = findViewById(R.id.startParkBtn);
        final TextView endTime = findViewById(R.id.durationEndsText);
        final TextView totalPurchase = findViewById(R.id.totalPayText);
        final TextView duration = findViewById(R.id.valueText);

        seekBar.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b) {
                double pay = i * 0.75;
                String price = "Total: $"+pay;
                totalPurchase.setText(price);

                if(i%2 == 1){
                    i = i/2;
                    String s30 = i+"h 30m";
                    duration.setText(s30);
                }
                else if(i == 10){
                    String max = "ALL DAY PARKING";
                    duration.setText(max);
                }
                else{
                    i = i/2;
                    String s = i+"h";
                    duration.setText(s);
                }
            }

            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

            }
        });
        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Change Car Registration", Toast.LENGTH_SHORT);
                toast.show();
            }
        });
        strtPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Confirm Park", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(HomeScreen.this, PaymentScreen.class));
            }
        });


    }
}
