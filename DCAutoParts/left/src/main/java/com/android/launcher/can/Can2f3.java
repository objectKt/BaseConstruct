package com.android.launcher.can;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.type.GearsType;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

//档位
public class Can2f3 implements CanParent {

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        String degreeD = msg.get(2);
        if (!lastData.equals(degreeD)) {
            LogUtils.printI(Can2f3.class.getSimpleName(),"handlerCan----msg="+msg +", degreeD="+degreeD);

            lastData = degreeD;

            GearsType gearsType = GearsType.TYPE_P;
            if (degreeD.equals("50")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "50");
                gearsType = GearsType.TYPE_P;
            }
            if (degreeD.equals("52")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "52");
                gearsType = GearsType.TYPE_R;
            }
            if (degreeD.equals("4E")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "4e");
                gearsType = GearsType.TYPE_N;
            }
            if (degreeD.equals("44")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "44");
                gearsType = GearsType.TYPE_D_OTHER;
            }
            if (degreeD.equals("01")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "44");
                gearsType = GearsType.TYPE_D;
            }

            if (degreeD.equals("02")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D1");
                gearsType = GearsType.TYPE_D1;
            }
            if (degreeD.equals("03")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D2");
                gearsType = GearsType.TYPE_D2;
            }
            if (degreeD.equals("04")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D3");
                gearsType = GearsType.TYPE_D3;
            }
            if (degreeD.equals("05")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D4");
                gearsType = GearsType.TYPE_D4;
            }
            if (degreeD.equals("06")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D5");
                gearsType = GearsType.TYPE_D5;
            }
            if (degreeD.equals("07")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D6");

                gearsType = GearsType.TYPE_D6;
            }


            if (degreeD.equals("31")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D1");

                gearsType = GearsType.TYPE_D1;
            }
            if (degreeD.equals("32")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D2");

                gearsType = GearsType.TYPE_D2;
            }
            if (degreeD.equals("33")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D3");

                gearsType = GearsType.TYPE_D3;
            }
            if (degreeD.equals("34")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D4");

                gearsType = GearsType.TYPE_D4;
            }
            if (degreeD.equals("35")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D5");

                gearsType = GearsType.TYPE_D5;
            }
            if (degreeD.equals("36")) {
//                CommonUtil.meterHandler.get("degree").handlerData( "D6");
                gearsType = GearsType.TYPE_D6;
            }

            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = gearsType;
            EventBus.getDefault().post(messageEvent);

//            alert = alert.substring(alert.length() - 1, alert.length());

//            AlertVo alertVo = new AlertVo();
//            alertVo.setAlertImg(0);
//            alertVo.setAlertColor(AlertMessage.TEXT_YELLOW);
//            if (alert.equals("04")) {
//                alertVo.setAlertMessage(AlertMessage.ALERT_2F3_04);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData( message);
//            }
//            if (alert.equals("02")) {
//                alertVo.setAlertMessage(AlertMessage.ALERT_2F3_02);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData( message);
//            }
//            if (alert.equals("01")) {
//                alertVo.setAlertMessage(AlertMessage.ALERT_2F3_01);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                CommonUtil.meterHandler.get("alert").handlerData( message);
//            }
        }

    }
}
