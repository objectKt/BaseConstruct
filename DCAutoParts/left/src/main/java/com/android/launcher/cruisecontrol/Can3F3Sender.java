package com.android.launcher.cruisecontrol;

import com.android.launcher.service.GetCMS;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;

/**
 * @date： 2023/11/6
 * @author: 78495
*/
public class Can3F3Sender extends CanSenderBase {

    @Override
    public void send() {
        isRunnable = true;

        new Thread(() -> {
            while (isRunnable) {
                try {
                    if (!isPause) {
                        String  send3f3 = GetCMS.cms3f3() ;
                       MUsb1Receiver.write(DataUtils.hexStringToByteArray(send3f3.toUpperCase()));
                        Thread.sleep(100);
                    } else {
                        Thread.sleep(100);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
