package com.android.launcher;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Handler;
import android.util.Log;

import androidx.annotation.RequiresApi;

import com.android.launcher.can.Can203;
import com.android.launcher.handler.hookMessageQueue;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.receiver.USBBroadCastReceiver;
import com.android.launcher.util.ACache;
import com.android.launcher.util.CacheManager;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogcatHelper;
import com.github.anrwatchdog.ANRError;
import com.github.anrwatchdog.ANRWatchDog;

import org.greenrobot.eventbus.EventBus;

import cockroach.Cockroach;
import cockroach.ExceptionHandler;

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


    public static volatile boolean startAss ;
    public static volatile String carSpeed =  "0";
    public static String oilPercent ="0.0";

    public ACache aCache;

    public static boolean isRestart = false;

    @SuppressLint("NewApi")
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    public void onCreate() {
        super.onCreate();
        CacheManager.clear();
        isAppStart = true;
        aCache = ACache.get(this);
        mContext = getApplicationContext();
        Log.i("leftacc","----------------app重新启动") ;

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


 /**********************************测试部分*************************************************/


//   new Thread(new Runnable() {
//       @Override
//       public void run() {
//
//           try {
//               Thread.sleep(1000);
//               while(true){
//                   Thread.sleep(1000);
//                   WIfiTask.sendMessage(System.currentTimeMillis()+"----");
//               }
////               new Thread(new Runnable() {
////                   @Override
////                   public void run() {
////
////                   }
////               }).start();
//           } catch (InterruptedException e) {
//               e.printStackTrace();
//           }
//       }
//   }).start();

    }

    /**
     * 获取全局Context
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

    //AlertDialog 生成方法一
//使用类 AlertDialog
//    public void  alertDialog() {
//
//        Log.i("anrWatchDog","================================log") ;
//
//        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
//        alertDialog.setTitle("提示：系统需要升级");
//        alertDialog.setMessage("确定要退出程序吗？");
//        alertDialog.setButton(DialogInterface.BUTTON_POSITIVE, "确定",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//                        restartApp();
//                    }
//                });
//
//        alertDialog.setButton(DialogInterface.BUTTON_NEUTRAL, "想一下",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//
//        alertDialog.setButton(DialogInterface.BUTTON_NEGATIVE, "取消",
//                new DialogInterface.OnClickListener() {
//                    @Override
//                    public void onClick(DialogInterface dialog, int which) {
//
//                    }
//                });
//        alertDialog.show();
//    }

}

