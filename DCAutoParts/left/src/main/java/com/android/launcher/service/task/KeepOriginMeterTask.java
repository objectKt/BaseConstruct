package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.cansender.Can045Sender;
import com.android.launcher.util.LogUtils;

/**
 * 点击方向盘方向键时, 保持原车仪表不动
 * @date： 2024/1/3
 * @author: 78495
*/
public class KeepOriginMeterTask extends TaskBase{

    private Can045Sender can045Sender = new Can045Sender();

    public KeepOriginMeterTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            LogUtils.printI(TAG, "keepOriginMeterView----");
            can045Sender.reset();
            Thread.sleep(100);
            for (int j = 0; j < 15; j++) {
                Thread.sleep(5);
                can045Sender.back();
            }
            Thread.sleep(100);
            can045Sender.reset();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
