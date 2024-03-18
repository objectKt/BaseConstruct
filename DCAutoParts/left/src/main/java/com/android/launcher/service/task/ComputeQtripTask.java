package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.service.LivingService;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.AppUtils;
import com.android.launcher.util.BigDecimalUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.QtripUtils;
import com.android.launcher.util.SPUtils;
import com.android.launcher.util.UnitUtils;
import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

/**
 * 计算油耗
 * @date： 2024/1/3
 * @author: 78495
*/
public class ComputeQtripTask extends TaskBase{

    private float launcherOil;

    public ComputeQtripTask(Context context, float launcherOil) {
        super(context);
        this.launcherOil = launcherOil;
    }

    @Override
    public void run() {
        try {
            float lastLauncherOil = SPUtils.getFloat(context, SPUtils.LAST_LAUNCHER_OIL, 0.0f);
            LogUtils.printI(TAG, "computeQtrip---launcherOil=" + launcherOil + ", lastLauncherOil=" + lastLauncherOil);
            if (launcherOil <= 0.0f) {
                SPUtils.putFloat(context, SPUtils.LAST_LAUNCHER_OIL, launcherOil);
                return;
            }

            //判断是否加油了
            float faultTolerant = launcherOil - lastLauncherOil;
            if (faultTolerant > 0.05f) {
                //加油了重新计算
                SPUtils.putFloat(context, SPUtils.LAST_LAUNCHER_OIL, launcherOil);

                LogUtils.printI(TAG, "computeQtrip---faultTolerant=" + faultTolerant + ", 加油了");
                return;
            }

            float consumeOilPercent = BigDecimalUtils.sub(String.valueOf(lastLauncherOil), String.valueOf(launcherOil));

            //消耗的汽油小于0.01不计算油耗
            if (consumeOilPercent < 0.01f) {
                SPUtils.putFloat(context, SPUtils.SP_CAR_LAUNCHER_RUN_MIL, 0.0f);
                SPUtils.putFloat(context, SPUtils.LAST_LAUNCHER_OIL, launcherOil);

                LogUtils.printI(TAG, "computeQtrip---consumeOilPercent=" + consumeOilPercent + ", 消耗的汽油太少，无法计算油耗");
                return;
            }

            //单位米
            float runMile = SPUtils.getFloat(context, SPUtils.SP_CAR_LAUNCHER_RUN_MIL);

//            runMile = BigDecimalUtils.div(String.valueOf(runMile), "1000", 4);

            if (runMile <= 1f) {
                SPUtils.putFloat(context, SPUtils.LAST_LAUNCHER_OIL, launcherOil);
                SPUtils.putFloat(context, SPUtils.SP_CAR_LAUNCHER_RUN_MIL, 0.0f);
                LogUtils.printI(TAG, "computeQtrip---consumeOilPercent=" + consumeOilPercent + ", 行驶的里程太少， 无法计算油耗--runMile=" + runMile);
                return;
            }

            LogUtils.printI(TAG, "computeQtrip---consumeOilPercent=" + consumeOilPercent + ", runMile=" + runMile);


            float qtrip = QtripUtils.computeRunningQtrip(consumeOilPercent, runMile);
            LogUtils.printI(TAG, "computeQtrip----qtrip=" +  qtrip);

            SPUtils.putFloat(context, SPUtils.LAST_LAUNCHER_OIL, launcherOil);
            SPUtils.putFloat(context, SPUtils.SP_CAR_LAUNCHER_RUN_MIL, 0.0f);
            if (LivingService.unitType == UnitType.MI.ordinal()) {
                qtrip = UnitUtils.kmQtripToMiQtrip(QtripUtils.DEFAULT_OIL);
            }
            CarTravelTable travelTable = CarTravelRepository.getInstance().getData(context, AppUtils.getDeviceId(context));
            if (travelTable != null) {
//                travelTable.setCurrentQtrip(LivingService.qtrip);

                float userAverageQtrip = travelTable.getUserAverageQtrip();
                if (userAverageQtrip <= 1.0f) {
                    travelTable.setUserAverageQtrip(qtrip);
                } else {
                    userAverageQtrip = BigDecimalUtils.div(String.valueOf(userAverageQtrip + qtrip), "2", 2);
                    travelTable.setUserAverageQtrip(userAverageQtrip);

                }

                float totalAverageQtrip = travelTable.getTotalAverageQtrip();
                if (totalAverageQtrip <= 1.0f) {
                    travelTable.setTotalAverageQtrip(qtrip);
                } else {
                    totalAverageQtrip = BigDecimalUtils.div(String.valueOf(totalAverageQtrip + qtrip), "2", 2);
                    travelTable.setTotalAverageQtrip(totalAverageQtrip);
                }
            }
            CarTravelRepository.getInstance().updateData(context, travelTable);
            LogUtils.printI(TAG, "computeQtrip----travelTable=" + travelTable);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
