package com.android.launcher.cruisecontrol;

import com.android.launcher.App;
import com.android.launcher.service.GetCMS;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

/**
 * @description:
 * @createDate: 2023/5/30
 */
public class Can3e1Sender extends CanSenderBase {

    private String data = "d1fc743d00ff5555";

    private String dataLength = "08";
    private static final String ID = "000003e1";

    //安全带收紧状态, 1是关， 5是开
    private String safeBeltFragStatus = "5";

    @Override
    public void send() {
        isRunnable = true;

        new Thread(() -> {
            try {
                boolean isFrap = SPUtils.getBoolean(App.getGlobalContext(), SPUtils.SP_SAFE_BELT_SWITCH, true);
//                LogUtils.printI(Can3e1Sender.class.getSimpleName(), "isFrap=" + isFrap);
                updateSafeBeltFrapStatus(isFrap);
            } catch (Exception e) {
                e.printStackTrace();
            }
            while (isRunnable) {
                try {
                    if (!isPause) {
                        String send3e1 = "";
                        String canId1 = "AA000008000003E1";
                        String send3e1s = "CDFC3F3C" + safeBeltFragStatus + "0FF5555";
                        String send3e1c = "CDFC3F3C" + safeBeltFragStatus + "0FF0000";
                        String send3e1m = "CDFC3F3C" + safeBeltFragStatus + "0FFAAAA";


                        String currentCms = GetCMS.aCache.getAsString("CMS");
                        String lightStatus = GetCMS.aCache.getAsString("lightStatus");

                        if (lightStatus == null) {
                            lightStatus = "off";
                        }
                        if (currentCms == null) {
                            GetCMS.aCache.put("CMS", "S");
                            currentCms = "S";
                        }
                        if (currentCms.equals("S")) {
                            send3e1 = lightStatus.equals("on") ? send3e1s.replaceFirst("C", "D") : send3e1s;
                        } else if (currentCms.equals("M")) {
                            send3e1 = lightStatus.equals("on") ? send3e1m.replaceFirst("C", "D") : send3e1m;
                        } else if (currentCms.equals("C")) {
                            send3e1 = lightStatus.equals("on") ? send3e1c.replaceFirst("C", "D") : send3e1c;
                        } else {
                            send3e1 = lightStatus.equals("on") ? send3e1s.replaceFirst("C", "D") : send3e1s;
                        }
                        String sendData = canId1 + send3e1;
//                        LogUtils.printI(Can3e1Sender.class.getSimpleName(), "sendData=" + sendData + ", length=" + sendData.length());
                        MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
                        Thread.sleep(1000);
                    } else {
                        Thread.sleep(1000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public void updateSafeBeltFrapStatus(Boolean isFrap) {
        if (isFrap) {
            safeBeltFragStatus = "5";
        } else {
            safeBeltFragStatus = "1";
        }
    }

    public void setDayRunLight(Boolean isOpen) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                if(isOpen){

                }else{

                }
                String send3e1 = "";
                String canId1 = "AA000008000003E1";
                String send3e1s = "CDFC3F3C" + safeBeltFragStatus + "0FF5555";
                String send3e1c = "CDFC3F3C" + safeBeltFragStatus + "0FF0000";
                String send3e1m = "CDFC3F3C" + safeBeltFragStatus + "0FFAAAA";


                String currentCms = GetCMS.aCache.getAsString("CMS");

                if (currentCms == null) {
                    GetCMS.aCache.put("CMS", "S");
                    currentCms = "S";
                }
                if (currentCms.equals("S")) {
                    send3e1 = isOpen ? send3e1s.replaceFirst("C", "D") : send3e1s;
                } else if (currentCms.equals("M")) {
                    send3e1 = isOpen ? send3e1m.replaceFirst("C", "D") : send3e1m;
                } else if (currentCms.equals("C")) {
                    send3e1 = isOpen ? send3e1c.replaceFirst("C", "D") : send3e1c;
                } else {
                    send3e1 = isOpen ? send3e1s.replaceFirst("C", "D") : send3e1s;
                }
                String sendData = canId1 + send3e1;
                    LogUtils.printI(Can3e1Sender.class.getSimpleName(), "setDayRunLight---sendData="+sendData);
                MUsb1Receiver.write(DataUtils.hexStringToByteArray(sendData.toUpperCase()));
            }
        }).start();

    }
}
