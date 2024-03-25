package com.android.launcher.wifi;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
* @description: 热点开启状态
* @createDate: 2023/5/8
*/
@Deprecated
public class WifiApBroadcast extends BroadcastReceiver {

    private static final String TAG = WifiApBroadcast.class.getSimpleName();

    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        LogUtils.printI(TAG, "action="+action);
        if ("android.net.wifi.WIFI_AP_STATE_CHANGED".equals(action)) {
            //便携式热点的状态为：10---正在关闭；11---已关闭；12---正在开启；13---已开启
            int state = intent.getIntExtra("wifi_state", 0);
            LogUtils.printI(TAG, "热点开关状态：state= " + state);
            if (state == 13) {
                if(!App.WifiApOpen){
                    App.WifiApOpen = true;
                    LogUtils.printI(TAG, "热点已开启");
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.WIFI_AP_OPEN));
                }

            } else if (state == 11) {
                if(App.WifiApOpen){
                    App.WifiApOpen = false;
                    LogUtils.printI(TAG, "热点已关闭");
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.WIFI_AP_CLOSE));
                }

            } else if (state == 10) {
                LogUtils.printI(TAG, "热点正在关闭");
            } else if (state == 12) {
                LogUtils.printI(TAG,"热点正在开启");
            }
        }else if("android.net.conn.TETHER_STATE_CHANGED".equals(action)){
            ArrayList<String> available = intent.getStringArrayListExtra("availableArray");
            ArrayList<String> active = intent.getStringArrayListExtra("tetherArray");
            ArrayList<String> error = intent.getStringArrayListExtra("erroredArray");

            LogUtils.printI(TAG,"available="+available +", active="+active + ", error="+error);
        }
    }

    public static void register(Context context, WifiApBroadcast wifiApBroadcast){
        IntentFilter intentFilter = new IntentFilter("android.net.wifi.WIFI_AP_STATE_CHANGED");
        intentFilter.addAction("android.net.conn.TETHER_STATE_CHANGED");
        context.registerReceiver(wifiApBroadcast,intentFilter);

    }

    public static void unregister(Context context, WifiApBroadcast wifiApBroadcast){
        context.unregisterReceiver(wifiApBroadcast);
    }
}