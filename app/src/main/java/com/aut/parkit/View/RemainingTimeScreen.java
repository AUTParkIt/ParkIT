package com.aut.parkit.View;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.aut.parkit.Model.DatabaseManagmentSystem.ParkingSession;
import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.Model.ParkingTimeFormatter;
import com.aut.parkit.R;
import com.google.firebase.Timestamp;

import java.math.BigDecimal;

public class RemainingTimeScreen extends AppCompatActivity {
    protected CountDownTimer countDownTimer;
    protected ProgressBar progressBar;
    protected TextView licencePlateText, parkingSpaceText, timeRemainingText, session_expired, remainingText, startTimeText, endTimeText;
    protected Button extendParking, startNewSession;
    protected Timestamp startTimeStamp, endTimeStamp;
    protected static long remainingMillis;
    protected boolean firstTickComplete;
    protected String formattedStartTime, formattedEndTime;
    protected ParkingTimeFormatter timeFormatter;
    private String content;
    private User user;
    private ParkingSession session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_remaining_time_screen);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Parking Session")
                .setContentText(content)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT);
        NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);

        // notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, builder.build());


        progressBar = findViewById(R.id.progressBar);
        licencePlateText = findViewById(R.id.licence_plate);
        parkingSpaceText = findViewById(R.id.parking_space);
        startTimeText = findViewById(R.id.start_time);
        endTimeText = findViewById(R.id.end_time);
        timeRemainingText = findViewById(R.id.time_remaining);
        session_expired = findViewById(R.id.session_expired);
        remainingText = findViewById(R.id.remaining);
        extendParking = findViewById(R.id.extend_parking);
        startNewSession = findViewById(R.id.new_session);

        timeFormatter = new ParkingTimeFormatter();

        new Thread(new Runnable() {
            @Override
            public void run() {
                user = new User();
                session = user.getCurrentParkingSession();

                setLicencePlate();//
                setParkingSpace();//
                setStartEndTimes();//
                setRemainingTime();//

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        startTimer();
                    }
                });
            }
        }).start();

        setButtonListeners();
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = "Parking Session Expiration";
            String description = "Your Parking Session has expired or is about to expire. Please purchase or extend your ticket";
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel("1", name, importance);
            channel.setDescription(description);
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }

    }


    public void setButtonListeners(){
        extendParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "Extend Parking Session", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(RemainingTimeScreen.this, HomeScreen.class));
            }
        });

        startNewSession.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RemainingTimeScreen.this, HomeScreen.class);
                startActivity(intent);
            }
        });
    }

    public void setLicencePlate(){
        //String plateNumber = "GLD620";
        final String plateNumber = session.getNumberPlate();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                licencePlateText.setText(plateNumber);
            }
        });

    }

    public void setParkingSpace(){
        //String spaceId = "B17";
        final String spaceId = session.getParkingSpaceID();
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                parkingSpaceText.setText(spaceId);
            }
        });

    }

    public void setStartEndTimes(){
        //Generate test timestamps
//        startTimeStamp = new Timestamp(new Date());
//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(startTimeStamp.getSeconds()*1000);
//        cal.add(Calendar.SECOND, 120);
//        endTimeStamp = new Timestamp(cal.getTime());
//        //End of test code
//
//        formattedStartTime = timeFormatter.convertTimestampToString(startTimeStamp);
//        startTimeText.setText("Started "+formattedStartTime);
//
//        formattedEndTime = timeFormatter.convertTimestampToString(endTimeStamp);
//        endTimeText.setText("Ends "+formattedEndTime);


//        Calendar cal = Calendar.getInstance();
//        cal.setTimeInMillis(startTimeStamp.getSeconds()*1000);
//        cal.add(Calendar.SECOND, 120);
        startTimeStamp = new Timestamp(session.getStartTime());
        endTimeStamp = new Timestamp(session.getEndTime());
        formattedStartTime = timeFormatter.convertTimestampToString(startTimeStamp);
        formattedEndTime = timeFormatter.convertTimestampToString(endTimeStamp);


        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                startTimeText.setText("Started "+formattedStartTime);
                endTimeText.setText("Ends "+formattedEndTime);
            }
        });
    }

    public void setRemainingTime() {
        remainingMillis = (endTimeStamp.getSeconds() - startTimeStamp.getSeconds())*1000;
        final String remainingTime = timeFormatter.calculateRemainingTime(remainingMillis);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                timeRemainingText.setText(remainingTime);
            }
        });

    }

    private void endParkingSession(){
        timeRemainingText.setVisibility(View.INVISIBLE);
        remainingText.setVisibility(View.INVISIBLE);
        session_expired.setVisibility(View.VISIBLE);
        endTimeText.setText("Ended "+formattedEndTime);
        extendParking.setVisibility(View.INVISIBLE);
        startNewSession.setVisibility(View.VISIBLE);
    }

    public void startTimer() {
        progressBar.setMax(new BigDecimal(remainingMillis).intValueExact());
        System.out.println("Max set to: "+new BigDecimal(remainingMillis).intValueExact());
        progressBar.setProgress(0);
        System.out.println("Progress set to: "+progressBar.getProgress());
        firstTickComplete = false;

        countDownTimer = new CountDownTimer(remainingMillis, (60000 - 500)) {
            @Override
            public void onTick(long millisUntilFinished) {
                if(!firstTickComplete) {
                    firstTickComplete = true;
                }
                else if(millisUntilFinished == 3540000){
                    createNotificationChannel();
                }
                else{

                    timeRemainingText.setText(timeFormatter.calculateRemainingTime(millisUntilFinished));
                    System.out.println(timeRemainingText.getText());
                    progressBar.setProgress(progressBar.getProgress()+60000);
                    System.out.println("Progress is: "+progressBar.getProgress());
                }
            }
            @Override
            public void onFinish() {
                endParkingSession();
            }
        };
        countDownTimer.start();
    }


    //SETTINGS MENU METHODS:
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.settingsMenu:
                startActivity(new Intent(RemainingTimeScreen.this, MenuScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
