package com.aut.parkit.View;

import androidx.appcompat.app.ActionBar;
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

import com.aut.parkit.GaragePopup;
import com.aut.parkit.Model.AccountManager;
import com.aut.parkit.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class HomeScreen extends AppCompatActivity {
    AccountManager aM = new AccountManager();
    Date currentTime;
    Calendar cTime;
    SimpleDateFormat df = new SimpleDateFormat("hh:mm aa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_home_screen);

        final HoloCircleSeekBar seekBar = findViewById(R.id.durationSeekbar);
        currentTime = Calendar.getInstance().getTime();
        cTime = Calendar.getInstance();
        Button change = findViewById(R.id.changeRegisBtn);
        final Button strtPark = findViewById(R.id.startParkBtn);
        final TextView endTime = findViewById(R.id.durationEndsText);
        final TextView totalPurchase = findViewById(R.id.totalPayText);
        final TextView duration = findViewById(R.id.valueText);

        seekBar.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b) {
                int time;
                double pay = i * 0.75;
                String price = "Total: $"+pay;
                totalPurchase.setText(price);
                currentTime.getTime();
                cTime.setTime(currentTime);

                if(currentTime.getHours() >= 14 && currentTime.getHours() < 15){
                        seekBar.setMax(8);
                }
                else if(currentTime.getHours() >= 15 && currentTime.getHours() < 16){
                    seekBar.setMax(6);
                }
                else if(currentTime.getHours() >= 16 && currentTime.getHours() < 17){
                    seekBar.setMax(4);
                }
                else if (currentTime.getHours() >= 17 && currentTime.getHours() < 18) {
                    seekBar.setMax(2);
                }
                else if(currentTime.getHours() >= 18){
                    seekBar.setMax(0);
                    String s6 = "FREE PARKING";
                    String t6 = "Free after 06:00 PM";
                    duration.setText(s6);
                    endTime.setText(t6);
                    return;
                }

                if(i%2 == 1){
                    i = i/2;
                    time = (i*60)+30;
                    cTime.add(Calendar.MINUTE, time);
                    String s30 = i+"h 30m";
                    String t30 = "Ends at: "+df.format(cTime.getTime());
                    duration.setText(s30);
                    endTime.setText(t30);
                }
                else if(i == seekBar.getMaxValue()){
                    String sMax = "ALL DAY PARKING";
                    String tMax = "Ends at: 06:00 PM";
                    duration.setText(sMax);
                    endTime.setText(tMax);
                }
                else{
                    i = i/2;
                    time = (i*60);
                    cTime.add(Calendar.MINUTE, time);
                    String s = i+"h";
                    String t = "Ends at: "+df.format(cTime.getTime());
                    duration.setText(s);
                    endTime.setText(t);
                }

            }
            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {}
            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {}
        });

        change.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast toast = Toast.makeText(getApplicationContext(), "Change Car Registration", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(HomeScreen.this, GaragePopup.class));
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
