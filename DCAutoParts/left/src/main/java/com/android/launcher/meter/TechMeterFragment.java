package com.android.launcher.meter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;

import com.amap.api.navi.AMapNavi;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.classic.fragment.ClassicAfterRecoveryFrag;
import com.android.launcher.meter.classic.fragment.ClassicAttentionAidSysFrag;
import com.android.launcher.meter.classic.fragment.ClassicFMFrag;
import com.android.launcher.meter.classic.fragment.ClassicLauncherAfterFrag;
import com.android.launcher.meter.classic.fragment.ClassicMediaFrag;
import com.android.launcher.meter.classic.fragment.ClassicSpeedCalibrationFrag;
import com.android.launcher.meter.tech.fragment.TechHomeFrag;
import dc.library.ui.view.dashboard.TechOilMeterView;
import dc.library.ui.view.dashboard.TreedWaterTempView;
import com.android.launcher.service.LivingService;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.AnimationUtils;
import com.android.launcher.util.CarConstants;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.IconUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.ValueAnimatorUtil;
import com.android.launcher.view.TreedRotateSpeedView;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * 前卫仪表盘
 */
public class TechMeterFragment extends MeterFragmentBase {

    public TextView carSpeedTV;
    public TreedRotateSpeedView treedRotateSpeedView;

    public TreedWaterTempView treedWaterTempView;

    public ConstraintLayout dateCL;

    public ImageView waterTempIV;

    private TechOilMeterView techOilMeterView;

    private int speedGetCount = 0;
    private TextView weekTV;
    private TextView dateTV;

    private TextView distanceToEmptyTV;
    private TextView distanceToEmptyUnitTV;
    private ImageView distanceToEmptyIV;

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if (event.type == MessageEvent.Type.UPDATE_RUNNING_DISTANCE) {
            if (distanceToEmptyTV != null) {
                distanceToEmptyTV.setText(String.valueOf((int) LivingService.distance));
            }
        } else if (event.type == MessageEvent.Type.ON_DOWN_KEY) {
            onDown();
        } else if (event.type == MessageEvent.Type.ON_UP_KEY) {
            onUp();
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        try {
//            float durationScale = Settings.Global.getFloat(getActivity().getContentResolver(), Settings.Global.ANIMATOR_DURATION_SCALE, 1f);
//            LogUtils.printI(TAG, "durationScale="+durationScale);
            ValueAnimatorUtil.resetDurationScaleIfDisable();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            distanceToEmptyIV = view.findViewById(R.id.distanceToEmptyIV);
            treedRotateSpeedView = view.findViewById(R.id.treedRotateSpeedView);
            carSpeedTV = view.findViewById(R.id.carSpeedTV);
            treedWaterTempView = view.findViewById(R.id.treedWaterTempView);

            menuViewPager = view.findViewById(R.id.menuVP);
            dateCL = view.findViewById(R.id.dateCL);
            weekTV = view.findViewById(R.id.weekTV);
            dateTV = view.findViewById(R.id.dateTV);
            waterTempIV = view.findViewById(R.id.waterTempIV);
            distanceToEmptyTV = view.findViewById(R.id.distanceToEmptyTV);
            distanceToEmptyUnitTV = view.findViewById(R.id.distanceToEmptyUnitTV);

            techOilMeterView = view.findViewById(R.id.treedOilMeterView);

            techOilMeterView.setOil(0.0f);

            CommonUtil.initWeekDate(weekTV, dateTV);

            try {
                oilAnimationStart = false;
                IconUtils.setColor(distanceToEmptyIV, 0);
                oilAnimationStart = true;
                distanceToEmptyIV.clearAnimation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        dateCL.setVisibility(View.VISIBLE);

        if (MeterActivity.unitType == UnitType.KM.ordinal()) {
//            mileUnitTV.setText("km/h");
            distanceToEmptyUnitTV.setText("km");
        } else {
//            mileUnitTV.setText("mph");
            distanceToEmptyUnitTV.setText("mp");
        }

        techOilMeterView.setOil(LivingService.currentPercentOil);
        treedWaterTempView.setWaterTemp(LivingService.currentWaterTemp);
    }

    @Override
    protected List<Fragment> getMenuFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new TechHomeFrag());
        fragments.add(new ClassicLauncherAfterFrag());
        fragments.add(new ClassicAfterRecoveryFrag());
//        fragments.add(new ClassicMapFrag());
        fragments.add(new ClassicAttentionAidSysFrag());
        fragments.add(new ClassicMediaFrag());
        fragments.add(new ClassicFMFrag());
        fragments.add(new ClassicSpeedCalibrationFrag());
        return fragments;
    }


    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_meter_treed;
    }

    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            MeterActivity activity = (MeterActivity) getActivity();
            activity.bottomStatusView.showWaterTempLineView(false);
            activity.bottomStatusView.showOilLineView(false);
        }
        AMapNavi.destroy();
    }

    @Override
    public void onPause() {
        super.onPause();
    }

    @Override
    public void onStart() {
        super.onStart();
        MeterActivity.currentFragmentType = MeterFragmentType.Tech;
    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public void startMeterAnimation() {
        LogUtils.printI(TAG, "startMeterAnimation-----");

        ValueAnimator speedAnimator = createSpeedAnimator(0, 260);
        speedAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
                LogUtils.printI(TAG, "startMeterAnimation-----onAnimationStart");
                showAllTroubleLight();
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.printI(TAG, "startMeterAnimation-----onAnimationEnd");
                endMeterAnimation();
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });

        ValueAnimator rotateSpeedAnimator = createRotateSpeedAnimator(0, 8000);

