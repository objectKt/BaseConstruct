package com.android.launcher.meter.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.meter.MeterFragmentType;
import com.android.launcher.service.LivingService;
import com.android.launcher.status.RadarSwitchStatus;
import com.android.launcher.util.AnimationUtils;
import com.android.launcher.util.CarConstants;
import com.android.launcher.util.IconUtils;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import dc.library.ui.view.dashboard.OilMeterLineView;
import dc.library.ui.view.dashboard.WaterTempLineView;
import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

public class BottomStatusView extends FrameLayout {
    private static final String TAG = BottomStatusView.class.getSimpleName();

    //电子手刹警告显示
    public static volatile boolean electricalParkBrakeWaringShow = false;

    //电子手刹显示
    public static volatile boolean electricalParkBrakeShow = false;

    //黄色的p, 电子手刹警告
    private ImageView electricalparkbrakewarning;

    //红色的p, 当P灯为红色且常亮时，表示驻车制动系统工作正常，手刹已经拉紧。这是正常状态，没有故障。
    private ImageView electricalParkBrakeIV;

    //制动系统警告灯
    private ImageView brakeSystemIV;

    private TextView timeTV;


    private OilMeterLineView oilMeterLineView;
    private WaterTempLineView waterTempLineView;
    //剩余里程
    private TextView remainKONTV;


    private ImageView clearanceLampIV;
    private ImageView oilBoxIV;
    private ConstraintLayout waterTempCL;
    private ConstraintLayout oilMeterLineCL;
    private TextView carTempTV;
    private ImageView radarPOnIV;
    private TextView holdTV;
    private WaterTempLineView waterTempView;
    private TextView unitTV;


    private boolean oilAnimationStart;


    public BottomStatusView(@NonNull Context context) {
        this(context, null);
    }

