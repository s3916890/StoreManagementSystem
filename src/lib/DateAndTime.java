package lib;

import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

public class DateAndTime {
    public static String getDateAndTime(){
        String hour = LocalTime.now().getHour() < 10 ? "0" + Integer.toString(LocalTime.now().getHour()) : Integer.toString(LocalTime.now().getHour());
        String minute = LocalTime.now().getMinute() < 10 ? "0" + Integer.toString(LocalTime.now().getMinute()) : Integer.toString(LocalTime.now().getMinute());

        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
        Date today = new Date();

        String day = formatter.format(today);

        return String.format("%s:%s(%s)", hour, minute, day);
    }
}
