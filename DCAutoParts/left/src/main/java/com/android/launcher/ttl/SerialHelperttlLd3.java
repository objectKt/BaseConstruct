package com.android.launcher.ttl;

import com.android.launcher.App;
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

import dc.library.auto.serial.SerialPort;

//import com.android.launcher.usbdriver.SendHelperCH340Can;

/**
 * 串口工具类
 */
public class SerialHelperttlLd3 {

    private SerialPort mSerialPort;
    private FileOutputStream mOutputStream;
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

    public SerialHelperttlLd3(String sPort, int iBaudRate) {
        this.sPort = "/dev/ttyS3";
        _isOpen = false;
        this._bLoopData = new byte[]{120};
        this.iDelay = 100;
        this.sPort = sPort;
        this.iBaudRate = iBaudRate;
        LogUtils.printI(SerialHelperttlLd3.class.getSimpleName(), "开始串口");
    }

    public void openLLd() throws SecurityException, IOException, InvalidParameterException {
        LogUtils.printI(SerialHelperttlLd3.class.getSimpleName(), "打开串口");
        RUN = false;
        this.mSerialPort = new SerialPort(new File("/dev/ttyS3"), 9600, 0);
        this.mOutputStream = this.mSerialPort.getOutputStream();
        this.mInputStream = this.mSerialPort.getInputStream();
        this.mReadThread = new ReadThread();
        this.mReadThread.start();
//        this.mSendThread = new SerialHelperttlLd.SendThread();
//        this.mSendThread.setSuspendFlag();
//        this.mSendThread.start();

        startSendThread();
        _isOpen = true;
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
                    LogUtils.printE(SerialHelperttlLd3.class.getSimpleName(), "startSendMessageThread---" + e.getMessage());
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
            mSendQueue.put(bOutArray);
        } catch (Exception var3) {
            var3.printStackTrace();
        }
    }

    //
    public static void sendHex(String sHex) {
        try {
            LogUtils.printI(SerialHelperttlLd3.class.getSimpleName(), "send---data=" + sHex);
            byte[] bOutArray = toByteArray(sHex);
            send(bOutArray);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 16位字符串转byte数组
     *
     * @return
     */
    public static byte[] toByteArray(String hexString) {
        hexString = hexString.replaceAll(" ", "");
        final byte[] byteArray = new byte[hexString.length() / 2];
        int k = 0;
        for (int i = 0; i < byteArray.length; i++) {//因为是16进制，最多只会占用4位，转换成字节需要两个16进制的字符，高位在先
            byte high = (byte) (Character.digit(hexString.charAt(k), 16) & 0xff);
            byte low = (byte) (Character.digit(hexString.charAt(k + 1), 16) & 0xff);
            byteArray[i] = (byte) (high << 4 | low);
            k += 2;
        }
        return byteArray;
    }

//    public static byte HexToByte(String inHex) {
//        return (byte) Integer.parseInt(inHex, 16);
//    }
//
//    public static int isOdd(int num) {
//        return num & 1;
//    }
//
//    public void sendTxt(String sTxt) {
//        byte[] bOutArray = sTxt.getBytes();
//
//        this.send(bOutArray);
//    }
//
//    public byte[] getbLoopData() {
//        return this._bLoopData;
//    }


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
//                send(getbLoopData());
//
//                try {
//                    Thread.sleep((long)iDelay);
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
            byte[] buffer = new byte[1024];
            while (!RUN) {
                try {
                    if (mInputStream != null) {
                        int size = mInputStream.read(buffer);
                        if (size > 0) {

                            byte[] by = new byte[size];
                            System.arraycopy(buffer, 0, by, 0, size);
                            String data = toHexString(by, by.length);
                            // todo
                            LogUtils.printI(SerialHelperttlLd3.class.getSimpleName(), "ReadThread---data=" + data);
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


    private void retry() {
        LogUtils.printI(SerialHelperttlLd3.class.getSimpleName(), "retry----");
        new Thread(() -> {
            try {
                FuncUtil.serialHelperttl3.close();
                FuncUtil.serialHelperttl3 = null;
                Thread.sleep(3000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                FuncUtil.initSerialHelper3();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
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
