package com.aut.parkit.View;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.CampusData;
import com.aut.parkit.Model.DatabaseManagmentSystem.CarPark;
import com.aut.parkit.Model.DatabaseManagmentSystem.CarparkManager;
import com.aut.parkit.Model.DatabaseManagmentSystem.ParkingSession;
import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.Model.DatabaseManagmentSystem.Vehicle;
import com.aut.parkit.R;

import java.util.Date;
import java.util.LinkedList;

public class LoggedInTestActivity extends AppCompatActivity implements Updatable {

    private TextView logInView;
    private User user;
    private Button AddVehicleBtn, viewVehicleBtn,bookButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_test);

        bookButton = findViewById(R.id.parkingSessionButton);

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                user = new User(LoggedInTestActivity.this);

                logInView = findViewById(R.id.textView_loginActivity);

                logInView.setText("Welcome " + user.getFirstName() + " " + user.getLastName());

                LinkedList<Vehicle> gar = (LinkedList<Vehicle>) user.getGarage().clone();

                for (Vehicle v : gar){
                    Log.i("Ve:", v.toString());
                }
            }
        });
        t.start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                LinkedList<CampusData> campus = CarparkManager.getAllCampusesFromDB();

                for (CampusData c : campus) {
                    LinkedList<CarPark> carParks = CarparkManager.getAllCarparksFromDB(c.getCampusID());

                    for (CarPark cp : carParks) {
                        Log.i(c.getCampusID(), cp.getCarParkID());
                    }
                }


            }
        }).start();

        this.AddVehicleBtn = findViewById(R.id.addVehicleBtn_LoggedInScreen);
        this.viewVehicleBtn = findViewById(R.id.viewVehicle_Button_loggedIn);

        this.AddVehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoggedInTestActivity.this, AddVehicleTestActivity.class));
            }
        });

        this.viewVehicleBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(LoggedInTestActivity.this, ViewVehicleTestActivity.class));
            }
        });

        this.bookButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        CampusData cam = CarparkManager.getCampus("Manukau");
                        Date date = new Date(System.currentTimeMillis());
                        Date date2 = new Date(date.getTime());
                        date2.setHours(date.getHours() + 4);
                        CarPark park = CarparkManager.getCarPark(cam.getCampusID(), cam.getCampusID() + "-A");
                        ParkingSession session = new ParkingSession(user.getDefaultVehicle().getNumberPlate(), cam.getCampusID() + "-"+ park.getCarParkID() + "-1", date, date2, park.getCarParkID(), cam.getCampusID());
                        CarparkManager.addParkingSessionToDB(session);
                    }
                }).start();
            }
        });
    }

    @Override
    public void update() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                logInView.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
            }
        }).start();
    }
}
