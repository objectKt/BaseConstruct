package com.android.launcher.can;

import com.android.launcher.util.LogUtils;

import java.util.List;

//灯相关操作告警
public class Can2fb implements CanParent {

    private static final String TAG = Can2fb.class.getSimpleName();

    public String flg_8 = "8";
    public String flg_4 = "4";
    public String flg_2 = "2";
    public String flg_1 = "1";

    public String handlermsg = "";

    @Override
    public void handlerCan(List<String> msg) {

        String senddata = String.join("", msg);
        if (!handlermsg.equals(senddata)) {
            handlermsg = senddata;
            LogUtils.printI(TAG, "msg=" + msg);
            String f_f = msg.get(2).substring(0, 1);
        }
    }
}