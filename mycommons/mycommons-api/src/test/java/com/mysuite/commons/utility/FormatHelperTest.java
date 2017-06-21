package com.mysuite.commons.utility;

import com.mysuite.commons.utility.format.DateTimeFormatHelper;
import junit.framework.TestCase;
import org.junit.Test;

import java.util.Date;


/**
 * Created by jianl on 6/04/2017.
 */
public class FormatHelperTest extends TestCase {

    @Test
    public void testGetDateFromDateString() throws Exception {
        String string = "2017-01-01";
        Date date = DateTimeFormatHelper.getDateFromDateString(string);
        assertEquals(string, DateTimeFormatHelper.getDateString(date));
    }

    @Test
    public void testGetTImeFromTimeString() throws Exception {
        String string = "16:16:16";
        Date date = DateTimeFormatHelper.getTimeFromTimeString(string);
        assertEquals("16:16:16", DateTimeFormatHelper.getTimeString(date));
    }

    @Test
    public void testGetDateTImeFromDateTimeString() throws Exception {
        String string = "2017-01-01 16:16:16";
        Date date = DateTimeFormatHelper.getDateFromDateTimeString(string);
        assertEquals("2017-01-01 16:16:16", DateTimeFormatHelper.getDateTimeString(date));
    }

    @Test
    public void testGetDateFromDateTimeString() throws Exception{
        String string = "20170419105609";
        Date date = DateTimeFormatHelper.getDateFromDateTimeSimpleString(string);
        System.out.println(date);
    }

    @Test
    public void testGetTimeStringFromDateTime() throws Exception{
        String string = "20170419105609";
        Date date = DateTimeFormatHelper.getDateFromDateTimeSimpleString(string);
        assertEquals("10:56:09", DateTimeFormatHelper.getTimeString(date));
    }

    @Test
    public void testGetDateStringFromDateTime() throws Exception{
        String string = "20170419105609";
        Date date = DateTimeFormatHelper.getDateFromDateTimeSimpleString(string);
        assertEquals("2017-04-19", DateTimeFormatHelper.getDateString(date));
    }
}