package com.android.launcher.ttl;

import com.android.launcher.App;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;
import com.dc.auto.library.launcher.util.ACache;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.LinkedTransferQueue;

import dc.library.auto.serial.SerialPort;
import dc.library.auto.task.logger.TaskLogger;
import dc.library.utils.HexUtilJava;

/**
 * 串口工具类
 */
public class SerialHelperTTLd3 {

    private SerialPort mSerialPort;
    private FileOutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    public volatile boolean _isOpen;
    public static ACache aCache = ACache.get(App.getGlobalContext());
    public volatile static boolean RUN;
    //发送队列
    private static final LinkedTransferQueue<byte[]> mSendQueue = new LinkedTransferQueue<>();

    public SerialHelperTTLd3() {
        _isOpen = false;
        TaskLogger.i("开始串口");
    }

    public void openLLd() throws SecurityException, IOException, InvalidParameterException {
        LogUtils.printI(SerialHelperTTLd3.class.getSimpleName(), "打开串口");
        RUN = false;
//        this.mSerialPort = new SerialPort(new File("/dev/ttyS3"), 9600, 0);
//        this.mOutputStream = this.mSerialPort.getOutputStream();
//        this.mInputStream = this.mSerialPort.getInputStream();
//        this.mReadThread = new ReadThread();
//        this.mReadThread.start();
//        startSendThread();
//        _isOpen = true;
    }

    private void startSendThread() {
        new Thread(() -> {
            while (!RUN) {
                try {
                    byte[] data = mSendQueue.take();
                    if (mOutputStream != null) {
                        mOutputStream.write(data);
                    }
                } catch (Exception e) {
                    TaskLogger.e("startSendMessageThread---" + e.getMessage());
                }
            }

        }).start();
    }

    public void close() {
        try {
            mSendQueue.clear();
            if (this.mReadThread != null) {
                this.mReadThread.interrupt();
            }
            if (this.mOutputStream != null) {
                this.mOutputStream.close();
                this.mOutputStream = null;
            }
            if (this.mSerialPort != null) {
                SerialPort.closePort();
                this.mSerialPort = null;
            }
            RUN = true;
            this._isOpen = false;
        } catch (Exception e) {
            TaskLogger.e("startSendMessageThread--- close " + e.getMessage());
        }
    }

    public static void send(byte[] bOutArray) {
        try {
            mSendQueue.put(bOutArray);
        } catch (Exception e) {
            TaskLogger.e("startSendMessageThread---send" + e.getMessage());
        }
    }

    public static void sendHex(String sHex) {
        try {
            LogUtils.printI(SerialHelperTTLd3.class.getSimpleName(), "send---data=" + sHex);
            byte[] bOutArray = HexUtilJava.toByteArray(sHex);
            send(bOutArray);
        } catch (Exception e) {
            TaskLogger.e("startSendMessageThread---sendHex" + e.getMessage());
        }
    }

    /**
     * 读取串口数据线程
     */
    private class ReadThread extends Thread {
        private ReadThread() {
        }

        public void run() {
            super.run();
            byte[] buffer = new byte[1024];
            while (!RUN) {
                try {
                    if (mInputStream != null) {
                        int size = mInputStream.read(buffer);
                        if (size > 0) {

                            byte[] by = new byte[size];
                            System.arraycopy(buffer, 0, by, 0, size);
                            String data = HexUtilJava.toHexString(by, by.length);
                            LogUtils.printI(SerialHelperTTLd3.class.getSimpleName(), "ReadThread---data=" + data);
                        }
                    }
                } catch (Exception e) {
                    TaskLogger.e("startSendMessageThread---ReadThread" + e.getMessage());
                    if (mInputStream != null && !App.livingServerStop) {
                        retry();
                    }
                }
            }
        }
    }

    private void retry() {
        LogUtils.printI(SerialHelperTTLd3.class.getSimpleName(), "retry----");
        new Thread(() -> {
            try {
                FuncUtil.serialHelperttl3.close();
                FuncUtil.serialHelperttl3 = null;
                Thread.sleep(3000);
                FuncUtil.initSerialHelper3();
            } catch (Exception e) {
                TaskLogger.e("startSendMessageThread---retry" + e.getMessage());
            }
        }).start();
    }
}