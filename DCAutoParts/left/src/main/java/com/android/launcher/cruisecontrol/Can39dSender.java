package com.android.launcher.cruisecontrol;

import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;

/**
 * @description:
 * @createDate: 2023/5/30
 */
public class Can39dSender extends CanSenderBase {

    private String dataLeft = "ff000000000000";


    private String dataLength = "08";
    private static final String ID = "0000039d";

    private String startData = "00";
    private String currentData = startData;

    private String[] dataList = {"00", "08", "10", "18", "20", "28", "30", "38", "40", "48", "50", "58", "60", "68", "70", "78"};
    private int currentPosition = 0;


    @Override
    public void send() {
        isRunnable = true;
        currentPosition = 0;
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (isRunnable) {
                    try {
                        if(!isPause){

                            String sendData = DATA_HEAD + dataLength + ID + dataLeft + currentData;
//                        LogUtils.printI(Can746Sender.class.getSimpleName(), "sendData=" + sendData + ", length=" + sendData.length());
                            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));

                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }

                            if (currentPosition >= (dataList.length - 1)) {
                                currentPosition = 0;
                            }
                            ++currentPosition;
                            currentData = dataList[currentPosition];
                        }else{
                            try {
                                Thread.sleep(100);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
