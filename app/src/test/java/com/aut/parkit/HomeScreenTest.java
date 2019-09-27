package com.aut.parkit;

import android.os.Build;
import android.widget.Button;
import android.widget.TextView;

import com.aut.parkit.View.HomeScreen;
import com.jesusm.holocircleseekbar.lib.HoloCircleSeekBar;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;

import java.util.Calendar;
import java.util.Date;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class HomeScreenTest {
    private HomeScreen activity;
    private HoloCircleSeekBar seekBar;
    private Date d;
    private TextView register, duration, tDuration, payment;
    private Button change, confirmPark;

    @Before
    public void setUp(){
        activity = Robolectric.buildActivity(HomeScreen.class)
                .create()
                .resume()
                .get();
        seekBar = activity.findViewById(R.id.durationSeekbar);
        register = activity.findViewById(R.id.carRegisText);
        duration = activity.findViewById(R.id.valueText);
        tDuration = activity.findViewById(R.id.durationEndsText);
        payment = activity.findViewById(R.id.totalPayText);
        change = activity.findViewById(R.id.changeRegisBtn);
        confirmPark = activity.findViewById(R.id.startParkBtn);

        d = Calendar.getInstance().getTime();
        Calendar cal = Calendar.getInstance();
        cal.setTime(d);
    }
    @After
    public void tearDown(){
        activity = null;
    }

    public void correctPaymentTotal(){
        Assert.assertTrue(payment.getText().equals(activity.paymentTotal(seekBar.getValue(), 0.75)));
    }
}
