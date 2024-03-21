package com.android.launcher.receiver

import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbManager
import android.os.Parcelable
import dc.library.utils.global.ConstVal
import dc.library.auto.task.logger.TaskLogger
import dc.library.utils.ContextUtil.getApplicationContext

class MUsb1Receiver : BroadcastReceiver() {

    companion object {
        private val CLASS_SELF = MUsb1Receiver::class.java.getSimpleName()
    }

    fun write(bytes: ByteArray) {
        if (true) { //if (!Can1.isClose)
            //UsbDataChannelManager.mSendQueue.put(bytes);
        }
    }

    override fun onReceive(context: Context, intent: Intent) {
        try {
            val action = intent.action
            TaskLogger.i("$CLASS_SELF Action$action")
            if (ConstVal.ReceiverAction.USB_LEFT == action) {
                synchronized(this) {
                    @Suppress("DEPRECATION")
                    val device: UsbDevice = intent.getParcelableExtra<Parcelable>(UsbManager.EXTRA_DEVICE) as UsbDevice
                    if (intent.getBooleanExtra(UsbManager.EXTRA_PERMISSION_GRANTED, false)) {
                        TaskLogger.i("$CLASS_SELF USB 授权=" + device.vendorId)
                        if (device.vendorId == 6790) {
//                            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.USB_REGISTER_SUCCESS);
//                            messageEvent.data = device;
//                            EventBus.getDefault().post(messageEvent);
                        }
                    } else {
                        TaskLogger.e("$CLASS_SELF USB 没有授权")
                    }
                }
            }
        } catch (e: Exception) {
            TaskLogger.e("$CLASS_SELF onReceive $e")
        }
    }

    fun initUsbAuth(usbDevice: UsbDevice) {
        try {
            val mUsbManager = getApplicationContext().getSystemService(Context.USB_SERVICE) as UsbManager
            val var3 = PendingIntent.getBroadcast(getApplicationContext(), 0, Intent("com.car.left.usb1"), PendingIntent.FLAG_CANCEL_CURRENT or PendingIntent.FLAG_IMMUTABLE)
            mUsbManager.requestPermission(usbDevice, var3)
            TaskLogger.i("$CLASS_SELF initUsbAuth " + usbDevice.deviceName)
        } catch (e: Exception) {
            TaskLogger.e("$CLASS_SELF initUsbAuth $e")
        }
    }
}
