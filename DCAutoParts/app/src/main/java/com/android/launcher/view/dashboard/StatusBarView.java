package com.android.launcher.view.dashboard;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.launcher.App;
import com.android.launcher.R;
import com.android.launcher.entity.LampShowStatus;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.meter.MeterFragmentType;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import dc.library.auto.event.MessageEvent;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

/**
 * 状态栏
 */
public class StatusBarView extends FrameLayout {

    private static final String TAG = StatusBarView.class.getSimpleName();
    public static volatile boolean showSafetyBelt = false;
    public static volatile boolean showGasSafe = false;
    //距离警告标志
    private ImageView distancewarningTV;
    //安全带
    private ImageView beltsafe;
    //安全气囊
    private ImageView gassafe;
    //动力转向
    private ImageView powersteer;
    //蓄电池警报
    private ImageView storageBatteryIV;
    private ImageView abs;
    //胎压
    private ImageView taiya;
    //发动机故障
    private ImageView enginefailure;
    //ESP,防侧滑
    private ImageView espStatusIV;
    //左转向，右转向灯
    private ImageView signalLeftIV, signalRightIV;
    //大灯 近光灯， 自动
    private ImageView dippedHeadlightIV, light_auto;
    //远光灯
    private ImageView highBeamIV;
    //前雾灯，后雾灯
    private ImageView frontFogLampIV, rearFogLampIV;
    private ImageView electricalparkbrake;
    //水温
    private ImageView water_temp_icon_alert;
    private ImageView brakingSystemsIV;

    public StatusBarView(@NonNull Context context) {
        this(context, null);
    }

