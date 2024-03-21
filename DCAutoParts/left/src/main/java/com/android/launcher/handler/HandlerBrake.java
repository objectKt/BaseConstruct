package com.android.launcher.handler;


import dc.library.utils.event.MessageEvent;

import com.android.launcher.meter.view.BottomStatusView;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 电子手刹
 */
public class HandlerBrake implements HandlerInteface {

    public Timer breakTimer;

    private MessageEvent messageEventWaring = new MessageEvent(MessageEvent.Type.SHOW_ELECTRICAL_PARK_BRAKE_WARNING);
    private MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_ELECTRICAL_PARK_BRAKE);


    public void  handlerbreak(final int w){
        switch (w){
            case 1:

                messageEventWaring.data = true;
                EventBus.getDefault().post(messageEventWaring);
                break;
            case 2:
                messageEventWaring.data = false;
                EventBus.getDefault().post(messageEventWaring);
                break;
            case 3:
                messageEvent.data = true;
                EventBus.getDefault().post(messageEvent);
                break;
            case 4:
                messageEvent.data = false;
                EventBus.getDefault().post(messageEvent);
                break;

            case 5:
                messageEvent.data = false;
                EventBus.getDefault().post(messageEvent);

                messageEventWaring.data = false;
                EventBus.getDefault().post(messageEventWaring);
                break;
            case 6:
                messageEventWaring.data = true;
                EventBus.getDefault().post(messageEventWaring);

                messageEvent.data = true;
                EventBus.getDefault().post(messageEvent);
                break;
            default:
                break;
        }

    }

    @Override
    public void handlerData( String msg) {
        final String brakeflg = msg;
        if (brakeflg.equals("8") || brakeflg.equals("2")) {
            if (breakTimer == null) {
                breakTimer = new Timer();
                breakTimer.schedule(new TimerTask() {// 不间断的更新播放进度
                    @Override
                    public void run() {

                        if (brakeflg.equals("2")) {
                            if(BottomStatusView.electricalParkBrakeWaringShow){
                                handlerbreak(2) ;
                            }else{
                                handlerbreak(1) ;
                            }
                        }
                        if (brakeflg.equals("8")) {
                            if (!BottomStatusView.electricalParkBrakeShow) {
                                handlerbreak(3) ;
                            } else {
                                handlerbreak(4) ;
                            }
                        }

                    }
                }, 0, 400);
            }
        } else {
            if (breakTimer != null) {
                breakTimer.cancel();
                breakTimer = null;
            }
            if (brakeflg.equals("0")) {
//                Message message = Message.obtain() ;
//                message.what = 5 ;
//                handler.sendMessage(message) ;
                handlerbreak(5) ;
            } else  if (brakeflg.equals("4")) {
//                Message message = Message.obtain() ;
//                message.what = 3 ;
//          i      handler.sendMessage(message) ;
                handlerbreak(3) ;
            }else   if (brakeflg.equals("1")) {
//                Message message = Message.obtain() ;
//                message.what = 1;
//                handler.sendMessage(message) ;
                handlerbreak(1) ;
            } else  if (brakeflg.equals("7")) {
//                Message message = Message.obtain() ;
//                message.what = 6;
//                handler.sendMessage(message) ;
                handlerbreak(6) ;
            }
        }
    }
}
