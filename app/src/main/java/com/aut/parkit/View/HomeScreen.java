package com.aut.parkit.View;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
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

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import com.aut.parkit.Model.DatabaseManagmentSystem.AccountManager;
import com.aut.parkit.Model.DatabaseManagmentSystem.CampusData;
import com.aut.parkit.Model.DatabaseManagmentSystem.CarPark;
import com.aut.parkit.Model.DatabaseManagmentSystem.CarparkManager;
import com.aut.parkit.Model.DatabaseManagmentSystem.ParkingSession;
import com.aut.parkit.Model.DatabaseManagmentSystem.ParkingSpace;
import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.firebase.firestore.GeoPoint;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;
import com.paypal.android.sdk.payments.PayPalAuthorization;
import com.paypal.android.sdk.payments.PayPalConfiguration;
import com.paypal.android.sdk.payments.PayPalFuturePaymentActivity;
import com.paypal.android.sdk.payments.PayPalPayment;
import com.paypal.android.sdk.payments.PayPalService;
import com.paypal.android.sdk.payments.PaymentActivity;
import com.paypal.android.sdk.payments.PaymentConfirmation;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.LinkedList;

import static android.Manifest.permission.ACCESS_COARSE_LOCATION;
import static android.Manifest.permission.ACCESS_FINE_LOCATION;

public class HomeScreen extends AppCompatActivity implements Updatable, LocationListener {
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

    private static final String PAYPALKEY = "AbczlE1gUuUamNWlTatUfGH-GEmzSG6Etz63PoJdSH6g0pQQuL2klMts7lkwafLC1i8eUMKt3OlDrEzq", ENVIROMENT = PayPalConfiguration.ENVIRONMENT_SANDBOX;
    private static final int PAYMENT = 1, FUTUREPAYMENT = 2, LOCATIONCODE = 3;
    private static PayPalConfiguration configuration;
    private PayPalPayment thingsToBuy;
    private FusedLocationProviderClient locationClient;
    private LocationManager locationManager;
    private LocationListener listener;
    private Criteria criteria;
    private int gpsAccuractDelay = 2; //request is every 5 seconds, 5*2 = 10;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_home_screen);

        locationClient = LocationServices.getFusedLocationProviderClient(this);
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        criteria = new Criteria();
        criteria.setAccuracy(Criteria.ACCURACY_FINE);
        criteria.setPowerRequirement(Criteria.POWER_HIGH);
        criteria.setAltitudeRequired(false);
        criteria.setCostAllowed(false);
        criteria.setBearingRequired(false);

        criteria.setHorizontalAccuracy(Criteria.ACCURACY_HIGH);
        criteria.setVerticalAccuracy(Criteria.ACCURACY_HIGH);
        requestLocation();

        configurPayPal();

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
                paymentTotal(i, 0.75);
                String price = "Total: $" + df.format(pay);
                totalPurchase.setText(price);
                currentTime.getTime();
                cTime.setTime(currentTime);

                seekBar.setMax((18 - currentTime.getHours()) * 2);

                if (seekBar.getMaxValue() > 10) {
                    seekBar.setMax(10);
                } else if (currentTime.getMinutes() >= 30 && seekBar.getMaxValue() < 10 && seekBar.getMaxValue() > 0) {
                    seekBar.setMax(seekBar.getMaxValue() - 1);
                } else if (currentTime.getHours() >= 18) {
                    String s6 = "FREE PARKING";
                    String t6 = "Free after 06:00 PM";
                    duration.setText(s6);
                    endTime.setText(t6);
                    seekBar.setMax(0);
                    return;
                }
//                else if (day == 0 || day == 6){
//                    String s6 = "FREE PARKING";
//                    String t6 = "Free on Weekends";
//                    duration.setText(s6);
//                    endTime.setText(t6);
//                    seekBar.setMax(0);
//                    return;
//                }

