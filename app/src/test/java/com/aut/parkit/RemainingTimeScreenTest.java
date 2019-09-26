package com.aut.parkit;

import android.os.Build;
import android.widget.TextView;
import com.aut.parkit.Model.DatabaseManagmentSystem.ParkingSession;
import com.aut.parkit.View.RemainingTimeScreen;
import org.junit.*;
import org.junit.runner.RunWith;
import org.robolectric.Robolectric;
import org.robolectric.RobolectricTestRunner;
import org.robolectric.annotation.Config;
import java.util.Calendar;
import java.util.Date;
import static org.junit.Assert.*;

@RunWith(RobolectricTestRunner.class)
@Config(sdk = Build.VERSION_CODES.P)
public class RemainingTimeScreenTest {

    private RemainingTimeScreen activity;
    private ParkingSession session;
    private Date testStartTime, testEndTime;
    private TextView licencePlateText, parkingSpaceText, startTimeText, endTimeText, timeRemainingText;

    @Before
    public void setUp(){
        activity = Robolectric.buildActivity(RemainingTimeScreen.class)
                .create()
                .resume()
                .get();

        licencePlateText = (TextView) activity.findViewById(R.id.licence_plate);
        parkingSpaceText = (TextView) activity.findViewById(R.id.parking_space);
        startTimeText = (TextView) activity.findViewById(R.id.start_time);
        endTimeText = (TextView) activity.findViewById(R.id.end_time);
        timeRemainingText = (TextView) activity.findViewById(R.id.time_remaining);

        testStartTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(testStartTime);
        c.add(Calendar.HOUR_OF_DAY, 1);
        testEndTime = c.getTime();

        session = new ParkingSession(
                "testSessionID",
                "testUserID",
                "testNumberPlate",
                "testParkingSpaceID",
                testStartTime,
                testEndTime,
                "testCarParkId",
                "testCampusID");
    }

    @After
    public void tearDown(){
        activity = null;
    }

    @Test
    public void correctLicencePlateRetrieved(){
        Assert.assertTrue(licencePlateText.getText() == "testNumberPlate");
    }

    @Test
    public void correctParkingSpaceRetrieved(){
        assertTrue(parkingSpaceText.getText() == "testParkingSpaceID");
    }

    @Test
    public void correctStartTimeRetrieved(){
        assertTrue(startTimeText.getText() == "testStartTime");
    }

    @Test
    public void correctEndTimeRetrieved(){
        assertTrue(endTimeText.getText() == "testEndtime");
    }

    @Test
    public void remainingTimeCalculatedCorrectly(){
        //activity.calculateTimeDifference();
        assertTrue(endTimeText.getText() == "testEndtime");
    }

    @Test
    public void correctRemainingTimeCalculated(){
        assertTrue(timeRemainingText.getText() == "testRemainingTime");
    }
}
