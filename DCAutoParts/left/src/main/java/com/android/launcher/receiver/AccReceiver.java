package com.android.launcher.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.android.launcher.App;

import dc.library.auto.event.MessageEvent;
import dc.library.auto.task.logger.TaskLogger;

import com.android.launcher.can.Can17a;
import com.android.launcher.can.Can378;
import com.android.launcher.can.Can379;
import com.android.launcher.can.CanParent;
import com.android.launcher.cansender.Can3DCSender;
import com.android.launcher.cruisecontrol.Can19fSender;
import com.android.launcher.meter.MeterFragmentBase;
import com.android.launcher.service.LivingService;
import com.android.launcher.ttl.SerialHelperTTLd;
import com.android.launcher.util.CacheManager;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.LogcatHelper;
import com.android.launcher.util.OilAntiShakeUtils;

import org.greenrobot.eventbus.EventBus;

public class AccReceiver extends BroadcastReceiver {

    private static final String TAG = AccReceiver.class.getSimpleName();

    public volatile static boolean accOn;

    private AccReceiver() {
    }

    private static class LazyHolder {
        private static final AccReceiver INSTANCE = new AccReceiver();
    }

    public static final AccReceiver getInstance() {
        return LazyHolder.INSTANCE;
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        LogUtils.printI(TAG, "action=" + intent.getAction());
        if (intent.getAction().equals("xy.android.acc.on")) {
            if (!accOn) {
                try {
                    LogcatHelper.getInstance(App.getGlobalContext()).stop();
                    LogcatHelper.getInstance(App.getGlobalContext()).start();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                OilAntiShakeUtils.clear();
                accOn = true;
                try {
                    LivingService.startLivingService(App.getGlobalContext());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (intent.getAction().equals("autochips.intent.action.PREQB_POWEROFF")) {
            try {
                poweroff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (intent.getAction().equals("xy.android.acc.off")) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.TO_METER_HOME));
        }
    }

    private void poweroff() {
        try {
            FuncUtil.isSendMaintainMessage = true;
            LivingService.carStartRunTime = 0L;
            Can3DCSender.isSendTest = false;
            OilAntiShakeUtils.clear();
            accOn = false;
            SerialHelperTTLd.RUN = true;
            TaskLogger.i("SerialHelperTTLd.RUN = true;");
            CacheManager.clear();
            LivingService.stopLivingService(App.getGlobalContext());
            Can3DCSender.isSendTest = false;
            try {
                clearCanCacheData();
            } catch (Exception e) {
                e.printStackTrace();
            }
            Can19fSender.isSetAutoClose = false;
            MeterFragmentBase.meterLauncherAnimateFinish = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearCanCacheData() {
        CanParent canParent = FuncUtil.canHandler.get("378");
        if (canParent instanceof Can378) {
            Can378 can378 = (Can378) canParent;
            can378.clear();
        }
        canParent = FuncUtil.canHandler.get("379");
        if (canParent instanceof Can379) {
            Can379 can379 = (Can379) canParent;
            can379.clear();
        }
        canParent = FuncUtil.canHandler.get("17a");
        if (canParent instanceof Can17a) {
            Can17a can17a = (Can17a) canParent;
            can17a.release();
        }
    }
}