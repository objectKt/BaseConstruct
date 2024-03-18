package com.android.launcher.util;

import android.content.Context;

import com.android.launcher.App;
import com.android.launcher.service.LivingService;
import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Map;

/**
 * @description: 油耗工具
 * @createDate: 2023/8/11
 */
public class QtripUtils {

    public static final float DEFAULT_OIL = 12.0f;
    public static final float NOT_VALUE = -1000.0f;
    private static final String TAG = QtripUtils.class.getSimpleName();

    //邮箱 80升
    public static final int MAILBOX = 80;

    //油耗是否计算完成
    public static volatile boolean qtripComputeFinish = true;

    public static volatile float lastRunComputeQtrip = DEFAULT_OIL;

    //真实的油耗百分比
    public static volatile float realOilPercent = 0.0f;
    //上一次真实的油耗百分比
    public static volatile float lastRealOilPercent = NOT_VALUE;

    //油耗计算值百分比
    public static final float qtripComputeValue = 0.001f;


    /**
     * @description: 计算总油耗  百公里油耗（L/100km）= 消耗的汽油量（L） / 行驶的距离（km） * 100
     * @createDate: 2023/8/11
     */
    public static float computeTotalQtrip(Context context) {
        try {
            CarTravelTable carTravelTable = CarTravelRepository.getInstance().getData(context, AppUtils.getDeviceId(context));
            if (carTravelTable != null) {
                float consumeOil = carTravelTable.getTotalConsumeOil();
                float mile = carTravelTable.getUserMile();
//                LogUtils.printI(TAG, "computeTotalQtrip---consumeOil=" + consumeOil + ", mile=" + mile);
                if (mile > 100f) {
                    float mul = BigDecimalUtils.mul(String.valueOf(mile), String.valueOf(100f));
                    float qtrip = BigDecimalUtils.div(String.valueOf(consumeOil), String.valueOf(mul), 2);
                    LogUtils.printI(TAG, "computeTotalQtrip---qtrip=" + qtrip);
                    if (qtrip < 6f) {
                        return DEFAULT_OIL;
                    } else {
                        return qtrip;
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return DEFAULT_OIL;
    }


    /**
     * @description: 计算可行驶距离
     * @createDate: 2023/8/25
     */
    public static synchronized float computeDistanceToEmpty(Context context, float currentPercentOil, float qtrip) {
        try {
            float oil = BigDecimalUtils.mul(String.valueOf(MAILBOX), String.valueOf(currentPercentOil));
            if (oil < 0.0f) {
                oil = 0.0f;
            }
            float distance = BigDecimalUtils.div(String.valueOf(oil), String.valueOf(qtrip), 2) * 100;

            LogUtils.printI(TAG, "computeDistanceToEmpty---distance=" + distance);
            return distance;


        } catch (Exception e) {
            e.printStackTrace();
        }
        return 0.0f;
    }


    /**
     * @description: 油耗计算= 消耗的汽油量（L） / 行驶的距离（km）
     * @createDate: 2023/9/2
     */
    public static synchronized float computeRunningQtrip(float consumeOilPercent, float runMile) {
        try {
            float consumeOil = BigDecimalUtils.mul(String.valueOf(MAILBOX), String.valueOf(consumeOilPercent));

            if(runMile <=0.0f){
                runMile = 1.0f;
            }
            //计算油耗
            float qtrip = BigDecimalUtils.div(String.valueOf(consumeOil), String.valueOf(runMile), 3);
            LogUtils.printI(TAG, "computeRunningQtrip---consumeOil=" + consumeOil + ", runMile=" + runMile + ", qtrip=" + qtrip);
            qtrip = BigDecimalUtils.mul(String.valueOf(qtrip),"100");
            LogUtils.printI(TAG, "computeRunningQtrip---qtrip=" + qtrip);


            //油耗校准值
            if (qtrip > 40f) {
                return 40.0f;
            }else if(qtrip < 5f){
                return DEFAULT_OIL;
            }
            return qtrip;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.printE(TAG, "computeRunningQtrip---Exception=" + e.getMessage());
        }
        return DEFAULT_OIL;
    }


    private static void computeAverageSpeed(CarTravelTable travelTable) {
        float totalMile = travelTable.getTotalMile();
        long totalRunTime = travelTable.getTotalRunTime();
        if (totalRunTime > 1000) {
            float hour = DateUtils.computeHourNumber(totalRunTime);
            if (hour > 0.0f) {
                float newTotalAverageSpeed = BigDecimalUtils.div(String.valueOf(totalMile), String.valueOf(hour), 2);
                LogUtils.printI(TAG, "computeAverageSpeed---newTotalAverageSpeed=" + newTotalAverageSpeed + ", hour=" + hour + ", totalRunTime=" + totalRunTime + ", totalMile=" + totalMile);
                if (newTotalAverageSpeed > 0.0f) {
                    travelTable.setTotalAverageSpeed(newTotalAverageSpeed);
                }
            }
        }

        float userMile = travelTable.getUserMile();
        long userRunTime = travelTable.getUserRunTime();
        if (userRunTime > 1000) {
            float hour = DateUtils.computeHourNumber(userRunTime);
            if (hour > 0.0f) {
                float newUserAverageSpeed = BigDecimalUtils.div(String.valueOf(userMile), String.valueOf(hour), 2);
                LogUtils.printI(TAG, "computeAverageSpeed---newUserAverageSpeed=" + newUserAverageSpeed + ", hour=" + hour + ", userRunTime=" + userRunTime + ", userMile=" + userMile);
                if (newUserAverageSpeed > 0.0f) {
                    travelTable.setUserAverageSpeed(newUserAverageSpeed);
                }
            }
        }

        float subtotalMile = travelTable.getSubtotalMile();
        long subtotalRuntime = travelTable.getSubtotalRuntime();
        if (subtotalRuntime > 1000) {
            float hour = DateUtils.computeHourNumber(subtotalRuntime);
            if (hour > 0.0f) {
                float newSubtotalAverageSpeed = BigDecimalUtils.div(String.valueOf(subtotalMile), String.valueOf(hour), 2);
                LogUtils.printI(TAG, "computeAverageSpeed---newSubtotalAverageSpeed=" + newSubtotalAverageSpeed + ", hour=" + hour + ", subtotalRuntime=" + subtotalRuntime + ", subtotalMile=" + subtotalMile);
                if (newSubtotalAverageSpeed > 0.0f) {
                    travelTable.setSubtotalAverageSpeed(newSubtotalAverageSpeed);
                }
            }
        }
    }


    /**
     * @description: 计算里程
     * @createDate: 2023/9/3
     */
    public static void computeMile(Map<String, String> map, BigDecimal realMile) {
        try {
            CarTravelTable travelTable = CarTravelRepository.getInstance().getData(App.getGlobalContext(), AppUtils.getDeviceId(App.getGlobalContext()));
            if (travelTable != null) {
                if (travelTable.getTotalMile() > 0f) {
                    if (QtripUtils.qtripComputeFinish) {

                        float totalMile = travelTable.getTotalMile();
                        float userMile = travelTable.getUserMile();
                        LogUtils.printI(FuncUtil.class.getSimpleName(), "computeMile---realMile=" + realMile + ", totalMile=" + totalMile + ", userMile=" + userMile);


                        float newTotalMile = BigDecimalUtils.add(String.valueOf(totalMile), String.valueOf(realMile.floatValue()));
                        float newUserMile = BigDecimalUtils.add(String.valueOf(userMile), String.valueOf(realMile.floatValue()));
                        LivingService.launchAfterMile = BigDecimalUtils.add(String.valueOf(LivingService.launchAfterMile), String.valueOf(realMile.floatValue()));
                        travelTable.setTotalMile(newTotalMile);
                        travelTable.setUserMile(newUserMile);

                        //计算时间

                        CarTravelRepository.getInstance().updateData(App.getGlobalContext(), travelTable);
                    }
                } else {
                    travelTable.setTotalMile(realMile.floatValue());
                    travelTable.setUserMile(realMile.floatValue());
                    CarTravelRepository.getInstance().updateData(App.getGlobalContext(), travelTable);
                    LivingService.launchAfterMile = realMile.floatValue();
                }
                map.put("userMile", travelTable.getUserMile() + "");
                map.put("totalMile", travelTable.getTotalMile() + "");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