//        // 创建 AnimatorSet 对象并将两个动画添加到其中
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(speedAnimator, rotateSpeedAnimator);
        animSet.setDuration(1000);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.start();

    }

    @Override
    public void updateCarSpeed(String speedData) {
        if (carSpeedTV != null) {
            carSpeedTV.setText(speedData);
        }

    }

    /**
     * @description: 发动机转速开始动画
     * @createDate: 2023/6/21
     */
    private ValueAnimator createRotateSpeedAnimator(int startValue, int endValue) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startValue, endValue);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LogUtils.printI(TAG, "createRotateSpeedAnimator----value=" + value);
                treedRotateSpeedView.setSweepAngle(value);
            }
        });
        valueAnimator.setDuration(1000);
        return valueAnimator;
    }

    @NotNull
    private ValueAnimator createSpeedAnimator(int startValue, int endValue) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startValue, endValue);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {

            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int value = (int) animation.getAnimatedValue();
                LogUtils.printI(TAG, "createSpeedAnimator----value=" + value);
                carSpeedTV.setText(String.valueOf(value));
            }
        });

        valueAnimator.setDuration(1000);
        return valueAnimator;
    }

    private void endMeterAnimation() {
        ValueAnimator speedAnimator = createSpeedAnimator(260, 0);
        speedAnimator.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {
            }

            @Override
            public void onAnimationEnd(Animator animation) {
                LogUtils.printI(TAG, "endMeterAnimation-----onAnimationEnd");
                hideAllTroubleLight();
                meterLauncherAnimateFinish = true;
                meterAnimationEnd();
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.HIDE_ALL_METER_WARING));
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
            }
        });

        ValueAnimator rotateSpeedAnimator = createRotateSpeedAnimator(8000, 0);

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(speedAnimator, rotateSpeedAnimator);
        animatorSet.setDuration(1000);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }


    @Override
    protected void updateOilProgress(float percent) {
        techOilMeterView.setOil(percent);

        if (percent <= CarConstants.OIL_BOX_WARN) {

            oilAnimationStart = false;
            if (!oilAnimationStart) {
                IconUtils.setColor(distanceToEmptyIV, getResources().getColor(R.color.colorRed));
                oilAnimationStart = true;
                AnimationUtils.startBlinkAnim(distanceToEmptyIV);
            }
        } else {
            oilAnimationStart = false;
            IconUtils.setColor(distanceToEmptyIV, 0);
            oilAnimationStart = true;
            distanceToEmptyIV.clearAnimation();
        }
    }


    @Override
    public void updateWaterTemp(int temp) {
        if (temp < 50) {
            temp = 51;
        }
        if (treedWaterTempView != null) {
            treedWaterTempView.setWaterTemp(temp);
            if (temp > 110) {
                waterTempIV.setImageResource(R.drawable.tmp_alert);
            } else {
                waterTempIV.setImageResource(R.drawable.tmp);
            }
        }
    }

    @Override
    public void updateEngineSpeed(int engineSpeed) {
        if (meterLauncherAnimateFinish) {
//            LogUtils.printI(TAG, "updateEngineSpeed-----handler=" + handler + ", treedRotateSpeedView=" + treedRotateSpeedView);
            if (treedRotateSpeedView != null) {
                treedRotateSpeedView.setSweepAngle(engineSpeed);
            }
        }
    }


    @Override
    protected void onBack() {
        super.onBack();
        try {
            menuViewPager.setCurrentItem(0, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected void onOK() {
        super.onOK();
    }

    @Override
    protected void onLeft() {
        super.onLeft();

    }

    @Override
    protected void onRight() {
        super.onRight();

    }
}
