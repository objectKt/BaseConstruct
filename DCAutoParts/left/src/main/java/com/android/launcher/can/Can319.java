package com.android.launcher.can;

import android.os.Build;

import java.util.List;

//sos紧急求助电话系统停止运作
public class Can319 implements CanParent {
    public String handlermsg = "";

    @Override
    public void handlerCan(List<String> msg) {

        String senddata = String.join("", msg);
        if (!handlermsg.equals(senddata)) {
            handlermsg = senddata;
            String alert = msg.get(2);

//            if (alert.equals("20")){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(0);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_319_2_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
        }

    }
}
