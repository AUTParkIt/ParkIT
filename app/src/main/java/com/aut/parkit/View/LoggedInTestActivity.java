package com.aut.parkit.View;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.R;

public class LoggedInTestActivity extends AppCompatActivity implements Updatable{

    private TextView logInView;
    private User user;
    private Button AddVehicleBtn, viewVehicleBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_logged_in_test);
        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                user = new User(LoggedInTestActivity.this);

                logInView = findViewById(R.id.textView_loginActivity);

                logInView.setText("Welcome " + user.getFirstName() + " " + user.getLastName());
            }
        });
        t.start();

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
