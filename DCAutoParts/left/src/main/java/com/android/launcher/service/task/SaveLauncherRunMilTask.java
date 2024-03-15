package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.service.LivingService;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

/**
 * 保存启动后行驶的距离
 * @date： 2024/1/3
 * @author: 78495
*/
public class SaveLauncherRunMilTask extends TaskBase{

    public SaveLauncherRunMilTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            //大于1公里才保存
            if (LivingService.launchCarRunMile >= 1.0f) {
                LogUtils.printI(TAG, "startSaveLauncherRunMilTask---launchCarRunMile=" + LivingService.launchCarRunMile);
                SPUtils.putFloat(context, SPUtils.SP_CAR_LAUNCHER_RUN_MIL, LivingService.launchCarRunMile);
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
    }
}
