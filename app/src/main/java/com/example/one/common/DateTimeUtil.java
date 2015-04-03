package com.example.one.common;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Administrator on 2015/1/8.
 */
public class DateTimeUtil {

    public static DateTimeUtil dateTimeUtil;


    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }

    private String dateTime;
    private static String[] mons = new String[]{
            "January",
            "February",
            "March",
            "April",
            "May",
            "June",
            "July",
            "August",
            "September",
            "October",
            "November",
            "December"
    };

    public DateTimeUtil(String dateTime) {
        this.dateTime = dateTime;
    }

    public static DateTimeUtil instance(String time) {
        if (dateTimeUtil == null) {
            dateTimeUtil = new DateTimeUtil(time);
        }
        return dateTimeUtil;
    }

    public String getYear() {
        return dateTime.substring(0, 4);
    }

    public String getMonth() {
        String mon = dateTime.substring(4, 6);
        return mons[Integer.parseInt(mon) - 1];
    }

    public String getDay() {
        return dateTimeUtil.getDateTime().substring(6, 8);
    }
}
