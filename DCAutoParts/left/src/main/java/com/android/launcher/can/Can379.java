package com.android.launcher.can;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.can.status.Can379Status;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.type.CarType;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

// 车身系统相关提示
public class Can379 implements CanParent {
    public String handlermsg = "";
    public volatile boolean show;
    private volatile boolean isDown = false;

    @Override
    public void handlerCan(List<String> msg) {

        //37920000000000000000
        try {
            String senddata = String.join("", msg);

            LogUtils.printI(Can379.class.getSimpleName(), "msg=" + msg + ", handlermsg=" + handlermsg);

            //[379, 2, 00, 01, 00, 00, 00, 00, 00, 00]
            if (MeterActivity.carType == CarType.S600.ordinal() || MeterActivity.carType == CarType.S500.ordinal()) { //s600
                if (!handlermsg.equals(senddata)) {
                    handlermsg = senddata;
                    String alert = msg.get(3);

                    LogUtils.printI(Can379.class.getSimpleName(), "carType=" + MeterActivity.carType + ", alert=" + alert);
                    if (alert.equals(Can379Status.STA_OFF.getValue())) {
                        if (show) {
                            show = false;
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CAN379_VIEW_REMOVE));
                        }
                        isDown = true;
                    } else {
                        if (isDown) {
                            show = true;
                            sendMessage(R.drawable.car379w, AlertMessage.ALERT_279_1_10);
                        }
                    }
                }
            } else {
                if (!handlermsg.equals(senddata)) {
                    handlermsg = senddata;
                    String alert = msg.get(2);
                    if (alert.equals(Can379Status.STA_OFF.getValue())) {
                        if (show) {
                            show = false;
                            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CAN379_VIEW_REMOVE));
                        }
                    } else {
                        show = true;
                        String alert_f = alert.substring(0, 1);
                        if (alert_f.equals(Can379Status.STA_LIFT.getValue())) {
                            sendMessage(R.drawable.car379w, AlertMessage.ALERT_279_1_10);
                        }
                        String alert_s = alert.substring(1, 2);

                        LogUtils.printI(Can379.class.getSimpleName(), "alert_f=" + alert_f + ", alert_s=" + alert_s);
                        if (alert_s.equals(Can379Status.STA_TO_LOW.getValue())) {
                            sendMessage(R.drawable.car379r, AlertMessage.ALERT_279_1_01);

                        } else if (alert_s.equals(Can379Status.STA_BREAKDOWN.getValue())) {
                            sendMessage(R.drawable.car379y, AlertMessage.ALERT_279_1_04);

                        } else if (alert_s.equals(Can379Status.STA_LIFT_NOW.getValue())) {
                            sendMessage(R.drawable.car379y, AlertMessage.ALERT_279_1_02);
                        }
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(int redId, String message) {
        AlertVo alertVo = new AlertVo();
        alertVo.setType(AlertVo.Type.CHASSIS);
        alertVo.setAlertImg(redId);
        alertVo.setAlertMessage(message);
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
        messageEvent.data = alertVo;
        EventBus.getDefault().post(messageEvent);
    }

    public void clear() {
        handlermsg = "";
        isDown = false;
    }
}