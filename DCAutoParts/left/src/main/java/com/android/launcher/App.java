package com.android.launcher;

import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Handler;
import android.util.Log;

import com.android.launcher.handler.hookMessageQueue;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.receiver.USBBroadCastReceiver;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.CacheManager;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogcatHelper;
import com.dc.auto.library.global.AndroidContext;
import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;

import org.greenrobot.eventbus.EventBus;

import com.dc.auto.library.cockroach.Cockroach;
import com.dc.auto.library.cockroach.ExceptionHandler;

import dc.library.auto.event.MessageEvent;

//import com.android.launcher.ch340.CH34xUARTDriver;

public class App extends Application {

    //热点是否是开启状态
    public static boolean WifiApOpen = false;
    //热点尝试开启的次数
    public static boolean currentMeterActivity = false;
    //原始车速
    public static int originalSpeed = 0;
    public static boolean livingServerStop = true;
    public static boolean isAppStart = false;
    //启动后的平均时速
    public static float runningAverageSpeed = 0.0f;
    public Handler handler = new Handler();
    /**
     * 全局Context
     */
    private static Context mContext;
    //车速校准值
    public static float speedAdjustValue = 1.00f;
    public static volatile boolean startAss;
    public static volatile String carSpeed = "0";
    public static String oilPercent = "0.0";
    public ACache aCache;
    public static boolean isRestart = false;

    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.clear();
        isAppStart = true;
        aCache = ACache.get(this);
        mContext = getApplicationContext();
        AndroidContext.getInstance().init(getApplicationContext());
        Log.i("leftacc", "----------------app重新启动");
        currentMeterActivity = false;
        isRestart = true;

        new ANRWatchDog().setANRListener(new ANRWatchDog.ANRListener() {
            @Override
            public void onAppNotResponding(ANRError error) {

                Log.i("anrWatchDog", error + "-----------------");
                alertDialogRestartApp();
            }
        }).setIgnoreDebugger(true).setInterruptionListener(new ANRWatchDog.InterruptionListener() {
            @Override
            public void onInterrupted(InterruptedException exception) {
                alertDialogRestartApp();
            }
        });

        hookMessageQueue.hookActivityThreadHandler();
        Cockroach.install(this, new ExceptionHandler() {
            @Override
            protected void onUncaughtExceptionHappened(Thread thread, Throwable throwable) {
                if (!throwable.getLocalizedMessage().contains("barrier token has not been posted or has already been removed")) {
                    alertDialogRestartApp();
                }
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:" + thread + "<---", throwable);

            }

            @Override
            protected void onBandageExceptionHappened(Throwable throwable) {
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:onBandageExceptionHappened" + "<---", throwable);
                if (!throwable.getLocalizedMessage().contains("barrier token has not been posted or has already been removed")) {
                    alertDialogRestartApp();
                }
                //打印警告级别log，该throwable可能是最开始的bug导致的，无需关心
            }

            @Override
            protected void onEnterSafeMode() {
                alertDialogRestartApp();
            }

            @Override
            protected void onMayBeBlackScreen(Throwable e) {
                Log.e("AndroidRuntime", "--->onUncaughtExceptionHappened:onMayBeBlackScreen" + "<---", e);
                if (!e.getLocalizedMessage().contains("barrier token has not been posted or has already been removed")) {
                    alertDialogRestartApp();
                }
            }
        });
        USBBroadCastReceiver.registerReceiver();
        FuncUtil.initMileAndParam();
        try {
            LogcatHelper.getInstance(App.getGlobalContext()).stop();
            LogcatHelper.getInstance(App.getGlobalContext()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取全局Context
     *
     * @return
     */
    public static Context getGlobalContext() {
        return mContext;
    }

    private void restartApp() {
        try {
            LogcatHelper.getInstance(App.getGlobalContext()).stop();
            LogcatHelper.getInstance(App.getGlobalContext()).start();
        } catch (Exception e) {
            e.printStackTrace();
        }
        aCache.put("restartApp", "restartApp");
        Intent intent = new Intent(getApplicationContext(), MeterActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        getApplicationContext().startActivity(intent);
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void alertDialogRestartApp() {
        EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SHOW_RESTART_APP_DIALOG));
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                restartApp();
            }
        }).start();
    }
}