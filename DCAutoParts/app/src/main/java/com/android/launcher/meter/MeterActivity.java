package com.android.launcher.meter;

import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.amap.api.maps.AMapException;
import com.amap.api.maps.offlinemap.OfflineMapManager;
import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.base.ActivityBase;
import com.android.launcher.base.IView;
import com.android.launcher.can.Can380;
import com.android.launcher.cansender.Can3DCSender;
import com.android.launcher.cansender.CanFDSender;
import com.android.launcher.cruisecontrol.CruiseControlDataSender;
import com.android.launcher.dialog.MapDownloadDialog;
import com.android.launcher.dialog.MessageDialog;
import com.android.launcher.dialog.ResetDialog;
import com.android.launcher.entity.NavEntity;
import com.android.launcher.menu.MenuHomeFragment;
import com.android.launcher.meter.view.RadarView;
import com.android.launcher.service.LivingService;
import com.android.launcher.service.task.StartMeterAnimationTask;
import com.android.launcher.type.CarType;
import com.android.launcher.type.SteerWheelKeyType;
import com.android.launcher.type.UnitType;
import com.android.launcher.usbdriver.SendHelperUsbToRight;
import com.android.launcher.util.ACache;
import com.android.launcher.util.AppVersionUtils;
import com.android.launcher.util.DensityUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;
import com.android.launcher.vo.AlertVo;
import com.lxj.xpopup.XPopup;
import com.lxj.xpopup.core.BasePopupView;
import com.lxj.xpopup.enums.PopupAnimation;
import com.lxj.xpopup.interfaces.SimpleCallback;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;

import compat.CustomDialog;
import test.DataTestManager;

/**
 * @description: 仪表盘页面
 * @createDate: 2023/6/20
 */
public class MeterActivity extends ActivityBase<MeterPresenter> implements IView, OfflineMapManager.OfflineMapDownloadListener {

    public static boolean doorIsShowStatus = false;

    //消息数量
    public static int messageNumber = 0;

    public static CopyOnWriteArraySet<AlertVo> infoList = new CopyOnWriteArraySet<>();

    //行驶方向
    public static volatile String drivingDirection;

    public static NavEntity mNavEntity = null;

    //是否是夜间模式
    public static volatile boolean isNightMode;

    public CustomDialog customDialog;

    public ACache aCache = ACache.get(App.getGlobalContext());


    private CruiseControlDataSender mCruiseControlDataSender = new CruiseControlDataSender();


    private CanFDSender canFDSender = new CanFDSender();
    private Can3DCSender can3DCSender = new Can3DCSender();

    public static boolean isCruiseControlStop = false;

    public static volatile int carType = CarType.S300.ordinal();

    public static int unitType = UnitType.KM.ordinal();

    public static volatile MenuType currentMenuType = MenuType.HOME;
    public static volatile MeterFragmentType currentFragmentType = MeterFragmentType.CLASSIC;

    private List<AlertVo> messageList = new ArrayList<>();
    private AlertVo currentMessage;
    private MessageDialog warnMessageDialog;

    protected boolean chassisRunning = false;

    private RadarView radarView;
    private MenuHomeFragment menuHomeFragment;
    private Fragment meterFragment;

    private ResetDialog launcherAfterResetDialog;
    private ResetDialog afterRecoveryDialog;
    private MaintainFragment maintainFragment;
    private int meterStyle;
    private MapDownloadDialog mapDownloadDialog;
    private OfflineMapManager amapManager;

    //下载的是否是城市还是省份
    private boolean isDownloadCity;
    private int downloadCitySize;
    private int downloadCityNumber;
    private MapNavMeterFragment navFragment;

