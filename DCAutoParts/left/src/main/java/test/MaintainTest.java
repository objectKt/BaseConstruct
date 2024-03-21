package test;

import dc.library.utils.global.type.SteerWheelKeyType;

public class MaintainTest {


    static long startTime = 5000;

    public static void start() {
        classicToMaintain();


    }

    private static void classicToMaintain() {
        new Thread(() -> {
            DataTestManager.sendKey(SteerWheelKeyType.KEY_OK);
            startTime += 2000;
            DataTestManager.sendKey(SteerWheelKeyType.KEY_RIGHT);
            startTime += 2000;
            DataTestManager.sendKey(SteerWheelKeyType.KEY_RIGHT);
            startTime += 2000;
            DataTestManager.sendKey(SteerWheelKeyType.KEY_OK);
        }).start();

    }
}
