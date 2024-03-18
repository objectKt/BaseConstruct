package com.android.launcher.can;

import android.os.Build;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.entity.BinaryEntity;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 水温告警 发动机告警等
 */
public class Can33d implements CanParent {
    public String handlermsg = "";

    @Override
    public void handlerCan(List<String> msg) {

        String senddata = String.join("", msg);


        if (!handlermsg.equals(senddata)) {
            handlermsg = senddata;


            String flg = msg.get(2);
            BinaryEntity binaryEntity = new BinaryEntity(flg);
            if (binaryEntity != null) {
                //发动机故障
                if (binaryEntity.getB3().equals(BinaryEntity.Value.NUM_1.getValue())) {
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_ENGINE_WARN);
                    messageEvent.data = true;
                    EventBus.getDefault().post(messageEvent);
                    SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_ENGINE_WARN, true);
                } else {
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_ENGINE_WARN);
                    messageEvent.data = false;
                    EventBus.getDefault().post(messageEvent);
                    SPUtils.putBoolean(App.getGlobalContext(), SPUtils.SP_ENGINE_WARN, false);
                }

                //气囊故障
//                if(binaryEntity.getB7().equals(BinaryEntity.Value.NUM_1.getValue())){
//                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_AIRBAG_FAILURE);
//                    messageEvent.data = true;
//                    EventBus.getDefault().post(messageEvent);
//                    SPUtils.putBoolean(App.getGlobalContext(),SPUtils.SP_AIRBAG_FAILURE,true);
//                }else if(binaryEntity.getB7().equals(BinaryEntity.Value.NUM_0.getValue())){
//                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_AIRBAG_FAILURE);
//                    messageEvent.data = false;
//                    EventBus.getDefault().post(messageEvent);
//                    SPUtils.putBoolean(App.getGlobalContext(),SPUtils.SP_AIRBAG_FAILURE,false);
//                }
//            //可能是气囊故障？
//            if(binaryEntity.getB4().equals(BinaryEntity.Value.NUM_1.getValue())){
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_AIRBAG_FAILURE);
//                messageEvent.data = true;
//                EventBus.getDefault().post(messageEvent);
//            }else if(binaryEntity.getB4().equals(BinaryEntity.Value.NUM_0.getValue())){
//                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_AIRBAG_FAILURE);
//                messageEvent.data = false;
//                EventBus.getDefault().post(messageEvent);
//            }
            }

            LogUtils.printI(Can33d.class.getSimpleName(), "handlerCan---msg=" + msg + ", flg=" + flg + ", binaryEntity=" + binaryEntity);

            switch (flg) {
                case "01":
                    AlertVo alertVo = new AlertVo();
                    alertVo.setAlertImg(R.drawable.ic_fuel_filter);
                    alertVo.setAlertColor(AlertMessage.TEXT_WHITE + "");
                    alertVo.setAlertMessage(AlertMessage.ALERT_33D_01);
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                    messageEvent.data = alertVo;
                    EventBus.getDefault().post(messageEvent);
                    break;
                case "00":
//                    AlertVo alertVo0 = new AlertVo() ;
//                    alertVo0.setAlertImg(0);
//                    alertVo0.setAlertColor(AlertMessage.TEXT_WHITE+"");
//                    alertVo0.setAlertMessage(AlertMessage.ALERT_33D_00);
//                    String message0 = FastJsonUtils.BeanToJson(alertVo0);
//                EventBusMeter.getInstance().postSticky(new MessageWrap("alert",message0 ));
//                    CommonUtil.meterHandler.get("alert").handlerData(message0);

                    messageEvent = new MessageEvent(MessageEvent.Type.SHOW_ENGINE_WARN);
                    messageEvent.data = false;
                    EventBus.getDefault().post(messageEvent);
                    break;

                case "40": //更换空气滤清器
                    AlertVo alertVo2 = new AlertVo();
                    alertVo2.setAlertImg(R.drawable.ic_air_filter);
                    alertVo2.setAlertColor(AlertMessage.TEXT_WHITE + "");
                    alertVo2.setAlertMessage(AlertMessage.ALERT_33D_40);
                    messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                    messageEvent.data = alertVo2;
                    EventBus.getDefault().post(messageEvent);
                    break;
                case "80": //水温高标志
                    AlertVo alertVo3 = new AlertVo();
                    alertVo3.setAlertImg(R.drawable.ic_water_temp_high);
                    alertVo3.setAlertColor(AlertMessage.TEXT_WHITE + "");
                    alertVo3.setAlertMessage(AlertMessage.ALERT_33D_80);
                    messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                    messageEvent.data = alertVo3;
                    EventBus.getDefault().post(messageEvent);
                    break;

                case "04": //油箱盖开启
                    AlertVo alertVo4 = new AlertVo();
                    alertVo4.setAlertImg(R.drawable.ic_fuel_tank_cap);
                    alertVo4.setAlertColor(AlertMessage.TEXT_WHITE + "");
                    alertVo4.setAlertMessage(AlertMessage.ALERT_33D_04);
                    messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                    messageEvent.data = alertVo4;
                    EventBus.getDefault().post(messageEvent);
                    break;
                default:
                    break;
            }
        }
    }
}
