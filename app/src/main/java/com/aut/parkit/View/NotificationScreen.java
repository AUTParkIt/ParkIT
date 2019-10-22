package com.aut.parkit.View;

import android.os.Bundle;
import android.widget.CompoundButton;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.aut.parkit.Model.DatabaseManagmentSystem.User;
import com.aut.parkit.R;

public class NotificationScreen extends AppCompatActivity {
    protected static Switch expire, bexpire;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_notification_screen);
        expire = (Switch) findViewById(R.id.expireSwitch2);
        bexpire = (Switch) findViewById(R.id.expireSwitch);

        expire.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final boolean value = b;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        user.setExpireNotifiction(value);
                        user.pushUser();
                    }
                }).start();
            }
        });

        bexpire.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton compoundButton, boolean b) {
                final boolean value = b;

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        user.setBreachNotifiction(value);
                        user.pushUser();
                    }
                }).start();
            }
        });
        new Thread(new Runnable() {
            @Override
            public void run() {
                user = new User();

                final boolean brech = user.isBreachNoticeNotification();
                final boolean expirebool = user.isExpireWarningNotification();

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        expire.setChecked(expirebool);
                        bexpire.setChecked(brech);
                    }
                });

            }
        }).start();
    }
}
