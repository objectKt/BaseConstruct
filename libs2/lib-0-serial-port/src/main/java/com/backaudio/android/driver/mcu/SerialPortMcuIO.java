package com.backaudio.android.driver.mcu;

import android.util.Log;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedTransferQueue;
import java.util.concurrent.atomic.AtomicBoolean;

import com.backaudio.android.driver.Utils;
import com.dc.auto.library.serial.SerialPort;
import com.drake.logcat.LogCat;

/**
 * 打开MCU串口
 *
 * @author liuguojia
 */
public class SerialPortMcuIO {
    private static final String TAG = SerialPortMcuIO.class.getSimpleName();
    private static SerialPortMcuIO instance = null;

    //发送队列
    private static LinkedTransferQueue<byte[]> mSendQueue = new LinkedTransferQueue<byte[]>();
    private static ExecutorService taskService;
    private boolean isRunning;

    public static SerialPortMcuIO getInstance() {
        if (instance == null) {
            synchronized (SerialPortMcuIO.class) {
                if (instance == null) {
                    instance = new SerialPortMcuIO();
                }
            }
        }
        return instance;
    }

    private FileInputStream mFileInputStream = null;
    private FileOutputStream mFileOutputStream = null;

    public SerialPortMcuIO() {
        init();
    }

    // LibSerialPort serialPort;
    SerialPort serialPort;
    private AtomicBoolean mInReInitState = new AtomicBoolean(false);

    public void reInit() {
        mInReInitState.set(true);
        LogCat.d(TAG + " reInit----");
        close();

        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        // serialPort.closeCurrentFileDescriptor();
        init();
    }

    private void init() {
        int count = 0;
        if (taskService != null) {
            taskService.shutdown();
            taskService = null;
        }
        if (mSendQueue == null) {
            mSendQueue = new LinkedTransferQueue<byte[]>();
        }

        //used = true;
        while (true) {
            Log.e(TAG, " mFileInputStream = " + mFileInputStream);
            try {
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                LogCat.d(TAG + " init read /dev/ttyMCU");
                serialPort = new SerialPort(new File("/dev/ttyMCU"), 115200, 0);
                mFileInputStream = serialPort.getInputStream();
                mFileOutputStream = serialPort.getOutputStream();
                mInReInitState.set(false);
                taskService = Executors.newSingleThreadExecutor();
                startWriteTask();
                break;
            } catch (Exception e) {
                count++;
                if (count > 2) {
                    break;
                }
                Log.e(TAG, "Exception mFileInputStream = " + mFileInputStream);
                try {
                    Thread.sleep(200);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
            }
        }
    }

    private void startWriteTask() {
        isRunning = true;
        taskService.execute(() -> {
            LogCat.d(TAG + " startWriteTask---");
            while (isRunning) {
                try {
                    byte[] data = mSendQueue.take();
                    try {
                        LogCat.d(TAG + " startWriteTask---data= " + Utils.byteArrayToHexString(data));
                        mFileOutputStream.write(data);
                        mFileOutputStream.flush();
                    } catch (Exception e) {
                        e.printStackTrace();
                        LogCat.d(TAG + " Exception=" + e.getMessage());
                    }
                    try {
                        Thread.sleep(10);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public int read(byte[] buffer) throws IOException {
        if (mInReInitState.get()) {
            LogCat.d(TAG + " read---mInReInitState=" + mInReInitState.get() + ", mFileInputStream=" + mFileInputStream);
            return 0;
        }
        return mFileInputStream.read(buffer);
    }

    public void write(byte[] responseBuffer) {
        if (mInReInitState.get()) {
            LogCat.d(TAG + " write---mInReInitState=" + mInReInitState.get());
            return;
        }
        try {
            mSendQueue.put(responseBuffer);
        } catch (Exception var3) {
            var3.printStackTrace();
        }

    }

    public void close() {
        LogCat.d(TAG + " close----");

        InitMCUSerialPort.isRecvThreadStarted = false;

        isRunning = false;
        mInReInitState.set(true);

        if (mSendQueue != null) {
            mSendQueue.clear();
        }

        try {
            if (taskService != null) {
                taskService.shutdownNow();
                taskService = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mFileOutputStream != null) {
            try {
                mFileOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mFileOutputStream = null;
            }
        }
        if (mFileInputStream != null) {
            try {
                mFileInputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                mFileInputStream = null;
            }
        }

        try {
            if (serialPort != null) {
                serialPort.closePort();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
