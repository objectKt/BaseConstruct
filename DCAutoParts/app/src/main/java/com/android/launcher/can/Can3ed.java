package com.android.launcher.can;

import android.os.Build;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.LogUtils;

import java.util.List;

public class Can3ed implements CanParent {

    private static final String TAG = Can3ed.class.getSimpleName();

    public static volatile String lastData = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handlerCan(List<String> msg) {

        String senddata = String.join("", msg);

        //[3ed, 8, 00, FE, 00, 00, 00, 00, 00, 00]
        if (!lastData.equals(senddata)) {
            lastData = senddata;

            LogUtils.printI(TAG, "msg=" + msg);

            String yeshi = msg.get(2);
            String yeshibinary = CommonUtil.convertHexToBinary(yeshi);

            if (yeshibinary.indexOf("1") == 6) {
//            EventBusMeter.getInstance().postSticky(new MessageWrap("yeshi", "on"));
            } else {
//            EventBusMeter.getInstance().postSticky(new MessageWrap("yeshi", "off"));
            }
        }
    }
}
