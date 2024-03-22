package com.android.launcher.service;

import android.annotation.SuppressLint;
import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.PixelFormat;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.WindowManager;

import com.android.launcher.App;
import com.android.launcher.R;
import com.android.launcher.can.Can003;
import com.android.launcher.can.Can005;
import com.android.launcher.can.Can1;
import com.android.launcher.can.Can105;
import com.android.launcher.can.Can139;
import com.android.launcher.can.Can180;
import com.android.launcher.can.Can181;
import com.android.launcher.can.Can187;
import com.android.launcher.can.Can1E5;
import com.android.launcher.can.Can203;
import com.android.launcher.can.Can207;
import com.android.launcher.can.Can247;
import com.android.launcher.can.Can29;
import com.android.launcher.can.Can2ee;
import com.android.launcher.can.Can2f3;
import com.android.launcher.can.Can2ff;
import com.android.launcher.can.Can305;
import com.android.launcher.can.Can30d;
import com.android.launcher.can.Can375;
import com.android.launcher.can.Can378;
import com.android.launcher.can.Can37d;
import com.android.launcher.can.Can380;
import com.android.launcher.can.Can39d;
import com.android.launcher.can.Can3e1;
import com.android.launcher.can.Can3ed;
import com.android.launcher.can.Can45;
import com.android.launcher.can.Can69;
import com.android.launcher.can.Canf8;
import com.android.launcher.handler.HandlerTiaoDadeng;
import com.android.launcher.kotlin.task.ScheduleTaskManager;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.service.task.AppUpdateTask;
import com.android.launcher.service.task.AttentionAidTask;
import com.android.launcher.service.task.AverageSpeedComputeTask;
import com.android.launcher.service.task.CarAlarmVolumeInitTask;
import com.android.launcher.service.task.CarMileComputeTask;
import com.android.launcher.service.task.CarTempTask;
import com.android.launcher.service.task.ComputeQtripTask;
import com.android.launcher.service.task.DayRunLightOpenTask;
import com.android.launcher.service.task.GetOilPercentTask;
import com.android.launcher.service.task.KeepOriginMeterTask;
import com.android.launcher.service.task.LauncherAfterUpdateTask;
import com.android.launcher.service.task.MaintainTask;
import com.android.launcher.service.task.SaveLauncherRunMilTask;
import com.android.launcher.service.task.ShowWarnMessageTask;
import com.android.launcher.service.task.UpdateCarRunTimeTask;
import com.android.launcher.service.task.UpdateCarSpeedUITask;
import com.android.launcher.service.task.UpdateRunDistanceTask;
import com.android.launcher.usbdriver.BenzLeftManager;
import com.android.launcher.usbdriver.MUsb1Receiver;
import com.android.launcher.usbdriver.UsbDataChannelManager;
import com.android.launcher.util.BY8302PCB;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SoundPlayer;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import dc.library.utils.event.MessageEvent;
import dc.library.auto.task.XTask;
import dc.library.auto.task.api.step.ConcurrentGroupTaskStep;
import dc.library.auto.task.core.step.impl.TaskCommand;
import dc.library.auto.task.logger.TaskLogger;
import dc.library.ui.progress.NumberProgressBar;

public class LivingService extends Service {
    private static final String TAG = LivingService.class.getSimpleName();
    //启动后的里程
    public static volatile float launchAfterMile = 0.0f;
    //启动后的平均时速
    public static volatile int launchAverageSpeed = 0;
    public static volatile boolean isPlayMusic = false;
    public static volatile long launcherTime = 0;
    public static volatile long carStartRunTime = 0;
    public static volatile float currentPercentOil;
    //启动到熄火这段时间 汽车跑的时间
    public static volatile long launchCarRunTime = 0L;
    //启动到熄火这段时间 汽车跑的里程
    public static volatile float launchCarRunMile = 0.0f;
    public static volatile float runStartComputeOil = 0.0f;
    //正在版本更新
    public static volatile boolean isDownloading = false;
    //可行驶距离
    public static volatile float distance = 0f;
    public static volatile int unitType;
    private View updateVersionFloating;
    private NumberProgressBar apkUpdateProgressBar;
    public static volatile int currentWaterTemp = 50;
    private UsbDataChannelManager usbDataChannelManager;
    private MUsb1Receiver mUsb1Receiver;
    private ScheduledExecutorService timeTaskExecutor;
    private ExecutorService taskExecutor;
    //启动时的油量
    private float launcherOil;
    private boolean isComputeQtrip;
    //允许操作原车仪表
    public static volatile boolean operateOriginMeter = false;
    //允许打开日行灯
    public static volatile boolean enableOpenDayRunLight = false;
    //发动机转速任务

