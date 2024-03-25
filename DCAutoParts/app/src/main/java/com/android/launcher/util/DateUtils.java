package com.android.launcher.util;

import android.content.Context;

import com.android.launcher.R;

import java.text.DecimalFormat;

/**
* @description:
* @createDate: 2023/9/1
*/
public class DateUtils {

    public static synchronized String getMileHour(long milliseconds){
        if(milliseconds >1000){
            // 将毫秒转换为秒
            long seconds = milliseconds / 1000;

            // 计算总分钟数
            long totalMinutes = seconds / 60;

            // 计算小时数和剩余分钟数
            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;
            return String.format("%02d", hours) + ":" + String.format("%02d", minutes);
        }else{
            return "00:00";
        }
    }

    public static synchronized String getMileHourStyle1(Context context, long milliseconds){
        if(milliseconds >1000){
            // 将毫秒转换为秒
            long seconds = milliseconds / 1000;

            // 计算总分钟数
            long totalMinutes = seconds / 60;

            // 计算小时数和剩余分钟数
            long hours = totalMinutes / 60;
            long minutes = totalMinutes % 60;
            return String.format("%02d", hours) + context.getResources().getString(R.string.hour) + String.format("%02d", minutes)+ context.getResources().getString(R.string.minute);
        }else{
            return "00:00";
        }
    }

    /**
    * @description: 计算有多少个小时
    * @createDate: 2023/9/3
    */
    public static synchronized float computeHourNumber(long totalRunTime) {
        final long oneHour = 60 * 60 * 1000;
        if(totalRunTime < 1000){
            return 0;
        }else{
            float hour = totalRunTime / (float) oneHour;
            try {
                DecimalFormat df = new DecimalFormat("#.##");
                String formattedNumber = df.format(hour);
                hour = Float.parseFloat(formattedNumber);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return hour;
        }
    }

}
