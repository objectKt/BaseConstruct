package com.android.launcher.can;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

// 告警
public class Can2f8 implements CanParent {

    private static final String TAG = Can2f8.class.getSimpleName();

   public  String flg_8 = "8" ;
    public  String flg_4 = "4" ;
    public  String flg_2 = "2" ;
    public  String flg_1 = "1" ;

    public String handlermsg = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handlerCan(List<String> msg) {


        String senddata = String.join("", msg);

        if (!handlermsg.equals(senddata)){
            handlermsg = senddata ;

            LogUtils.printI(TAG, "msg="+msg);

            String alert =msg.get(2) ;

            String alert_f = alert.substring(0,1) ;

            AlertVo alertVo = new AlertVo() ;
            alertVo.setAlertImg(0);
             alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");

//            if (alert_f.equals(flg_8)){
//                alertVo.setAlertMessage( AlertMessage.ALERT_2F8_2_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert",message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
            if (alert_f.equals(flg_4)){
                alertVo.setAlertImg(R.drawable.ic_car_key);
                alertVo.setAlertMessage( AlertMessage.ALERT_2F8_2_40);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
//            if (alert_f.equals(flg_2)){
//                alertVo.setAlertMessage( AlertMessage.ALERT_2F8_2_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert",message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }

//            String alert_s = alert.substring(1,2) ;
//
//
//            if (alert_s.equals(flg_8)){
//                alertVo.setAlertMessage( AlertMessage.ALERT_2F8_2_08);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (alert_s.equals(flg_4)){
//                alertVo.setAlertMessage( AlertMessage.ALERT_2F8_2_04);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (alert_s.equals(flg_2)){
//                alertVo.setAlertMessage( AlertMessage.ALERT_2F8_2_02);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert",message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (alert_s.equals(flg_1)){
//                alertVo.setAlertMessage( AlertMessage.ALERT_2F8_2_01);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //    EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }

        }
    }
}
