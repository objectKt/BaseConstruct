package com.android.launcher.can;

import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

//副驾驶气囊关闭参见用户手册 告警
public class Can305 implements CanParent {


    private static final String TAG = Can305.class.getSimpleName();

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        String data = msg.get(5);
        if (!lastData.equals(data)) {
            LogUtils.printI(TAG, "msg=" + msg);
            lastData = data;

            if (data.equals("02")) {
                AlertVo alertVo = new AlertVo();
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW + "");
//                alertVo.setAlertMessage(AlertMessage.ALERT_305_5_02);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_305_5_02));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
        }


    }
}
