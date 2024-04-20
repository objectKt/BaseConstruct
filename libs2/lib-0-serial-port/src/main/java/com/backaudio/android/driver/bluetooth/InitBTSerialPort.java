package com.backaudio.android.driver.bluetooth;

import android.os.Looper;
import android.os.Process;

import com.backaudio.android.driver.mcu.InitMCUSerialPort;

/**
 * 开启蓝牙串口监听
 *
 * @author liuguojia
 */
public class InitBTSerialPort {
    private static final String TAG = InitBTSerialPort.class.getSimpleName();

    private SerialPortBTIO btIO = null;
    private boolean isReceiveThreadStarted;
    private static InitBTSerialPort instance = null;

    public static InitBTSerialPort getInstance() {
        if (instance == null) {
            synchronized (InitBTSerialPort.class) {
                if (instance == null) {
                    instance = new InitBTSerialPort();
                }
            }
        }
        return instance;
    }

    private InitBTSerialPort() {
        isReceiveThreadStarted = true;
        btIO = SerialPortBTIO.getInstance();
        bluetoothProtocolAnalyzer = new BluetoothProtocolAnalyzer2();
        new Thread(new Runnable() {

            public void run() {
                Looper.prepare();
                Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
                byte[] buffer = new byte[200];
                while (isReceiveThreadStarted) {
                    int size;
                    try {
                        size = btIO.read(buffer);
                        if (size > 0) {
                            // 解析蓝牙协议
                            bluetoothProtocolAnalyzer.push(buffer, 0, size);
                        } else {
                            Thread.sleep(2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                Looper.loop();
            }
        }, "btThread").start();
    }

    public synchronized void writeBlueTooth(byte[] btProtocol) {
        if (btIO == null) {
            return;
        }
        btIO.write(btProtocol);
    }

    public void addEventHandler(IBluetoothEventHandler handler) {
        if (handler == null) {
            return;
        }
        bluetoothProtocolAnalyzer.setEventHandler(handler);
    }

    private IBluetoothProtocolAnalyzer bluetoothProtocolAnalyzer;
}
