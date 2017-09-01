package com.example.administrator.gongda.Utils;

import java.util.Calendar;
import java.util.TimeZone;

/**
 * Created by Administrator on 2016/11/13.
 */

public class WeekUtil {
    public static String getCurrentDate(){
        final Calendar calendar=Calendar.getInstance();
        calendar.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
        int year=calendar.get(Calendar.YEAR);
        int date= calendar.get(Calendar.WEEK_OF_YEAR);
        return year+";"+date;
    }
}
