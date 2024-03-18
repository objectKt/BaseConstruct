package com.android.launcher.cansender;

import com.android.launcher.cruisecontrol.CanSenderBase;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.LogUtils;

/**
 * @description: 发动机机油液位
 * @createDate: 2023/8/17
 */
public class Can3DCSender extends CanSenderBase {

    private static final String DEFAULT_DATA1 = "3008140000000000";
    private static final String DEFAULT_DATA2 = "0313060600D0010A";

    private String dataLength = "08";

    private static final String ID = "000003DC";

    private boolean testEngineOil = false;

    public static volatile boolean isSendTest = false;

    @Override
    public void send() {
        isRunnable = true;
        testEngineOil = false;
        new Thread(() -> {
            while (isRunnable) {
                try {
                    if (!testEngineOil) {
                        String sendData = DATA_HEAD + dataLength + ID + DEFAULT_DATA1;
                        LogUtils.printI(Can3DCSender.class.getSimpleName(), "sendDData----sendData=" + sendData + ", lenth=" + sendData.length());
                        MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

                        //间隔100毫秒
                        Thread.sleep(40);
                        sendData = DATA_HEAD + dataLength + ID + DEFAULT_DATA2;
                        LogUtils.printI(Can3DCSender.class.getSimpleName(), "sendDData----sendData=" + sendData + ", lenth=" + sendData.length());
                        MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

                        //总体10秒执行一次
                        Thread.sleep(1000 * 10);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void testEngineOil() {
        new Thread(() -> {
            try {
                testEngineOil = true;

                //间隔100毫秒
                Thread.sleep(40);

                String sendData = DATA_HEAD + dataLength + ID + "100A13050B03000C";
                LogUtils.printI(Can3DCSender.class.getSimpleName(), "sendDData----sendData=" + sendData + ", lenth=" + sendData.length());
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

                //间隔100毫秒
                Thread.sleep(40);
                sendData = DATA_HEAD + dataLength + ID + "21030000A603000C";
                LogUtils.printI(Can3DCSender.class.getSimpleName(), "sendDData----sendData=" + sendData + ", lenth=" + sendData.length());
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

                if (!isSendTest) {
                    isSendTest = true;

                    Thread.sleep(40);
                    sendData = DATA_HEAD + dataLength + "00005818" + "0000000002000A00";
                    LogUtils.printI(Can3DCSender.class.getSimpleName(), "sendDData----sendData=" + sendData + ", lenth=" + sendData.length());
                    MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                testEngineOil = false;
            }
        }).start();
    }
}
