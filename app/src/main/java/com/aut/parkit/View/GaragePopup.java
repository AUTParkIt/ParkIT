package com.aut.parkit.View;

import android.app.Activity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.R;

import java.util.ArrayList;
import java.util.Arrays;

public class GaragePopup extends Activity {
    User user;
    ListView garage;
    TextView rego;
    ArrayList<String> carList;
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
            }
        }).start();
        String[] cars = {user.getGarage().toString()};
        carList = new ArrayList<>(Arrays.asList(cars));
        //Resources res = getResources();
        garage = findViewById(R.id.garageListView);
    }

    public void update() {
        rego.setText(user.getDefaultVehicle().getNumberPlate());
    }
}
