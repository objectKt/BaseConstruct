package com.android.launcher.can;

import dc.library.auto.event.MessageEvent;
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
//            if (ss.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_2_40);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //  EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (ss.equals(flg_2)){
//
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_2_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (ss.equals(flg_1)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_2_10);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//
//            String sl = s.substring(1,2) ;
//            if (sl.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_2_08);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //    EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (sl.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_2_04);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //    EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (sl.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_2_02);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //    EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//
//            String fouth =msg.get(4) ;
//            if (fouth.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_4_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //    EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//
//            String five = msg.get(5) ;
//
//
//            String five_f = five.substring(0,1) ;
//
//
//            if (five_f.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_5_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //    EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (five_f.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_5_40);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //    EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (five_f.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_5_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //    EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (five_f.equals(flg_1)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_5_10);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            String five_s = five.substring(1,2) ;
//
//            if (five_s.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_5_08);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (five_s.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_5_04);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //  EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (five_s.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_5_02);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//
//
//            String six = msg.get(6) ;
//            six= six.substring(0,1);
//
//            if (six.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                 alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FA_6_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //   EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
        }
    }
}
