package com.android.launcher.can;

import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 告警
 *
 * @date： 2023/12/25
 * @author: 78495
 */
public class Can181 implements CanParent {


    private static final String TAG = Can180.class.getSimpleName();

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        String data = msg.get(3);
        String alert_s = data.substring(1, 2);

        if (!lastData.equals(data)) {
            LogUtils.printI(TAG, "msg=" + msg);
            lastData = data;


            if (alert_s.equals("1")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(0);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_181_2_01);

                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_181_2_01));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
        }
    }
}
