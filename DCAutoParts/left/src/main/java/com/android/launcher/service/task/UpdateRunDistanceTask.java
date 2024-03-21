package com.android.launcher.service.task;

import android.content.Context;

import dc.library.utils.event.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 更新可行驶距离任务
 * @date： 2024/1/3
 * @author: 78495
*/
public class UpdateRunDistanceTask extends TaskBase{


    public UpdateRunDistanceTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.UPDATE_RUNNING_DISTANCE));
    }
}
