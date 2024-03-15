package test;

import android.os.Handler;

import com.android.launcher.MessageEvent;
import com.android.launcher.type.SteerWheelKeyType;

import org.greenrobot.eventbus.EventBus;

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
