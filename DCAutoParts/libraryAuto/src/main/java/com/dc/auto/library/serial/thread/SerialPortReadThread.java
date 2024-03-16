package com.dc.auto.library.serial.thread;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;

/**
 * 串口消息读取线程
 */
public abstract class SerialPortReadThread extends Thread {

    public abstract void onDataReceived(byte[] bytes);

    private InputStream mInputStream;
    private byte[] mReadBuffer;
    private static final String TAG = "<<<SerialPortReadThread";

    public SerialPortReadThread(InputStream inputStream) {
        mInputStream = inputStream;
        mReadBuffer = new byte[1024];
    }

    @Override
    public void run() {
        super.run();
        while (!isInterrupted()) {
            try {
                if (mInputStream == null) return;
                int size = mInputStream.read(mReadBuffer);
                if (size <= 0) return;
                byte[] readBytes = new byte[size];
                System.arraycopy(mReadBuffer, 0, readBytes, 0, size);
                Log.d(TAG, "run: readBytes = " + readBytes);
                onDataReceived(readBytes);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 关闭线程，资源释放
     */
    public void release() {
        interrupt();
        if (mInputStream != null) {
            try {
                mInputStream.close();
                mInputStream = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
