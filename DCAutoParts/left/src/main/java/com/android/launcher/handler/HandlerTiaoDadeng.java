package com.android.launcher.handler;

import com.android.launcher.MessageEvent;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;


/**
 * 调大灯
 */
public class HandlerTiaoDadeng implements HandlerInteface {

    public static volatile String lastData  ;


    @Override
    public void handlerData( final String msg) {
        LogUtils.printI(HandlerTiaoDadeng.class.getSimpleName(), "msg="+msg);
        if (!msg.equals(lastData)){
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_HIGH_BEAM);
            if (msg.equals("01")){
                messageEvent.data = true;
                EventBus.getDefault().post(messageEvent);
            }
            if (msg.equals("00")){
                messageEvent.data = false;
                EventBus.getDefault().post(messageEvent);
            }
            lastData = msg ;
        }
    }
}
