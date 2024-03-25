package com.android.launcher.usbdriver;

import android.content.Context;
import android.hardware.usb.UsbDeviceConnection;
import android.hardware.usb.UsbManager;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.service.LivingService;
import com.android.launcher.service.task.USBConnectCheckTask;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;
import com.hoho.android.usbserial.driver.UsbSerialDriver;
import com.hoho.android.usbserial.driver.UsbSerialPort;
import com.hoho.android.usbserial.driver.UsbSerialProber;

import org.greenrobot.eventbus.EventBus;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;

/**
 * USB数据通道管理器
 * @date： 2023/11/23
 * @author: 78495
*/
public class UsbDataChannelManager {

    private static final String TAG = UsbDataChannelManager.class.getSimpleName();

    private UsbSerialPort port;

    private StringBuffer sb = new StringBuffer();

    private boolean isRunning = false;

    /*
    *   发送队列
    */
    public static LinkedTransferQueue<byte[]> mSendQueue = new LinkedTransferQueue<byte[]>();
    private ExecutorService readTask;
    private ExecutorService writeTask;

    public UsbDataChannelManager(){
        readTask = Executors.newSingleThreadExecutor();
        writeTask = Executors.newSingleThreadExecutor();
    }


    public void startRun(Context context){
        UsbManager  usbManager = (UsbManager) context.getSystemService(Context.USB_SERVICE);
        List<UsbSerialDriver> allDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager);

        LogUtils.printI(TAG, "startRun----allDrivers="+allDrivers);
        if(allDrivers == null || allDrivers.isEmpty()){
            LogUtils.printI(TAG, "not UsbSerialDriver---------");
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.NOT_USB_SERIALDRIVER));
            return;
        }
        //只有一个USB
        UsbSerialDriver driver = allDrivers.get(0);
        LogUtils.printI(TAG, "UsbSerialDriver---" + driver.toString());

        UsbDeviceConnection connection = usbManager.openDevice(driver.getDevice());
        if (connection == null) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.USB_OPEN_DEVICE_FAIL));
            LogUtils.printI(TAG, "-----UsbDeviceConnection is null， add UsbManager.requestPermission(driver.getDevice(), ..) handling here");
            return;
        }

        // Most devices have just one port (port 0)
        port = driver.getPorts().get(0);
        if (port == null) {
            LogUtils.printI(TAG, "-----UsbSerialPort is null");
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.USB_OPEN_PORT_FAIL));
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
            e.printStackTrace();
            LogUtils.printI(TAG, "-----Exception="+e.getMessage());
            try {
                if (port != null) {
                    port.close();
                    port = null;
                }
            } catch (Exception ioException) {
                ioException.printStackTrace();
            }
        }
        isRunning = true;
    }

    private Runnable readThread = new Runnable() {

        final byte[] buffer = new byte[32];
        @Override
        public void run() {
            LogUtils.printI(TAG,"readThread-----port=" +port);
            if(port!=null){
                LogUtils.printI(TAG,"readThread-----port isOpen=" +port.isOpen());
            }
            while (port != null && port.isOpen()) {
                try {
                    int len = port.read(buffer, 2000);
                    if (len > 0) {
                        final byte[] data = new byte[len];
                        System.arraycopy(buffer, 0, data, 0, len);
                        String ss1 = toHexString(data, data.length);
                        BenzHandlerData.handlerCan(ss1);

                        USBConnectCheckTask.normal = true;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    LogUtils.printE(TAG,"IOException: "+e.getMessage()+ ", livingServerStop="+App.livingServerStop);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    };


    private Runnable writeThread = new Runnable() {
        @Override
        public void run() {
            LogUtils.printI(TAG,"writeThread-----port=" +port);
            if(port!=null){
                LogUtils.printI(TAG,"writeThread-----port isOpen=" +port.isOpen());
            }
            while (port != null && port.isOpen()) {
                try {
                    // 阻塞直到有数据
                    byte[] bytes = mSendQueue.take();
                    if(port != null && port.isOpen()){
                        port.write(bytes,2000);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.printE(TAG, e.getMessage());
                }
            }
        }
    };

    public synchronized String toHexString(byte[] arg, int length) {
        if (arg != null && arg.length != 0) {
            char[] hexArray = "0123456789ABCDEF".toCharArray();
            for (int j = 0; j < length; ++j) {
                int v = arg[j] & 255;
                sb.append(hexArray[v >>> 4]).append(hexArray[v & 15]);
            }
            String returnString = sb.toString();
            sb.setLength(0);
            return returnString;
        } else {
            return "";
        }
    }


    public  void close() {
        LogUtils.printI(TAG, "close------");

        mSendQueue.clear();
        try {
            if (port != null) {
                try {
                    port.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                port = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            writeTask.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            readTask.shutdownNow();
        } catch (Exception e) {
            e.printStackTrace();
        }


        isRunning = false;
    }


    public boolean isRunning() {
        return isRunning;
    }
}
