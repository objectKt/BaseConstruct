package com.android.launcher.can;

import android.text.TextUtils;

import com.android.launcher.App;
import com.android.launcher.entity.MileSet;
import com.android.launcher.entity.MileStart;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// 车速
public class Can203 implements CanParent {
    private static final String TAG = Can203.class.getSimpleName();
    public static volatile List<String> miles = new CopyOnWriteArrayList();
    public ACache aCache = ACache.get(App.getGlobalContext());
    //    public MileStartDaoUtil mileStartDaoUtil = new MileStartDaoUtil(App.getGlobalContext());
    public MileStart mileStart;
    //    public MileSetDaoUtil mileSetDaoUtil = new MileSetDaoUtil(App.getGlobalContext());
    public MileSet mileSet;
    public String speed;
    public static volatile String lastData = "";
    public static volatile int speedInt;
    public static volatile boolean isRunning = false;

    public Can203() {
//        mileStart = mileStartDaoUtil.queryMileStart();
//        mileSet = mileSetDaoUtil.queryMileSet();
    }

    @Override
    public void handlerCan(List<String> msg) {
        //[203, 8, 00, 00, 00, 00, 00, 00, 00, 00]
        String carspeed = msg.get(2) + msg.get(3);
        speed = FuncUtil.getCarSpeed(carspeed);
        speedInt = 0;
        if (!TextUtils.isEmpty(speed)) {
            try {
                speedInt = Integer.parseInt(speed);
                App.originalSpeed = speedInt;
                //车速加上10%
                speedInt = (int) (speedInt * App.speedAdjustValue);
            } catch (Exception e) {
                e.printStackTrace();
                speedInt = 0;
            }
        }
        if (!lastData.equals(carspeed)) {
            lastData = carspeed;
            LogUtils.printI(TAG, "handlerCan---msg=" + msg);
            LogUtils.printI(TAG, "handlerCan---speed=" + speed + ", speedInt=" + speedInt + ", speedAdjustValue=" + App.speedAdjustValue);
        }
        if (speedInt > 0) {
            if (!isRunning) {
                isRunning = true;
            }
        } else {
            if (isRunning) {
                isRunning = false;
            }
        }
        speed = String.valueOf(speedInt);
        String currentmile = FuncUtil.getMileJustInTime(speed);
        miles.add(currentmile);
        App.carSpeed = speed;
    }
}