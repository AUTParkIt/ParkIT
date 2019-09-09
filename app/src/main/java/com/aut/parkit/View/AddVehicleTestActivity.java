package com.aut.parkit.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.AccountManager;
import com.aut.parkit.Model.DatabaseManagmentSystem.Vehicle;
import com.aut.parkit.R;
import com.google.firebase.auth.FirebaseAuth;

public class AddVehicleTestActivity extends AppCompatActivity {

    private EditText numbPlateText, vehicleNameText;
    private Button addVehicleButton, cancleButton;
    private FirebaseAuth mAuth = FirebaseAuth.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_vehicle_test);

        numbPlateText = findViewById(R.id.NumberPlate_EditText_AddVehicle);
        vehicleNameText = findViewById(R.id.VehicleName_EditText_AddVehicle);

        addVehicleButton = findViewById(R.id.AddVehicle_Button_AddVehicle);
        cancleButton = findViewById(R.id.Cancel_Button_AddVehicle);

        addVehicleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String numb = numbPlateText.getText().toString();
                String name = vehicleNameText.getText().toString();

                if (numb.length() == 0){
                    numbPlateText.setError("Number Plate Required");
                    numbPlateText.requestFocusFromTouch();
                    return;
                }
                if (name.length() == 0){
                    name = numb;
                }

                Vehicle v = new Vehicle(numb, name, mAuth.getUid());

                AccountManager.addVehicle(v);

                numbPlateText.setText("");
                vehicleNameText.setText("");
                Toast.makeText(AddVehicleTestActivity.this, "Your Ride has Been Added", Toast.LENGTH_SHORT).show();
            }
        });

        cancleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }
}
