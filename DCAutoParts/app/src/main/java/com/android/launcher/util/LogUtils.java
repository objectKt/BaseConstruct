package com.android.launcher.util;

import android.util.Log;

/**
* @description:
* @createDate: 2023/4/27
*/
public class LogUtils {

    public static boolean isOpen = true;

    public static void printI(String tag, String message){
        if(isOpen){
            if (message != null){
                Log.i(tag, message);
            }
        }
    }

    public static void printE(String tag, String message){
        if(isOpen){
            if (message != null){
                Log.e(tag, message);
            }
        }
    }
}
