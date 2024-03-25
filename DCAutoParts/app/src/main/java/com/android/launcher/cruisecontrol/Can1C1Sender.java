package com.android.launcher.cruisecontrol;

import com.android.launcher.service.GetCMS;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;
import com.android.launcher.util.LogUtils;

/**
 * @date： 2023/11/6
 * @author: 78495
*/
public class Can1C1Sender extends CanSenderBase {

    @Override
    public void send() {
        isRunnable = true;

        new Thread(() -> {
            while (isRunnable) {
                try {
                    if (!isPause) {
                        String  cmd = GetCMS.cms1c1();
                        LogUtils.printI(Can1C1Sender.class.getSimpleName(), "sendData="+cmd);
                        MUsb1Receiver.write(DataUtils.hexStringToByteArray(cmd.toUpperCase()));
                        Thread.sleep(3000);
                    } else {
                        LogUtils.printI(Can1C1Sender.class.getSimpleName(), "send data pause---");
                        Thread.sleep(3000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