                if (i == seekBar.getMaxValue() || (currentTime.getHours() + (seekBar.getValue() / 2)) >= 18 || seekBar.getValue() >= 10) {
                    String sMax = "ALL DAY PARKING";
                    String tMax = "Ends at: 06:00 PM";
                    duration.setText(sMax);
                    endTime.setText(tMax);
                } else if (i % 2 == 1 && i != 0) {
                    i = i / 2;
                    time = (i * 60) + 30;
                    cTime.add(Calendar.MINUTE, time);
                    String s30 = i + "h 30m";
                    String t30 = "Ends at: " + sdf.format(cTime.getTime());
                    duration.setText(s30);
                    endTime.setText(t30);
                } else if (i != 0) {
                    i = i / 2;
                    time = (i * 60);
                    cTime.add(Calendar.MINUTE, time);
                    String s = i + "h";
                    String t = "Ends at: " + sdf.format(cTime.getTime());
                    duration.setText(s);
                    endTime.setText(t);
                } else if (i == 0) {
                    duration.setText("$1.50 per hour");
                    endTime.setText("Loading...");
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
                Toast.makeText(getApplicationContext(), "Change Car Registration", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(HomeScreen.this, GaragePopup.class));
            }
        });

        final Activity parent = this;
        strtPark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                locationManager.removeUpdates(HomeScreen.this);
                if (pay == 0) {
                    Toast.makeText(getApplicationContext(), "Please Choose an amount of time", Toast.LENGTH_LONG).show();
                    return;
                }
                //Toast.makeText(getApplicationContext(), "Payment system not working, getting the error \n " +
                //        "java.net.ConnectException: failed to connect to us-central1-autparkitnz.cloudfunctions.net/2404:6800:4006:805::200e (port 443) from /2001:db8:ad:0:ff:: (port 35004) after 10000ms: isConnected failed: ENETUNREACH (Network is unreachable). But our network is enabled as our database is being used so we cannot figure what the error is",
                //        Toast.LENGTH_LONG).show();


                //startActivity(new Intent(HomeScreen.this, PaymentScreen.class));


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CampusData cam = camList.get(camSpin.getSelectedItemPosition());
                        CarPark park = carList.get(camSpin.getSelectedItemPosition());

                        String spaceNumb = space.getText().toString();

                        if (spaceNumb.isEmpty()) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    space.setError("Space Number Required");
                                    space.requestFocusFromTouch();
                                }
                            });
                            return;
                        }

                        try {
                            Integer inte = Integer.getInteger(spaceNumb);
                        } catch (Exception e) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    space.setError("Spaces can only be a number");
                                    space.requestFocusFromTouch();
                                }
                            });
                            return;
                        }

                        ParkingSpace pSpace = CarparkManager.getParkingSpace(cam.getCampusID() + "-" + park.getCarParkID() + "-" + spaceNumb);
                        if (pSpace == null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    space.setError("Spaces does not exist");
                                    space.requestFocusFromTouch();
                                }
                            });
                            return;
                        }

                        makePayment(pay);

