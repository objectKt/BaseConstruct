package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.can.Can203;
import com.android.launcher.dao.ParamDaoUtil;
import com.android.launcher.entity.Param;
import com.android.launcher.service.LivingService;
import com.android.launcher.util.ACache;
import com.android.launcher.util.CacheManager;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.OilAntiShakeUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;

/**
 * 汽车里程计算
 * @date： 2024/1/3
 * @author: 78495
*/
public class CarMileComputeTask extends TaskBase{



    public CarMileComputeTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            List<String> milesCopy = new ArrayList<>();
            milesCopy.addAll(Can203.miles);
            Can203.miles.clear();
            FuncUtil.getResultMap(milesCopy);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
