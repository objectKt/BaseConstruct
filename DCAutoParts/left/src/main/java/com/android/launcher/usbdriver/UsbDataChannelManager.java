package com.android.launcher.usbdriver;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

import com.android.launcher.App;
import com.android.launcher.util.LogUtils;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

import dc.library.auto.event.MessageEvent;
import dc.library.auto.global.ConstVal;
import dc.library.auto.task.logger.TaskLogger;
import dc.library.utils.HexUtilJava;

/**
 * USB数据通道管理器
 */
public class UsbDataChannelManager {
    private static final String TAG = ConstVal.Log.TAG;
    private UsbSerialPort port;
    private boolean isRunning = false;
    public static LinkedTransferQueue<byte[]> mSendQueue = new LinkedTransferQueue<byte[]>();
    private final ExecutorService readTask;
    private final ExecutorService writeTask;

    public UsbDataChannelManager() {
        readTask = Executors.newSingleThreadExecutor();
        writeTask = Executors.newSingleThreadExecutor();
    }

    public void startRun(Context context) {
        UsbManager usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> allDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);
        LogUtils.printI(TAG, "startRun----allDrivers=" + allDrivers);
        if (allDrivers == null || allDrivers.isEmpty()) {
            LogUtils.printI(TAG, "not UsbSerialDriver---------");
            return;
        }
        //只有一个USB
        UsbSerialDriver driver = allDrivers.get(0);
        LogUtils.printI(TAG, "UsbSerialDriver---" + driver.toString());

        UsbDeviceConnection connection = usbManager.openDevice(driver.getDevice());
        if (connection == null) {
            LogUtils.printI(TAG, "-----UsbDeviceConnection is null， add UsbManager.requestPermission(driver.getDevice(), ..) handling here");
            return;
        }
        // Most devices have just one port (port 0)
        port = driver.getPorts().get(0);
        if (port == null) {
            LogUtils.printI(TAG, "-----UsbSerialPort is null");
            return;
        }
        try {
            port.open(connection);
            port.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE);
            LogUtils.printI(TAG, "开始读写数据");
            mSendQueue.clear();
            readTask.execute(readThread);
            writeTask.execute(writeThread);
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.USB_CONNECTED));
        } catch (Exception e) {
            TaskLogger.e("-----Exception=" + e.getMessage());
            try {
                if (port != null) {
                    port.close();
                    port = null;
                }
            } catch (Exception ee) {
                TaskLogger.e(ee.getMessage());
            }
        }
        isRunning = true;
    }

    private final Runnable readThread = new Runnable() {

        final byte[] buffer = new byte[32];

        @Override
        public void run() {
            LogUtils.printI(TAG, "readThread-----port=" + port);
            if (port != null) {
                LogUtils.printI(TAG, "readThread-----port isOpen=" + port.isOpen());
            }
            while (port != null && port.isOpen()) {
                try {
                    int len = port.read(buffer, 2000);
                    if (len > 0) {
                        final byte[] data = new byte[len];
                        System.arraycopy(buffer, 0, data, 0, len);
                        String ss1 = HexUtilJava.toHexString(data, data.length);
                        BenzHandlerData.handlerCan(ss1);
                    }
                } catch (IOException e) {
                    TaskLogger.e("IOException: " + e.getMessage() + ", livingServerStop=" + App.livingServerStop);
                    if ("USB get_status request failed".equals(e.getMessage())) {
                        if (!App.livingServerStop) {
                            close();
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.USB_INTERRUPT));
                        }
                    }
                } catch (Exception e) {
                    TaskLogger.e(e.getMessage());
                }
            }
        }
    };

    private final Runnable writeThread = new Runnable() {
        @Override
        public void run() {
            LogUtils.printI(TAG, "writeThread-----port=" + port);
            if (port != null) {
                LogUtils.printI(TAG, "writeThread-----port isOpen=" + port.isOpen());
            }
            while (port != null && port.isOpen()) {
                try {
                    // 阻塞直到有数据
                    byte[] bytes = mSendQueue.take();
                    if (port != null && port.isOpen()) {
                        port.write(bytes, 2000);
                    }
                } catch (Exception e) {
                    TaskLogger.e(e.getMessage());
                }
            }
        }
    };

    public void close() {
        LogUtils.printI(TAG, "close------");
        mSendQueue.clear();
        try {
            if (port != null) {
                port.close();
                port = null;
            }
            writeTask.shutdownNow();
            readTask.shutdownNow();
        } catch (Exception e) {
            TaskLogger.e("USbDataChannelManager close()" + e.getMessage());
        }
        isRunning = false;
    }

    public boolean isRunning() {
        return isRunning;
    }
}
