package com.android.launcher.util;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;

/**
* @description:
* @createDate: 2023/5/9
*/
public class AppVersionUtils {

    public static String getVersionName(Context context) throws Exception
    {
        // 获取packagemanager的实例
        PackageManager packageManager = context.getPackageManager();
        // getPackageName()是你当前类的包名，0代表是获取版本信息
        PackageInfo packInfo = packageManager.getPackageInfo(context.getPackageName(),0);
        return packInfo.versionName;
    }
}
