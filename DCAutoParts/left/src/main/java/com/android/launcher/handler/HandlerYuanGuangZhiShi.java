package com.android.launcher.handler;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.android.launcher.MessageEvent;
import com.android.launcher.meter.MeterFragmentBase;
import com.android.launcher.util.FuncUtil;

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
