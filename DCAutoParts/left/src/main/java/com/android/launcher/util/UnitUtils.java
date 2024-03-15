package com.android.launcher.util;

/**
* @description:
* @createDate: 2023/9/14
*/
public class UnitUtils {


    public static synchronized float kmToMi(float km){
        final float factor = 0.62137119f;
        float mi = BigDecimalUtils.mul(String.valueOf(km), String.valueOf(factor));
        return mi;
    }

    public static synchronized float miToKm(float mi){
        final float factor = 1.60934f;
        float km = BigDecimalUtils.mul(String.valueOf(mi), String.valueOf(factor));
        return km;
    }

    public static synchronized float kmQtripToMiQtrip(float qtrip){
        final float factor = 0.62137119f;
        float miQtrip = BigDecimalUtils.mul(String.valueOf(qtrip), String.valueOf(factor));
//        LogUtils.printI(UnitUtils.class.getSimpleName(), "kmQtripToMiQtrip----miQtrip="+miQtrip+", qtrip="+qtrip);
        return miQtrip;
    }

    public static synchronized float kmSpeedToMiSpeed(float speed){
        final float factor = 0.62137119f;
        float miSpeed = BigDecimalUtils.mul(String.valueOf(speed), String.valueOf(factor));
//        LogUtils.printI(UnitUtils.class.getSimpleName(), "kmSpeedToMiSpeed----speed="+speed+", miSpeed="+miSpeed);
        return miSpeed;
    }

    //米转公里
    public static float mToKilometre(int curStepRetainDistance) {
        return BigDecimalUtils.div(String.valueOf(curStepRetainDistance),"1000",1);
    }
}
