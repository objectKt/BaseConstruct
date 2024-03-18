package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.util.AppUtils;
import com.android.launcher.util.LogUtils;

import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

/**
 * 重置启动后数据任务
 * @date： 2024/1/11
 * @author: 78495
*/
public class ResetLaunchAfterDataTask extends TaskBase{

    public ResetLaunchAfterDataTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            CarTravelTable carTravelTable = CarTravelRepository.getInstance().getData(context, AppUtils.getDeviceId(context));
            if(carTravelTable!=null){
                carTravelTable.setCurrentQtrip(0.0f);
                CarTravelRepository.getInstance().updateData(context,carTravelTable);
            }
            LogUtils.printI(TAG, "run---");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
