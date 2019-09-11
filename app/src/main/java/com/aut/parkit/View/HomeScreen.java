package com.aut.parkit.View;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.aut.parkit.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import java.sql.Time;

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
        final int[] hour = {0};
        final int[] minute = {0};

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
            }
        });

        seekBar.setOnSeekBarChangeListener(new HoloCircleSeekBar.OnCircleSeekBarChangeListener(){

            @Override
            public void onProgressChanged(HoloCircleSeekBar holoCircleSeekBar, int i, boolean b) {
                int progress = holoCircleSeekBar.getValue();
                float purchase;
                for(; progress%60 == 0; progress -=60){
                    hour[0]++;
                    minute[0] = progress - 60;

                    if(minute[0] > 0 && minute[0] < 60){
                        String s1 = hour[0]+"hr(s) "+minute[0]+"min(s)";
                        duration.setText(s1);
                        String s2 = "Ends at: "+tsLong;
                        endTime.setText(s2);
                        purchase = (float) (hour[0] * 1.50);
                        String s3 = "Total: $"+purchase;
                        totalPurchase.setText(s3);
                    }
                    else{
                        String s1 = hour[0]+"hr(s)";
                        duration.setText(s1);
                        String s2 = "Ends at: "+tsLong;
                        endTime.setText(s2);
                        purchase = (float) (hour[0] * 1.50);
                        String s3 = "Total: $"+purchase;
                        totalPurchase.setText(s3);
                    }
                }

            }
            @Override
            public void onStartTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

            }

            @Override
            public void onStopTrackingTouch(HoloCircleSeekBar holoCircleSeekBar) {

            }
        });
    }

}
