package com.android.launcher.can;

import android.os.Build;

import com.android.launcher.MessageEvent;
import com.android.launcher.util.BigDecimalUtils;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * @date： 2023/12/30
 * @author: 78495
*/
public class Can37d implements CanParent {

    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {

        String senddata = String.join("", msg);
        String d7 = msg.get(9);
        if (!lastData.equals(d7)) {
            lastData = d7;
            String mflg = msg.get(2);

            mflg = mflg.substring(mflg.length() - 1, mflg.length());

            LogUtils.printI(Can37d.class.getSimpleName(),"msg="+msg +", mflg="+mflg);
            if (mflg.equals("1")) {
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_STORAGE_BATTERY);
                messageEvent.data = true;
                EventBus.getDefault().post(messageEvent);
            }else{
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_STORAGE_BATTERY);
                messageEvent.data = false;
                EventBus.getDefault().post(messageEvent);
            }


            try {

                int d7Data = Integer.parseInt(d7, 16);
                LogUtils.printI(Can37d.class.getSimpleName(), "d7="+d7 +", d7Data="+d7Data);
            } catch (NumberFormatException e) {
                e.printStackTrace();
            }
        }
    }
}
