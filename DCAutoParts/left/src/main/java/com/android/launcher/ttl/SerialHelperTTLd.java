package com.android.launcher.ttl;

import com.android.launcher.App;
import com.android.launcher.usbdriver.BenzHandlerData;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;
import com.dc.auto.library.launcher.util.ACache;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.LinkedTransferQueue;

import dc.library.auto.global.ConstVal;
import dc.library.auto.serial.SerialPort;
import dc.library.auto.task.logger.TaskLogger;
import dc.library.auto.util.HexUtilJava;

/**
 * 串口工具类
 */
public class SerialHelperTTLd {

    private SerialPort mSerialPort;
    private static FileOutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    public volatile boolean _isOpen;
    public static ACache aCache = ACache.get(App.getGlobalContext());
    public volatile static boolean RUN;

    //发送队列
    private static final LinkedTransferQueue<byte[]> mSendQueue = new LinkedTransferQueue<>();

    public SerialHelperTTLd() {
        _isOpen = false;
        LogUtils.printI(ConstVal.Log.TAG, "开始串口----");
    }

    public void openLLd() throws SecurityException, IOException, InvalidParameterException {
        LogUtils.printI(ConstVal.Log.TAG, "打开串口----");
        RUN = false;
        this.mSerialPort = new SerialPort(new File("/dev/ttyS1"), 115200, 0);
        if (!this.mSerialPort.getmFd().valid()) {
            retry();
        } else {
            mOutputStream = this.mSerialPort.getOutputStream();
            this.mInputStream = this.mSerialPort.getInputStream();
            this.mReadThread = new ReadThread();
            this.mReadThread.start();
            startSendThread();
            _isOpen = true;
        }
    }

    private void retry() {
        LogUtils.printI(ConstVal.Log.TAG, "retry----");
        new Thread(() -> {
            try {
                FuncUtil.serialHelperttl.close();
                FuncUtil.serialHelperttl = null;
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                TaskLogger.e("SerialHelperLLD InterruptedException" + e.getMessage());
            }
            try {
                FuncUtil.initSerialHelper1();
            } catch (Exception e) {
                TaskLogger.e("SerialHelperLLD initSerialHelper1" + e.getMessage());
            }
        }).start();
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
                    TaskLogger.e("SerialHelperLLD startSendMessageThread" + e.getMessage());
                }
            }

        }).start();
    }

    public void close() {
        try {
            mSendQueue.clear();
            if (mInputStream != null) {
                mInputStream.close();
                mInputStream = null;
            }
            if (this.mReadThread != null) {
                this.mReadThread.interrupt();
            }
            if (mOutputStream != null) {
                mOutputStream.close();
                mOutputStream = null;
            }
            if (this.mSerialPort != null) {
                SerialPort.closePort();
                this.mSerialPort = null;
            }
            RUN = true;
            this._isOpen = false;
        } catch (Exception e) {
            TaskLogger.e("SerialHelperLLD close" + e.getMessage());
        }
    }

    public static void send(byte[] bOutArray) {
        try {
            LogUtils.printI(ConstVal.Log.TAG, "send---length=" + bOutArray.length);
            mSendQueue.put(bOutArray);
        } catch (Exception e) {
            TaskLogger.e("SerialHelperLLD send" + e.getMessage());
        }
    }

    public static void sendHex(String sHex) {
        byte[] bOutArray = HexUtilJava.toByteArrayTTLLD(sHex);
        send(bOutArray);
    }

    /**
     * 读取串口数据线程
     */
    private class ReadThread extends Thread {

        private ReadThread() {
        }

        public void run() {
            super.run();
            LogUtils.printI(ConstVal.Log.TAG, "读取串口数据线程--- RUN " + RUN);
            byte[] buffer = new byte[1024];
            while (!RUN) {
                try {
                    if (mInputStream != null) {
                        int size = mInputStream.read(buffer);
                        if (size > 0) {
                            byte[] by = new byte[size];
                            System.arraycopy(buffer, 0, by, 0, size);
                            String ss = HexUtilJava.toHexString(by, by.length);
                            LogUtils.printI(ConstVal.Log.TAG, "ss=" + ss);
                            BenzHandlerData.handlerFromRight(ss);
                        }
                    }
                } catch (Exception e) {
                    TaskLogger.e("SerialHelperttlLd " + e.getMessage());
                    if (mInputStream != null && !App.livingServerStop) {
                        retry();
                    }
                }
            }
        }
    }
}