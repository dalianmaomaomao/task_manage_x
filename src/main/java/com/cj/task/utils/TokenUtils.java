package com.cj.task.utils;


import java.util.Date;

/**
 * Created by cj on 2018/8/26.
 */
public class TokenUtils {
    public static String getToken(String account){
        long date=System.currentTimeMillis();
        String key=account+date+"#";
        return MD5Utils.MD5(key);
    }
}
