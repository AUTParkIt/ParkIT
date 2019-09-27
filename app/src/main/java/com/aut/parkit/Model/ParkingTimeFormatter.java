package com.aut.parkit.Model;

import com.google.firebase.Timestamp;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class ParkingTimeFormatter {

    public String convertTimestampToString(Timestamp time){
        SimpleDateFormat formattedTime = new SimpleDateFormat("h:mm aa");
        Calendar cal = Calendar.getInstance();
        cal.setTimeInMillis(time.getSeconds()*1000);
        return formattedTime.format(cal.getTime());
    }

    public String calculateRemainingTime(long milliseconds){
        int minutes = new BigDecimal((milliseconds / (1000 * 60)) % 60).intValueExact();
        int hours = new BigDecimal((milliseconds / (1000* 60 * 60)) % 24).intValueExact();
        return String.format("%dH %02dM", hours, minutes);
    }
}
