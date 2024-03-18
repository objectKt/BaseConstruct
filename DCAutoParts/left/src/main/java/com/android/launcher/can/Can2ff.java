package com.android.launcher.can;

import android.os.CountDownTimer;

import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.util.FastJsonUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 胎压
 */
public class Can2ff implements CanParent {


    public CountDownTimer timer = null;

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        try {
            String data = FastJsonUtils.BeanToJson(msg);

            if (!lastData.equals(data)) {
                lastData = data;
                LogUtils.printI(Can2ff.class.getSimpleName(), "lastData=" + lastData + ", data=" + data);

                SPUtils.putString(App.getGlobalContext(), SPUtils.SP_TAIYAI, lastData);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_TAIYAI);
                messageEvent.data = lastData;
                EventBus.getDefault().post(messageEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
