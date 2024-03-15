package com.android.launcher.can;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

// 告警
public class Can2fa implements CanParent {

    private static final String TAG = Can2fa.class.getSimpleName();

    public String flg_8 = "8";
    public String flg_4 = "4";
    public String flg_2 = "2";
    public String flg_1 = "1";

    public String handlermsg = "";

    @Override
    public void handlerCan(List<String> msg) {
        String senddata = String.join("", msg);
        if (!handlermsg.equals(senddata)) {
            LogUtils.printI(TAG, "msg=" + msg);
            handlermsg = senddata;
            String f = msg.get(2);
            f = f.substring(0, 1);
            if (f.equals(flg_8)) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.icon2fa180);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_1_80);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
            if (f.equals(flg_4)) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_brake_oil);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_1_40);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
            if (f.equals(flg_2)) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_glass_of_water);
                alertVo.setAlertColor(AlertMessage.TEXT_WHITE + "");
                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_1_20);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
            if (f.equals(flg_1)) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_antifreeze_solution);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_1_10);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }

            String s = msg.get(3);
            String ss = s.substring(0, 1);
            if (ss.equals(flg_8)) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.icon2flamp);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_2_80);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
        }
    }
}
