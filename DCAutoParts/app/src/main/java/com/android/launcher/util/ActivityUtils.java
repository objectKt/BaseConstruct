package com.android.launcher.util;

import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;

import java.util.List;

/**
 * @description:
 * @createDate: 2023/5/15
 */
public class ActivityUtils {

    public static boolean isTopActivity(Context context, String packageName, String topActivityName) {
        try {
            ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            LogUtils.printI(ActivityUtils.class.getSimpleName(), "packageName=" + packageName + ", topActivityName=" + topActivityName);
            if (am != null) {
                List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(1);
                if (taskInfoList.size() > 0) {
                    ComponentName topActivity = taskInfoList.get(0).topActivity;
                    if (topActivity == null) {
                        return false;
                    }
                    LogUtils.printI(ActivityUtils.class.getSimpleName(), "topActivityName=" + topActivityName + ", topActivity=" + topActivity);
                    if (topActivity.getPackageName().equals(packageName) && topActivity.getClassName().equals(topActivityName)) {
                        // 当前 Activity 在栈顶
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            LogUtils.printI(ActivityUtils.class.getSimpleName(), "Exception=" + e.getMessage());
            e.printStackTrace();
        }
        return false;
    }
}