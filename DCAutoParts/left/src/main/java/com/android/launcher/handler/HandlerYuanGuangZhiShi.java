package com.android.launcher.handler;

import dc.library.auto.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;


/**
 *
 * 远光指示
 *
 */
public class HandlerYuanGuangZhiShi implements HandlerInteface {


    @Override
    public void handlerData(final String msg) {
        if (msg.equals("01")){
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_HIGH_BEAM);
            messageEvent.data = true;
            EventBus.getDefault().post(messageEvent);
        } else if (msg.equals("00")){
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_HIGH_BEAM);
            messageEvent.data = false;
            EventBus.getDefault().post(messageEvent);
        }
    }
}
