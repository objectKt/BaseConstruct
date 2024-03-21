package com.android.launcher.handler;

import com.android.launcher.App;
import dc.library.utils.event.MessageEvent;
import com.android.launcher.service.task.GetOilPercentTask;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * 油表数据
 */
@Deprecated
public class HandlerOil implements HandlerInteface {

    private static final String TAG = HandlerOil.class.getSimpleName();

    @Override
    public void handlerData(final String msg) {


        if (GetOilPercentTask.lastMmoil == 0) {
            String myoil = msg;

            //[f8, 2, 9F, D6, 00, 00, 00, 00, 00, 00]
            //D69F
            String pre = myoil.substring(0, 2);

            String suff = myoil.substring(2, 4);

            //munis为510时，汽车完全没油了
            int munis = Integer.parseInt(pre, 16) + Integer.parseInt(suff, 16);
            GetOilPercentTask.lastMmoil = munis;

            float percent = FuncUtil.getOilPrecent(munis);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.LAUNCHER_OIL);
            messageEvent.data = percent;
            EventBus.getDefault().post(messageEvent);

            LogUtils.printI(TAG, "handlerData----percent=" + percent);
            App.oilPercent = percent + "";

            MessageEvent oilBoxEvent = new MessageEvent(MessageEvent.Type.UPDATE_OIL_BOX);
            oilBoxEvent.data = percent;
            EventBus.getDefault().post(oilBoxEvent);
        } else {
            GetOilPercentTask.oilList.add(msg);
        }
    }
}

