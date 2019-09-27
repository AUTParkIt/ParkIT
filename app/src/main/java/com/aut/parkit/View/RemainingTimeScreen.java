package com.aut.parkit.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import com.aut.parkit.Model.ParkingTimeFormatter;
import com.aut.parkit.R;
import com.google.firebase.Timestamp;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

public class RemainingTimeScreen extends AppCompatActivity {

    private CountDownTimer countDownTimer;
    private ProgressBar progressBar;
    private TextView licencePlateText, parkingSpaceText, timeRemainingText, session_expired, remainingText, startTimeText, endTimeText;
    private Button extendParking, startNewSession;
    private Timestamp startTimeStamp, endTimeStamp;
    private long remainingMillis;
    private boolean firstTickComplete;
    private String formattedStartTime, formattedEndTime;
    private ParkingTimeFormatter timeFormatter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_remaining_time_screen);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        licencePlateText = (TextView) findViewById(R.id.licence_plate);
        parkingSpaceText = (TextView) findViewById(R.id.parking_space);
        startTimeText = (TextView) findViewById(R.id.start_time);
        endTimeText = (TextView) findViewById(R.id.end_time);
        timeRemainingText = (TextView) findViewById(R.id.time_remaining);
        session_expired = (TextView) findViewById(R.id.session_expired);
        remainingText = (TextView) findViewById(R.id.remaining);
        extendParking = (Button) findViewById(R.id.extend_parking);
        startNewSession = (Button) findViewById(R.id.new_session);

        timeFormatter = new ParkingTimeFormatter();
        setButtonListeners();
        setLicencePlate();
        setParkingSpace();
        setStartEndTimes();
        setRemainingTime();
        startTimer();
    }

    public void setButtonListeners(){
        extendParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
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
        String plateNumber = "GLD620";
        licencePlateText.setText(plateNumber);
    }

    public void setParkingSpace(){
        String spaceId = "B17";
        parkingSpaceText.setText("PARKING SPACE "+spaceId);
    }

    public void setStartEndTimes(){
        //Generate test timestamps
        startTimeStamp = new Timestamp(new Date());
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(startTimeStamp.getSeconds()*1000);
        cal.add(Calendar.SECOND, 120);
        endTimeStamp = new Timestamp(cal.getTime());
        //End of test code

        formattedStartTime = timeFormatter.convertTimestampToString(startTimeStamp);
        startTimeText.setText("Started "+formattedStartTime);

        formattedEndTime = timeFormatter.convertTimestampToString(endTimeStamp);
        endTimeText.setText("Ends "+formattedEndTime);
    }

    public void setRemainingTime() {
        remainingMillis = (endTimeStamp.getSeconds() - startTimeStamp.getSeconds())*1000;
        String remainingTime = timeFormatter.calculateRemainingTime(remainingMillis);
        timeRemainingText.setText(remainingTime);
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
                }else{

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
