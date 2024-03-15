package com.android.launcher.cruisecontrol;

import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.LogUtils;

/**
 * @description:
 * @createDate: 2023/5/30
 */
public class Can39fSender extends CanSenderBase {

    private String dataRight = "01011612ff";

    private String dataLength = "08";
    private static final String ID = "0000039f";

    private String dataHour = "0c"; //小时
    private String dataMinute = "00"; //分钟
    private String dataSecond = "00"; //秒

    private String currentMinute = dataMinute;
    private String currentSecond = dataSecond;
    private String currentHour = dataHour;

    private long lastTime;

    //每10秒变换一次
    private static final long INTERVAL_TIME = 10 * 1000;

    @Override
    public void send() {
        isRunnable = true;
        new Thread(new Runnable() {
            @Override
            public void run() {
                currentMinute = dataMinute;
                currentSecond = dataSecond;
                currentHour = dataHour;
                lastTime = System.currentTimeMillis();

                while (isRunnable) {
                    try {
                        if(!isPause){

                            //AA 00 00 08 00 00 03 9f  c0 01 01 01 16 12 ff
                            //AA 00 00 08 00 00 03 9f 0c 00 10 10 11 61 2f f

//                        String sendData = GetCMS.cms39f();
                            String sendData = DATA_HEAD + dataLength + ID + currentHour + currentMinute + currentSecond + dataRight;
//                        LogUtils.printI(Can39fSender.class.getSimpleName(), "sendData=" + sendData + ", length=" + sendData.length());
                            MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                            long currentTimeMillis = System.currentTimeMillis();
                            long interval = currentTimeMillis - lastTime;
                            if (interval >= INTERVAL_TIME) {
                                lastTime = currentTimeMillis;
                                computeSecond();
                            }
                        }else{
                            try {
                                Thread.sleep(1000);
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

    /**
     * @description: 计算秒
     * @createDate: 2023/5/30
     */
    private void computeSecond() {
        try {
            int second = Integer.parseInt(currentSecond, 16);
            if (second == 59) { //过了60秒，秒重置为0,分钟加1
                currentSecond = dataSecond;
                second = Integer.parseInt(currentSecond, 16);
                computeMinute();
            } else {
                second += 1;
                currentSecond = Integer.toHexString(second);
                if (currentSecond.length() < 2) {
                    currentSecond = "0" + currentSecond;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        LogUtils.printI(Can39fSender.class.getSimpleName(), "computeSecond---currentSecond=" + currentSecond);
    }

    /**
     * @description: 计算分钟
     * @createDate: 2023/5/30
     */
    private void computeMinute() {
        try {
            int minute = Integer.parseInt(currentMinute, 16);
            if (minute == 59) {
                currentMinute = dataMinute;
                minute = Integer.parseInt(currentMinute, 16);
                computeHour();
            } else {
                minute += 1;
                currentMinute = Integer.toHexString(minute);
                if (currentMinute.length() < 2) {
                    currentMinute = "0" + currentMinute;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        LogUtils.printI(Can39fSender.class.getSimpleName(), "computeMinute---currentMinute=" + currentMinute);
    }

    /**
     * @description: 计算小时
     * @createDate: 2023/5/30
     */
    private void computeHour() {
        try {
            if (currentHour.equalsIgnoreCase("17")) {
                currentHour = dataHour;
            } else {
                int hour = Integer.parseInt(currentHour, 16);
                hour += 1;
                currentHour = Integer.toHexString(hour);
                if (currentHour.length() < 2) {
                    currentHour = "0" + currentHour;
                }
            }
        } catch (NumberFormatException e) {
            e.printStackTrace();
        }
        LogUtils.printI(Can39fSender.class.getSimpleName(), "computeHour---currentHour=" + currentHour);
    }
}
