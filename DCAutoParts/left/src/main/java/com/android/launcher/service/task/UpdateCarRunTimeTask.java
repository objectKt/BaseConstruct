package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.can.Can105;
import com.android.launcher.can.Can203;
import com.android.launcher.service.LivingService;
import com.android.launcher.util.AppUtils;
import com.android.launcher.util.BigDecimalUtils;
import com.android.launcher.util.LogUtils;

import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

/**
 * 汽车启动后的行驶时间
 * @date： 2024/1/3
 * @author: 78495
 */
public class UpdateCarRunTimeTask extends TaskBase {

    public UpdateCarRunTimeTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            if (Can105.engineRunning) {
                LivingService.launchCarRunTime += 1000;
                CarTravelTable travelTable = CarTravelRepository.getInstance().getData(context, AppUtils.getDeviceId(context));
                if (travelTable != null) {
                    long totalRunTime = 1000 + travelTable.getTotalRunTime();
                    long userRunTime = 1000 + travelTable.getUserRunTime();
                    long subTotalRuntime = 1000 + travelTable.getSubtotalRuntime();

//                    LogUtils.printI(TAG, "computeCarRunTime---totalRunTime=" + totalRunTime + ", userRunTime=" + userRunTime + ", subTotalRuntime=" + subTotalRuntime);
                    travelTable.setTotalRunTime(totalRunTime);
                    travelTable.setUserRunTime(userRunTime);
                    travelTable.setSubtotalRuntime(subTotalRuntime);


                    if (LivingService.launchAverageSpeed >= 5f) {
                        float userAverageSpeed = travelTable.getUserAverageSpeed();
                        if (userAverageSpeed <= 0.1f) {
                            travelTable.setUserAverageSpeed(LivingService.launchAverageSpeed);
                        } else {
                            userAverageSpeed = BigDecimalUtils.div(String.valueOf(userAverageSpeed + LivingService.launchAverageSpeed), String.valueOf(2), 2);
                            travelTable.setUserAverageSpeed(userAverageSpeed);
                        }

                        float totalAverageSpeed = travelTable.getTotalAverageSpeed();
                        if (totalAverageSpeed <= 0.1f) {
                            travelTable.setTotalAverageSpeed(LivingService.launchAverageSpeed);
                        } else {
                            totalAverageSpeed = BigDecimalUtils.div(String.valueOf(totalAverageSpeed + LivingService.launchAverageSpeed), String.valueOf(2), 2);
                            travelTable.setTotalAverageSpeed(totalAverageSpeed);
                        }
                    }
                    CarTravelRepository.getInstance().updateData(context, travelTable);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
