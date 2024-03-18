package com.android.launcher.can;

import com.android.launcher.service.LivingService;
import com.android.launcher.util.LogUtils;

import java.util.List;

/**
 * @date： 2023/12/29
 * @author: 78495
 */
public class Can39d implements CanParent {


    private static final String TAG = Can39d.class.getSimpleName();

    public static volatile String lastData = "";
    public static volatile String lastD0 = "";
    public static volatile String lastD1 = "";

    @Override
    public void handlerCan(List<String> msg) {
        String distanceRunCount255Hex = msg.get(4);
        String distanceRunHex = msg.get(5);
        if (!lastData.equals(distanceRunHex)) {
            int distanceRunCount255 = Integer.parseInt(distanceRunCount255Hex, 16);
            LivingService.distance = distanceRunCount255 * 255 + Integer.parseInt(distanceRunHex, 16);
            LogUtils.printI(TAG, "msg=" + msg + ", distanceRunHex=" + distanceRunHex + ", distanceRun=" + LivingService.distance);
            lastData = distanceRunHex;
        }
        String d0Hex = msg.get(2);
        String d1Hex = msg.get(3);
        if (!lastD0.equals(d0Hex)) {
            lastD0 = d0Hex;
            LogUtils.printI(TAG, "d0Hex=" + d0Hex + ", d0=" + Integer.parseInt(d0Hex, 16) + ", d1Hex=" + d1Hex + ", d1=" + Integer.valueOf(d1Hex, 16));
        }
        if (!lastD1.equals(d1Hex)) {
            lastD1 = d1Hex;
            LogUtils.printI(TAG, "d0Hex=" + d0Hex + ", d0=" + Integer.parseInt(d0Hex, 16) + ", d1Hex=" + d1Hex + ", d1=" + Integer.valueOf(d1Hex, 16));
        }

    }

    public static void clear() {
        lastData = "";
        lastD0 = "";
        lastD1 = "";
    }
}
