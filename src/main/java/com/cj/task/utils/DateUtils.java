package com.cj.task.utils;

import javax.xml.crypto.Data;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cj on 2018/9/12.
 */
public class DateUtils {
    public static String format(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yy/MM/dd HH:mm");
        return format.format(date);
    }
}
