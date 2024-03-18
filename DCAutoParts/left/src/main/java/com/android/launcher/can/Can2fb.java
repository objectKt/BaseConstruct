package com.android.launcher.can;

import android.os.Build;

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

//            if (f_f.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_1_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (f_f.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_1_40);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (f_f.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_1_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (f_f.equals(flg_1)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_1_10);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            String f_s = msg.get(2).substring(1,2) ;
//
//            if (f_s.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_1_08);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (f_s.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_1_04);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (f_s.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_1_02);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//
//            String t_f = msg.get(3).substring(1,2) ;
//
//            if (t_f.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_2_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (t_f.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_2_40);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (t_f.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_2_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (t_f.equals(flg_1)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_1_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", AlertMessage.ALERT_2FB_2_10));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//
//            String t_s = msg.get(3).substring(1,2) ;
//
//            if (t_s.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_1_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", AlertMessage.ALERT_2FB_2_08));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (t_s.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_2_04);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//
//            String fourth_f = msg.get(4).substring(0,1) ;
//
//            if (fourth_f.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_3_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (fourth_f.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_3_40);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (fourth_f.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_3_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
////            EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//
//            String fourth_s = msg.get(4).substring(1,2) ;
//
//            if (fourth_s.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_3_08);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //       EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (fourth_s.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_3_04);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //    EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (fourth_s.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_3_02);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                // EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }

//            String five_s = msg.get(5).substring(0,1) ;
//
//            if (five_s.equals(flg_8)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_4_80);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //  EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (five_s.equals(flg_4)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_4_40);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //  EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
//            if (five_s.equals(flg_2)){
//                AlertVo alertVo = new AlertVo() ;
//                alertVo.setAlertImg(R.drawable.icon2flamp);
//                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_2FB_4_20);
//                String message = FastJsonUtils.BeanToJson(alertVo);
//                //  EventBusMeter.getInstance().postSticky(new MessageWrap("alert", message));
//                CommonUtil.meterHandler.get("alert").handlerData(message);
//            }
        }

    }
}
