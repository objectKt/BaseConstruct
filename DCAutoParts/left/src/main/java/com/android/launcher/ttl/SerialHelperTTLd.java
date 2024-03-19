package com.android.launcher.ttl;

import androidx.annotation.NonNull;

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
import dc.library.auto.task.XTask;
import dc.library.auto.task.api.TaskChainEngine;
import dc.library.auto.task.api.step.SimpleTaskStep;
import dc.library.auto.task.api.step.XTaskStep;
import dc.library.auto.task.core.ITaskChainEngine;
import dc.library.auto.task.core.param.ITaskResult;
import dc.library.auto.task.core.step.impl.TaskChainCallbackAdapter;
import dc.library.auto.task.core.step.impl.TaskCommand;
import dc.library.auto.task.logger.TaskLogger;
import dc.library.auto.task.thread.pool.cancel.ICanceller;
import dc.library.auto.util.HexUtilJava;

/**
 * 串口工具类
 */
public class SerialHelperTTLd {

    private SerialPort mSerialPort;
    private FileOutputStream mOutputStream;
    private InputStream mInputStream;
    private ReadThread mReadThread;
    public static ACache aCache = ACache.get(App.getGlobalContext());
    public volatile static boolean RUN;

    //发送队列
    private static final LinkedTransferQueue<byte[]> mSendQueue = new LinkedTransferQueue<>();

    public SerialHelperTTLd() {
        LogUtils.printI(ConstVal.Log.TAG, "开始串口----");
    }

    public void openLLd() throws SecurityException, IOException, InvalidParameterException {
        TaskLogger.i("打开串口----openLLd()");
        RUN = false;
//        mSerialPort = new SerialPort(new File("/dev/ttyS1"), 115200, 0);
//        boolean valid = mSerialPort.getmFd().valid();
//        if (!valid) {
//            TaskLogger.e("打开串口----getmFd().valid() = false");
//            retry();
//        } else {
//            startReadThread();
//            startSendThread();
//        }
    }

    private void startReadThread() {
//        mOutputStream = mSerialPort.getOutputStream();
//        mInputStream = mSerialPort.getInputStream();
//        mReadThread = new ReadThread();
//        mReadThread.start();
    }

    private void retry() {
        TaskChainEngine engine = XTask.getTaskChain();
        XTaskStep taskStep = XTask.getTask(new TaskCommand() {
            @Override
            public void run() {
                close();
                try {
                    Thread.sleep(3000);
                    notifyTaskSucceed();
                } catch (InterruptedException e) {
                    notifyTaskFailed();
                    TaskLogger.e("SerialHelperLLD retry " + e.getMessage());
                }
            }
        });
        engine.addTask(taskStep)
                .addTask(XTask.getTask(new TaskCommand() {
                    @Override
                    public void run() {
                        try {
                            FuncUtil.initSerialHelper1();
                            notifyTaskSucceed();
                        } catch (IOException e) {
                            notifyTaskFailed();
                            TaskLogger.e("SerialHelperLLD initSerialHelper1" + e.getMessage());
                        }
                    }
                }));
        engine.setTaskChainCallback(new TaskChainCallbackAdapter() {

            @Override
            public boolean isCallBackOnMainThread() {
                // 回调是否返回主线程, 默认是true
                return false;
            }

            @Override
            public void onTaskChainStart(@NonNull ITaskChainEngine engine) {
                TaskLogger.i("task chain start");
            }

            @Override
            public void onTaskChainCompleted(@NonNull ITaskChainEngine engine, @NonNull ITaskResult result) {
                TaskLogger.i("task chain completed, thread:" + Thread.currentThread().getName());
            }

            @Override
            public void onTaskChainError(@NonNull ITaskChainEngine engine, @NonNull ITaskResult result) {
                TaskLogger.e("task chain error");
            }
        });
        engine.start();
    }

    private void startSendThread() {
        TaskLogger.i("startSendThread");
        new Thread(() -> {
            while (true) {
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

    /**
     * 读取串口数据线程
     */
    private class ReadThread extends Thread {

        private ReadThread() {
        }

        @Override
        public void run() {
            super.run();
            LogUtils.printI(ConstVal.Log.TAG, "读取串口数据线程--- RUN " + RUN);
            byte[] buffer = new byte[1024];
            LogUtils.printI(ConstVal.Log.TAG, "读取串口数据线程--- buffer.length " + buffer.length);
            while (true) {
                TaskLogger.i("mInputStream--- try ");
                try {
                    if (mInputStream != null) {
                        int size = mInputStream.read(buffer);
                        TaskLogger.i("mInputStream--- size " + size);
                        if (size > 0) {
                            byte[] by = new byte[size];
                            System.arraycopy(buffer, 0, by, 0, size);
                            String ss = HexUtilJava.toHexString(by, by.length);
                            TaskLogger.i("ss=" + ss);
                            BenzHandlerData.handlerFromRight(ss);
                        }
                    } else {
                        TaskLogger.e("mInputStream--- null ");
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

    public void close() {
        try {
            mSendQueue.clear();
            if (mInputStream != null) {
                mInputStream.close();
                mInputStream = null;
            }
            if (mReadThread != null) {
                mReadThread.interrupt();
            }
            if (mOutputStream != null) {
                mOutputStream.close();
                mOutputStream = null;
            }
            if (mSerialPort != null) {
                SerialPort.closePort();
                mSerialPort = null;
            }
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
}