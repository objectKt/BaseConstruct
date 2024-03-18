package com.android.launcher.can;

import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.service.task.GetOilPercentTask;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 油表数据
 * <p>
 * f8270D6000000000000
 * content=77, real=D6
 */
public class Canf8 implements CanParent {

    private static final String TAG = Canf8.class.getSimpleName();

    public static volatile String lastData = "";
    public static volatile String lastPercent = "0.0";

    @Override
    public void handlerCan(List<String> msg) {

        String senddata = String.join("", msg);

        if (!lastData.equals(senddata)) {
            lastData = senddata;
            LogUtils.printI(TAG, "handlerCan--msg=" + msg);
        }

        if (senddata.startsWith("f82")) {
            String content = msg.get(2);
            String real = msg.get(3);
            //亮红灯 [f8, 2, 9F, D6, 00, 00, 00, 00, 00, 00]
            if (GetOilPercentTask.lastMmoil == 0) {
                String myoil = real + content;

                //[f8, 2, 9F, D6, 00, 00, 00, 00, 00, 00]
                //D69F
                String pre = myoil.substring(0, 2);

                String suff = myoil.substring(2, 4);

                //munis为510时，汽车完全没油了
                int munis = Integer.parseInt(pre, 16) + Integer.parseInt(suff, 16);
                GetOilPercentTask.lastMmoil = munis;

                float percent = FuncUtil.getOilPrecent(munis);
                if (percent >= 0.0f) {
                    GetOilPercentTask.launchOilPercent = percent;

                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.LAUNCHER_OIL);
                    messageEvent.data = percent;
                    EventBus.getDefault().post(messageEvent);

                    LogUtils.printI(TAG, "handlerData----percent=" + percent);
                    App.oilPercent = percent + "";

                    MessageEvent oilBoxEvent = new MessageEvent(MessageEvent.Type.UPDATE_OIL_BOX);
                    oilBoxEvent.data = percent;
                    EventBus.getDefault().post(oilBoxEvent);
                } else {
                    GetOilPercentTask.lastMmoil = 0;
                }
            } else {
                String myoil = real + content;
                GetOilPercentTask.oilList.add(myoil);
            }
        }
    }

}
