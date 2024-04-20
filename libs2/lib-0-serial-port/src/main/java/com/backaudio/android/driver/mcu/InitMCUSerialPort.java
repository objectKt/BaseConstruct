package com.backaudio.android.driver.mcu;

import android.os.Looper;
import android.os.Process;

import com.backaudio.android.driver.CommonConfig;
import com.backaudio.android.driver.Utils;
import com.dc.android.launcher.util.HUtils;
import com.drake.logcat.LogCat;

import lib.dc.utils.UtilByteArray;

/**
 * 开启MCU串口监听
 *
 * @author liuguojia
 */
public class InitMCUSerialPort {
    private static final String TAG = InitMCUSerialPort.class.getSimpleName();

    private SerialPortMcuIO mcuIO = null;
    private MCUParse mcuProtocolAnalyzer = null;
    public static volatile boolean isRecvThreadStarted = false;
    private static InitMCUSerialPort instance = null;

    public static InitMCUSerialPort getInstance() {
        if (instance == null) {
            synchronized (InitMCUSerialPort.class) {
                if (instance == null) {
                    instance = new InitMCUSerialPort();
                }
            }
        }
        return instance;
    }

    private InitMCUSerialPort() {
        isRecvThreadStarted = true;
        mcuIO = SerialPortMcuIO.getInstance();
        mcuProtocolAnalyzer = MCUParse.getInstance();
        new Thread(new Runnable() {

            @Override
            public void run() {
                Looper.prepare();
                Process.setThreadPriority(Process.THREAD_PRIORITY_URGENT_AUDIO);
                byte[] buffer = new byte[200];

                while (isRecvThreadStarted) {
                    int size;
                    try {
                        size = mcuIO.read(buffer);
                        if (size > 0) {
                            //Log.d("", "aaaaa ="+size);
                            if (size >= 170) {
                                CommonConfig.errNum += 20;
                            } else {
                                mcuProtocolAnalyzer.pushMcu(buffer, 0, size);
                            }
                            if (CommonConfig.errNum > 100) {
                                CommonConfig.errNum = 0;
                                mcuIO.reInit();
                            }
                        } else {
                            Thread.sleep(2);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e1) {
                            e1.printStackTrace();
                        }
                    }
                }
                Looper.loop();
            }
        }, "mcuThread").start();
    }

    public void writeToMcu(byte[] mcuProtocol) {
        LogCat.d(TAG + " writeToMcu---data=" + Utils.byteArrayToHexString(mcuProtocol));
        if (mcuIO == null) {
            return;
        }
        mcuIO.write(mcuProtocol);
    }

    public void writeCanbox(byte[] buff) {
        byte[] canboxProtocol = wrapCanbox(buff, EWrapType.CAN);
        if (mcuIO == null) {
            return;
        }
        if (canboxProtocol != null) {
            LogCat.i("发送CAN信息：" + UtilByteArray.INSTANCE.toHeXLog(canboxProtocol));
            mcuIO.write(canboxProtocol);
        }
    }

    public synchronized void writeTTL1(byte[] buff) {
        byte[] ttl1Protocol = wrapCanbox(buff, EWrapType.TTL1);
        if (mcuIO == null) {
            return;
        }
        if (ttl1Protocol != null) {
            mcuIO.write(ttl1Protocol);
        }
    }

    // aa 55 0c 01 e2
    // aa bb 22 18 ef 96 e6 2a 2c cd
    // 1c
    protected byte[] wrapCanbox(byte[] buffer, EWrapType type) {
        if (buffer == null) {
            return null;
        }
        byte[] mcuProtocol = new byte[buffer.length + 6];
        mcuProtocol[0] = (byte) 0xAA;
        mcuProtocol[1] = 0x55;
        mcuProtocol[2] = (byte) (buffer.length + 2);// 子协议号，类型各一个字节
        mcuProtocol[3] = 0x01;
        mcuProtocol[4] = type.getCode();
        byte check = (byte) (mcuProtocol[2] + mcuProtocol[3] + mcuProtocol[4]);
        for (int i = 0; i < buffer.length; i++) {
            mcuProtocol[5 + i] = buffer[i];
            check = (byte) (check + buffer[i]);
        }
        mcuProtocol[buffer.length + 5] = check;
        LogCat.d("包装 TTL mcuProtocol " + HUtils.ByteX.INSTANCE.bytesToHeXX(mcuProtocol));
        return mcuProtocol;
    }

    enum EWrapType {
        CAN(0x50),
        TTL1(0xE2);
        private int code;

        private EWrapType(int value) {
            code = value;
        }

        public byte getCode() {
            return (byte) code;
        }
    }
}
