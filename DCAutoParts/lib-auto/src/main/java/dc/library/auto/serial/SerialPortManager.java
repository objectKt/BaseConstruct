package dc.library.auto.serial;

import android.os.Handler;
import android.os.HandlerThread;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import dc.library.auto.serial.listener.OnSerialPortDataListener;
import dc.library.auto.serial.listener.OnSerialPortOpenListener;
import dc.library.auto.serial.thread.SerialPortReadThread;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * 串口管理
 */
public class SerialPortManager extends SerialPort {

    private static final String TAG = SerialPortManager.class.getSimpleName();
    /*
     * Do not remove or rename the field mFd: it is used by native method close();
     */
    private FileDescriptor mFd;
    private FileInputStream mFileInputStream;
    private FileOutputStream mFileOutputStream;
    private OnSerialPortOpenListener mOpenListener;
    private Handler mSendHandler;
    private HandlerThread mSendHandlerThread;
    private OnSerialPortDataListener mDataListener;
    private SerialPortReadThread mReadThread;

    /**
     * 打开串口
     *
     * @param device   串口地址
     * @param baudRate 串口波特率
     * @return 结果
     */
    public boolean openSerialPort(File device, int baudRate) {
        // 检查串口权限
        if (!device.canRead() || !device.canWrite()) {
            boolean chmod666 = chmod666(device);
            if (!chmod666) {
                Log.i(TAG, "openSerialPort：没有读写权限");
                if (mOpenListener != null) {
                    mOpenListener.onFail(device, OnSerialPortOpenListener.Status.WITHOUT_READ_WRITE_PERMISSION);
                }
                return false;
            }
        }

        try {
            mFd = openPort(device.getAbsolutePath(), baudRate, 0);
            mFileInputStream = new FileInputStream(mFd);
            mFileOutputStream = new FileOutputStream(mFd);
            if (mOpenListener != null) {
                mOpenListener.onSuccess(device);
            }
            // 开启发送数据的线程
            startSendThread();
            // 开启读取数据的线程
            startReadThread();
            return true;
        } catch (Exception e) {
            Logger.getGlobal().warning("Exception" + e);
            if (mOpenListener != null) {
                mOpenListener.onFail(device, OnSerialPortOpenListener.Status.OPEN_FAIL);
            }
        }
        return false;
    }

    /**
     * 关闭串口
     */
    public void closeSerialPort() {
        if (mFd != null) {
            closePort();
            mFd = null;
        }
        // 停止发送数据线程
        stopSendThread();
        // 停止读取数据线程
        stopReadThread();
        if (mFileInputStream != null) {
            try {
                mFileInputStream.close();
            } catch (IOException e) {
                Logger.getGlobal().warning("Exception" + e);
            }
            mFileInputStream = null;
        }
        if (mFileOutputStream != null) {
            try {
                mFileOutputStream.close();
            } catch (IOException e) {
                Logger.getGlobal().warning("Exception" + e);
            }
            mFileOutputStream = null;
        }
        mOpenListener = null;
        mDataListener = null;
    }

    /**
     * 设置串口打开监听器
     */
    public SerialPortManager setSerialPortOpenListener(OnSerialPortOpenListener listener) {
        this.mOpenListener = listener;
        return this;
    }

    /**
     * 发送串口数据
     */
    public boolean send(byte[] bytes) {
        if (mFd != null && mFileInputStream != null && mFileOutputStream != null) {
            if (mSendHandler != null) {
                Message message = Message.obtain();
                message.obj = bytes;
                return mSendHandler.sendMessage(message);
            }
        }
        return false;
    }

    /**
     * 开启发送数据的线程
     */
    private void startSendThread() {
        mSendHandlerThread = new HandlerThread("mSendHandlerThread");
        mSendHandlerThread.start();
        mSendHandler = new Handler(mSendHandlerThread.getLooper()) {
            @Override
            public void handleMessage(@NonNull Message msg) {
                byte[] bytes = (byte[]) msg.obj;
                if (mFileOutputStream != null && bytes != null && bytes.length > 0) {
                    try {
                        mFileOutputStream.write(bytes);
                        if (mDataListener != null) {
                            mDataListener.onDataSent(bytes);
                        }
                    } catch (IOException e) {
                        Logger.getGlobal().warning("Exception" + e);
                    }
                }
            }
        };
    }

    /**
     * 停止发送数据线程
     */
    private void stopSendThread() {
        mSendHandler = null;
        if (mSendHandlerThread != null) {
            mSendHandlerThread.interrupt();
            mSendHandlerThread.quit();
            mSendHandlerThread = null;
        }
    }

    /**
     * 开启读线程
     */
    private void startReadThread() {
        mReadThread = new SerialPortReadThread(mFileInputStream) {
            @Override
            public void onDataReceived(byte[] bytes) {
                if (mDataListener != null) {
                    mDataListener.onDataReceived(bytes);
                }
            }
        };
        mReadThread.start();
    }

    /**
     * 停止读取数据
     */
    private void stopReadThread() {
        if (mReadThread != null) {
            mReadThread.release();
        }
    }

    /**
     * 设置数据监听器
     */
    public SerialPortManager setSerialPortDataListener(OnSerialPortDataListener listener) {
        this.mDataListener = listener;
        return this;
    }
}