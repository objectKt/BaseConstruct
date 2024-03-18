package com.android.launcher.service.task;

import android.content.Context;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 开始仪表动画
 * @date： 2024/1/3
 * @author: 78495
*/
public class StartMeterAnimationTask extends TaskBase{

    public StartMeterAnimationTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        LogUtils.printI(TAG, "START_METER_ANIMATION 发送开始仪表动画事件");
        EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.START_METER_ANIMATION));
    }
}
