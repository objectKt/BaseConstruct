package com.android.launcher.service.task;

import android.content.Context;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.can.Can139;

import org.greenrobot.eventbus.EventBus;

/**
 * @date： 2024/1/3
 * @author: 78495
*/
public class CarTempTask extends TaskBase{


    public CarTempTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_CAR_TEMP);
        messageEvent.data = Can139.currentCarTemp;
        EventBus.getDefault().post(messageEvent);
    }
}
