package com.aut.parkit.View;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.CarPark;
import com.aut.parkit.Model.DatabaseManagmentSystem.CarparkManager;
import com.aut.parkit.R;

import java.util.LinkedList;

public class ViewEmptySpace extends AppCompatActivity {

    private TextView car1, space1, car2, space2;
    private LinkedList<CarPark> cars;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_empty_space);

        car1 = findViewById(R.id.carPark1View);
        space1 = findViewById(R.id.carPark1Space);
        car2 = findViewById(R.id.carPark2View);
        space2 = findViewById(R.id.carPark1Space);

        new Thread(new Runnable() {
            @Override
            public void run() {
                cars = CarparkManager.getAllCarparks("Manukau");

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        car1.setText(cars.get(1).getCarParkID());
                        space1.setText(cars.get(1).getFreeSpaces() + "/" + cars.get(2).getTotalSpaces());
                        car2.setText(cars.get(2).getCarParkID());
                        space2.setText(cars.get(2).getFreeSpaces() + "/" + cars.get(2).getTotalSpaces());
                    }
                });
            }
        }).start();
    }
}
