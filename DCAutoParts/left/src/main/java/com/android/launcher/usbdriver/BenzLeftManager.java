package com.android.launcher.usbdriver;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;

import com.android.launcher.App;
import com.android.launcher.util.LogUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.annotations.NonNull;


public class BenzLeftManager {
    private static final String TAG = "hufei";

    public static UsbManager mUsbManager;
    public static List<UsbDevice> usbDevices;
//    public volatile static boolean receiver1Flg = false;

    public static Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            this.obtainMessage();
            mUsbManager = (UsbManager) App.getGlobalContext().getSystemService(Context.USB_SERVICE);
            usbDevices = getAllUsbDevice(mUsbManager);
            int size = usbDevices.size();

            LogUtils.printI(TAG, size + "===============多少个Usb口");
            if (size == 2) {
                MUsb1Receiver.initUsbAuth(usbDevices.get(0));
            }
            if (size == 1) {
                MUsb1Receiver.initUsbAuth(usbDevices.get(0));
            }
        }
    };


    public static void init() {
        Message message = Message.obtain();
        message.what = 1;
        handler.sendMessage(message);
    }


    private static List<UsbDevice> getAllUsbDevice(UsbManager mUsbManager) {

        List<UsbDevice> usbDevices = new ArrayList<>();
        HashMap<String, UsbDevice> allusb = mUsbManager.getDeviceList();
        try {
            LogUtils.printI(TAG, "getAllUsbDevice-----allusb--size=" + allusb.size());
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
