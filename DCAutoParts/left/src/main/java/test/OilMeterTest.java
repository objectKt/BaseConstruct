package test;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.type.SteerWheelKeyType;

import org.greenrobot.eventbus.EventBus;

/**
 * @date： 2023/12/23
 * @author: 78495
*/
public class OilMeterTest {

    public static void start() {
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                executeTask();
//            }
//        }).start();
    }

    private static void executeTask() {
        try {
            Thread.sleep(6000);
            MessageEvent messageEvent;


            messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
            messageEvent.data = SteerWheelKeyType.KEY_OK.ordinal();
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
            messageEvent.data = 1.0f;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
            messageEvent.data = 0.92f;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
            messageEvent.data = 0.82f;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
            messageEvent.data = 0.5f;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
            messageEvent.data = 0.2f;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
            messageEvent.data = 0.15f;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
            messageEvent.data = 0.08f;
            EventBus.getDefault().post(messageEvent);

//            Thread.sleep(2000);
//            messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
//            messageEvent.data = 0.6f;
//            EventBus.getDefault().post(messageEvent);
//
//            Thread.sleep(2000);
//            messageEvent = new MessageEvent(MessageEvent.Type.CURRENT_ML);
//            messageEvent.data = 0.8f;
//            EventBus.getDefault().post(messageEvent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