    public BottomStatusView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public BottomStatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public BottomStatusView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.layout_bottom_status, this, true);

        oilBoxIV = findViewById(R.id.oilBoxIV);
        clearanceLampIV = findViewById(R.id.clearanceLampIV);
        electricalparkbrakewarning = findViewById(R.id.electricalparkbrakewarning);
        electricalParkBrakeIV = findViewById(R.id.electricalparkbrake);
        brakeSystemIV = findViewById(R.id.zhidongalarm);
        carTempTV = findViewById(R.id.carTempTV);
        remainKONTV = findViewById(R.id.remainKONTV);
        unitTV = findViewById(R.id.unitTV);
        radarPOnIV = findViewById(R.id.radarPOnIV);
        holdTV = findViewById(R.id.holdTV);
        timeTV = findViewById(R.id.timeTV);
        waterTempView = findViewById(R.id.waterTempView);
        oilMeterLineView = findViewById(R.id.oilMeterLineView);
        oilMeterLineCL = findViewById(R.id.oilMeterLineCL);
        waterTempCL = findViewById(R.id.waterTempCL);

        oilMeterLineCL.setVisibility(View.INVISIBLE);
        waterTempCL.setVisibility(View.INVISIBLE);
        showOilLineView(false);
        showWaterTempLineView(false);
        radarPOnIV.setVisibility(View.INVISIBLE);
        hideAll();
        loadData();

        IconUtils.setColor(radarPOnIV, getResources().getColor(R.color.oilLineSelect));

        unitTV.setText(getResources().getString(R.string.mile_unit));

        try {
            oilAnimationStart = false;
            IconUtils.setColor(oilBoxIV, 0);
            oilAnimationStart = true;
            oilBoxIV.clearAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void loadData() {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        String time = simpleDateFormat.format(new Date());
        timeTV.setText(time);
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();
        try {
            EventBus.getDefault().register(this);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        try {
            EventBus.getDefault().unregister(this);

        } catch (Exception e) {
            e.printStackTrace();
        }

        super.onDetachedFromWindow();
    }

    public void hideLineOilMeterView(boolean isHide) {
        if (isHide) {
            if (waterTempCL != null) {
                waterTempCL.setVisibility(View.INVISIBLE);
                oilMeterLineCL.setVisibility(View.INVISIBLE);
            }
        } else {
            if (waterTempCL != null) {
                waterTempCL.setVisibility(View.VISIBLE);
                oilMeterLineCL.setVisibility(View.VISIBLE);
            }
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        try {
            if (event.type == MessageEvent.Type.HIDE_ALL_METER_WARING) {
                LogUtils.printI(TAG, "HIDE_ALL_METER_WARING---");
                hideAll();
            } else if (event.type == MessageEvent.Type.SHOW_CLEARANCE_LAMP) {
                if (event.data instanceof Boolean) {
                    Boolean isShow = (Boolean) event.data;
                    showClearanceLamp(isShow);
                }
            } else if (event.type == MessageEvent.Type.SHOW_ELECTRICAL_PARK_BRAKE_WARNING) {
                if (event.data instanceof Boolean) {
                    electricalParkBrakeWaringShow = (Boolean) event.data;
                    showElectricalPackBrakeWaring(electricalParkBrakeWaringShow);
                }
            } else if (event.type == MessageEvent.Type.SHOW_ELECTRICAL_PARK_BRAKE) {
                if (event.data instanceof Boolean) {
                    electricalParkBrakeShow = (Boolean) event.data;
                    showElectricalPackBrake(electricalParkBrakeShow);
                }
            } else if (event.type == MessageEvent.Type.RADAR_P_OFF) {
                try {
                    String radarStatus = (String) event.data;
                    setupRadarSwitch(radarStatus);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.HOLD_STATUS) {
                try {
                    //Hold状态
                    Boolean status = (Boolean) event.data;
                    if (status) {
                        setHoldViewShowStatus(View.VISIBLE);
                    } else {
                        setHoldViewShowStatus(View.GONE);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.CALIBRATION_TIME) {
                try {
                    updateTime((String) event.data);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.UPDATE_WATER_TEMP) { //水温
                try {
                    if (event.data != null) {
                        int temp = (int) event.data;
                        updateWaterTemp(temp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.UPDATE_OIL_BOX) {
                try {
                    if (event.data != null) {
                        float oilPercent = (float) event.data;
                        updateOilProgress(oilPercent);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.UPDATE_RUNNING_DISTANCE) {
                if (remainKONTV != null) {
                    remainKONTV.setText(String.valueOf((int) LivingService.distance));
                }
            } else if (event.type == MessageEvent.Type.START_METER_ANIMATION) {
                LogUtils.printI(TAG, "START_METER_ANIMATION---");
                if (MeterActivity.currentFragmentType == MeterFragmentType.Tech || MeterActivity.currentFragmentType == MeterFragmentType.SPORT || MeterActivity.currentFragmentType == MeterFragmentType.CLASSIC) {
                    showAll();
                }
            } else if (event.type == MessageEvent.Type.CURRENT_ML) {
                if (event.data instanceof Float) {
                    float oilPercent = (float) event.data;
                    if (oilMeterLineView != null) {
                        oilMeterLineView.setCurrentOil(oilPercent);
                    }
                }
            } else if (event.type == MessageEvent.Type.UPDATE_CAR_TEMP) {
                if (event.data instanceof Float) {
                    float temp = (float) event.data;
                    if (carTempTV != null) {
                        carTempTV.setText(temp + "℃");
                    }
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    protected void updateOilProgress(float percent) {
        try {
            LogUtils.printI(TAG, "updateOilProgress----percent=" + percent);
            if (oilMeterLineView != null) {
                oilMeterLineView.setCurrentOil(percent);
            }
            if (percent <= CarConstants.OIL_BOX_WARN) {

                oilAnimationStart = false;
                if (!oilAnimationStart) {
                    IconUtils.setColor(oilBoxIV, getResources().getColor(R.color.colorRed));
                    oilAnimationStart = true;
                    AnimationUtils.startBlinkAnim(oilBoxIV);
                }
            } else {
                oilAnimationStart = false;
                IconUtils.setColor(oilBoxIV, 0);
                oilAnimationStart = true;
                oilBoxIV.clearAnimation();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void updateWaterTemp(int temp) {
        LogUtils.printI(TAG, "updateWaterTemp----temp=" + temp);
        try {
            if (temp < 50) {
                temp = 51;
            }
            if (waterTempView != null) {
                waterTempView.setTemp(temp);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void updateTime(String time) {
        try {
//            LogUtils.printI(TAG, "CALIBRATION_TIME---time=" + time);
            if (time.length() > 13) { //时间长度固定13
                LogUtils.printI(TAG, "CALIBRATION_TIME---长度不对");
                return;
            }
            Long rightTime = Long.valueOf(time);
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
            String currentTime = simpleDateFormat.format(new Date(rightTime));
            timeTV.setText(currentTime);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setHoldViewShowStatus(int visible) {
        LogUtils.printI(TAG, "setHoldViewShowStatus----visible=" + visible);
        if (holdTV != null) {
            try {
                holdTV.setVisibility(visible);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void setupRadarSwitch(String radarPStatus) {
        LogUtils.printI(TAG, "setupRadarSwitch----radarPStatus=" + radarPStatus);
        if (RadarSwitchStatus.STATE_ON.getValue().equals(radarPStatus)) {
            radarPOnIV.setVisibility(View.VISIBLE);
        } else {
            radarPOnIV.setVisibility(View.GONE);
        }
    }

    private void showElectricalPackBrake(boolean isShow) {
        LogUtils.printI(TAG, "showElectricalPackBrake----isShow=" + isShow);
        if (electricalParkBrakeIV == null) {
            return;
        }
        if (isShow) {
            electricalParkBrakeIV.setVisibility(View.VISIBLE);
        } else {
            electricalParkBrakeIV.setVisibility(View.INVISIBLE);
        }
    }

    private void hideAll() {
        if (electricalparkbrakewarning != null) {
            electricalparkbrakewarning.setVisibility(View.INVISIBLE);
            clearanceLampIV.setVisibility(View.INVISIBLE);
            electricalParkBrakeIV.setVisibility(View.INVISIBLE);
            brakeSystemIV.setVisibility(View.INVISIBLE);
        }
    }

    public void showAll() {
        if (electricalparkbrakewarning != null) {
            electricalparkbrakewarning.setVisibility(View.VISIBLE);
            clearanceLampIV.setVisibility(View.VISIBLE);
            electricalParkBrakeIV.setVisibility(View.VISIBLE);
            brakeSystemIV.setVisibility(View.VISIBLE);
        }
    }

    private void showElectricalPackBrakeWaring(Boolean isShow) {
        LogUtils.printI(TAG, "showElectricalPackBrakeWaring----isShow=" + isShow);
        if (electricalparkbrakewarning == null) {
            return;
        }
        if (isShow) {
            electricalparkbrakewarning.setVisibility(View.VISIBLE);
        } else {
            electricalparkbrakewarning.setVisibility(View.INVISIBLE);
        }
    }

    private void showClearanceLamp(Boolean isShow) {
        LogUtils.printI(TAG, "showClearanceLamp----isShow=" + isShow);
        if (clearanceLampIV == null) {
            return;
        }
        if (isShow) {
            clearanceLampIV.setVisibility(View.VISIBLE);
        } else {
            clearanceLampIV.setVisibility(View.INVISIBLE);
        }
    }

    public void showOilLineView(boolean isShow) {
        if (oilMeterLineCL != null) {
            if (isShow) {
                oilMeterLineCL.setVisibility(View.VISIBLE);
            } else {
                oilMeterLineCL.setVisibility(View.INVISIBLE);
            }
        }
    }

    public void showWaterTempLineView(boolean isShow) {
        if (waterTempCL != null) {
            if (isShow) {
                waterTempCL.setVisibility(View.VISIBLE);
            } else {
                waterTempCL.setVisibility(View.INVISIBLE);
            }
        }
    }
}
