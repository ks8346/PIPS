package com.soprabanking.ips.utilities;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Utility class responsible for performing Date formatting related functions.
 * @author araghav
 *
 */
public class DateUtil {
	/**
	 * Returns a Date object representing the date and time contained in the specified Date string.
	 * <p>This method parses and converts a given String containing Date and Time values in "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'" format with a specified TimeZone and then returns the resulting Date Object. </p>
	 * @param date A String object containing Date and Time values
	 * @return A Date object obtained as a result of parsing the parameter String in a particular format
	 * @throws ParseException if the parameter string is invalid or not enough for the parsing to be done
	 */
    public static Date stringToISTDate(String date) throws ParseException {

        String dateFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
        SimpleDateFormat sdf = new SimpleDateFormat(dateFormat);
        sdf.setTimeZone(TimeZone.getTimeZone("Asia/kolkata"));

        return sdf.parse(date);
    }
}
