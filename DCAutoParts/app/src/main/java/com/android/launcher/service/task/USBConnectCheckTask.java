package com.android.launcher.service.task;

import android.content.Context;
import android.content.res.Resources;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.util.LogUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

/**
 * USB检测任务
 * @date： 2024/3/21
 * @author: 78495
*/
public class USBConnectCheckTask extends TaskBase {

    public static volatile boolean normal = true;

    public USBConnectCheckTask(Context context) {
        super(context);
        normal = true;
    }

    @Override
    public void run() {
        try {
            LogUtils.printI(TAG,"run---normal="+normal);
            if(normal){
                normal = false;
            }else{
                //提示USB通信异常
                AlertVo alertVo = new AlertVo() ;
                alertVo.setAlertImg(R.drawable.ic_usb_interrupt);
                alertVo.setAlertMessage(context.getResources().getString(R.string.usb_interrupt_hint));
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                messageEvent.data = alertVo;
                EventBus.getDefault().post(messageEvent);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
