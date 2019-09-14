package com.aut.parkit.View;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import com.aut.parkit.R;
import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class RemainingTimeScreen extends AppCompatActivity {

    private ProgressBar progressBar;
    private Button extendParking;
    int myProgress = 0;
    int progress;
    CountDownTimer countDownTimer;
    int endTime = 250;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_remaining_time_screen);

        progressBar = (ProgressBar) findViewById(R.id.progressBar);
        extendParking = (Button)findViewById(R.id.extend_parking);
        extendParking.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO extend parking
            }
        });
        startTimer();
    }

    public void startTimer(){
        progressBar.setSecondaryProgress(endTime);
        progressBar.setProgress(0);

/*        if (timer_text.getText().toString().length()>0) {
            myProgress = 0;

            *//*try {
                countDownTimer.cancel();

            } catch (Exception e) {

            }*//*

            String timeInterval = timer_text.getText().toString();
            progress = 1;
            endTime = Integer.parseInt(timeInterval); // up to finish time*/

            countDownTimer = new CountDownTimer(endTime * 1000, 1000) {
                @Override
                public void onTick(long millisUntilFinished) {
                    setProgress(progress, endTime);
                    progress = progress + 1;
                    int seconds = (int) (millisUntilFinished / 1000) % 60;
                    int minutes = (int) ((millisUntilFinished / (1000 * 60)) % 60);
                    int hours = (int) ((millisUntilFinished / (1000 * 60 * 60)) % 24);
                    String newtime = hours + ":" + minutes + ":" + seconds;

                    /*if (newtime.equals("0:0:0")) {
                        timer_text.setText("00:00:00");
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        timer_text.setText("0" + hours + ":0" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(minutes).length() == 1)) {
                        timer_text.setText("0" + hours + ":0" + minutes + ":" + seconds);
                    } else if ((String.valueOf(hours).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        timer_text.setText("0" + hours + ":" + minutes + ":0" + seconds);
                    } else if ((String.valueOf(minutes).length() == 1) && (String.valueOf(seconds).length() == 1)) {
                        timer_text.setText(hours + ":0" + minutes + ":0" + seconds);
                    } else if (String.valueOf(hours).length() == 1) {
                        timer_text.setText("0" + hours + ":" + minutes + ":" + seconds);
                    } else if (String.valueOf(minutes).length() == 1) {
                        timer_text.setText(hours + ":0" + minutes + ":" + seconds);
                    } else if (String.valueOf(seconds).length() == 1) {
                        timer_text.setText(hours + ":" + minutes + ":0" + seconds);
                    } else {
                        timer_text.setText(hours + ":" + minutes + ":" + seconds);
                    }*/

                }

                @Override
                public void onFinish() {
                    setProgress(progress, endTime);


                }
            };
            countDownTimer.start();
        }/*else {
            Toast.makeText(getApplicationContext(),"Please enter the value",Toast.LENGTH_LONG).show();
        }*/



    public void setProgress(int startTime, int endTime) {
        progressBar.setMax(endTime);
        progressBar.setSecondaryProgress(endTime);
        progressBar.setProgress(startTime);
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
