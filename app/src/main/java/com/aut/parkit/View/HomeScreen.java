package com.aut.parkit.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class HomeScreen extends AppCompatActivity implements Updatable{
    protected Date currentTime;
    protected Calendar cTime;
    protected SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
    protected DecimalFormat df = new DecimalFormat("0.00");
    protected HoloCircleSeekBar seekBar;
    protected Spinner campusSpin, numberSpin;
    protected TextView rego, endTime, totalPurchase, duration;
    protected EditText space;
    protected Button strtPark, change;
    protected User user;
    private int day, time;
    private ProgressBar loadBar;
    static double pay = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_home_screen);

        rego = findViewById(R.id.carRegisText);

        loadBar = findViewById(R.id.progressBar2);
        loadBar.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                user = new User(HomeScreen.this);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();
        seekBar = findViewById(R.id.durationSeekbar);
        currentTime = Calendar.getInstance().getTime();
        cTime = Calendar.getInstance();
        day = currentTime.getDay();
        change = findViewById(R.id.changeRegisBtn);
        strtPark = findViewById(R.id.startParkBtn);
        endTime = findViewById(R.id.durationEndsText);
        totalPurchase = findViewById(R.id.totalPayText);
        duration = findViewById(R.id.valueText);
        space = findViewById(R.id.spaceNumText);

        duration.setText("$1.50 per hour");

        seekBar.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener() {
            @Override
            public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b) {
                paymentTotal(i,0.75);
                String price = "Total: $"+df.format(pay);
                totalPurchase.setText(price);
                currentTime.getTime();
                cTime.setTime(currentTime);

                seekBar.setMax((18 - currentTime.getHours()) * 2);
                if(seekBar.getMaxValue() > 10) {
                    seekBar.setMax(10);
                }
                else if (currentTime.getMinutes() >= 30 && seekBar.getMaxValue() < 10) {
                    seekBar.setMax(seekBar.getMaxValue() - 1);
                }
                else if(currentTime.getHours() >= 18 || day == 0 || day == 6){
                    String s6 = "FREE PARKING";
                    String t6 = "Free after 06:00 PM";
                    duration.setText(s6);
                    endTime.setText(t6);
                    seekBar.setMax(0);
                    return;
                }

                if(i == seekBar.getMaxValue() || (currentTime.getHours() + (seekBar.getValue()/2)) >= 18 || seekBar.getValue() >= 10 ){
                    String sMax = "ALL DAY PARKING";
                    String tMax = "Ends at: 06:00 PM";
                    duration.setText(sMax);
                    endTime.setText(tMax);
                }
                else if(i%2 == 1 && i != 0){
                    i = i/2;
                    time = (i*60)+30;
                    cTime.add(Calendar.MINUTE, time);
                    String s30 = i+"h 30m";
                    String t30 = "Ends at: "+sdf.format(cTime.getTime());
                    duration.setText(s30);
                    endTime.setText(t30);
                }
                else if(i != 0){
                    i = i/2;
                    time = (i*60);
                    cTime.add(Calendar.MINUTE, time);
                    String s = i+"h";
                    String t = "Ends at: "+sdf.format(cTime.getTime());
                    duration.setText(s);
                    endTime.setText(t);
                }
                else if (i == 0){
                    duration.setText("$1.50 per hour");
                    endTime.setText("Loading...");
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
                if (pay == 0){
                    return;
                }
                Toast toast = Toast.makeText(getApplicationContext(), "Confirm Park", Toast.LENGTH_SHORT);
                toast.show();
                startActivity(new Intent(HomeScreen.this, PaymentScreen.class));
            }
        });
    }

    @Override
    public void update() {
        rego.setText(user.getDefaultVehicle().getNumberPlate());
    }

    public double paymentTotal (int roller, double price){
        pay = roller * price;
        return pay;
    }
}
