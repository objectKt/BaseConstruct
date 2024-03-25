package com.android.launcher.can;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.android.launcher.MessageEvent;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

//abs
public class Can247 implements  CanParent{


    public static volatile String lastData = "";

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public void handlerCan(List<String> msg) {

        try {
            String senddata = String.join("", msg);
            if (!lastData.equals(senddata)) {
                lastData = senddata;
                String absflg = msg.get(4) ;

                LogUtils.printI(Can247.class.getSimpleName(), "handlerCan---msg="+msg +",absflg="+absflg);

                if (absflg.contains("0")){
                    CommonUtil.meterHandler.get("absflg").handlerData("off");
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_ABS_STATUS);
                    messageEvent.data = false;
                    EventBus.getDefault().post(messageEvent);
                }
                if (absflg.contains("c")||absflg.contains("3")){
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_ABS_STATUS);
                    CommonUtil.meterHandler.get("absflg").handlerData("on");
                    messageEvent.data = true;
                    EventBus.getDefault().post(messageEvent);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
