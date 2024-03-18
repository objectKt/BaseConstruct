package test;

import com.android.launcher.handler.HandlerDoor;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.meter.MeterFragmentType;
import com.android.launcher.util.CommonUtil;

public class DoorMessageTest {


    public static void start(){
        MeterActivity.currentFragmentType = MeterFragmentType.MAP;
        new Thread(() -> {
            try {
                Thread.sleep(5000);
            } catch (Exception e) {
                e.printStackTrace();
            }
            HandlerDoor door = (HandlerDoor) CommonUtil.meterHandler.get("door");
            door.handlerData("59");

        }).start();


    }
}
