package com.android.launcher.cruisecontrol;

import com.android.launcher.service.GetCMS;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.util.DataUtils;

/**
 * @date： 2023/11/6
 * @author: 78495
*/
public class Can3E4Sender extends CanSenderBase {

    @Override
    public void send() {
        isRunnable = true;

        new Thread(() -> {
            while (isRunnable) {
                try {
                    if (!isPause) {
                        String  cmd = GetCMS.cms3e4() ;
                        MUsb1Receiver.write(DataUtils.hexStringToByteArray(cmd.toUpperCase()));
                        Thread.sleep(3000);
                    } else {
                        Thread.sleep(3000);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
}
