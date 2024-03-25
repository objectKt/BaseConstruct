package com.android.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.wifi.WifiManager;
import android.util.Log;

public class WifiReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(WifiManager.NETWORK_STATE_CHANGED_ACTION)) {
            NetworkInfo info = intent.getParcelableExtra(WifiManager.EXTRA_NETWORK_INFO);
            if (info.getState().equals(NetworkInfo.State.DISCONNECTED)) {
//                Constant.wifiInfo = null;
//                ToastUtil.show("断开wifi");
                Log.i("WIFI","wifi已经断开") ;
            } else if (info.getState().equals(NetworkInfo.State.CONNECTED)) {
                if (info.getType() == ConnectivityManager.TYPE_WIFI) {
                    WifiManager wifiManager = (WifiManager) context.getSystemService(Context.WIFI_SERVICE);
//                    Constant.wifiInfo = wifiManager.getConnectionInfo();
//                    ToastUtil.show("连接wifi");
                    Log.i("WIFI","wifi已经连接") ;


                }
            }
        }
    }
}