//                        Date date = new Date(System.currentTimeMillis());
//                        Date date2 = new Date(date.getTime());
//
//
//                        date2.setHours(date.getHours() + seekBar.getValue()/2);
//                        if (date.getHours() % 2 == 1){
//                            date2.setHours(date2.getHours());
//                            date2.setMinutes(30);
//                        }
//
//                        ParkingSession session = new ParkingSession(user.getDefaultVehicle().getNumberPlate(), cam.getCampusID() + "-" +park.getCarParkID()+ "-" + spaceNumb, date, date2, park.getCarParkID(), cam.getCampusID());
//                        CarparkManager.addParkingSessionToDB(session);
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

    private void configurPayPal() {
        configuration = new PayPalConfiguration()
                .environment(ENVIROMENT)
                .clientId(PAYPALKEY)
                .merchantName("Paypal Login");
    }

    private void makePayment(double payment) {
        Intent intent = new Intent(this, PayPalService.class);
        intent.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startService(intent);
        thingsToBuy = new PayPalPayment(new BigDecimal(String.valueOf(((payment * 1.034) + 0.43) * 0.93)), "AUD", "Payment", PayPalPayment.PAYMENT_INTENT_SALE);
        Intent paymentAct = new Intent(this, PaymentActivity.class);
        paymentAct.putExtra(PaymentActivity.EXTRA_PAYMENT, thingsToBuy);
        paymentAct.putExtra(PayPalService.EXTRA_PAYPAL_CONFIGURATION, configuration);
        startActivityForResult(paymentAct, PAYMENT);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PaymentConfirmation confirm = data.getParcelableExtra(PaymentActivity.EXTRA_RESULT_CONFIRMATION);

                if (confirm != null) {
                    try {
                        System.out.println(confirm.toJSONObject().toString(4));
                        System.out.println(confirm.getPayment().toJSONObject().toString(4));
                        Toast.makeText(this, "Payment successful", Toast.LENGTH_SHORT).show();

                        CampusData cam = camList.get(camSpin.getSelectedItemPosition());
                        CarPark park = carList.get(camSpin.getSelectedItemPosition());

                        String spaceNumb = space.getText().toString();

                        Date date = new Date(System.currentTimeMillis());
                        Date date2 = new Date(date.getTime());


                        date2.setHours(date.getHours() + seekBar.getValue() / 2);
                        if (date.getHours() % 2 == 1) {
                            date2.setHours(date2.getHours());
                            date2.setMinutes(30);
                        }


                        ParkingSession session = new ParkingSession(user.getDefaultVehicle().getNumberPlate(), cam.getCampusID() + "-" + park.getCarParkID() + "-" + spaceNumb, date, date2, park.getCarParkID(), cam.getCampusID());
                        AccountManager.addParkingSession(session);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                Intent intent = new Intent(getApplicationContext(), RemainingTimeScreen.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                                startActivity(intent);
                            }
                        }).start();

                        CarparkManager.addParkingSessionToDB(session);
                    } catch (Exception e) {
                        System.out.println(e.toString());
                    }
                }
            } else if (resultCode == Activity.RESULT_CANCELED) {
                Toast.makeText(this, "Payment has been cancelled", Toast.LENGTH_SHORT).show();
            } else if (resultCode == PaymentActivity.RESULT_EXTRAS_INVALID) {
                Toast.makeText(this, "An Error occurred", Toast.LENGTH_SHORT).show();
            }
        } else if (resultCode == FUTUREPAYMENT) {
            if (resultCode == Activity.RESULT_OK) {
                PayPalAuthorization authorization = data.getParcelableExtra(PayPalFuturePaymentActivity.EXTRA_RESULT_AUTHORIZATION);
                if (authorization != null) {
                    String authorisationCode = authorization.getAuthorizationCode();
                }
            }
        }
    }

    @Override
    public void update() {
        rego.setText(user.getDefaultVehicle().getNumberPlate());
    }

    public double paymentTotal(int roller, double price) {
        pay = roller * price;
        return pay;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){
            case R.id.settingsMenu:
                startActivity(new Intent(HomeScreen.this, MenuScreen.class));
                return true;

            case R.id.CarparkItemMenu:
                Intent intent = new Intent(HomeScreen.this, ViewEmptySpace.class);
                CampusData cam = camList.get(camSpin.getSelectedItemPosition());
                intent.putExtra(ViewEmptySpace.CampusKey, cam.getCampusID());
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void populateCampusSpinner() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<CampusData> list = CarparkManager.getAllCampusesFromDB();
                camList = list;
                String[] nameList = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
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

    private void populateCarParkSpinner(String campusID) {
        final CampusData campus = CarparkManager.getCampus(campusID);
        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<CarPark> list = CarparkManager.getAllCarparks(campus.getCampusID());

                if (list.isEmpty()) {
                    list = CarparkManager.getAllCarparksFromDB(campus.getCampusID());
                }

                carList = list;

                String[] nameList = new String[list.size()];

                for (int i = 0; i < list.size(); i++) {
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

    private void chooseCarpark(Location userLoc) {
        if (gpsAccuractDelay > 0){
            gpsAccuractDelay--;
            return;
        }
        //TODO: Decide what carpark here;
        final Location userLocation = userLoc;

        new Thread(new Runnable() {
            @Override
            public void run() {

                for(int i = 0; i < carList.size(); i++){
                    CarPark carpark = carList.get(i);
                    GeoPoint tpL = carpark.getTopLeftCorner();
                    GeoPoint boR = carpark.getBottomRightCorner();

                    double userlat =  userLocation.getLatitude();
                    double userLong =  userLocation.getLongitude();

                    if (tpL.getLatitude() > userlat && tpL.getLongitude() < userLong){
                        if ( boR.getLatitude() < userlat && boR.getLongitude() > userLong){

                            final int carparkSelection = i;
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    carSpin.setSelection(carparkSelection);
                                }
                            });
                            return;
                        }
                    }
                }
            }
        }).start();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == LOCATIONCODE){
            requestLocation();
        }
    }


    @Override
    public void onLocationChanged(Location location) {
        chooseCarpark(location);
    }

    @Override
    public void onStatusChanged(String s, int i, Bundle bundle) {
    }

    @Override
    public void onProviderEnabled(String s) {

    }

    @Override
    public void onProviderDisabled(String s) {

    }

    private void requestLocation(){
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    Activity#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for Activity#requestPermissions for more details.
            ActivityCompat.requestPermissions(HomeScreen.this, new String[]{ACCESS_FINE_LOCATION, ACCESS_COARSE_LOCATION}, LOCATIONCODE);
            return;
        }
        locationManager.requestLocationUpdates(5000, 1, criteria, HomeScreen.this, HomeScreen.this.getMainLooper());
    }
}
