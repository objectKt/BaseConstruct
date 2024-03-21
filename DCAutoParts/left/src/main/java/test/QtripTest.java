package test;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.service.LivingService;

import org.greenrobot.eventbus.EventBus;

public class QtripTest {

    public static void start(){
        new Thread(() -> {
            try {
                Thread.sleep(6000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.LAUNCHER_OIL);
            messageEvent.data = 0.63f;
            EventBus.getDefault().post(messageEvent);

            //100公里
            LivingService.launchCarRunMile = 10 * 1000;
        }).start();
    }
}
