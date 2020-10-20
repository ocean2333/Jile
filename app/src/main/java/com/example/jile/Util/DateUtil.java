package com.example.jile.Util;

import android.annotation.SuppressLint;
import android.icu.text.SimpleDateFormat;

import com.example.jile.Constant.Constants;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

public class DateUtil {
    /**
     * 判断当前日期是星期几
     *
     * @param  pTime     设置的需要判断的时间  //格式如2012-09-08
     *

     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String getWeek(String pTime) {
        String Week = "周";
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(Constants.DATE_FORMAT_SIMPLE.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 1) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 2) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 3) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 4) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 5) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 6) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == 7) {
            Week += "六";
        }
        return Week;
    }

    public static String getShortDate(String date) throws ParseException {
        Date d =  Constants.DATE_FORMAT_SIMPLE.parse(date);
        return Constants.DATE_FORMAT_MONTH_DAY.format(d);
    }

    //获取当前月份
    public static String getMonth(){
        @SuppressLint("SimpleDateFormat") SimpleDateFormat simpleDateFormat = new SimpleDateFormat("MM月");
        Date date = new Date(System.currentTimeMillis());
        return simpleDateFormat.format(date);
    }

    /**
     * 获得一天的00:00:00
     * */
    public static Date getFirstTImeOfDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(date.getYear()+1900,date.getMonth(),date.getDate(),0,0,0);
        return calendar.getTime();
    }

    /**
     * 获得一天的23:59:59
     * */
    public static Date getLastTImeOfDay(Date date){
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(date.getYear()+1900,date.getMonth(),date.getDate(),23,59,59);
        return calendar.getTime();
    }
}
