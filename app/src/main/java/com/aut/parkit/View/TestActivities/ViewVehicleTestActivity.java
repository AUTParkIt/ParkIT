package com.aut.parkit.View.TestActivities;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.Model.DatabaseManagmentSystem.Vehicle;
import com.aut.parkit.R;

public class ViewVehicleTestActivity extends AppCompatActivity {

    private EditText numberPlate_EditText;
    private Button findVehicle_Button;
    private TextView numberPlate_TextView, vehicleName_TextView, ownerID_TextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_vehicle_test);

        this.findVehicle_Button = findViewById(R.id.findVehicle_button_viewVehicle);
        this.numberPlate_EditText = findViewById(R.id.numberPlate_editText_viewVehicle);

        this.numberPlate_TextView = findViewById(R.id.numberPlate_textView_viewVehicle);
        this.ownerID_TextView = findViewById(R.id.ownerID_textView_viewVehicle);
        this.vehicleName_TextView = findViewById(R.id.name_textView_viewVehicle);

        findVehicle_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String numberPlate = numberPlate_EditText.getText().toString();

                        if (numberPlate.length() == 0){
                            numberPlate_EditText.setError("Licence Number Required");
                            numberPlate_EditText.requestFocusFromTouch();
                            return;
                        }

                        User user = new User();
                        final Vehicle v = user.getVehicle(numberPlate);



                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                if (v == null){
                                    Toast.makeText(ViewVehicleTestActivity.this, "No Vehicle with this Licence Number Exists", Toast.LENGTH_LONG).show();
                                    return;
                                }

                                numberPlate_TextView.setText(v.getNumberPlate());
                                vehicleName_TextView.setText(v.getVehicleName());
                                ownerID_TextView.setText(v.getOwnerID());
                            }
                        });
                    }
                }).start();
            }
        });

    }
}