    public LivingService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        LogUtils.printI(TAG, "onStartCommand---------");
        // 服务被系统 kill 掉之后重启进来的
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        taskExecutor = Executors.newCachedThreadPool();
        try {
            runInit();
            startTimeTask();
        } catch (Exception e) {
            TaskLogger.e("runInit " + e.getMessage());
        }
    }

    private void runInit() {
        //单独的任务，没有执行上的先后顺序. 例如：非核心数据的加载
        ConcurrentGroupTaskStep groupTaskStep = XTask.getConcurrentGroupTask();
        for (int i = 1; i <= 4; i++) {
            int stepIndex = i;
            groupTaskStep.addTask(XTask.getTask(new TaskCommand() {
                @Override
                public void run() {
                    switch (stepIndex) {
                        case 1:
                            launcherTime = System.currentTimeMillis();
                            launchCarRunTime = 0L;
                            launchCarRunMile = 0.0f;
                            runStartComputeOil = 0.0f;
                            currentWaterTemp = 50;
                            isComputeQtrip = false;
                            TaskLogger.i("LivingService --- onCreate --- App.isRestart = " + App.isRestart);
                            EventBus.getDefault().register(LivingService.this);
                            resetCanData();
                        case 2:
                            registerUsb1();
                            try {
                                Thread.sleep(1600);
                            } catch (InterruptedException e) {
                                TaskLogger.e("requestUsbPermission delay " + e.getMessage());
                            }
                            BenzLeftManager.init();
                        case 3:
                            closeBluetooth();
                        case 4:
                            SoundPlayer.init();
                    }
                }
            }));
        }
        ScheduleTaskManager.INSTANCE.runInitTask(groupTaskStep, System.currentTimeMillis());
    }

    //关闭蓝牙
    @SuppressLint("MissingPermission")
    private void closeBluetooth() {
        new Handler(Looper.getMainLooper()).postDelayed(() -> {
            try {
                BluetoothAdapter mBluetooth = BluetoothAdapter.getDefaultAdapter();
                if (mBluetooth != null) {
                    if (mBluetooth.isEnabled()) {
                        mBluetooth.disable();
                    }
                }
            } catch (Exception e) {
                TaskLogger.e("closeBluetooth" + e.getMessage());
            }
        }, 4000);
    }

    //开始定时任务
    private void startTimeTask() {
        timeTaskExecutor = Executors.newScheduledThreadPool(13);
        timeTaskExecutor.scheduleAtFixedRate(new MaintainTask(getApplicationContext()), 3, 10, TimeUnit.SECONDS);
        timeTaskExecutor.scheduleAtFixedRate(new UpdateCarSpeedUITask(getApplicationContext()), 4, 1, TimeUnit.SECONDS);
        //1小时检测一次
        timeTaskExecutor.scheduleAtFixedRate(new AttentionAidTask(getApplicationContext()), 1, 1, TimeUnit.HOURS);
        timeTaskExecutor.scheduleAtFixedRate(new SaveLauncherRunMilTask(getApplicationContext()), 60, 15, TimeUnit.SECONDS);
        timeTaskExecutor.scheduleAtFixedRate(new UpdateRunDistanceTask(getApplicationContext()), 3, 20, TimeUnit.SECONDS);
        timeTaskExecutor.scheduleAtFixedRate(new CarTempTask(getApplicationContext()), 3, 60 * 2, TimeUnit.SECONDS);
        timeTaskExecutor.scheduleAtFixedRate(new AppUpdateTask(getApplicationContext()), 6, 60, TimeUnit.SECONDS);
        timeTaskExecutor.scheduleAtFixedRate(new CarMileComputeTask(getApplicationContext()), 4, 5, TimeUnit.SECONDS);
        //5分钟计算一次剩余油量
        timeTaskExecutor.scheduleAtFixedRate(new GetOilPercentTask(getApplicationContext()), 60 * 5, 60 * 5, TimeUnit.SECONDS);
        //第10个定时任务
        timeTaskExecutor.scheduleAtFixedRate(new ShowWarnMessageTask(getApplicationContext()), 8, 4, TimeUnit.SECONDS);
        timeTaskExecutor.scheduleAtFixedRate(new UpdateCarRunTimeTask(getApplicationContext()), 3, 1, TimeUnit.SECONDS);
        timeTaskExecutor.scheduleAtFixedRate(new AverageSpeedComputeTask(getApplicationContext()), 3, 3, TimeUnit.SECONDS);
        timeTaskExecutor.scheduleAtFixedRate(new LauncherAfterUpdateTask(getApplicationContext()), 5, 10, TimeUnit.SECONDS);
    }

    private void requestUsbPermission() {
        new Handler().postDelayed(BenzLeftManager::init, 1600);
    }

    public void registerUsb1() {
        mUsb1Receiver = new MUsb1Receiver();
        //2.创建intent-filter对象
        IntentFilter filter = new IntentFilter();
        filter.addAction("com.car.left.usb1");
        //3.注册广播接收者
        registerReceiver(mUsb1Receiver, filter);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        try {
            if (event.type == MessageEvent.Type.CURRENT_ML) {
                currentPercentOil = (float) event.data;
            } else if (event.type == MessageEvent.Type.LAUNCHER_OIL) {
                if (!isComputeQtrip) {
                    isComputeQtrip = true;
                    launcherOil = (float) event.data;
                    currentPercentOil = launcherOil;
                    taskExecutor.execute(new ComputeQtripTask(getApplicationContext(), launcherOil));
                }
            } else if (event.type == MessageEvent.Type.UPDATE_WATER_TEMP) { //水温
                if (event.data != null) {
                    currentWaterTemp = (int) event.data;
                }
            } else if (event.type == MessageEvent.Type.APK_DOWNLOAD_SHOW) {
                showApkDownloadView();
            } else if (event.type == MessageEvent.Type.APK_DOWNLOAD_PROGRESS) {
                int progress = (int) event.data;
                updateDownloadProgress(progress);
            } else if (event.type == MessageEvent.Type.APK_INSTALL) {
                installApk();
            } else if (event.type == MessageEvent.Type.USB_REGISTER_SUCCESS) {
                if (usbDataChannelManager == null) {
                    usbDataChannelManager = new UsbDataChannelManager();
                }
                if (usbDataChannelManager.isRunning()) {
                    LogUtils.printI(TAG, "usbDataChannelManager---isRunning");
                    return;
                }
                usbDataChannelManager.startRun(getApplicationContext());
            } else if (event.type == MessageEvent.Type.BLUETOOTH_DISCONNECTED) {
                LivingService.isPlayMusic = false;
            } else if (event.type == MessageEvent.Type.MUSIC_STOP) {
                LivingService.isPlayMusic = false;
            } else if (event.type == MessageEvent.Type.DISABLE_ORIGIN_METER) {
                if (!operateOriginMeter) {
                    taskExecutor.execute(new KeepOriginMeterTask(getApplicationContext()));
                }
            } else if (event.type == MessageEvent.Type.OPEN_DAY_RUN_LIGHT) {
                enableOpenDayRunLight = (Boolean) event.data;
                taskExecutor.execute(new DayRunLightOpenTask(getApplicationContext()));
            } else if (event.type == MessageEvent.Type.USB_INTERRUPT) {
                try {
                    AlertVo alertVo = new AlertVo();
                    alertVo.setAlertImg(R.drawable.ic_usb_interrupt);
                    alertVo.setAlertMessage(getResources().getString(R.string.usb_interrupt_hint));
                    MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_WARN_INFO);
                    messageEvent.data = alertVo;
                    EventBus.getDefault().post(messageEvent);
                    unregisterReceiver(mUsb1Receiver);
                    new Handler(Looper.getMainLooper()).postDelayed(() -> {
                        try {
                            usbInterruptResetRegister();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }, 3000);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.UPDATE_ALARM_VOLUME) {
                taskExecutor.execute(new CarAlarmVolumeInitTask(getApplicationContext()));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //USB通信中断， 重新注册
    private void usbInterruptResetRegister() {
        LogUtils.printI(TAG, "usbInterruptResetRegister---");
        resetCanData();
        registerUsb1();
        requestUsbPermission();
    }

    private void installApk() {
        closeVersionUpdateFloating();
        try {
            LogUtils.printI(TAG, "------完成 安装------------");
            Intent intent = new Intent("xy.android.silent.install");
            intent.putExtra("ins_apk_pkgname", "com.android.launcher");
            intent.putExtra("ins_apk_pathname", "/storage/emulated/0/left.apk");
            intent.putExtra("force_apk_state", 1);
            sendBroadcast(intent);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            isDownloading = false;
        }
    }

    private void updateDownloadProgress(int progress) {
        if (updateVersionFloating != null) {
            if (apkUpdateProgressBar != null) {
                apkUpdateProgressBar.setProgress(progress);
            }
        }
    }

    private void showApkDownloadView() {
        try {
            isDownloading = true;
            closeVersionUpdateFloating();
            WindowManager.LayoutParams params;
            WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
            updateVersionFloating = LayoutInflater.from(this).inflate(R.layout.layout_update_apk, null);
            apkUpdateProgressBar = updateVersionFloating.findViewById(R.id.progressBar);
            params = new WindowManager.LayoutParams(
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.WRAP_CONTENT,
                    WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY,
                    WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE | WindowManager.LayoutParams.FLAG_DIM_BEHIND,
                    PixelFormat.TRANSLUCENT);
            params.gravity = Gravity.CENTER;
            params.dimAmount = 0.3f;
            windowManager.addView(updateVersionFloating, params);
        } catch (Exception e) {
            e.printStackTrace();
            isDownloading = false;
        }
    }

    private void closeVersionUpdateFloating() {
        if (updateVersionFloating != null) {
            try {
                WindowManager windowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
                windowManager.removeView(updateVersionFloating);
                updateVersionFloating = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onDestroy() {
        launcherTime = 0;
        launchAfterMile = 0.0f;
        launchAverageSpeed = 0;
        isComputeQtrip = false;
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        closeVersionUpdateFloating();
        isDownloading = false;
        try {
            unregisterReceiver(mUsb1Receiver);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (timeTaskExecutor != null) {
                timeTaskExecutor.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (FuncUtil.serialHelperttl != null) {
                FuncUtil.serialHelperttl.close();
                FuncUtil.serialHelperttl = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        BY8302PCB.stop();
        SoundPlayer.destroy();
        try {
            if (FuncUtil.serialHelperttl3 != null) {
                FuncUtil.serialHelperttl3.close();
                FuncUtil.serialHelperttl3 = null;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        if (usbDataChannelManager != null) {
            usbDataChannelManager.close();

        }
        resetCanData();
        super.onDestroy();
        Log.i("LivingService", "onDestroy---------");
        App.runningAverageSpeed = 0.0f;
        MeterActivity.mNavEntity = null;
    }

    private void resetCanData() {
        try {
            Can003.lastData = "";
            Can105.engineRunning = false;
            Can203.isRunning = false;
            Can380.lastCheckOilLevelData = "";
            Can305.lastData = "";
            Can180.lastData = "";
            Can181.lastData = "";
            Can380.lastData = "";
            Can1E5.lastData = "";
            Can2ff.lastData = "";
            Can1.lastStatus = "";
            Can2ee.lastRadarSwitchState = "";
            Can2ee.lastRadarData = "";
            Can2f3.lastData = "";
            Can29.lastData = "";
            Can45.lastData = "";
            Can69.lastData = "";
            Can139.lastData = "";
            Can203.lastData = "";
            Can247.lastData = "";
            Can375.lastData = "";
            Can375.lastSafetyBeltFlag = "";
            Can378.lastData = "";
            Canf8.lastData = "";
            Can30d.lastData = "";
            Can30d.waterlist.clear();
            Can005.lastData = "";
            Can380.lastData = "";
            Can139.lastESPStatus = "";
            Can139.lastUpdateTempDate = 0L;
            Can3e1.currentDriveModeStatus = "";
            Can187.lastData = "";
            Can3ed.lastData = "";
            Can207.lastData = "";
            Can37d.lastData = "";
            GetOilPercentTask.clear();
            HandlerTiaoDadeng.lastData = "";
            Can39d.clear();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void startLivingService(Context context) {
        try {
            App.livingServerStop = false;
            Intent intent = new Intent(context, LivingService.class);
            context.startService(intent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopLivingService(Context context) {
        try {
            App.livingServerStop = true;
            Intent intent = new Intent(context, LivingService.class);
            context.stopService(intent);
        } catch (Exception e) {
            e.printStackTrace();
            if (e.getMessage() != null) {
                Log.e("LivingService", e.getMessage());
            }
        }
    }
}
