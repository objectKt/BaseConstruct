package com.android.launcher.usbdriver;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import androidx.annotation.NonNull;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class BenzLeftManager {
    private static final String TAG = BenzLeftManager.class.getSimpleName();

    public static UsbManager mUsbManager;
    public static List<UsbDevice> usbDevices;
//    public volatile static boolean receiver1Flg = false;

    public static void init() {
        mUsbManager = (UsbManager) App.getGlobalContext().getSystemService(Context.USB_SERVICE);
        usbDevices = getAllUsbDevice(mUsbManager);
        int size = usbDevices.size();

        if(size == 0){
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.NOT_USB_DEVICE));
        }

        LogUtils.printI(TAG, size + "===============多少个Usb口");
        if (size == 2) {
            MUsb1Receiver.initUsbAuth(usbDevices.get(0));
        }
        if (size == 1) {
            MUsb1Receiver.initUsbAuth(usbDevices.get(0));
        }
    }


    private static List<UsbDevice> getAllUsbDevice(UsbManager mUsbManager) {

        List<UsbDevice> usbDevices = new ArrayList<>();
        HashMap<String, UsbDevice> allusb = mUsbManager.getDeviceList();
        try {
            LogUtils.printI(TAG, "getAllUsbDevice-----allusb--size="+allusb.size());
        } catch (Exception e) {
            e.printStackTrace();
        }

        for (Map.Entry<String, UsbDevice> map : allusb.entrySet()) {
            try {
                LogUtils.printI(TAG, "getAllUsbDevice----device---vendorId=" + map.getValue().getVendorId() + ", DeviceName=" + map.getValue().getDeviceName());
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (map.getValue().getVendorId() == 6790) {
                usbDevices.add(map.getValue());
            }
        }
        return usbDevices;
    }


}
