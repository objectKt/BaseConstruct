package com.android.launcher.can;

import com.android.launcher.MessageEvent;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
 * 雷达
 */
public class Can2ee implements CanParent {

    private static final String TAG = Can2ee.class.getSimpleName();
    public static volatile String lastRadarSwitchState = "";
    public static volatile String lastRadarData = "";

    @Override
    public void handlerCan(List<String> msg) {
        String data = String.join("", msg);
        String radar = msg.get(5);
        if (!lastRadarData.equals(radar)) {
            lastRadarData = radar;
            LogUtils.printI(TAG, "handlerCan----msg=" + msg + ", radar=" + radar);
            String back = msg.get(8);
            CommonUtil.meterHandler.get("radarfront").handlerData(radar);
        }
        String radarSwitchState = msg.get(2);
        if (!lastRadarSwitchState.equals(radarSwitchState)) {
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.RADAR_P_OFF);
            messageEvent.data = radarSwitchState;
            EventBus.getDefault().post(messageEvent);
            lastRadarSwitchState = radarSwitchState;
        }
        // CommonUtil.meterHandler.get("radarback").handlerData(MeterPresentation.localMeterPresentation,back);
    }
}