package com.aut.parkit.View;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.widget.Switch;

import androidx.appcompat.app.ActionBar;
import androidx.core.app.NotificationCompat;

import com.aut.parkit.R;

public class NotificationScreen extends RemainingTimeScreen {
    protected Switch expire, bexpire;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM | ActionBar.DISPLAY_SHOW_HOME);
        getSupportActionBar().setCustomView(R.layout.actionbar_title);
        setContentView(R.layout.activity_notification_screen);
        expire = (Switch) findViewById(R.id.expireSwitch);
        bexpire = (Switch) findViewById(R.id.expireSwitch2);



    }

    //Function creates an Expiry Notification, as to when a driver's parking session has expired
    private void expireNotification(){
        //First build the notification
        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Parking Session has Expired")
                .setContentText("Your parking session has now expired. " +
                        "\nPlease purchase another parking session to avoid being fined.");

        //Create an intent in order to display the notification
        Intent intent = new Intent(this, HomeScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(contentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }

    /*Function creates a Notification, that notifies the driver that their parking session is about
    to expire soon*/
    private void beforeExpireNotification(){
        Intent intent = new Intent(this, RemainingTimeScreen.class);
        PendingIntent contentIntent = PendingIntent.getActivity(this, 0, intent,
                PendingIntent.FLAG_UPDATE_CURRENT);

        Intent homeIntent = new Intent(this, HomeScreen.class);
        PendingIntent homeContentIntent = PendingIntent.getActivity(this, 0,
                homeIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.mipmap.ic_launcher_round)
                .setContentTitle("Parking Session is about to Expire")
                .setContentText("Your parking session is about to expire. " +
                        "\nPlease extend your current parking session to avoid being fined.")
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setAutoCancel(true)
                .setOnlyAlertOnce(true)
                .setColor(Color.MAGENTA)
                .addAction(R.mipmap.ic_launcher_round, "EXTEND", homeContentIntent);

        builder.setContentIntent(contentIntent);
        builder.setContentIntent(homeContentIntent);
        NotificationManager manager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(0, builder.build());
    }
}
