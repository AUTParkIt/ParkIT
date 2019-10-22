package com.aut.parkit.View;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.R;

public class UpdateDetailsScreen extends AppCompatActivity
{
    private EditText fName, lName;
    Button cancel, update_details;
    private String firstName, lastName;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_update_details_screen);

        setupUI();

        new Thread(new Runnable() {
            @Override
            public void run() {
                user = new User();
                firstName = user.getFirstName();
                lastName = user.getLastName();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        fName.setText(firstName);
                        lName.setText(lastName);
                    }
                });
            }
        }).start();
    }

    public void setupUI()
    {
        fName = findViewById(R.id.firstNameUpdate);
        lName = findViewById(R.id.lastNameUpdate);
        cancel = findViewById(R.id.cancel);
        update_details = findViewById(R.id.updateName);

        cancel.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        update_details.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firName = fName.getText().toString();
                String lasName = lName.getText().toString();

                if (!firName.isEmpty()){
                    firstName = firName;
                }

                if (!lasName.isEmpty()){
                    lastName = lasName;
                }

                user.setFirstName(firstName);
                user.setLastName(lastName);

                user.pushUser();
            }
        });
    }
}
