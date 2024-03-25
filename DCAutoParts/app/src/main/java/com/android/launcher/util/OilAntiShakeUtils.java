package com.android.launcher.util;

/**
 * @description: 油箱波动防抖
 * @createDate: 2023/8/10
 */
@Deprecated
public class OilAntiShakeUtils {

    private static final String TAG = OilAntiShakeUtils.class.getSimpleName();

    private static float lastOilML = 0f;

    //偏离值, 说明加油了
    private static final float DEVIATE = 0.3f;

    public static synchronized float getOilML(float oilML) {
        LogUtils.printI(TAG, "oilML=" + oilML + ", lastOilML=" + lastOilML);
        if (lastOilML <= 0f) {
            lastOilML = oilML;
            return oilML;
        }

        float value = BigDecimalUtils.sub(String.valueOf(oilML), String.valueOf(lastOilML));

        if (oilML > lastOilML && value <= DEVIATE) {
            return lastOilML;
        } else {
            lastOilML = oilML;
        }

//        LogUtils.printI(TAG, "oilML="+oilML +", lastOilML="+lastOilML +", value="+value);
//        if(value > DEVIATE){
//            return lastOilML;
//        }

        return lastOilML;
    }

    public static void clear() {
        lastOilML = 0f;
    }
}
