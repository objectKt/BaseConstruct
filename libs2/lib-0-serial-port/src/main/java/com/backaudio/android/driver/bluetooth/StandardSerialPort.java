package com.backaudio.android.driver.bluetooth;

/**
 * 串口访问
 * 
 * @author hknaruto
 * @date 2014-1-15 下午1:55:37
 */
public class StandardSerialPort {
    static {
        try {
            System.loadLibrary("StandardSerialPort");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public native int open(String path, int nBaudrate, int bBits, int nStop, int hasParity, int vtime,
            int vmin);

    public native int close();

    public native int write(byte[] buffer);

    public native int writeLength(byte[] buffer, int length);

    public native int read(byte[] buffer, int length);

}
