package com.android.launcher.handler;

import android.util.Log;
import android.view.View;

import com.android.launcher.MessageEvent;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.meter.MeterFragmentBase;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.FastJsonUtils;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

/**
 * 告警处理
 */
@Deprecated
public class HandlerAlert implements HandlerInteface {

    String message = "";

    public void handler1() {
//           MeterActivity.localMeterActivity.handler.postAtFrontOfQueue(new Runnable() {
//               @Override
//               public void run() {
//                   MeterActivity.enginefailure.setVisibility(View.GONE);

//
//               }
//           });

//        MeterFragmentBase.localMeterFragment.handler.postAtFrontOfQueue(() -> MeterFragmentBase.enginefailure.setVisibility(View.GONE));

    }

    public void handler2() {
//           MeterActivity.localMeterActivity.handler.postAtFrontOfQueue(new Runnable() {
//               @Override
//               public void run() {
////                   MeterActivity.enginefailure.setVisibility(View.GONE);
//               }
//           });

//        MeterFragmentBase.localMeterFragment.handler.postAtFrontOfQueue(() -> MeterFragmentBase.enginefailure.setVisibility(View.GONE));
    }

    @Override
    public void handlerData(final String msg) {
        String ale = msg;
        Log.i("alert", "================================++" + msg);

//        if (!message.equals(msg)) {
//            message = msg;
//            //todo pankong
//            final AlertVo alertVo = FastJsonUtils.fromBean(msg, AlertVo.class);
//            if (FuncUtil.showDoor) {
//                if (alertVo != null) {
//                    String alertMessage = alertVo.getAlertMessage();
//
//                    if (alertMessage != null) {
//
//                    }
//                    if (alertMessage.equals("08")) {
////                        Message message = Message.obtain() ;
////                        message.what =1 ;
////                        handler.sendMessage(message) ;
//                        handler1();
//                    } else if (alertMessage.equals("00")) {
////                        Message message = Message.obtain() ;
////                        message.what =2 ;
////                        handler.sendMessage(message) ;
//                        handler2();
//
//                    } else {
//
//                        Log.i("alert", alertVo.toString() + "------------------------");
//
//                        if (alertVo != null) {
//                            if (alertVo.getAlertMessage().equals(AlertMessage.ALERT_279_1_10) ||
//                                    alertVo.getAlertMessage().equals(AlertMessage.ALERT_279_1_01) ||
//                                    alertVo.getAlertMessage().equals(AlertMessage.ALERT_279_1_04) ||
//                                    alertVo.getAlertMessage().equals(AlertMessage.ALERT_279_1_02)) {
//
//
////                                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
////                                messageEvent.data = alertVo;
////                                EventBus.getDefault().post(messageEvent);
//
//                            }
//                        }
//                    }
//
//                    MeterActivity.messageNumber = MeterActivity.messageNumber + 1;
//                    MeterActivity.infoList.add(alertVo);
//
//                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
//                    messageEvent.data = alertVo;
//                    EventBus.getDefault().post(messageEvent);
//                }
//            }
//        }
    }
}