package com.android.launcher.can;

import com.android.launcher.App;
import dc.library.utils.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.entity.BinaryEntity;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

// 安全带，安全气囊 (已测试)
public class Can375 implements CanParent {

    public static volatile String lastData = "";
    public static volatile String lastSafetyBeltFlag = "";

    @Override
    public void handlerCan(List<String> msg) {

        //30 两边都有人 都系安全带
        //34 主有人复有人  副不系安全带
        //35 两边都未系安全带
        // 31 主未系 副系


        String senddata = String.join("", msg);
//        375, 8, 15, 2F, 35, 3F, 3F, 06, 06, 00
//        3758152F353F3F0
        if (!lastData.equals(senddata)) {
            lastData = senddata;
            LogUtils.printI(Can375.class.getSimpleName(), "handlerCan---msg=" + msg);

            String safeTag = msg.get(2);

            String safeTag1 = CommonUtil.convertHexToBinary(safeTag.substring(1, 2));
            LogUtils.printI(Can375.class.getSimpleName(), "safeTag=" + safeTag + ", safeTag1=" + safeTag1);
            if (safeTag1.equals("0000")) {
                CommonUtil.meterHandler.get("safe").handlerData("10");
            }
            String safeTag11 = safeTag1.substring(0, 1);

            String safeTag12 = safeTag1.substring(1, 2);

            String safeTag13 = safeTag1.substring(2, 3);

            String safeTag14 = safeTag1.substring(3, 4);


            //标志闪烁
            if (safeTag11.contains("1")) {
//                CommonUtil.meterHandler.get("safe").handlerData("8");
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_AIRBAG_FAILURE, true);

                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_AIRBAG_FAILURE);
                messageEvent.data = true;
                EventBus.getDefault().post(messageEvent);
            } else if (safeTag12.contains("1")) {
                //标志亮起
//                CommonUtil.meterHandler.get("safe").handlerData("4");
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_AIRBAG_FAILURE);
                messageEvent.data = true;
                EventBus.getDefault().post(messageEvent);
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_AIRBAG_FAILURE, true);
            } else if (safeTag13.contains("1")) {
                //标志熄灭
//                CommonUtil.meterHandler.get("safe").handlerData("4");
                SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_AIRBAG_FAILURE, false);

                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_AIRBAG_FAILURE);
                messageEvent.data = false;
                EventBus.getDefault().post(messageEvent);
            }

            try {
                String d3Hex = msg.get(3);
                BinaryEntity binaryEntity = new BinaryEntity(d3Hex);
                String d3Status = binaryEntity.getB1() + binaryEntity.getB0();
                String d4Status = msg.get(4);


                String safetyBeltStatus = d3Status + d4Status;
                LogUtils.printI(Can375.class.getSimpleName(), "d3Status=" + d3Status + ", d4Status=" + d4Status + ", safetyBeltStatus=" + safetyBeltStatus);

                if (!lastSafetyBeltFlag.equals(safetyBeltStatus)) {
                    lastSafetyBeltFlag = safetyBeltStatus;
                    //有人
                    CommonUtil.meterHandler.get("an").handlerData(safetyBeltStatus);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }


            String alert = msg.get(8);

            BinaryEntity binaryEntity = new BinaryEntity(msg.get(8));
            LogUtils.printI(Can375.class.getSimpleName(), "d6Hex=" + binaryEntity.getHexData() + ", d6=" + binaryEntity.getBinaryData());


            String alert_f = alert.substring(0, 1);

            LogUtils.printI(Can375.class.getSimpleName(), "alert=" + alert + ", alert_f=" + alert_f);

            if (alert_f.equals("8")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_8_80);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_8_80));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            } else if (alert_f.equals("4")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_8_40);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_8_40));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            } else if (alert_f.equals("2")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_8_20);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_8_20));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);

            } else if (alert_f.equals("1")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
                alertVo.setAlertMessage(AlertMessage.ALERT_375_8_10);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_8_10));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }

            String alert_s = alert.substring(1, 2);


            if (alert_s.equals("8")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_8_08);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_8_08));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            } else if (alert_s.equals("4")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_8_04);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_8_04));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);

            } else if (alert_s.equals("2")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_8_02);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_8_02));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);

            } else if (alert_s.equals("1")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_8_01);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_8_01));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }

//
            String last = msg.get(9);

            String last_s = last.substring(1, 2);
            if (last_s.equals("8")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_9_08);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_9_08));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            } else if (last_s.equals("4")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_9_04);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_9_04));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            } else if (last_s.equals("2")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_9_02);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_9_02));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            } else if (last_s.equals("1")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_375_9_01);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_375_9_01));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }

        }

    }
}
