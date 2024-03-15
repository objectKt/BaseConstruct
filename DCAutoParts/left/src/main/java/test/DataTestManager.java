package test;

import android.os.Handler;

import com.android.launcher.MessageEvent;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.meter.MeterFragmentType;
import com.android.launcher.type.SteerWheelKeyType;
import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @date： 2023/11/22
 * @author: 78495
*/
public class DataTestManager {

    public static void start(){
//        NavTest.start();

//        QtripTest.start();

//        GearsTest.start();

//        OilMeterTest.start();

//        WaterTempTest.start();

//        MapMeterTest.start();

//        ClassicMenuTest.start();
//        MeterActivity.currentFragmentType = MeterFragmentType.MAP;
//        WarnMessageTest.start();
//        ClassicSpeedAdjustMenuTest.start();

//        CanDataTest.start();

//        TechMenuTest.start();

//        SportMeterTest.start();

//        MaintainTest.start();

//        DoorMessageTest.start();

//        new Handler().postDelayed(new Runnable() {
//            @Override
//            public void run() {
//                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.RESTART_SERVICE));
//            }
//        },15000);

//
//        String data = "1bb00100000000000003".substring(4);
//        String s = "AA000008000000FB" + data;
//        LogUtils.printI("Test","1bb---"+s+", length="+s.length());

//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                while (true){
//
//                    SendHelperUsbToRight.handler("AABB000008000001E52050CCDD");
//                    try {
//                        Thread.sleep(500);
//                    } catch (InterruptedException e) {
//                        e.printStackTrace();
//                    }
//                }
//            }
//        }).start();
    }


    public static void sendKey(SteerWheelKeyType key) {
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
        messageEvent.data = key.ordinal();
        EventBus.getDefault().post(messageEvent);
    }
}
