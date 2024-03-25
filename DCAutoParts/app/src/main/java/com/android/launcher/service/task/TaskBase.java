package com.android.launcher.service.task;

import android.content.Context;

/**
 * @date： 2024/1/3
 * @author: 78495
*/
public abstract class TaskBase implements Runnable{

    protected Context context;

    protected  String TAG;

    public TaskBase(Context context) {
        this.context = context;
        TAG = getClass().getSimpleName();
    }
}
