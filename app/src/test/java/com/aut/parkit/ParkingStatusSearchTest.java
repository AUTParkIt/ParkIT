package com.aut.parkit;

import com.aut.parkit.Model.PEO.ParkingStatusSearch;
import com.aut.parkit.Model.ParkingSession;
import org.apache.tools.ant.util.DateUtils;
import org.junit.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.TimeUnit;

import static org.junit.Assert.*;

public class ParkingStatusSearchTest {
    private ParkingStatusSearch search;
    private ParkingSession testSession;
    private String tSessionID, tUserID, tLicencePlate, tParkingSpaceID, tCarParkID, tCampusID;
    private Date tStartTime, tEndTime;

    @Before
    public void setUp()
    {
        search = new ParkingStatusSearch();
        tSessionID = "TestSessionID";
        tUserID = "TestUserID";
        tLicencePlate = "TEST01";
        tParkingSpaceID = "A01";
        tCarParkID = "CarparkA";
        tCampusID = "South";

        tStartTime = new Date();
        Calendar c = Calendar.getInstance();
        c.setTime(tStartTime);
        c.add(Calendar.HOUR_OF_DAY, 1);
        tEndTime = c.getTime();

        testSession = new ParkingSession(tSessionID,tUserID,tParkingSpaceID, tStartTime, tEndTime, tCarParkID, tCampusID);
    }

/*    @Test
    public void licenceTooShort(){
        String response = search.searchforLicence("L8");
        assertTrue(response == "Invalid licence plate entered: Licence plate number must be 2-6 characters in length");
    }

    @Test
    public void licenceTooLong(){
        String response = search.searchforLicence("LN9283NDKJ");
        assertTrue(response == "Invalid licence plate entered: Licence plate number must be 2-6 characters in length");
    }

    @Test
    public void spaceIdDoesntStartWithLetter(){
        String response = search.searchForSpace("890");
        assertTrue(response == "Invalid carpark space ID entered: Space ID must begin with a letter (A-Z)");
    }

    @Test
    public void spaceIdTooShort(){
        String response = search.searchForSpace("N");
        assertTrue(response == "Invalid carpark space ID entered: Space ID must be 2-3 characters in length");
    }

    @Test
    public void spaceIdTooLong(){
        String response = search.searchForSpace("B111");
        assertTrue(response == "Invalid carpark space ID entered: Space ID must be 2-3 characters in length");
    }

    @Test
    public void licenceIsntActive(){
        String response = search.searchForLicence("GJD626");
        assertTrue(response == "There is no active parking session for GJD626");
    }

    @Test
    public void spaceIsntActive(){
        String response = search.searchForSpace("B11");
        assertTrue(response == "There is no active parking session for carpark space B11");
    }*/

    @Test
    public void licenceSearchReturnsCorrectParkingSession(){

    }

    @Test
    public void spaceSearchReturnsCorrectParkingSession(){

    }
}
