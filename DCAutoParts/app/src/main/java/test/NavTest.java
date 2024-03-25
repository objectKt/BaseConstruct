package test;

import com.android.launcher.MessageEvent;
import com.android.launcher.entity.NavEntity;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @date： 2024/1/5
 * @author: 78495
*/
public class NavTest {

    public static void start(){

        new Thread(() -> {
            try {
                Thread.sleep(8000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            NavEntity navEntity = new NavEntity("23.340893", "113.22861", Integer.valueOf(20), Integer.valueOf(12));

            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.START_MAP_NAV);
            messageEvent.data = navEntity;
            EventBus.getDefault().post(messageEvent);
        }).start();

    }

}
