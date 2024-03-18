package com.android.launcher.ttl;


import android.util.Log;

import com.android.launcher.App;
import com.android.launcher.usbdriver.BenzHandlerData;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.HandlerRequestbyte;
import com.android.launcher.util.LogUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;
import java.util.concurrent.LinkedTransferQueue;

import com.dc.auto.library.serial.SerialPort;

//import com.android.launcher.usbdriver.SendHelperCH340Can;


/**
 * 串口工具类
 */
public class SerialHelperttlLd {

    private static final String TAG = "hufei";

    private SerialPort mSerialPort;
    private static FileOutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    private String sPort;
    private int iBaudRate;
    public volatile static boolean _isOpen;
    private byte[] _bLoopData;
    private int iDelay;
    public static HandlerRequestbyte handlerRequest = new HandlerRequestbyte();
    public static ACache aCache = ACache.get(App.getGlobalContext());
    public volatile static boolean RUN;

    //发送队列
    private static LinkedTransferQueue<byte[]> mSendQueue = new LinkedTransferQueue<byte[]>();


    public SerialHelperttlLd(String sPort, int iBaudRate) {
        this.sPort = "/dev/ttyS1";
        _isOpen = false;
        this._bLoopData = new byte[]{120};
        this.iDelay = 100;
        this.sPort = sPort;
        this.iBaudRate = iBaudRate;

        LogUtils.printI(TAG, "开始串口----");
    }


    public void openLLd() throws SecurityException, IOException, InvalidParameterException {

        LogUtils.printI(TAG, "打开串口----");
        RUN = false;
        this.mSerialPort = new SerialPort(new File("/dev/ttyS1"), 115200, 0);

        if (!this.mSerialPort.getmFd().valid()) { //串口打开失败
            retry();
        } else {
            mOutputStream = this.mSerialPort.getOutputStream();
            this.mInputStream = this.mSerialPort.getInputStream();
            this.mReadThread = new ReadThread();
            this.mReadThread.start();
//        this.mSendThread = new SerialHelperttlLd.SendThread();
//        this.mSendThread.setSuspendFlag();
//        this.mSendThread.start();

            startSendThread();
            _isOpen = true;
        }
    }

    private void retry() {
        LogUtils.printI(SerialHelperttlLd.class.getSimpleName(), "retry----");
        new Thread(() -> {
            try {
                FuncUtil.serialHelperttl.close();
                FuncUtil.serialHelperttl = null;
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            try {

                FuncUtil.initSerialHelper1();
            } catch (Exception e) {
                e.printStackTrace();
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
                    e.printStackTrace();
                    LogUtils.printE(SerialHelperttlLd.class.getSimpleName(), "startSendMessageThread---" + e.getMessage());
                }
            }

        }).start();
    }

