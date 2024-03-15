package com.android.launcher.handler;

import android.text.TextUtils;
import android.util.Log;

import com.android.launcher.MessageEvent;
import com.android.launcher.type.SteerWheelKeyType;
import com.android.launcher.type.SteeringWheelKeyType;
import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

//import com.android.launcher.vo.SendToAssistant;

/**
 * 方向盘按键控制
 */
public class HandlerLamp implements HandlerInteface {

    private static final String TAG = HandlerLamp.class.getSimpleName();

    private long lastOperationTime = 0;

    //间隔时间一秒
    private static final long INTERVAL_TIME = 1000;


    @Override
    public void handlerData(final String lamp) {

        if (lamp != null) {
            LogUtils.printI(TAG, "lamp="+lamp);
            //静音
            if (lamp.equals("15")) {
                String send = "AABB2000CCDD";
                SendHelperUsbToRight.handler(send);

            } else if (lamp.equals("11")) {
                //音量加
                String send = "AABB1900CCDD";
                SendHelperUsbToRight.handler(send);
            } else  if (lamp.equals("12")) {
                //音量减
                String send = "AABB1800CCDD";
                SendHelperUsbToRight.handler(send);
            } else  if (lamp.equals("14")) {
                //接电话
                String send = "AABB1100CCDD";
                SendHelperUsbToRight.handler(send);
            } else if (lamp.equals("13")) {
                //挂电话
                String send = "AABB1700CCDD";
                SendHelperUsbToRight.handler(send);
            }else{
                disposeSteerWheelKeyData(lamp);
            }
        }
    }

    /**
     * @description: 方向盘按键
     * @createDate: 2023/6/25
     */
    private void disposeSteerWheelKeyData(String lamp) {
        try {
            long currentTime = System.currentTimeMillis();

            long interval = currentTime - lastOperationTime;
            LogUtils.printI(HandlerLamp.class.getSimpleName(), "disposeSteerWheelKeyData----interval=" + interval);
//            if (interval <= INTERVAL_TIME && interval > 0) {
//
//                return;
//            }
            lastOperationTime = currentTime;

            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.STEER_WHEEL_TYPE);
            if (lamp.equals(SteeringWheelKeyType.UP.getTypeValue())) { //上
                messageEvent.data = SteerWheelKeyType.KEY_UP.ordinal();

            } else if (lamp.equals(SteeringWheelKeyType.DOWN.getTypeValue())) {//下
                messageEvent.data = SteerWheelKeyType.KEY_DOWN.ordinal();

            } else if (lamp.equals(SteeringWheelKeyType.LEFT.getTypeValue())) {//左
                messageEvent.data = SteerWheelKeyType.KEY_LEFT.ordinal();

            } else if (lamp.equals(SteeringWheelKeyType.RIGHT.getTypeValue())) {//右
                messageEvent.data = SteerWheelKeyType.KEY_RIGHT.ordinal();

            } else if (lamp.equals(SteeringWheelKeyType.OK.getTypeValue())) { //ok
                messageEvent.data = SteerWheelKeyType.KEY_OK.ordinal();

            } else if (lamp.equals(SteeringWheelKeyType.BACK.getTypeValue())) { //返回
                messageEvent.data = SteerWheelKeyType.KEY_BACK.ordinal();
            }
            EventBus.getDefault().post(messageEvent);

            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.DISABLE_ORIGIN_METER));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
