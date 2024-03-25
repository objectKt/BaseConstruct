package com.android.launcher.can;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

//预防性安全系统 告警
public class Can180 implements CanParent {

    private static final String TAG = Can180.class.getSimpleName();

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        String data = msg.get(3) ;
        String status = data.substring(1,2) ;

        if (!lastData.equals(data)) {
            LogUtils.printI(TAG, "msg="+msg);
            lastData = data;

            if (status.equals("1")){
                AlertVo alertVo = new AlertVo() ;
                alertVo.setAlertImg(R.drawable.ic_airbag_warn);
                alertVo.setAlertColor(AlertMessage.TEXT_YELLOW+"");
//                alertVo.setAlertMessage(AlertMessage.ALERT_180_2_01);
                alertVo.setAlertMessage(App.getGlobalContext().getResources().getString(R.string.alert_180_2_01));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
        }


    }
}
