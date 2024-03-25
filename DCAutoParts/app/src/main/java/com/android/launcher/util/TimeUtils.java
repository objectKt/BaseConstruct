package com.android.launcher.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
* @description:
* @createDate: 2023/5/3
*/
public class TimeUtils {


    /**
     * 获取时间
     * @return
     */
    public static synchronized String getCurrentTime() {
        Calendar c = Calendar.getInstance();//可以对每个时间域单独修改


        int hour = c.get(Calendar.HOUR_OF_DAY);
        int minute = c.get(Calendar.MINUTE);

        String hourStr;
        if(hour < 10){
            hourStr = "0"+hour;
        }else{
            hourStr = String.valueOf(hour);
        }
        String minuteStr;
        if(minute < 10){
            minuteStr = "0"+minute;
        }else{
            minuteStr = String.valueOf(minute);
        }

        return hourStr+":"+minuteStr ;
    }

    public static boolean isNight() {
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH");
        String str = df.format(date);
        int a = Integer.parseInt(str);
        if (a >= 0 && a <= 6) {
            return true;
        }else if (a >= 18 && a <= 24) {
            return true;
        }else{
            return false;
        }
    }
}
