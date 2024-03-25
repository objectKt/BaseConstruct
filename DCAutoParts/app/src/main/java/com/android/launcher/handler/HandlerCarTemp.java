package com.android.launcher.handler;

import android.os.CountDownTimer;
import android.os.Handler;
import android.os.Looper;
import android.util.Log;

import com.android.launcher.MessageEvent;
import com.android.launcher.meter.MeterFragmentBase;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

/**
 *
 * 环境温度
 *
 */
@Deprecated
public class HandlerCarTemp implements HandlerInteface {

    public static final String TAG = HandlerCarTemp.class.getSimpleName() ;

    public boolean flg ;

    @Override
    public void handlerData( String msg) {
        final String carTemp = msg ;

        LogUtils.printI(TAG,"handlerData---msg="+msg);
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_CAR_TEMP);
        messageEvent.data = carTemp+"℃";
        EventBus.getDefault().post(messageEvent);


    }
}
