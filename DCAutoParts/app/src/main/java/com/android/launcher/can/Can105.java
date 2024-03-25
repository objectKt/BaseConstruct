package com.android.launcher.can;

import android.os.Build;
import android.os.SystemClock;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.launcher.meter.MeterFragmentBase;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.LogUtils;

import java.math.BigDecimal;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * 发动机转速
 */
public class Can105 implements CanParent {

    public List<String> list = new CopyOnWriteArrayList<>();

    public String startEngA = "0.0", endEngA = "0.0";


    public volatile boolean run;
    public int realSpeed;

    public long stopTime = System.currentTimeMillis();

    public static volatile boolean engineRunning = false;

    //发动机转速
    public static volatile int engineSpeed;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handlerCan(List<String> msg) {
        String speedAl = msg.get(2) + msg.get(3);
        String vaild = msg.get(4);
        if (!vaild.toUpperCase().equals("FF")) {
            realSpeed = CommonUtil.getHexToDecimal(speedAl);
            engineSpeed = realSpeed;
        } else {
            run = false;
            stopTime = System.currentTimeMillis();
        }
        if(!engineRunning){
            engineRunning = true;
        }
    }


    String fff = "";

    // TODO: 2023-02-11 后续重写
//    private synchronized void handler105(String s) {
//
//        if (fff.equals("")) {
//            fff = s;
//        } else {

//            String frin = fff.split("-")[0];
//            Long fritime = Long.parseLong(fff.split("-")[1]);

//            engineSpeed = s.split("-")[0];
//            Long lasttime = Long.parseLong(s.split("-")[1]);

//            ll = lasttime - fritime;

//            LogUtils.printI(Can105.class.getSimpleName(), "handler105----fae=" + fae + ", s=" + s + ", animationTime=" + ll);
//            LogUtils.printI(Can105.class.getSimpleName(), "handler105----MeterFragmentBase.localMeterFragment=" + MeterFragmentBase.localMeterFragment);
//            if (MeterFragmentBase.localMeterFragment != null) {
//                MeterFragmentBase.localMeterFragment.updateEngineSpeed(fae, ll);
//            }


//            BigDecimal bignum3 = new BigDecimal(fae);
//            final String f = getEngDegree(bignum3.floatValue());
//            setEnergySpeed(f);
//            fff = s;
//        }
//
//    }


    private String getEngDegree(float f) {
        BigDecimal bignum1 = new BigDecimal(f);
        BigDecimal bignum2 = new BigDecimal("0.032");
        BigDecimal bignum3 = bignum1.multiply(bignum2);
        return bignum3.toString();
    }


    /**
     * 发送机转速指针转动
     *
     * @param f
     */
    public void setEnergySpeed(final String f) {
        BigDecimal fromd = new BigDecimal(startEngA);
        endEngA = f;
        BigDecimal tod = new BigDecimal(endEngA);
        startEngA = endEngA;
//        MeterActivity.localMeterActivity.imageViewrightUpdate(fromd,tod,ll);
    }

}
