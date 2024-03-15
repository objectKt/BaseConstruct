package com.android.launcher.can;

import android.os.Build;

import com.android.launcher.util.LogUtils;

import java.util.List;

// 告警
public class Can2f9 implements CanParent {

    private static final String TAG = Can2f9.class.getSimpleName();

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

            String alert = msg.get(2);

            String alert_f = alert.substring(1, 2);

//            if (alert_f.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                 alertVo.setAlertColor(AlertMessage.TEXT_WHITE+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2F9_2_04);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (alert_f.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                 alertVo.setAlertColor(AlertMessage.TEXT_WHITE+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2F9_2_02);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert",message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }


            String alert2 = msg.get(3);

            String alert_s = alert2.substring(1, 2);

//
//            if (alert_s.equals(flg_1)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                 alertVo.setAlertColor(AlertMessage.TEXT_WHITE+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2F9_3_01);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //  EventBusMeter.getInstance().postSticky(new MessageWrap("alert",message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
        }
    }
}
