package test;

import dc.library.utils.event.MessageEvent;
import dc.library.utils.global.type.SteerWheelKeyType;

import org.greenrobot.eventbus.EventBus;

/**
 * @date： 2023/12/22
 * @author: 78495
*/
public class MapMeterTest {

    private static long startTime = 6000;

    public static void start() {


        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                    messageEvent.data = SteerWheelKeyType.KEY_OK.ordinal();
                    EventBus.getDefault().post(messageEvent);

                    Thread.sleep(5000);
//
                    messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                    messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();
                    EventBus.getDefault().post(messageEvent);

                    Thread.sleep(3000);

                    messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
                    messageEvent.data = SteerWheelKeyType.KEY_OK.ordinal();
                    EventBus.getDefault().post(messageEvent);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        }).start();

    }

}
