package com.backaudio.android.driver.bluetooth;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;


import com.backaudio.android.driver.Utils;
import com.dc.auto.library.serial.SerialPort;

/**
 * 打开蓝牙串口
 * @author liuguojia
 *
 */
public class SerialPortBTIO{
    private final String TAG = "driverlog";

    private static SerialPortBTIO instance = null;


    public static SerialPortBTIO getInstance() {
        if (instance == null) {
            synchronized (SerialPortBTIO.class) {
                if (instance == null) {
                    instance = new SerialPortBTIO();
                }
            }
        }
        return instance;
    }

    private FileInputStream mFileInputStream = null;
    private FileOutputStream mFileOutputStream = null;

    private SerialPortBTIO() {
        init(this);
    }

    public int read(byte[] buffer) throws IOException {
        if (mFileInputStream == null) {
            while (mFileInputStream == null) {
                init(this);
                if (mFileInputStream == null) {
                    Log.e(TAG, "/dev/BT_serial cannot open");
                }
            }
        }
        return mFileInputStream.read(buffer);
    }

    public void write(byte[] responseBuffer) {
    	String temp = "bluetoothprot: write->[" + Utils.byteArrayToHexString(responseBuffer, 0, responseBuffer.length) + "]>";
    	Log.d(TAG, temp);
//        LogCat.d(TAG +temp);
    	synchronized (SerialPortBTIO.class) {
            if (mFileOutputStream == null) {
                init(this);
                if (mFileOutputStream == null) {
                    Log.e(TAG, "/dev/BT_serial cannot open");
                    return;
                }
            }
            try {
                mFileOutputStream.write(responseBuffer);
                mFileOutputStream.flush();
            } catch (Exception e) {
                Log.e(TAG, "/dev/BT_serial write error " + e.getMessage());
            }
            /*try {
                Thread.sleep(40);
            } catch (InterruptedException e) {
            }*/
        }

    }

    private void init(SerialPortBTIO serialPortBTIO) {
        int count = 0;
        while (true) {
            try {
                SerialPort serialPort = new SerialPort(new File("/dev/BT_serial"), 115200, 0);
            	serialPortBTIO.mFileInputStream = serialPort.getInputStream();
            	serialPortBTIO.mFileOutputStream = serialPort.getOutputStream();
                break;
            } catch (Exception e) {
                count++;
                if (count > 2) {
                    break;
                }
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    public void close(){
        SerialPort.closePort();
    }
}
