package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * 显示警告消息任务
 * @date： 2024/1/5
 * @author: 78495
*/
public class ShowWarnMessageTask extends TaskBase{

    public ShowWarnMessageTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_WARN_MESSAGE));
    }
}
