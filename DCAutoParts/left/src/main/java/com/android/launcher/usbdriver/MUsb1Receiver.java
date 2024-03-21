package com.android.launcher.usbdriver;

import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;

import com.android.launcher.App;
import dc.library.utils.event.MessageEvent;
import com.android.launcher.can.Can1;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;


public class MUsb1Receiver extends BroadcastReceiver{

    private static final String TAG = MUsb1Receiver.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            String action = intent.getAction();
            LogUtils.printI(TAG, "onReceive----action="+action);
            if ("com.car.left.usb1".equals(action)) {
                synchronized (this) {
                    UsbDevice device = (UsbDevice) intent.getParcelableExtra(UsbManager.EXTRA_DEVICE);
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        LogUtils.printI(TAG, "onReceive----usb授权=" + device.getVendorId());
                        if (device.getVendorId() == 6790) {
                            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.USB_REGISTER_SUCCESS);
                            messageEvent.data = device;
                            EventBus.getDefault().post(messageEvent);
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void write(byte[] bytes) {
        if(!Can1.isClose){
            UsbDataChannelManager.mSendQueue.put(bytes);
        }
    }


    public static void initUsbAuth(UsbDevice usbDevice) {
        try {
            UsbManager  mUsbManager = (UsbManager) App.getGlobalContext().getSystemService(Context.USB_SERVICE);
            PendingIntent var3 = PendingIntent.getBroadcast(App.getGlobalContext(), 0, new Intent("com.car.left.usb1"), PendingIntent.FLAG_CANCEL_CURRENT);
            mUsbManager.requestPermission(usbDevice, var3);
            LogUtils.printI(TAG, "initUsbAuth----"+usbDevice.getDeviceName());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
