package com.aut.parkit.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.AccountManager;
import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.Model.DatabaseManagmentSystem.Vehicle;
import com.aut.parkit.R;

import java.util.LinkedList;

public class GaragePopup extends AppCompatActivity {
    User user;
    TextView tv1, tv2, tv3, tv4, tv5;
    LinkedList<Vehicle> gar;
    EditText name, licencePlate;
    Button addButton, removeButton;

    private ProgressBar loadBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_garage_popup);

        tv1 = findViewById(R.id.carTView1);
        tv2 = findViewById(R.id.carTView2);
        tv3 = findViewById(R.id.carTView3);
        tv4 = findViewById(R.id.carTView4);
        tv5 = findViewById(R.id.carTView5);

        addButton = findViewById(R.id.addButton);
        removeButton = findViewById(R.id.removeButton);

        name = findViewById(R.id.carName);
        licencePlate = findViewById(R.id.carRego);

        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String licPlate = licencePlate.getText().toString().trim().toUpperCase();
                        String carName = name.getText().toString();

                        if (licPlate.isEmpty()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    licencePlate.setError("Licence Plate Required");
                                    licencePlate.requestFocusFromTouch();
                                }
                            });

                            return;
                        }

                        if (licPlate.length() > 6){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    licencePlate.setError("No more than 6 Characters Allowed");
                                    licencePlate.requestFocusFromTouch();
                                }
                            });

                            return;
                        }

                        for (char c: licPlate.toCharArray()){
                            if (!Character.isLetterOrDigit(c)){
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        licencePlate.setError("Alphanumerical Characters Only");
                                        licencePlate.requestFocusFromTouch();
                                    }
                                });

                                return;
                            }
                        }



                        if (gar.size() >= 5){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    licencePlate.setError("Maximum Cars Reached");
                                    licencePlate.requestFocusFromTouch();
                                }
                            });

                            return;
                        }

                        if (carName.isEmpty()){
                            carName = licPlate;
                        }
                        Vehicle v = new Vehicle(licPlate, carName, user.getUserID());
                        AccountManager.addVehicle(v);
                        user.pushUser();

                        gar = user.getGarage();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setText();
                                licencePlate.setText("");
                            }
                        });
                    }
                }).start();
            }
        });

        removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        final String licPlate = licencePlate.getText().toString().trim().toUpperCase();

                        if (licPlate.isEmpty()){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    licencePlate.setError("Licence Plate Required");
                                    licencePlate.requestFocusFromTouch();
                                }
                            });

                            return;
                        }

                        if (gar.size() == 1){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    licencePlate.setError("You must have at least one Vehicle");
                                    licencePlate.requestFocusFromTouch();
                                }
                            });

                            return;
                        }

                        Vehicle v = user.getVehicle(licPlate);

                        if (v == null){
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    licencePlate.setError("No Car with that Licence Plate Exists");
                                    licencePlate.requestFocusFromTouch();
                                }
                            });

                            return;
                        }

                        if (user.getDefaultVehicle().getNumberPlate().contentEquals(v.getNumberPlate())){
                            user.setDefaultVehicle(gar.get(1));
                        }

                        user.getGarage().remove(v);
                        AccountManager.removeVehicle(v);
                        user.pushUser();

                        gar = user.getGarage();

                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                setText();
                                licencePlate.setText("");
                            }
                        });

                    }
                }).start();
            }
        });

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicle v = gar.get(0);

                if (v != null) {
                    user.setDefaultVehicle(v);
                    user.pushUser();
                    finish();
                }
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicle v = gar.get(1);

                if (v != null) {
                    user.setDefaultVehicle(v);
                    user.pushUser();
                    finish();
                }
            }
        });

        tv3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicle v = gar.get(2);

                if (v != null) {
                    user.setDefaultVehicle(v);
                    user.pushUser();
                    finish();
                }
            }
        });

        tv4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicle v = gar.get(3);

                if (v != null) {
                    user.setDefaultVehicle(v);
                    user.pushUser();
                    finish();
                }
            }
        });

        tv5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Vehicle v = gar.get(4);

                if (v != null) {
                    user.setDefaultVehicle(v);
                    user.pushUser();
                    finish();
                }
            }
        });



        loadBar = findViewById(R.id.progressBar4);
        loadBar.setVisibility(View.VISIBLE);

        new Thread(new Runnable() {
            @Override
            public void run() {
                user = new User();

                gar = user.getGarage();

                setText();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        loadBar.setVisibility(View.GONE);
                    }
                });
            }
        }).start();

//        String[] cars = {user.getGarage().toString()};
//        carList = new ArrayList<>(Arrays.asList(cars));
//        //Resources res = getResources();

    }

    public void sortGarage(){
        LinkedList<Vehicle> soGar = new LinkedList<>();

        for (Vehicle v : gar){
            if (user.getDefaultVehicle().getNumberPlate().contentEquals(v.getNumberPlate())){
                soGar.add(v);
                gar.remove(v);

                break;
            }
        }

        for (Vehicle v : gar){
            soGar.add(v);
        }

        gar = soGar;
    }

    public void setText(){
        sortGarage();

        for (int i = 0; i < 5; i++){

            Vehicle v;

            if (i < gar.size()){
                v = gar.get(i);
            }
            else {
                v = null;
            }

            switch (i){
                case 0:
                    if (v != null){
                        tv1.setText(v.getVehicleName() + "      "+ v.getNumberPlate() + "       Default");
                    }
                    else {
                        tv1.setText("");
                    }
                    break;

                case 1:
                    if (v != null){
                        tv2.setText(v.getVehicleName() + "      "+ v.getNumberPlate());
                    }
                    else {
                        tv2.setText("");
                    }
                    break;

                case 2:
                    if (v != null){
                        tv3.setText(v.getVehicleName() + "      "+ v.getNumberPlate());
                    }
                    else {
                        tv3.setText("");
                    }
                    break;

                case 3:
                    if (v != null){
                        tv4.setText(v.getVehicleName() + "      "+ v.getNumberPlate());
                    }
                    else {
                        tv4.setText("");
                    }
                    break;

                case 4:
                    if (v != null){
                        tv5.setText(v.getVehicleName() + "      "+ v.getNumberPlate());
                    }
                    else {
                        tv5.setText("");
                    }
                    break;
            }
        }
    }
}
