package com.android.launcher.receiver;

import android.content.IntentFilter;

import com.android.launcher.App;
import com.android.launcher.usbdriver.MUsb1Receiver;

public class USBBroadCastReceiver {

    public static AccReceiver accReceiver;

    public static void registerReceiver() {

        accReceiver = AccReceiver.getInstance() ;

//        IntentFilter filteracc = new IntentFilter();
//        filteracc.addAction("xy.android.acc.off");
        //3.注册广播接收者

//        App.getGlobalContext().registerReceiver(accReceiver, filteracc);


        IntentFilter filter3 = new IntentFilter();
        filter3.addAction("xy.android.acc.on");
        filter3.addAction("xy.android.acc.off");
        filter3.addAction("autochips.intent.action.QB_POWERON");
        filter3.addAction("autochips.intent.action.PREQB_POWEROFF");
        filter3.addAction("autochips.intent.action.QB_POWEROFF");
        filter3.addAction("android.intent.action.PACKAGE_REPLACED");
        filter3.addAction("xy.SilentUninstall");
        //3.注册广播接收者
        App.getGlobalContext().registerReceiver(accReceiver, filter3);

    }

}