    public StatusBarView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public StatusBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public StatusBarView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_status_bar, this, true);
        distancewarningTV = findViewById(R.id.distancewarning);
        beltsafe = findViewById(R.id.beltsafe);
        gassafe = findViewById(R.id.gassafe);
        powersteer = findViewById(R.id.powersteer);
        storageBatteryIV = findViewById(R.id.storageBatteryIV);
        abs = findViewById(R.id.abs);
        taiya = findViewById(R.id.taiya);
        enginefailure = findViewById(R.id.enginefailure);
        espStatusIV = findViewById(R.id.espStatusIV);
        brakingSystemsIV = findViewById(R.id.brakingSystemsIV);
        signalLeftIV = findViewById(R.id.signalleft);
        signalRightIV = findViewById(R.id.signalright);
        dippedHeadlightIV = findViewById(R.id.light_lb);
        light_auto = findViewById(R.id.light_auto);
        highBeamIV = findViewById(R.id.light_hb);
        frontFogLampIV = findViewById(R.id.light_wd);
        rearFogLampIV = findViewById(R.id.light_hwd);
        hideAll();
    }

    private void initData() {
        new Thread(() -> {
            try {
                boolean engineWarnShow = SPUtils.getBoolean(App.getGlobalContext(), SPUtils.SP_ENGINE_WARN, false);
                MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_ENGINE_WARN);
                messageEvent.data = engineWarnShow;
                EventBus.getDefault().post(messageEvent);
                boolean airbagFailureShow = SPUtils.getBoolean(App.getGlobalContext(), SPUtils.SP_AIRBAG_FAILURE, false);
                messageEvent = new MessageEvent(MessageEvent.Type.SHOW_AIRBAG_FAILURE);
                messageEvent.data = airbagFailureShow;
                EventBus.getDefault().post(messageEvent);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        LogUtils.printI(TAG, "onAttachedToWindow----");
        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.printI(TAG, "onDetachedFromWindow----");
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        try {
            if (event.type == MessageEvent.Type.HIDE_ALL_METER_WARING) {
                hideAll();
                initData();
            } else if (event.type == MessageEvent.Type.SHOW_SAFETY_BELT) {
                if (event.data instanceof Boolean) {
                    showSafetyBelt = (boolean) event.data;
                    setShowSafetyBelt(showSafetyBelt);
                }
            } else if (event.type == MessageEvent.Type.SHOW_GASSAFE) {
                if (event.data instanceof Boolean) {
                    showGasSafe = (boolean) event.data;
                    setShowGasSafe(showGasSafe);
                }
            } else if (event.type == MessageEvent.Type.SHOW_STORAGE_BATTERY) {
                if (event.data instanceof Boolean) {
                    boolean isShow = (boolean) event.data;
                    showStorageBattery(isShow);
                }
            } else if (event.type == MessageEvent.Type.UPDATE_ABS_STATUS) {
                try {
                    Boolean isOpen = (Boolean) event.data;
                    updateAbsStatus(isOpen);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.SHOW_ENGINE_WARN) {
                if (event.data instanceof Boolean) {
                    boolean isShow = (boolean) event.data;
                    showEngineWarn(isShow);
                }
            } else if (event.type == MessageEvent.Type.ESP_SWITCH) {
                switchESP();
            } else if (event.type == MessageEvent.Type.SHOW_LEFT_TURN_SIGNAL) {
                if (event.data instanceof Boolean) {
                    boolean isShow = (boolean) event.data;
                    showLeftTurnSignal(isShow);
                }
            } else if (event.type == MessageEvent.Type.SHOW_RIGHT_TURN_SIGNAL) {
                if (event.data instanceof Boolean) {
                    boolean isShow = (boolean) event.data;
                    showRightTurnSignal(isShow);
                }
            } else if (event.type == MessageEvent.Type.SHOW_LAMP) {
                if (event.data instanceof LampShowStatus) {
                    LampShowStatus lampShowStatus = (LampShowStatus) event.data;
                    showLampStatus(lampShowStatus);
                }
            } else if (event.type == MessageEvent.Type.SHOW_HIGH_BEAM) {
                if (event.data instanceof Boolean) {
                    boolean isShow = (boolean) event.data;
                    showHighBeam(isShow);
                }
            } else if (event.type == MessageEvent.Type.SHOW_AIRBAG_FAILURE) {
                if (event.data instanceof Boolean) {
                    boolean isShow = (boolean) event.data;
                    if (gassafe != null) {
                        if (isShow) {
                            gassafe.setVisibility(View.VISIBLE);
                        } else {
                            gassafe.setVisibility(View.INVISIBLE);
                        }
                    }
                }
            } else if (event.type == MessageEvent.Type.START_METER_ANIMATION) {
                LogUtils.printI(TAG, "START_METER_ANIMATION---");
                if (MeterActivity.currentFragmentType == MeterFragmentType.Tech || MeterActivity.currentFragmentType == MeterFragmentType.SPORT || MeterActivity.currentFragmentType == MeterFragmentType.CLASSIC) {
                    showAll();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    //显示远光
    private void showHighBeam(boolean isShow) {
        LogUtils.printI(TAG, "showHighBeam---isShow=" + isShow);
        if (highBeamIV == null) {
            return;
        }
        if (isShow) {
            highBeamIV.setVisibility(View.VISIBLE);
        } else {
            highBeamIV.setVisibility(View.INVISIBLE);
        }
    }

    private void showLampStatus(LampShowStatus lampShowStatus) {
        LogUtils.printI(TAG, "showLampStatus---lampShowStatus=" + lampShowStatus);
        if (light_auto == null || lampShowStatus == null) {
            return;
        }
        if (lampShowStatus.isLightAutoShow()) {
            light_auto.setVisibility(View.VISIBLE);
        } else {
            light_auto.setVisibility(View.INVISIBLE);
        }
        if (lampShowStatus.isDippedHeadlightShow()) {
            dippedHeadlightIV.setVisibility(View.VISIBLE);
        } else {
            dippedHeadlightIV.setVisibility(View.INVISIBLE);
        }
        showHighBeam(lampShowStatus.isHighBeamShow());
        if (lampShowStatus.isFrontFogLampShow()) {
            frontFogLampIV.setVisibility(View.VISIBLE);
        } else {
            frontFogLampIV.setVisibility(View.INVISIBLE);
        }
        if (lampShowStatus.isRearFogLampShow()) {
            rearFogLampIV.setVisibility(View.VISIBLE);
        } else {
            rearFogLampIV.setVisibility(View.INVISIBLE);
        }
    }

    private void showRightTurnSignal(boolean isShow) {
        LogUtils.printI(TAG, "showRightTurnSignal---isShow=" + isShow);
        if (signalRightIV == null) {
            return;
        }
        if (isShow) {
            signalRightIV.setVisibility(View.VISIBLE);
        } else {
            signalRightIV.setVisibility(View.INVISIBLE);
        }
    }

    private void showLeftTurnSignal(boolean isShow) {
        LogUtils.printI(TAG, "showLeftTurnSignal---isShow=" + isShow);
        if (signalLeftIV == null) {
            return;
        }
        if (isShow) {
            signalLeftIV.setVisibility(View.VISIBLE);
        } else {
            signalLeftIV.setVisibility(View.INVISIBLE);
        }
    }

    public void switchESP() {
        LogUtils.printI(TAG, "disposeEspSwitch---" + espStatusIV.getVisibility());
        if (espStatusIV.getVisibility() == View.VISIBLE) {
            espStatusIV.setVisibility(View.GONE);
        } else { //开
            espStatusIV.setVisibility(View.VISIBLE);
        }
    }

    //显示发动机故障
    public void showEngineWarn(boolean isShow) {
        LogUtils.printI(TAG, "showEngineWarn----isShow=" + isShow);
        if (enginefailure == null) {
            return;
        }
        if (isShow) {
            enginefailure.setVisibility(View.VISIBLE);
        } else {
            enginefailure.setVisibility(View.INVISIBLE);
        }
    }

    /**
     * @description: ABS打开状态
     * @createDate: 2023/6/24
     */
    public void updateAbsStatus(Boolean isShow) {
        LogUtils.printI(TAG, "updateAbsStatus----isShow=" + isShow);
        if (isShow) {
            abs.setVisibility(View.VISIBLE);
        } else {
            abs.setVisibility(View.INVISIBLE);
        }
    }

    private void showStorageBattery(boolean isShow) {
        if (storageBatteryIV == null) {
            return;
        }
        if (isShow) {
            storageBatteryIV.setVisibility(View.VISIBLE);
        } else {
            storageBatteryIV.setVisibility(View.INVISIBLE);
        }
    }

    private void setShowGasSafe(boolean showGasSafe) {
        LogUtils.printI(TAG, "setShowGasSafe---showGasSafe=" + showGasSafe);
        if (gassafe != null) {
            if (showGasSafe) {
                gassafe.setVisibility(View.VISIBLE);
            } else {
                gassafe.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void setShowSafetyBelt(boolean showSafetyBelt) {
        LogUtils.printI(TAG, "setShowSafetyBelt---showSafetyBelt=" + showSafetyBelt);
        if (beltsafe != null) {
            if (showSafetyBelt) {
                beltsafe.setVisibility(View.VISIBLE);
            } else {
                beltsafe.setVisibility(View.INVISIBLE);
            }
        }
    }

    private void hideAll() {
        try {
            if (distancewarningTV != null) {
                int invisible = View.INVISIBLE;
                distancewarningTV.setVisibility(invisible);
                showGasSafe = false;
                setShowGasSafe(showGasSafe);
                showSafetyBelt = false;
                setShowSafetyBelt(showSafetyBelt);
                powersteer.setVisibility(invisible);
                showStorageBattery(false);
                updateAbsStatus(false);
                taiya.setVisibility(invisible);
                enginefailure.setVisibility(invisible);
                espStatusIV.setVisibility(invisible);
                signalLeftIV.setVisibility(invisible);
                signalRightIV.setVisibility(invisible);
                dippedHeadlightIV.setVisibility(invisible);
                light_auto.setVisibility(invisible);
                highBeamIV.setVisibility(invisible);
                frontFogLampIV.setVisibility(invisible);
                rearFogLampIV.setVisibility(invisible);
                brakingSystemsIV.setVisibility(invisible);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void showAll() {
        try {
            if (distancewarningTV != null) {
                int invisible = View.VISIBLE;
                distancewarningTV.setVisibility(invisible);
                showGasSafe = false;
                setShowGasSafe(showGasSafe);
                showSafetyBelt = false;
                setShowSafetyBelt(showSafetyBelt);
                powersteer.setVisibility(invisible);
                showStorageBattery(false);
                updateAbsStatus(false);
                taiya.setVisibility(invisible);
                enginefailure.setVisibility(invisible);
                espStatusIV.setVisibility(invisible);
                signalLeftIV.setVisibility(invisible);
                signalRightIV.setVisibility(invisible);
                dippedHeadlightIV.setVisibility(invisible);
                light_auto.setVisibility(invisible);
                highBeamIV.setVisibility(invisible);
                frontFogLampIV.setVisibility(invisible);
                rearFogLampIV.setVisibility(invisible);
                brakingSystemsIV.setVisibility(invisible);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}