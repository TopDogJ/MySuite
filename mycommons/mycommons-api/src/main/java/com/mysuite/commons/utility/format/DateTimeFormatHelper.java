package com.mysuite.commons.utility.format;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by jianl on 6/04/2017.
 */
public class DateTimeFormatHelper {

    private static final SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("yyyy-MM-dd");
    private static final SimpleDateFormat TIME_FORMAT = new SimpleDateFormat("HH:mm:ss");
    private static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    private static final SimpleDateFormat DATE_SIMPLE_FORMAT = new SimpleDateFormat("yyyyMMdd");
    private static final SimpleDateFormat TIME_SIMPLE_FORMAT = new SimpleDateFormat("HHmmss");
    private static final SimpleDateFormat DATE_TIME_SIMPLE_FORMAT = new SimpleDateFormat("yyyyMMddHHmmss");

    public static Date getDateFromDateTimeSimpleString(final String string){
        try {
            return DATE_TIME_SIMPLE_FORMAT.parse(string);
        } catch (ParseException e) {
            //TODO
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateFromTimeSimpleString(final String string){
        try {
            return TIME_SIMPLE_FORMAT.parse(string);
        } catch (ParseException e) {
            //TODO
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateFromDateSimpleString(final String string){
        try {
            return DATE_SIMPLE_FORMAT.parse(string);
        } catch (ParseException e) {
            //TODO
            e.printStackTrace();
            return null;
        }
    }


    public static Date getDateFromDateString(final String string) {
        try {
            return DATE_FORMAT.parse(string);
        } catch (ParseException e) {
            //TODO
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateFromDateTimeString(final String string) {
        try {
            return DATE_TIME_FORMAT.parse(string);
        } catch (ParseException e) {
            //TODO
            e.printStackTrace();
            return null;
        }
    }

    public static Date getTimeFromTimeString(final String string) {
        try {
            return TIME_FORMAT.parse(string);
        } catch (ParseException e) {
            //TODO
            e.printStackTrace();
            return null;
        }
    }

    public static Date getTimeFromDateTimeString(final String string) {
        try {
            return DATE_TIME_FORMAT.parse(string);
        } catch (ParseException e) {
            //TODO
            e.printStackTrace();
            return null;
        }
    }


    public static String getDateString(final Date date) {
        return DATE_FORMAT.format(date);
    }
    public static String getTimeString(final Date date) {
        return TIME_FORMAT.format(date);
    }
    public static String getDateTimeString(final Date date) {
        return DATE_TIME_FORMAT.format(date);
    }
}
