package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 定时更新发动机转速和车速任务
 * @date： 2024/1/3
 * @author: 78495
*/
public class UpdateCarSpeedUITask extends TaskBase{


    public UpdateCarSpeedUITask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            MessageEvent carSpeedMessage = new MessageEvent(MessageEvent.Type.UPDATE_CAR_SPEED);
            EventBus.getDefault().post(carSpeedMessage);

            MessageEvent engineSpeedMessage = new MessageEvent(MessageEvent.Type.UPDATE_ENGINE_SPEED);
            EventBus.getDefault().post(engineSpeedMessage);
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
