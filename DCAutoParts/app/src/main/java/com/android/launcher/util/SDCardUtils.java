package com.android.launcher.util;

import android.os.Environment;

/**
 * @description:
 * @createDate: 2023/8/10
 */
public class SDCardUtils {

    private static final String TAG = SDCardUtils.class.getSimpleName();

    public static boolean isFull(){
        try {
            long totalSpace = Environment.getExternalStorageDirectory().getTotalSpace();
            long freeSpace = Environment.getExternalStorageDirectory().getFreeSpace();
            long usedSpace = totalSpace - freeSpace;

            LogUtils.printI(TAG, "totalSpace="+totalSpace +", freeSpace="+freeSpace);

            if (usedSpace >= totalSpace) {
                // 存储已满
                return true;
            } else {
                // 存储未满
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}