    @Override
    protected void disposeMessageEvent(MessageEvent event) {
        try {
            if (event.type == MessageEvent.Type.USB_CONNECTED) {
                LogUtils.printI(TAG, "disposeMessageEvent--------event=" + event.type + ", data=" + event.data);
                sendCruiseControl();
                if (MeterActivity.carType == CarType.S600.ordinal()) {
                    if (can3DCSender != null) {
                        can3DCSender.send();
                    }
                }
            } else if (event.type == MessageEvent.Type.SET_HEADREST_DOWN) { //头枕放倒,预留功能
                try {
                    canFDSender.send();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.RESUME_HEADREST_DOWN) { //恢复头枕放倒
                LogUtils.printI(MeterActivity.class.getSimpleName(), "message----type=" + event.type.name() + ", data=" + event.data);
                canFDSender.resume();
            } else if (event.type == MessageEvent.Type.UPDATE_DRIVE_MODE) { //驾驶模式切换
                if (canFDSender != null) {
                    try {
                        String driveMode = (String) event.data;
                        canFDSender.setDriveMode(driveMode);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.OPEN_SEAT_FUNCTION_ADJUSTMENT) {
                if (canFDSender != null) {
                    try {
                        Boolean isOpen = (Boolean) event.data;
                        canFDSender.setSeatAdjustment(isOpen);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.OPEN_O_N_KEY) {
                if (canFDSender != null) {
                    try {
                        Boolean isOpen = (Boolean) event.data;
                        canFDSender.setONKey(isOpen);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.SAFE_BELT_SWITCH) { //安全带开关
                try {
                    LogUtils.printI(MeterActivity.class.getSimpleName(), "message----type=" + event.type.name() + ", data=" + event.data);
                    mCruiseControlDataSender.updateSafeBeltFrapStatus((Boolean) event.data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.SET_CAN35D_D5) {
                try {
                    LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                    String can35dD5 = (String) event.data;
                    if (mCruiseControlDataSender != null) {
                        mCruiseControlDataSender.setCan35dD5(can35dD5);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.SET_AUTO_LOCK) {
                if (event.data != null) {
                    try {
                        LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                        String status = (String) event.data;
                        setupAutoLock(status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.SET_CAR_ASSIST) {
                if (event.data != null) {
                    try {
                        LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                        String status = (String) event.data;
                        setCarAssist(status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.SET_CAR_BG_LIGHTING) {
                if (event.data != null) {
                    try {
                        LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                        String status = (String) event.data;
                        setCarBgLighting(status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.SET_CAR_ENVIR_LIGHTING) {
                if (event.data != null) {
                    try {
                        LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                        String status = (String) event.data;
                        setCarEnvirLighting(status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.SET_REAR_HATCH_COVER) {
                if (event.data != null) {
                    try {
                        LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                        String status = (String) event.data;
                        if (mCruiseControlDataSender != null) {
                            mCruiseControlDataSender.setRearHatchCover(status);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.SET_CAR_INNER_LIGHTING) {
                if (event.data != null) {
                    try {
                        LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                        String status = (String) event.data;
                        setCarInnerLighting(status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.SET_CAR_OUTER_LIGHTING) {
                if (event.data != null) {
                    try {
                        LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                        String status = (String) event.data;
                        setCarOuterLighting(status);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            } else if (event.type == MessageEvent.Type.UPDATE_CAR_TYPE_STATUS) {
                try {
                    if (mCruiseControlDataSender != null) {
                        mCruiseControlDataSender.setSendPauseStatus((boolean) event.data);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.UPDATE_WARN_INFO) {
                if (event.data instanceof AlertVo) {
                    disposeWarnMessage(event);
                }
            } else if (event.type == MessageEvent.Type.STEER_WHEEL_TYPE) {
                if (event.data != null) {
                    if (event.data != null) {
                        int keyType = (int) event.data;
                        if (keyType == SteerWheelKeyType.KEY_BACK.ordinal()) {
                            onBack();
                        } else if (keyType == SteerWheelKeyType.KEY_OK.ordinal()) {
                            onOK();
                        } else if (keyType == SteerWheelKeyType.KEY_LEFT.ordinal()) {
                            onLeft();
                        } else if (keyType == SteerWheelKeyType.KEY_RIGHT.ordinal()) {
                            onRight();
                        } else if (keyType == SteerWheelKeyType.KEY_UP.ordinal()) {
                            onUp();
                        } else if (keyType == SteerWheelKeyType.KEY_DOWN.ordinal()) {
                            onDown();
                        }
                    }

                    int keyType = (int) event.data;
                    if (keyType == SteerWheelKeyType.KEY_BACK.ordinal()) {
                        try {
                            closeMessageDialog();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (event.type == MessageEvent.Type.CAN379_VIEW_REMOVE) {
                closeChassisShowDialog();
            } else if (event.type == MessageEvent.Type.UPDATE_LEFT_RADAR) { //左侧雷达
                try {
                    LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                    if (event.data != null) {
                        String status = (String) event.data;
                        updateLeftRadar(status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.UPDATE_RIGHT_RADAR) { //右侧雷达
                try {
                    LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                    if (event.data != null) {
                        String status = (String) event.data;
                        updateRightRadar(status);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.SHOW_RESTART_APP_DIALOG) {
                showRestartAppDialog();
            } else if (event.type == MessageEvent.Type.TO_METER_HOME) {
                try {
                    FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                    if (fragmentTransaction != null) {
                        if (menuHomeFragment != null) {
                            fragmentTransaction.remove(menuHomeFragment);
                            menuHomeFragment = null;
                        }
                        if (navFragment != null) {
                            fragmentTransaction.remove(navFragment);
                            navFragment = null;
                        }
                        if (maintainFragment != null) {
                            fragmentTransaction.remove(maintainFragment);
                            maintainFragment = null;
                        }
                        if (meterFragment != null) {
                            fragmentTransaction.show(meterFragment);
                        }
                        fragmentTransaction.commit();
                    }
                    if (meterStyle == MeterFragmentType.CLASSIC.getValue()) {
                        currentFragmentType = MeterFragmentType.CLASSIC;
                        bottomStatusView.showOilLineView(false);
                        bottomStatusView.showWaterTempLineView(false);
                    } else if (meterStyle == MeterFragmentType.Tech.getValue()) {
                        currentFragmentType = MeterFragmentType.Tech;
                        bottomStatusView.showOilLineView(false);
                        bottomStatusView.showWaterTempLineView(false);
                    } else if (meterStyle == MeterFragmentType.SPORT.getValue()) {
                        currentFragmentType = MeterFragmentType.SPORT;
                        bottomStatusView.showOilLineView(true);
                        bottomStatusView.showWaterTempLineView(true);
                    }

                    messageList.clear();

                    closeMessageDialog();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.RESTART_SERVICE) {
                LivingService.stopLivingService(getApplicationContext());
                new Handler(Looper.getMainLooper()).postDelayed(() -> LivingService.startLivingService(getApplicationContext()), 3000);

            } else if (event.type == MessageEvent.Type.START_MAP_NAV) {
                if (event.data instanceof NavEntity) {
                    NavEntity navEntity = (NavEntity) event.data;
                    startMapNav(navEntity);
                }
            } else if (event.type == MessageEvent.Type.STOP_MAP_NAV) {
                mNavEntity = null;
                closeNavFragmentToMeter();
            } else if (event.type == MessageEvent.Type.DOWNLOAD_OFFLINE_MAP_CITY) {
                if (event.data instanceof String) {
                    String cityName = (String) event.data;
                    showDownloadOfflineMapDialog(cityName, true);
                }
            } else if (event.type == MessageEvent.Type.DOWNLOAD_OFFLINE_MAP_PROVINCE) {
                if (event.data instanceof String) {
                    String province = (String) event.data;
                    showDownloadOfflineMapDialog(province, false);
                }
            } else if (event.type == MessageEvent.Type.DOWNLOAD_OFFLINE_MAP_PROVINCE_CITY_SIZE) {
                if (event.data instanceof Integer) {
                    downloadCitySize = (Integer) event.data;
                }
            } else if (event.type == MessageEvent.Type.CANCEL_DOWNLOAD_OFFLINE_MAP) {
                try {
                    if (mapDownloadDialog != null) {
                        mapDownloadDialog.dismiss();
                        mapDownloadDialog = null;
                        amapManager.stop();
                        amapManager.destroy();
                        amapManager = null;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.SHOW_WARN_MESSAGE) {
                if (messageList != null && !messageList.isEmpty()) {
                    showMessageDialog(messageList.remove(0));
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeNavFragmentToMeter() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (navFragment != null) {
            fragmentTransaction.remove(navFragment);
            navFragment = null;
        }
        if(meterFragment!=null){
            fragmentTransaction.show(meterFragment);
        }
        fragmentTransaction.commit();
        bottomStatusView.showOilLineView(false);
        bottomStatusView.showWaterTempLineView(false);
    }

    private void showDownloadOfflineMapDialog(String cityName, boolean isCity) {

        isDownloadCity = isCity;
        //构造OfflineMapManager对象
        try {
            if (amapManager == null) {
                amapManager = new OfflineMapManager(this, this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        mapDownloadDialog = new MapDownloadDialog(this, cityName, v -> {
            amapManager.stop();
            mapDownloadDialog.dismiss();
            mapDownloadDialog = null;
        });

        new XPopup.Builder(this)
                .dismissOnTouchOutside(false)
                .dismissOnBackPressed(false)
                .hasStatusBar(false)
                .hasShadowBg(true)
                .hasNavigationBar(false)
                .asCustom(mapDownloadDialog).show();

        try {
            if (isCity) {
                amapManager.downloadByCityName(cityName);
            } else {
                amapManager.downloadByProvinceName(cityName);
                downloadCityNumber = 0;
            }
        } catch (AMapException e) {
            e.printStackTrace();
        }

        try {
            messageList.clear();
            if (warnMessageDialog != null) {
                warnMessageDialog.dismiss();
                warnMessageDialog = null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void startMapNav(NavEntity entity) {
        mNavEntity = entity;
//        if(currentFragmentType == MeterFragmentType.CLASSIC || currentFragmentType == MeterFragmentType.Tech || currentFragmentType == MeterFragmentType.SPORT){
//            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.TO_NAV_MENU));
//        }

        toNavFragment();
        currentFragmentType = MeterFragmentType.MAP;

        messageList.clear();
        if (warnMessageDialog != null) {
            warnMessageDialog.dismiss();
            warnMessageDialog = null;
        }
    }


    //关闭底盘升降对话框
    private void closeChassisShowDialog() {
        LogUtils.printI(TAG, "closeChassisShowDialog----currentMessage=" + currentMessage);
        if (currentMessage != null) {
            if (currentMessage.getType() == AlertVo.Type.CHASSIS) {
                closeMessageDialog();
            }
        }
        if (messageList == null || messageList.isEmpty()) {
            return;
        }

        for (int i = 0; i < messageList.size(); i++) {
            AlertVo alertVo = messageList.get(i);
            if (alertVo.getType() == AlertVo.Type.CHASSIS) {
                messageList.remove(i);
                break;
            }
        }
    }

    private synchronized void disposeWarnMessage(MessageEvent event) {
        AlertVo alertVo = (AlertVo) event.data;
        if (alertVo != null) {
            LogUtils.printI(TAG, "disposeWarnMessage---alertVo=" + alertVo.getAlertMessage());
            if (alertVo.getType() == AlertVo.Type.DOOR) {
                disposeDoorShow(alertVo);
            } else if (alertVo.getType() == AlertVo.Type.CHASSIS) {
                disposeChassisShow(alertVo);
            } else {
                messageList.add(alertVo);
//                showMessageDialog(messageList.remove(0));
            }
        }
    }

    //底盘升降显示
    private void disposeChassisShow(AlertVo chassisMessage) {
        LogUtils.printI(TAG, "disposeChassisShow---doorMessage=" + chassisMessage);

        try {
            //当前显示的是车门对话框的情况
            boolean currentIsChassis = false;
            if (currentMessage != null) {
                if (currentMessage.getType() == AlertVo.Type.CHASSIS) {
                    currentIsChassis = true;
                    if (warnMessageDialog != null) {
                        warnMessageDialog.updateMessage(chassisMessage.getAlertMessage());
                        warnMessageDialog.updateImg(chassisMessage.getAlertImg());
                    }
                }
            }
            LogUtils.printI(TAG, "disposeChassisShow---currentIsChassis=" + currentIsChassis);
            if (!currentIsChassis) {
                for (int i = 0; i < messageList.size(); i++) {
                    AlertVo message = messageList.get(i);
                    if (message.getType() == AlertVo.Type.DOOR) {
                        messageList.remove(i);
                        break;
                    }
                }
                messageList.add(0, chassisMessage);
                closeMessageDialog();
            }
            if (MeterActivity.carType == CarType.S600.ordinal()) {
                timingCloseChassisLift();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void timingCloseChassisLift() {
        try {
            if (chassisRunning) {
                return;
            }
            chassisRunning = true;
            new Handler().postDelayed(() -> {
                closeMessageDialog();
                chassisRunning = false;
            }, 6000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //车门显示
    private void disposeDoorShow(AlertVo doorMessage) {
        LogUtils.printI(TAG, "disposeDoorShow---doorMessage=" + doorMessage);
        try {
            //当前显示的是车门对话框的情况
            boolean currentIsDoor = false;
            if (currentMessage != null) {
                if (currentMessage.getType() == AlertVo.Type.DOOR) {
                    currentIsDoor = true;
                    if (!doorMessage.isShow()) { //不显示
                        closeMessageDialog();
                    } else {
                        if (warnMessageDialog != null) {
                            warnMessageDialog.updateImg(doorMessage.getAlertImg());
                        }
                    }
                }
            }
            LogUtils.printI(TAG, "disposeDoorShow---currentIsDoor=" + currentIsDoor);
            if (!currentIsDoor) {
                boolean isAdd = false;
                //车门消息是否已经添加
                int addIndex = -1;
                for (int i = 0; i < messageList.size(); i++) {
                    AlertVo message = messageList.get(i);
                    if (message.getType() == AlertVo.Type.DOOR) {
                        messageList.remove(i);
                        break;
                    }
                }
                LogUtils.printI(TAG, "disposeDoorShow---isAdd=" + isAdd + ", addIndex=" + addIndex);
                if (doorMessage.isShow()) {
                    messageList.add(doorMessage);
                }
            }
//            if (currentMessage == null && messageList.size() > 0) {
//                showMessageDialog(messageList.remove(0));
//            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void closeMessageDialog() {
        if (warnMessageDialog != null) {
            warnMessageDialog.dismiss();
            warnMessageDialog = null;
        }
    }

    private void showMessageDialog(AlertVo message) {
        if (warnMessageDialog != null) {
            return;
        }
        if (mapDownloadDialog != null) {
            return;
        }

        currentMessage = message;

        int offsetX = 0;
        int offsetY = 0;

        int dialogWidth = -DensityUtil.dip2px(getApplicationContext(), 380);
        int dialogHeight = DensityUtil.dip2px(getApplicationContext(), 260);

        PopupAnimation popupAnimation = PopupAnimation.ScaleAlphaFromCenter;

//            offsetX = -DensityUtil.dip2px(getApplicationContext(), 440);
//            offsetY = -DensityUtil.dip2px(getApplicationContext(), 120);
//            dialogWidth = DensityUtil.dip2px(getApplicationContext(), 260);
//            dialogHeight = DensityUtil.dip2px(getApplicationContext(), 240);
//            popupAnimation = PopupAnimation.ScrollAlphaFromLeftTop;

        warnMessageDialog = new MessageDialog(this, message, dialogWidth, dialogHeight);

        new XPopup.Builder(this).hasShadowBg(false).hasStatusBar(false)
                .popupAnimation(popupAnimation)
                .isViewMode(true)
                .animationDuration(200)
                .customHostLifecycle(getLifecycle())
                .offsetX(offsetX)
                .offsetY(offsetY)
                .setPopupCallback(new SimpleCallback() {
                    @Override
                    public void onDismiss(BasePopupView popupView) {
                        super.onDismiss(popupView);
                        warnMessageDialog = null;
                        currentMessage = null;
                        if (messageList.isEmpty()) {
                            return;
                        }
//                        showMessageDialog(messageList.remove(0));
                    }
                })
                .asCustom(warnMessageDialog)
                .show();
    }


    @Override
    protected void setupData() {

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        LogUtils.printI(TAG, "onNewIntent------");
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        if (messageList != null) {
            messageList.clear();
        }

        unitType = SPUtils.getInt(this, SPUtils.SP_UNIT_TYPE, UnitType.getDefaultType());


        radarView = findViewById(R.id.radarView);
        radarView.setVisibility(View.INVISIBLE);

        currentMenuType = MenuType.HOME;


        meterStyle = SPUtils.getInt(this.getApplicationContext(), SPUtils.SP_METER_STYLE, MeterFragmentType.CLASSIC.getValue());
        LogUtils.printI(TAG, "initView---meterStyle=" + meterStyle);
        switchMeterFragment(meterStyle);


        App.isRestart = false;

        App.originalSpeed = 0;
        App.currentMeterActivity = true;
        try {
            App.speedAdjustValue = SPUtils.getFloat(this.getApplicationContext(), SPUtils.SPEED_ADJUST, 1.00f);
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            if (EventBus.getDefault().isRegistered(this)) {
                EventBus.getDefault().unregister(this);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        EventBus.getDefault().register(this);

        try {
            LogUtils.printI(MeterActivity.class.getSimpleName(), "apk_version=" + AppVersionUtils.getVersionName(this.getApplicationContext()));
        } catch (Exception e) {
            e.printStackTrace();
        }


        AudioManager audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);

        try {
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int currentVolume = SPUtils.getInt(App.getGlobalContext(), SPUtils.SP_CAR_ALARM_VOLUME, 0);
            if (currentVolume == 0) {
                currentVolume = maxVolume;
            }
            LogUtils.printI(MeterActivity.class.getSimpleName(), "maxVolume=" + maxVolume + ", currentVolume=" + currentVolume);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }


        try {
            carType = SPUtils.getInt(this.getApplicationContext(), SPUtils.SP_CAR_TYPE, CarType.S500.ordinal());
            LogUtils.printI(MeterActivity.class.getSimpleName(), "carType=" + carType);

        } catch (Exception e) {
            e.printStackTrace();
        }

        doorIsShowStatus = false;

        DataTestManager.start();

    }


    @Override
    protected void onPause() {
        super.onPause();
        App.currentMeterActivity = false;
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (App.livingServerStop) {
            LivingService.startLivingService(this.getApplicationContext());
        }
        new Handler(Looper.getMainLooper()).postDelayed(new StartMeterAnimationTask(getApplicationContext()), 3000);

    }

    //切换表盘
    private void switchMeterFragment(int fragmentType) {
        try {
            LogUtils.printI(TAG, "switchFragment----fragmentType=" + fragmentType);

            FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
            if (meterFragment != null) {
                fragmentTransaction.remove(meterFragment).commit();
            }

            Fragment fragment = getFragment(fragmentType);

            if (fragment != null) {
                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.replace(R.id.fragmentContainerView, fragment).commit();
            }
            meterFragment = fragment;

            if (fragmentType == MeterFragmentType.Tech.getValue()) {
                speedCenterView.setVisibility(View.INVISIBLE);
            } else {
                speedCenterView.setVisibility(View.VISIBLE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Fragment getFragment(int fragmentType) {
        MeterFragmentBase fragment = null;
        if (fragmentType == MeterFragmentType.SPORT.getValue()) {
            fragment = new SportMeterFragment();
        } else if (fragmentType == MeterFragmentType.CLASSIC.getValue()) {
            fragment = new ClassicMeterFragment();
        } else if (fragmentType == MeterFragmentType.Tech.getValue()) {
            fragment = new TechMeterFragment();
        }
        return fragment;
    }


    /**
     * @description: 发送定速巡航
     * @createDate: 2023/5/30
     */
    private void sendCruiseControl() {
        LogUtils.printI(MeterActivity.class.getSimpleName(), "开启定速巡航-----");
        mCruiseControlDataSender.startSendData();
    }

    @Override
    protected void onStop() {
        super.onStop();
        try {
            if (amapManager != null) {
                amapManager.stop();
                amapManager.destroy();
                amapManager = null;
            }
            if (mapDownloadDialog != null) {
                mapDownloadDialog.dismiss();
                mapDownloadDialog = null;
            }

            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.CLOSE_MAP_NAV));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        closeMessageDialog();


        super.onDestroy();
        try {
            App.currentMeterActivity = false;
            LogUtils.printI(MeterActivity.class.getSimpleName(), "------onDestroy--------");
            EventBus.getDefault().unregister(this);
            closeDialog();

        } catch (Exception e) {
            e.printStackTrace();
        }
        if (mCruiseControlDataSender != null) {
            mCruiseControlDataSender.release();
        }

//        if( FuncUtil.serialHelperttl!=null){
//            FuncUtil.serialHelperttl.close();
//            FuncUtil.serialHelperttl = null;
//        }
        isCruiseControlStop = false;
    }

    public void closeDialog() {
        if (customDialog != null && customDialog.isShowing() && isValidActivity()) {
            customDialog.dismiss();
        }
    }


    public void showRestartAppDialog() {
        new Handler(Looper.getMainLooper()).postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                customDialog = new CustomDialog(MeterActivity.this, R.layout.dialog_custom, () -> {

                }, () -> {

                });
                customDialog.show();
            }
        });

    }


    @Override
    protected void onResume() {
        super.onResume();

        String restartAppFlag = aCache.getAsString("restartApp");
        LogUtils.printI(TAG, "onResume---restartAppFlag=" + restartAppFlag);
        if (!TextUtils.isEmpty(restartAppFlag) && restartAppFlag.equals("restartApp")) {
            aCache.put("restartApp", "");
        }
    }


    private boolean isValidActivity() {
        if (!isDestroyed() && !isFinishing()) {
            return true;
        }
        return false;
    }


    public void setupAutoLock(String status) {
        if (mCruiseControlDataSender != null) {
            mCruiseControlDataSender.setAutoLock(status);
        }
    }

    public void setCarAssist(String status) {
        if (mCruiseControlDataSender != null) {
            mCruiseControlDataSender.updateAssist(status);
        }
    }

    public void setCarBgLighting(String status) {
        if (mCruiseControlDataSender != null) {
            mCruiseControlDataSender.setLighting(status);
        }
    }

    public void setCarEnvirLighting(String status) {
        if (mCruiseControlDataSender != null) {
            mCruiseControlDataSender.setEnvirLighting(status);
        }
    }

    public void setCarInnerLighting(String status) {
        if (mCruiseControlDataSender != null) {
            mCruiseControlDataSender.setInnerLighting(status);
        }
    }

    public void setCarOuterLighting(String status) {
        if (mCruiseControlDataSender != null) {
            mCruiseControlDataSender.setOuterLighting(status);
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == 111) {
            keyCode = 99;
        }
        String sendkey = "AABB" + keyCode + "CCDD";
        SendHelperUsbToRight.handler(sendkey.toUpperCase());
        Log.i("activityMeter" + "touch", keyCode + "----------");
        return true;
    }

    public void startTestEngineOil() {
        if (can3DCSender != null) {
            Can380.lastData = "";
            can3DCSender.testEngineOil();
        }
    }

    /**
     * @description: 更新右侧雷达
     * @createDate: 2023/6/24
     */
    public void updateRightRadar(String status) {
        radarView.setRightRadarLevel(status);
    }

    /**
     * @description: 更新左侧雷达
     * @createDate: 2023/6/24
     */
    public void updateLeftRadar(final String status) {
        radarView.setLeftRadarLevel(status);
    }

    public void toMenuHome() {

        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (menuHomeFragment == null) {
            menuHomeFragment = new MenuHomeFragment();
            Bundle bundle = new Bundle();

            if (currentFragmentType == MeterFragmentType.CLASSIC) {
                bundle.putInt("position", 2);
            } else if (currentFragmentType == MeterFragmentType.SPORT) {
                bundle.putInt("position", 1);
            } else if (currentFragmentType == MeterFragmentType.Tech) {
                bundle.putInt("position", 0);
            } else if (currentFragmentType == MeterFragmentType.MAP) {
                bundle.putInt("position", 3);
            } else if (currentFragmentType == MeterFragmentType.MAINTAIN) {
                bundle.putInt("position", 4);
            }
            menuHomeFragment.setArguments(bundle);
            fragmentTransaction.add(R.id.fragmentContainerView, menuHomeFragment);
        }
        fragmentTransaction.show(menuHomeFragment);
        if (meterFragment != null) {
            fragmentTransaction.hide(meterFragment);
        }
        fragmentTransaction.commit();
        bottomStatusView.showOilLineView(true);
        bottomStatusView.showWaterTempLineView(true);
    }


    @Override
    protected void onDown() {

        if (launcherAfterResetDialog != null && launcherAfterResetDialog.isShow()) {
            return;
        }
        if (afterRecoveryDialog != null && afterRecoveryDialog.isShow()) {
            return;
        }

        if (currentFragmentType == MeterFragmentType.CLASSIC || currentFragmentType == MeterFragmentType.Tech
                || currentFragmentType == MeterFragmentType.SPORT || currentFragmentType == MeterFragmentType.MAP) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.ON_DOWN_KEY));
        }
    }

    @Override
    protected void onUp() {
        if (afterRecoveryDialog != null && afterRecoveryDialog.isShow()) {
            return;
        }
        if (launcherAfterResetDialog != null && launcherAfterResetDialog.isShow()) {
            return;
        }
        if (currentFragmentType == MeterFragmentType.CLASSIC || currentFragmentType == MeterFragmentType.Tech
                || currentFragmentType == MeterFragmentType.SPORT || currentFragmentType == MeterFragmentType.MAP) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.ON_UP_KEY));
        }
    }

    @Override
    protected void onRight() {
        if (currentFragmentType == MeterFragmentType.CLASSIC || currentFragmentType == MeterFragmentType.Tech || currentFragmentType == MeterFragmentType.SPORT) {
            disposeMeterFragmentRightKey();
        } else if (currentFragmentType == MeterFragmentType.MENU) {
            if (menuHomeFragment != null) {
                menuHomeFragment.onRight();
            }
        }
    }

    @Override
    protected void onLeft() {
        if (currentFragmentType == MeterFragmentType.CLASSIC || currentFragmentType == MeterFragmentType.Tech || currentFragmentType == MeterFragmentType.SPORT) {
            disposeMeterFragmentLeftKey();
        } else if (currentFragmentType == MeterFragmentType.MENU) {
            if (menuHomeFragment != null) {
                menuHomeFragment.onLeft();
            }
        }

    }

    private void disposeMeterFragmentLeftKey() {
        if (launcherAfterResetDialog != null && launcherAfterResetDialog.isShow()) {
            launcherAfterResetDialog.changePosition();

        } else if (afterRecoveryDialog != null && afterRecoveryDialog.isShow()) {
            afterRecoveryDialog.changePosition();

        } else if (currentMenuType == MenuType.FM) {
            executeAsyncTask(() -> {
                String send = "AABB2100CCDD";
                SendHelperUsbToRight.handler(send);
            });
        } else if (MeterActivity.currentMenuType == MenuType.SPEED_CALIBRATION) { //速度校准
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SPEED_ADJUST_SUBTRACT));
        } else if (currentMenuType == MenuType.MEDIA) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.ON_LEFT_KEY));
        }
    }

    private void disposeMeterFragmentRightKey() {
        if (launcherAfterResetDialog != null && launcherAfterResetDialog.isShow()) {
            launcherAfterResetDialog.changePosition();

        } else if (afterRecoveryDialog != null && afterRecoveryDialog.isShow()) {
            afterRecoveryDialog.changePosition();

        } else if (currentMenuType == MenuType.FM) {
            executeAsyncTask(() -> {
                String send = "AABB2200CCDD";
                SendHelperUsbToRight.handler(send);
            });
        } else if (currentMenuType == MenuType.SPEED_CALIBRATION) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.SPEED_ADJUST_PLUS));
        } else if (currentMenuType == MenuType.MEDIA) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.ON_RIGHT_KEY));
        }
    }

    @Override
    protected void onOK() {
        LogUtils.printI(TAG, "onOK----currentFragmentType=" + MeterActivity.currentFragmentType);
        if (warnMessageDialog != null) {
            messageList.clear();
            closeMessageDialog();
        }
        if (currentFragmentType == MeterFragmentType.CLASSIC || currentFragmentType == MeterFragmentType.Tech || currentFragmentType == MeterFragmentType.SPORT) {
            disposeClassicFragmentOkKey();
        } else if (currentFragmentType == MeterFragmentType.MENU) {
            disposeMenuHomeFragmentOkKey();

        } else if (currentFragmentType == MeterFragmentType.MAINTAIN) {
            disposeMaintainFragmentOkKey();

        } else if (currentFragmentType == MeterFragmentType.MAP) {
            disposeMapFragmentOkKey();

        }
    }

    private void disposeMapFragmentOkKey() {
        toMenuHome();
        closeNavFragment();
    }

    //保养页面 ok键处理
    private void disposeMaintainFragmentOkKey() {
        toMenuHome();
        closeMaintainFragment();
    }


    private void disposeMenuHomeFragmentOkKey() {
        if (menuHomeFragment != null) {
            int selectPosition = menuHomeFragment.getSelectPosition();
            LogUtils.printI(TAG, "disposeMenuHomeFragmentOkKey---selectPosition=" + selectPosition);
            if (selectPosition == 0) {
                switchMeterFragment(MeterFragmentType.Tech.getValue());
                currentFragmentType = MeterFragmentType.Tech;
                bottomStatusView.showOilLineView(false);
                bottomStatusView.showWaterTempLineView(false);
                executeAsyncTask(() -> SPUtils.putInt(getApplicationContext(), SPUtils.SP_METER_STYLE, currentFragmentType.getValue()));
                meterStyle = currentFragmentType.getValue();
            } else if (selectPosition == 1) {
                switchMeterFragment(MeterFragmentType.SPORT.getValue());
                bottomStatusView.showOilLineView(false);
                bottomStatusView.showWaterTempLineView(false);
                currentFragmentType = MeterFragmentType.SPORT;
                executeAsyncTask(() -> SPUtils.putInt(getApplicationContext(), SPUtils.SP_METER_STYLE, currentFragmentType.getValue()));
                meterStyle = currentFragmentType.getValue();
            } else if (selectPosition == 2) {
                switchMeterFragment(MeterFragmentType.CLASSIC.getValue());
                bottomStatusView.showOilLineView(false);
                bottomStatusView.showWaterTempLineView(false);
                currentFragmentType = MeterFragmentType.CLASSIC;
                executeAsyncTask(() -> SPUtils.putInt(getApplicationContext(), SPUtils.SP_METER_STYLE, currentFragmentType.getValue()));
                meterStyle = currentFragmentType.getValue();
            } else if (selectPosition == 3) { //地图
                toNavFragment();
            } else if (selectPosition == 4) { //保养
                toMaintainFragment();

            }
            hideMenuHomeFragment();
        }
    }

    private void toNavFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (navFragment == null) {
            navFragment = new MapNavMeterFragment();
            fragmentTransaction.add(R.id.fragmentContainerView, navFragment);
        }

        fragmentTransaction.show(navFragment);
        if (meterFragment != null) {
            fragmentTransaction.hide(meterFragment);
        }
        if (menuHomeFragment != null) {
            fragmentTransaction.remove(menuHomeFragment);
            menuHomeFragment = null;
        }
        fragmentTransaction.commit();

        bottomStatusView.showWaterTempLineView(true);
        bottomStatusView.showOilLineView(true);
        speedCenterView.setVisibility(View.VISIBLE);
    }

    private void toMaintainFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();

        if (maintainFragment == null) {
            maintainFragment = new MaintainFragment();
            fragmentTransaction.add(R.id.fragmentContainerView, maintainFragment);
        }

        fragmentTransaction.show(maintainFragment);
        if (meterFragment != null) {
            fragmentTransaction.hide(meterFragment);
        }
        fragmentTransaction.commit();
    }


    private void disposeClassicFragmentOkKey() {
        if (MeterActivity.currentMenuType == MenuType.LAUNCHER_AFTER) {
            if (launcherAfterResetDialog != null) { //启动后复位显示
                int selectPosition = launcherAfterResetDialog.getSelectPosition();
                if (selectPosition == 0) {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.REST_LAUNCHER_AFTER));
                }
                launcherAfterResetDialog.dismiss();
                launcherAfterResetDialog = null;
            } else {
                if (warnMessageDialog == null) {
                    showLauncherAfterResetDialog();
                }
            }
        } else if (MeterActivity.currentMenuType == MenuType.AFTER_RECOVERY) { //复位后
            if (afterRecoveryDialog != null) { //启动后复位显示
                int selectPosition = afterRecoveryDialog.getSelectPosition();
                if (selectPosition == 0) {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.REST_AFTER_RECOVERY));
                }
                afterRecoveryDialog.dismiss();
                afterRecoveryDialog = null;
            } else {
                if (warnMessageDialog == null) {
                    showAfterRecoveryDialog();
                }
            }
        } else {
            toMenuHome();
        }
    }


    //显示复位后对话框
    private void showAfterRecoveryDialog() {
        afterRecoveryDialog = new ResetDialog(this, getResources().getString(R.string.after_recovery_reset));
        new XPopup.Builder(this).hasShadowBg(false).hasStatusBar(false)
                .isViewMode(true)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .animationDuration(200)
                .atView(speedCenterView)
                .isCenterHorizontal(true)
                .offsetY(DensityUtil.dip2px(getBaseContext(), 50))
                .customHostLifecycle(getLifecycle())
                .asCustom(afterRecoveryDialog)
                .show();
    }

    private void showLauncherAfterResetDialog() {
        launcherAfterResetDialog = new ResetDialog(this, getResources().getString(R.string.reset_launcher_after));
        new XPopup.Builder(this).hasShadowBg(false).hasStatusBar(false)
                .isViewMode(true)
                .popupAnimation(PopupAnimation.ScaleAlphaFromCenter)
                .animationDuration(200)
                .atView(speedCenterView)
                .isCenterHorizontal(true)
                .offsetY(DensityUtil.dip2px(getBaseContext(), 50))
                .customHostLifecycle(getLifecycle())
                .asCustom(launcherAfterResetDialog)
                .show();
    }

    @Override
    protected void onBack() {
        LogUtils.printI(TAG, "onBack----currentFragmentType=" + currentFragmentType);
        if (currentFragmentType == MeterFragmentType.CLASSIC || currentFragmentType == MeterFragmentType.Tech || currentFragmentType == MeterFragmentType.SPORT || currentFragmentType == MeterFragmentType.MAP) {
            disposeClassicFragmentBackKey();

        } else if (currentFragmentType == MeterFragmentType.MENU) {
            disposeMenuFragmentBackKey();

        } else if (currentFragmentType == MeterFragmentType.MAINTAIN) {
            disposeMaintainFragmentBackKey();
        }
    }

    //地图页返回按键处理
    private void disposeMapFragmentBackKey() {
        if (launcherAfterResetDialog != null && launcherAfterResetDialog.isShow()) {
            launcherAfterResetDialog.dismiss();
            launcherAfterResetDialog = null;
        }
        if (afterRecoveryDialog != null && afterRecoveryDialog.isShow()) {
            afterRecoveryDialog.dismiss();
            afterRecoveryDialog = null;
        } else if (currentMessage != null) {
            try {
                closeMessageDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            toMenuHome();
            closeNavFragment();
        }
    }

    //关闭导航仪表
    private void closeNavFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (navFragment != null) {
            fragmentTransaction.remove(navFragment);
            navFragment = null;
            fragmentTransaction.commit();
        }
    }

    //保养页返回按键处理
    private void disposeMaintainFragmentBackKey() {
        if (launcherAfterResetDialog != null && launcherAfterResetDialog.isShow()) {
            launcherAfterResetDialog.dismiss();
            launcherAfterResetDialog = null;
        }
        if (afterRecoveryDialog != null && afterRecoveryDialog.isShow()) {
            afterRecoveryDialog.dismiss();
            afterRecoveryDialog = null;
        } else if (currentMessage != null) {
            try {
                closeMessageDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    //菜单页返回按键处理
    private void disposeMenuFragmentBackKey() {
        LogUtils.printI(TAG, "disposeMenuFragmentBackKey----messageList size=" + messageList.size());
        if (launcherAfterResetDialog != null && launcherAfterResetDialog.isShow()) {
            launcherAfterResetDialog.dismiss();
            launcherAfterResetDialog = null;
        }
        if (afterRecoveryDialog != null && afterRecoveryDialog.isShow()) {
            afterRecoveryDialog.dismiss();
            afterRecoveryDialog = null;
        } else if (currentMessage != null) {
            try {
                closeMessageDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            hideMenuHomeShowMeterFragment();
            if (meterStyle == MeterFragmentType.CLASSIC.getValue()) {
                currentFragmentType = MeterFragmentType.CLASSIC;
            } else if (meterStyle == MeterFragmentType.Tech.getValue()) {
                currentFragmentType = MeterFragmentType.Tech;
            } else if (meterStyle == MeterFragmentType.SPORT.getValue()) {
                currentFragmentType = MeterFragmentType.SPORT;
            }
            bottomStatusView.showOilLineView(false);
            bottomStatusView.showWaterTempLineView(false);
        }
    }

    private void hideMenuHomeShowMeterFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (menuHomeFragment != null) {
            try {
                fragmentTransaction.remove(menuHomeFragment);
            } catch (Exception e) {
                e.printStackTrace();
            }
            menuHomeFragment = null;
        }
        if (meterFragment != null) {
            fragmentTransaction.show(meterFragment);
        }
        fragmentTransaction.commit();
    }

    private void closeMaintainFragment() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        if (navFragment != null) {
            fragmentTransaction.remove(maintainFragment);
            maintainFragment = null;
            fragmentTransaction.commit();
        }
    }

    private void hideMenuHomeFragment() {
        if (menuHomeFragment != null) {
            try {
                FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
                fragmentTransaction.remove(menuHomeFragment);
                fragmentTransaction.commit();
            } catch (Exception e) {
                e.printStackTrace();
            }
            menuHomeFragment = null;
        }
    }

    private void disposeClassicFragmentBackKey() {
        LogUtils.printI(TAG, "disposeClassicFragmentBackKey---warnMessageDialog=" + warnMessageDialog);
        if (launcherAfterResetDialog != null && launcherAfterResetDialog.isShow()) {
            launcherAfterResetDialog.dismiss();
            launcherAfterResetDialog = null;
        }
        if (afterRecoveryDialog != null && afterRecoveryDialog.isShow()) {
            afterRecoveryDialog.dismiss();
            afterRecoveryDialog = null;
        } else if (warnMessageDialog != null) {
            try {
                closeMessageDialog();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.ON_BACK_KEY));
        }
    }


    //status: 0:正在下载， 4：下载成功， 1： 解压中
    @Override
    public void onDownload(int status,
                           int completeCode,
                           String name) {
        LogUtils.printI(TAG, "onDownload---status=" + status + ",completeCode=" + completeCode + ", name=" + name);
        //下载进度，下载完成之后为解压进度
        //当前所下载的城市的名字
        if (mapDownloadDialog != null) {

            if (!isDownloadCity) {
                if (status == 4) {
                    downloadCityNumber++;
                    LogUtils.printI(TAG, "onDownload---downloadCityNumber=" + downloadCityNumber + ",downloadCitySize=" + downloadCitySize);
                    if (downloadCityNumber == downloadCitySize) {
                        mapDownloadDialog.dismiss();
                        mapDownloadDialog = null;
                    }
                }
            } else {
                mapDownloadDialog.updateProgress(status, completeCode);
                if (status == 4) {
                    mapDownloadDialog.dismiss();
                    mapDownloadDialog = null;
                }
            }
        }
    }

    //hasNew - true表示有更新，说明官方有新版或者本地未下载
    //name - 被检测更新的城市的名字
    @Override
    public void onCheckUpdate(boolean hasNew, String name) {
        LogUtils.printI(TAG, "onCheckUpdate---hasNew=" + hasNew + ", name=" + name);
    }

    //describe - 删除描述，如 删除成功 "本地无数据"
    //name - 所删除的城市的名字
    @Override
    public void onRemove(boolean success, String name, String describe) {

    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
//        super.onSaveInstanceState(outState);
    }


}