    public void close() {
        try {

            mSendQueue.clear();

            try {
                if (mInputStream != null) {
                    mInputStream.close();
                    mInputStream = null;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (this.mReadThread != null) {
                this.mReadThread.interrupt();
            }

            try {
                if (mOutputStream != null) {
                    mOutputStream.close();
                    mOutputStream = null;
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

            if (this.mSerialPort != null) {
                this.mSerialPort.closePort();
                this.mSerialPort = null;
            }

            RUN = true;
            this._isOpen = false;
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    public static void send(byte[] bOutArray) {
        try {
            LogUtils.printI(TAG, "send---length=" + bOutArray.length);
            mSendQueue.put(bOutArray);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    //
    public static void sendHex(String sHex) {
//        Log.i("sendHex",sHex+"=================");
        byte[] bOutArray = toByteArray(sHex);
        send(bOutArray);
    }


//    /**
//     * 16位字符串转byte数组
//     *
//     * @return
//     */
//    public static byte[] toByteArray(String hexString) {
//        hexString = hexString.replaceAll(" ", "");
//        final byte[] byteArray = new byte[hexString.length() / 2];
//        int k = 0;
//        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
//            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
//            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
//            byteArray[i] = (byte) (high << 4 | low);
//            k += 2;
//        }
//        return byteArray;
//    }


    /**
     * 数据转成二进制数据发送
     *
     * @param hexString
     * @return
     */
    public static byte[] toByteArray(String hexString) {
        if (hexString != null) {
//            LogUtils.printI(TAG,"toByteArray----hexString=" +hexString);
            char[] NewArray = new char[1000];
            char[] array = hexString.toCharArray();

            int length = 0;
            for (int i = 0; i < array.length; i++) {
                if (array[i] != ' ') {
                    NewArray[length] = array[i];
                    length++;
                }
            }
            int EvenLength = (length % 2 == 0) ? length : length + 1;
            if (EvenLength != 0) {
                int[] data = new int[EvenLength];
                data[EvenLength - 1] = 0;
                for (int i = 0; i < length; i++) {
                    if (NewArray[i] >= '0' && NewArray[i] <= '9') {
                        data[i] = NewArray[i] - '0';
                    } else if (NewArray[i] >= 'a' && NewArray[i] <= 'f') {
                        data[i] = NewArray[i] - 'a' + 10;
                    } else if (NewArray[i] >= 'A' && NewArray[i] <= 'F') {
                        data[i] = NewArray[i] - 'A' + 10;
                    }
                }
                byte[] byteArray = new byte[EvenLength / 2];
//                LogUtils.printI(TAG,"EvenLength="+EvenLength +", byteArray.length="+byteArray.length);
                for (int i = 0; i < EvenLength / 2; i++) {
                    byteArray[i] = (byte) (data[i * 2] * 16 + data[i * 2 + 1]);
                }
                return byteArray;
            }
        }
        return new byte[]{};
    }


    public static byte HexToByte(String inHex) {
        return (byte) Integer.parseInt(inHex, 16);
    }

    public static int isOdd(int num) {
        return num & 1;
    }

    public void sendTxt(String sTxt) {
        byte[] bOutArray = sTxt.getBytes();

        this.send(bOutArray);
    }

    public int getBaudRate() {
        return this.iBaudRate;
    }

    public boolean setBaudRate(int iBaud) {
        if (this._isOpen) {
            return false;
        } else {
            this.iBaudRate = iBaud;
            return true;
        }
    }

    public boolean setBaudRate(String sBaud) {
        int iBaud = Integer.parseInt(sBaud);
        return this.setBaudRate(iBaud);
    }

    public byte[] getbLoopData() {
        return this._bLoopData;
    }


    public boolean isOpen() {
        return this._isOpen;
    }

    public int getiDelay() {
        return this.iDelay;
    }

    public void setiDelay(int iDelay) {
        this.iDelay = iDelay;
    }


//    /**
//     * 发送数据线程
//     */
//    private class SendThread extends Thread {
//        public boolean suspendFlag;
//
//        private SendThread() {
//            this.suspendFlag = true;
//        }
//
//        public void run() {
//            super.run();
//
//            while (!this.isInterrupted()) {
//                synchronized (this) {
//                    while (this.suspendFlag) {
//                        try {
//                            this.wait();
//                        } catch (InterruptedException var5) {
//                            var5.printStackTrace();
//                        }
//                    }
//                }
//
//
//                SerialHelperttlLd.this.send(SerialHelperttlLd.this.getbLoopData());
//
//                try {
//                    Thread.sleep((long) SerialHelperttlLd.this.iDelay);
//                } catch (InterruptedException var4) {
//                    var4.printStackTrace();
//                }
//            }
//
//
//        }
//
//        public void setSuspendFlag() {
//            this.suspendFlag = true;
//        }
//
//        public synchronized void setResume() {
//            this.suspendFlag = false;
//            this.notify();
//        }
//    }


    /**
     * 读取串口数据线程
     */
    private class ReadThread extends Thread {
        private ReadThread() {
        }

        public void run() {
            super.run();
            LogUtils.printI(TAG, "读取串口数据线程---");

            byte[] buffer = new byte[1024];
            while (!RUN) {
                try {
                    if (mInputStream != null) {
                        int size = mInputStream.read(buffer);
                        if (size > 0) {

                            byte[] by = new byte[size];
                            System.arraycopy(buffer, 0, by, 0, size);
                            String ss = toHexString(by, by.length);
                            // todo
                            LogUtils.printI("SerialHelperttlLd", "ss=" + ss);
                            //Log.i("hufei", "执行了 SerialHelperttlLd ReadThread");
                            BenzHandlerData.handlerFromRight(ss);
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    if (mInputStream != null && !App.livingServerStop) {
                        retry();
                    }
                }
            }
        }
    }


    public static String toHexString(byte[] arg, int length) {
        if (arg != null && arg.length != 0) {
            StringBuilder sb = new StringBuilder();
            char[] hexArray = "0123456789ABCDEF".toCharArray();

            for (int j = 0; j < length; ++j) {
                int v = arg[j] & 255;
                sb.append(hexArray[v >>> 4]).append(hexArray[v & 15]);
            }

            return sb.toString();
        } else {
            return null;
        }
    }
}
