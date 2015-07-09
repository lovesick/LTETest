package com.futureinternet.cysmile.lte;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by cysmile on 2015/6/18.
 */


public class DateTime {

    /* 获取当前时间(ms) */
    public long dateTime() {
        long date = System.currentTimeMillis();
        return date;
    }

    /* 获取当前时间 格式 yyyy-MM-dd HH:mm:ss*/
    public String fileDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "yyyy年MM月dd日    HH:mm:ss    ");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);
        return date;
    }

    /* 获取当前时间 格式 MM/dd HH:mm:ss*/
    public String currentDateTime() {
        SimpleDateFormat formatter = new SimpleDateFormat(
                "MM/dd HH:mm:ss     ");
        Date curDate = new Date(System.currentTimeMillis());
        String date = formatter.format(curDate);
        return date;
    }


}
