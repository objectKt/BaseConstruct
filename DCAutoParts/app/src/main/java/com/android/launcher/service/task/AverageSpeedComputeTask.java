package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.App;
import com.android.launcher.can.Can203;
import com.android.launcher.dao.ParamDaoUtil;
import com.android.launcher.entity.Param;
import com.android.launcher.service.LivingService;
import com.android.launcher.util.ACache;
import com.android.launcher.util.FuncUtil;

/**
 * 平均时速计算
 * @date： 2024/1/9
 * @author: 78495
*/
public class AverageSpeedComputeTask extends TaskBase{

    private ACache aCache;
    private ParamDaoUtil paramDaoUtil;
    private int speedSetLimit = 0;

    public AverageSpeedComputeTask(Context context) {
        super(context);
        aCache = ACache.get(context);
        paramDaoUtil = new ParamDaoUtil(App.getGlobalContext());
    }

    @Override
    public void run() {
        try {
            if (speedSetLimit == 0) {
                Param param = paramDaoUtil.queryParamName("CARSETSPEED");
                if (param != null) {
                    String speedLevel = param.getParamValue();
                    speedLevel = speedLevel.replace("km/h", "");
                    speedSetLimit = Integer.parseInt(speedLevel);
                }
            }

            if (Can203.speedInt > 0) {
                if (LivingService.launchAverageSpeed <= 0) {
                    LivingService.launchAverageSpeed = Can203.speedInt;
                } else {
                    LivingService.launchAverageSpeed = (LivingService.launchAverageSpeed + Can203.speedInt) / 2;
                }
            }
            if (Can203.speedInt > speedSetLimit && !App.startAss) {
                App.startAss = true;
                aCache.put("MidAttention", FuncUtil.getCurrentDate());
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
