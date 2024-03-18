package com.android.launcher.service.task;

import android.content.Context;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.service.LivingService;
import com.android.launcher.util.AppUtils;
import com.android.launcher.util.BigDecimalUtils;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.QtripUtils;
import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 获取剩余油量
 *
 * @date： 2024/1/4
 * @author: 78495
 */
public class GetOilPercentTask extends TaskBase {

    public static volatile List<String> oilList = new ArrayList<>();

    public static volatile int lastMmoil = 0;
//    public static volatile float lastCarRunMile = 0.0f;

    //启动时的油量
    public static volatile float launchOilPercent;

    public GetOilPercentTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
            LogUtils.printI(TAG, "oilList--- size=" + oilList.size());
            List<String> copyList = new ArrayList<>();
            copyList.addAll(oilList);
            oilList.clear();
            int mmoil = analysis(copyList);

            float percent = FuncUtil.getOilPrecent(mmoil);
            LogUtils.printI(TAG, "percent=" + percent + ", currentPercentOil=" + LivingService.currentPercentOil + ", mmoil=" + mmoil + ", lastMmoil=" + lastMmoil);
            lastMmoil = mmoil;


//            float runMile = LivingService.launchCarRunMile - lastCarRunMile;

            float consumeOil = BigDecimalUtils.sub(String.valueOf(launchOilPercent), String.valueOf(percent));
            float runningQtrip = computeRunningQtrip(consumeOil, LivingService.launchCarRunMile);


            LogUtils.printI(TAG, "consumeOil=" + consumeOil + ", runningQtrip=" + runningQtrip + ", launchCarRunMile=" + LivingService.launchCarRunMile);

            //油耗校准值
            if (runningQtrip > 40f || runningQtrip < 6.0f) {
                return;
            }

//            lastCarRunMile = LivingService.launchCarRunMile;

            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
            messageEvent.data = percent;
            EventBus.getDefault().post(messageEvent);

            App.oilPercent = percent + "";

            MessageEvent oilBoxEvent = new MessageEvent(MessageEvent.Type.UPDATE_OIL_BOX);
            oilBoxEvent.data = percent;
            EventBus.getDefault().post(oilBoxEvent);

            CarTravelTable carTravelTable = CarTravelRepository.getInstance().getData(context, AppUtils.getDeviceId(context));
            if (carTravelTable != null) {
                if (runningQtrip > 0f) {
                    carTravelTable.setCurrentQtrip(runningQtrip);
//                    float userAverageQtrip = carTravelTable.getUserAverageQtrip();
//                    if (userAverageQtrip > 0.0f) {
//                        userAverageQtrip = BigDecimalUtils.div(String.valueOf(userAverageQtrip + runningQtrip), "2", 1);
//                        carTravelTable.setUserAverageQtrip(userAverageQtrip);
//                    } else {
//                        carTravelTable.setUserAverageQtrip(runningQtrip);
//                    }
//
//                    float totalAverageQtrip = carTravelTable.getTotalAverageQtrip();
//                    if (totalAverageQtrip > 0.0f) {
//                        totalAverageQtrip = BigDecimalUtils.div(String.valueOf(totalAverageQtrip + runningQtrip), "2", 1);
//                        carTravelTable.setTotalAverageQtrip(totalAverageQtrip);
//                    } else {
//                        carTravelTable.setTotalAverageQtrip(runningQtrip);
//                    }
                    CarTravelRepository.getInstance().updateData(context, carTravelTable);
                    LogUtils.printI(TAG, "carTravelTable=" + carTravelTable);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //分析油耗数据
    public int analysis(List<String> oilList) {

        List<Integer> numOil = new ArrayList<Integer>();
        for (int i = 0; i < oilList.size(); i++) {
            String oil = oilList.get(i);
            String pre = oil.substring(0, 2);

            String suff = oil.substring(2, 4);

            int munis = Integer.parseInt(pre, 16) + Integer.parseInt(suff, 16);

            if (munis >= lastMmoil) {
                numOil.add(munis);
            }
        }
        if (numOil.isEmpty()) {
            return lastMmoil;
        }
        Collections.sort(numOil);

//        LogUtils.printI(TAG,"previous---numOil="+numOil);

        //去掉波动比较大的数， 相邻的数差值大于1就去掉
        int preNumber = numOil.get(0);
        for (int i = 1; i < numOil.size(); i++) {
            if (numOil.size() == 1) {
                break;
            }
            int number = numOil.get(i);
            int difference = number - preNumber;
//            LogUtils.printI(TAG,"preNumber="+preNumber +", number="+number+", difference="+difference +", index="+i);
            if (difference > 1) {
                numOil.remove(i);
                --i;
            } else {
                //移到下一位
                preNumber = number;
            }
        }

        //去掉和上一次相同的油耗
//        for (int i = 1; i < numOil.size(); i++) {
//            if (numOil.size() == 1) {
//                break;
//            }
//            int number = numOil.get(i);
//            if (number == lastMmoil || number == (lastMmoil + 1)) {
//                numOil.remove(i);
//                --i;
//            }
//        }

        LogUtils.printI(TAG, "result---lastMmoil=" + lastMmoil);
        for (int i = 0; i < numOil.size(); i++) {
            int mmoil = numOil.get(i);
            LogUtils.printI(TAG, "result---mmoil=" + mmoil);
        }

//        LogUtils.printI(TAG,"result---numOil="+numOil);

        int sumOil = 0;
        for (int i = 0; i < numOil.size(); i++) {
            sumOil += numOil.get(i);
        }

//        return numOil.get(numOil.size() - 1);
        return sumOil / numOil.size();
    }


    public static void clear() {
        oilList.clear();
        lastMmoil = 0;
//        lastCarRunMile = 0.0f;
    }


    /**
     * @description: 油耗计算= 消耗的汽油量（L） / 行驶的距离（km）
     * @createDate: 2023/9/2
     */
    public  float computeRunningQtrip(float consumeOilPercent, float runMile) {
        try {
            float consumeOil = BigDecimalUtils.mul(String.valueOf(QtripUtils.MAILBOX), String.valueOf(consumeOilPercent));

            if(runMile <=0.0f){
                runMile = 1.0f;
            }
            //计算油耗
            float qtrip = BigDecimalUtils.div(String.valueOf(consumeOil), String.valueOf(runMile), 3);
            LogUtils.printI(TAG, "computeRunningQtrip---consumeOil=" + consumeOil + ", runMile=" + runMile + ", qtrip=" + qtrip);
            qtrip = BigDecimalUtils.mul(String.valueOf(qtrip),"100");
            LogUtils.printI(TAG, "computeRunningQtrip---qtrip=" + qtrip);

            return qtrip;
        } catch (Exception e) {
            e.printStackTrace();
            LogUtils.printE(TAG, "computeRunningQtrip---Exception=" + e.getMessage());
        }
        return 0.0f;
    }

}
