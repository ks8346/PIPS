package com.soprabanking.ips.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;


public class DateUtil {
    public static Date stringToISTDate(String date) throws ParseException {

        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));

        return sdf.parse(date);
    }
}
