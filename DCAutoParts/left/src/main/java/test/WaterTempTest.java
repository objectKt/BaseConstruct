package test;

import com.android.launcher.MessageEvent;

import org.greenrobot.eventbus.EventBus;

/**
 * @date： 2023/12/23
 * @author: 78495
 */
public class WaterTempTest {

    public static void start() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                executeTask();
            }
        }).start();
    }

    private static void executeTask() {
        try {
            Thread.sleep(6000);
            MessageEvent messageEvent;

//            messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
//            messageEvent.data = SteerWheelKeyType.KEY_OK.ordinal();
//            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
            messageEvent.data = 50;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);

            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
            messageEvent.data = 60;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);

            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
            messageEvent.data = 73;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
            messageEvent.data = 75;
            EventBus.getDefault().post(messageEvent);


            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
            messageEvent.data = 97;
            EventBus.getDefault().post(messageEvent);


            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
            messageEvent.data = 120;
            EventBus.getDefault().post(messageEvent);


            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
            messageEvent.data = 100;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
            messageEvent.data = 80;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);

            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WATER_TEMP);
            messageEvent.data = 60;
            EventBus.getDefault().post(messageEvent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
