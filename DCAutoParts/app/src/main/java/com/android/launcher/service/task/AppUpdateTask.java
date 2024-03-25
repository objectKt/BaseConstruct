package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.util.APKUtil;

/**
 * App更新任务
 * @date： 2024/1/3
 * @author: 78495
*/
public class AppUpdateTask extends TaskBase{


    public AppUpdateTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        APKUtil.checkUpdate();
    }
}
