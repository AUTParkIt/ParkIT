package com.aut.parkit.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.CampusData;
import com.aut.parkit.Model.DatabaseManagmentSystem.CarPark;
import com.aut.parkit.Model.DatabaseManagmentSystem.CarparkManager;
import com.aut.parkit.Model.DatabaseManagmentSystem.ParkingSession;
import com.aut.parkit.Model.DatabaseManagmentSystem.ParkingSpace;
import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.R;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

public class HomeScreen extends AppCompatActivity implements Updatable{
    protected Date currentTime;
    protected Calendar cTime;
    protected SimpleDateFormat sdf = new SimpleDateFormat("hh:mm aa");
    protected DecimalFormat df = new DecimalFormat("0.00");
    protected HoloCircleSeekBar seekBar;
    protected TextView rego, endTime, totalPurchase, duration;
    protected EditText space;
    protected Button strtPark, change;
    protected User user;
    private int day, time;
    private ProgressBar loadBar;
    static double pay = 0;
    private Spinner camSpin, carSpin;
    private LinkedList<CampusData> camList;
    private LinkedList<CarPark> carList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_home_screen);

        rego = findViewById(R.id.carRegisText);

        loadBar = findViewById(R.id.progressBar2);
        loadBar.setVisibility(View.VISIBLE);
        camSpin = findViewById(R.id.spinner);
        carSpin = findViewById(R.id.spinner2);

        new Thread(new Runnable() {
            @Override
            public void run() {
                user = new User(HomeScreen.this);
                populateCampusSpinner();
                populateCarParkSpinner("Manukau");

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
                else if (currentTime.getMinutes() >= 30 && seekBar.getMaxValue() < 10 && seekBar.getMaxValue() > 0) {
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
                Toast.makeText(getApplicationContext(), "Change Car Registration", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeScreen.this, GaragePopup.class));
            }
        });
        strtPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (pay == 0){
                    Toast.makeText(getApplicationContext(), "Please Choose an amount of time",Toast.LENGTH_LONG).show();
                    return;
                }
                Toast.makeText(getApplicationContext(), "Payment system not working, getting the error \n " +
                        "java.net.ConnectException: failed to connect to us-central1-autparkitnz.cloudfunctions.net/2404:6800:4006:805::200e (port 443) from /2001:db8:ad:0:ff:: (port 35004) after 10000ms: isConnected failed: ENETUNREACH (Network is unreachable). But our network is enabled as our database is being used so we cannot figure what the error is",
                        Toast.LENGTH_LONG).show();


                //startActivity(new Intent(HomeScreen.this, PaymentScreen.class));

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CampusData cam = camList.get(camSpin.getSelectedItemPosition());
                        CarPark park = carList.get(camSpin.getSelectedItemPosition());

                        String spaceNumb = space.getText().toString();

                        if (spaceNumb.isEmpty()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    space.setError("Space Number Required");
                                    space.requestFocusFromTouch();
                                }
                            });
                        }

                        try {
                            Integer inte = Integer.getInteger(spaceNumb);
                        }
                        catch (Exception e){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    space.setError("Spaces can only be a number");
                                    space.requestFocusFromTouch();
                                }
                            });
                        }

                        ParkingSpace pSpace = CarparkManager.getParkingSpace(cam.getCampusID() + "-" +park.getCarParkID()+ "-" + spaceNumb);
                        if (pSpace == null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    space.setError("Spaces does not exist");
                                    space.requestFocusFromTouch();
                                }
                            });
                        }

                        Date date = new Date(System.currentTimeMillis());
                        Date date2 = new Date(date.getTime());


                        date2.setHours(date.getHours() + seekBar.getValue()/2);
                        if (date.getHours() % 2 == 1){
                            date2.setHours(date2.getHours());
                            date2.setMinutes(30);
                        }

                        ParkingSession session = new ParkingSession(user.getDefaultVehicle().getNumberPlate(), cam.getCampusID() + "-" +park.getCarParkID()+ "-" + spaceNumb, date, date2, park.getCarParkID(), cam.getCampusID());
                        CarparkManager.addParkingSessionToDB(session);
                    }
                }).start();

            }
        });

        camSpin.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                populateCarParkSpinner(camList.get(i).getCampusID());
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

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

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.action_myaccount:
                startActivity(new Intent(HomeScreen.this, MenuScreen.class));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateCampusSpinner(){
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<CampusData> list = CarparkManager.getAllCampusesFromDB();
                camList = list;
                String[] nameList = new String[list.size()];

                for (int i = 0; i < list.size(); i++){
                    nameList[i] = list.get(i).getCampusID();
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, nameList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        camSpin.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }

    private void populateCarParkSpinner(String campusID){
        final CampusData campus = CarparkManager.getCampus(campusID);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<CarPark> list = CarparkManager.getAllCarparks(campus.getCampusID());

                if (list.isEmpty()){
                    list = CarparkManager.getAllCarparksFromDB(campus.getCampusID());
                }

                carList = list;

                String[] nameList = new String[list.size()];

                for(int i = 0; i < list.size(); i++){
                    nameList[i] = list.get(i).getCarParkID();
                }

                final ArrayAdapter<String> adapter = new ArrayAdapter<>(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, nameList);
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        carSpin.setAdapter(adapter);
                    }
                });
            }
        }).start();
    }


}
