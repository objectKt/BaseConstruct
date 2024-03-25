package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.App;
import com.android.launcher.service.LivingService;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

/**
 * 获取配置
 * @date： 2024/1/3
 * @author: 78495
*/
public class GetConfigTask extends TaskBase{

    public GetConfigTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            LivingService.unitType = SPUtils.getInt(context, SPUtils.SP_UNIT_TYPE, UnitType.getDefaultType());
            LivingService.operateOriginMeter = SPUtils.getBoolean(context, SPUtils.SP_ORIGIN_METER_ON, false);
            LivingService.enableOpenDayRunLight = SPUtils.getBoolean(context, SPUtils.SP_DAY_RUN_LIGHT_ON, false);
            LogUtils.printI(TAG, "operateOriginMeter="+LivingService.operateOriginMeter +", enableOpenDayRunLight="+LivingService.enableOpenDayRunLight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
