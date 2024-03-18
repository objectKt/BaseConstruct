package test;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.type.GearsType;

import org.greenrobot.eventbus.EventBus;

/**
 * 档位测试
 * @date： 2023/12/23
 * @author: 78495
*/
public class GearsTest {

    public static void start(){
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

            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = GearsType.TYPE_R;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = GearsType.TYPE_N;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = GearsType.TYPE_D;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = GearsType.TYPE_D1;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = GearsType.TYPE_D2;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = GearsType.TYPE_D3;
            EventBus.getDefault().post(messageEvent);


            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = GearsType.TYPE_D4;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = GearsType.TYPE_D5;
            EventBus.getDefault().post(messageEvent);

            Thread.sleep(2000);
            messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_GEARS_STATUS);
            messageEvent.data = GearsType.TYPE_P;
            EventBus.getDefault().post(messageEvent);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
