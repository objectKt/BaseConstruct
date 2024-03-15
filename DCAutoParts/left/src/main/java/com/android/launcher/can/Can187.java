package com.android.launcher.can;

import android.annotation.SuppressLint;

import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.android.launcher.util.LogUtils;

import java.util.List;

/**
 * @date： 2023/12/27
 * @author: 78495
*/
public class Can187 implements  CanParent{

    public static volatile String lastData = "";

    private static final String TAG = Can187.class.getSimpleName();

    @SuppressLint("NewApi")
    @Override
    public void handlerCan(List<String> msg) {

        String data = String.join("", msg);
        if(!lastData.equals(data)){
            lastData = data;
            LogUtils.printI(TAG,"msg="+msg);
        }

    }
}