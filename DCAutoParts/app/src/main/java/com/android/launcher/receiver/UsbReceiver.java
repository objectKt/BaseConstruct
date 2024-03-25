package com.android.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbManager;

import com.android.launcher.util.LogUtils;

/**
 * @date： 2023/11/23
 * @author: 78495
*/
public class UsbReceiver extends BroadcastReceiver {

    private static final String TAG = UsbReceiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {

        LogUtils.printI(TAG,"action="+intent.getAction());
        if(UsbManager.ACTION_USB_DEVICE_ATTACHED.equals(intent.getAction())){

        }else if(UsbManager.ACTION_USB_DEVICE_DETACHED.equals(intent.getAction())){

        }
    }
}
