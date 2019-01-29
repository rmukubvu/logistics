package za.charurama.logistics.common;


import org.ocpsoft.prettytime.PrettyTime;

import java.util.Calendar;
import java.util.Date;

public class TimeUtil {
    public static String getPrettyTime(double elapsedTime){
        //if more than 24 hrs format to days
        /*if (elapsedTime > 24){
            double days = elapsedTime/24;
            return days > 1 ? String.format("%.0f days",DoubleRounder.round(days,0)) : "1 day";
        }else{
            return String.format("%.0f hours", DoubleRounder.round(elapsedTime,0));
        }*/
        PrettyTime prettyTime = new PrettyTime();
        Date estimatedTimeOfArrival = addHoursToJavaUtilDate(new Date(), (int) elapsedTime);
        return prettyTime.format(estimatedTimeOfArrival);
    }

    private static Date addHoursToJavaUtilDate(Date date, int hours) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR_OF_DAY, hours);
        return calendar.getTime();
    }
}
