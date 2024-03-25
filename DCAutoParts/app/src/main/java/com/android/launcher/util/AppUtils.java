package com.android.launcher.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.provider.Settings;

import com.android.launcher.App;

/**
* @description:
* @createDate: 2023/6/5
*/
public class AppUtils {


    public static synchronized String getDeviceId(Context context){
        String deviceId ="";
        try {
            deviceId = Settings.System.getString(App.getGlobalContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return deviceId;
    }

    public static void restart(Activity activity) {
        try {
            Intent intent = activity.getPackageManager().getLaunchIntentForPackage(activity.getPackageName());
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            activity.startActivity(intent);
            activity.finish();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
