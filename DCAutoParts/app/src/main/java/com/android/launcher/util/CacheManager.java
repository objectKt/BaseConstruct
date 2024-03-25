package com.android.launcher.util;

import android.content.Context;
import android.text.TextUtils;

/**
* @description: 缓存管理器
* @createDate: 2023/5/16
*/
public class CacheManager {

    private static String rightDeviceId = "";

    /**
    * @description:
    * @createDate: 2023/5/16
    */
    public static synchronized String getRightDeviceId(Context context){
        if(TextUtils.isEmpty(rightDeviceId)){
            try {
                rightDeviceId =  SPUtils.getString(context, SPUtils.SP_RIGHT_DEVICE_ID,"");
                LogUtils.printI(CacheManager.class.getSimpleName(), "rightDeviceId="+rightDeviceId);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return rightDeviceId;
    }

    public static void clear(){
        rightDeviceId = "";
    }

}
