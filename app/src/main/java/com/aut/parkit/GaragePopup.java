package com.aut.parkit;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.Model.DatabaseManagmentSystem.Vehicle;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GaragePopup extends Activity {
    User user;
    ListView garage;
    TextView rego;
    LinkedList<Vehicle> carList;
    ArrayAdapter<String> adapter;
    EditText input;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_garage_popup);


        rego = findViewById(R.id.carRegisText);
        new Thread(new Runnable() {
            @Override
            public void run() {
                user = new User();

                carList = user.getGarage();

                for(Vehicle v : carList){
                    //garage.setText(v.getNumberPlate());
                }
            }
        }).start();
        //Resources res = getResources();
    }

    public void update() {
        rego.setText(user.getDefaultVehicle().getNumberPlate());
    }
}
