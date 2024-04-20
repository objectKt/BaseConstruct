package com.backaudio.android.driver;

import com.dc.auto.library.serial.SerialPort;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 同一个串口可以被同时打开，但会导致抢数据
 * 
 * @author hknaruto
 * 
 */
public class LibSerialPort {
    private static List<File> openedSerialPort;

    static {
        System.loadLibrary("SerialPort");
        openedSerialPort = new ArrayList<File>();
    }

    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;
    /*public void closeSerialPort(){
    	close(mFd);
    }*/
    public LibSerialPort(File device, int baudrate, int flags) throws Exception {
        // /dev/ttyMT3
        // 115200

        boolean alreadyOpened = false;
        if (!openedSerialPort.contains(device)) {
            synchronized (LibSerialPort.class) {
                if (!openedSerialPort.contains(device)) {
                    mFd  =SerialPort.openPort(device.getAbsolutePath(), baudrate, flags);

                    if (mFd == null) {
                        throw new Exception("cannot open serialport:" + device.getAbsolutePath());
                    }
                    mFileInputStream = new FileInputStream(mFd);
                    mFileOutputStream = new FileOutputStream(mFd);
                    openedSerialPort.add(device);
                } else {
                    alreadyOpened = true;
                }
            }
        } else {
            alreadyOpened = true;
        }

        if (alreadyOpened) {
            throw new Exception("serialport is already opened! cannot open now");
        }
    }
    public void closeCurrentFileDescriptor(){
        SerialPort.closePort();
        try {
            mFileOutputStream.close();
            mFileInputStream.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        openedSerialPort.clear();
    }

    public FileInputStream getmFileInputStream() {
        return mFileInputStream;
    }

    public FileOutputStream getmFileOutputStream() {
        return mFileOutputStream;
    }

}
