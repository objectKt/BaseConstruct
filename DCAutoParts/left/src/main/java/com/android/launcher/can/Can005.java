package com.android.launcher.can;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
* @description: Hold功能
* @createDate: 2023/8/18
*/
public class Can005 implements CanParent {

    private static final String TAG = Can005.class.getSimpleName();

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        try {
            String d1 = msg.get(2);
            if(!lastData.equalsIgnoreCase(d1)){
                lastData = d1;
                LogUtils.printI(TAG, "msg="+msg);

                String status = d1.substring(1);
                LogUtils.printI(TAG, "Hold---status="+status);

                if(status.equals("5")){ //变成 5时 屏幕显示hold功能
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.HOLD_STATUS);
                    messageEvent.data = true;
                    EventBus.getDefault().post(messageEvent);
                }else if(d1.equals("11")){ //11为刹车状态

                }else{
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.HOLD_STATUS);
                    messageEvent.data = false;
                    EventBus.getDefault().post(messageEvent);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
