package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.util.LogcatHelper;

/**
 * 清除多余日志任务
 * @date： 2024/1/3
 * @author: 78495
*/
public class ClearUnnecessaryLogTask extends TaskBase{

    public ClearUnnecessaryLogTask(Context context) {
        super(context);
    }

    @Override
    public void run() {

        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        try {
            LogcatHelper.getInstance(context).clearUnnecessaryLog();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
