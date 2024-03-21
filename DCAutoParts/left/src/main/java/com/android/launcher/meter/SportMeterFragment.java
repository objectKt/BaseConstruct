package com.android.launcher.meter;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.os.Bundle;
import android.view.View;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.amap.api.navi.AMapNavi;
import dc.library.utils.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.sport.SportAfterRecoveryFrag;
import com.android.launcher.meter.sport.SportAttentionAidSysFrag;
import com.android.launcher.meter.sport.SportFMFrag;
import com.android.launcher.meter.sport.SportHomeFrag;
import com.android.launcher.meter.sport.SportLauncherAfterFrag;
import com.android.launcher.meter.sport.SportMediaFrag;
import com.android.launcher.meter.sport.SportSpeedCalibrationFrag;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

/**
 * @description: 运动仪表盘
 * @createDate: 2023/6/20
 */
public class SportMeterFragment extends MeterFragmentBase {

    private TextView engineSpeedTV;

    private List<String> engineSpeedList = new ArrayList<>();
    private TextView engineSpeedUnitTV;

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if (event.type == MessageEvent.Type.ON_DOWN_KEY) {
            onDown();
        } else if (event.type == MessageEvent.Type.ON_UP_KEY) {
            onUp();
        }
    }

    @Override
    protected void updateOilProgress(float percent) {

    }


    @Override
    public void updateWaterTemp(int temp) {

    }

    @Override
    public void onMenuPositionChange(int position) {
        super.onMenuPositionChange(position);
        LogUtils.printI(TAG,"onMenuPositionChange---position="+position);
        if(engineSpeedTV!=null){
            if(position!=0){
                engineSpeedTV.setVisibility(View.INVISIBLE);
                engineSpeedUnitTV.setVisibility(View.INVISIBLE);
            }else{
                engineSpeedTV.setVisibility(View.VISIBLE);
                engineSpeedUnitTV.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void updateEngineSpeed(int engineSpeed) {
        if (meterLauncherAnimateFinish) {
            try {
                if (engineSpeedTV != null) {
                    engineSpeedTV.setText(String.valueOf(engineSpeed));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        engineSpeedTV = view.findViewById(R.id.engineSpeedTV);
        engineSpeedUnitTV = view.findViewById(R.id.engineSpeedUnitTV);

        engineSpeedList.clear();
    }

    @Override
    protected List<Fragment> getMenuFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new SportHomeFrag());
        fragments.add(new SportLauncherAfterFrag());
        fragments.add(new SportAfterRecoveryFrag());
//        fragments.add(new SportMapFrag());
        fragments.add(new SportAttentionAidSysFrag());
        fragments.add(new SportMediaFrag());
        fragments.add(new SportFMFrag());
        fragments.add(new SportSpeedCalibrationFrag());
        return fragments;
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_sport_meter;
    }

    @Override
    public void startMeterAnimation() {

        ValueAnimator speedAnimator = createSpeedAnimator(0, 8000);
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

//        // 创建 AnimatorSet 对象并将两个动画添加到其中
        AnimatorSet animSet = new AnimatorSet();
        animSet.playTogether(speedAnimator);
        animSet.setDuration(1500);
        animSet.setInterpolator(new LinearInterpolator());
        animSet.start();
    }

    @NotNull
    private ValueAnimator createSpeedAnimator(int startValue, int endValue) {
        ValueAnimator valueAnimator = ValueAnimator.ofInt(startValue, endValue);
        valueAnimator.setInterpolator(new LinearInterpolator());
        valueAnimator.addUpdateListener(animation -> {
            int value = (int) animation.getAnimatedValue();
            engineSpeedTV.setText(String.valueOf(value));
        });

        valueAnimator.setDuration(1500);
        return valueAnimator;
    }

    private void endMeterAnimation() {
        ValueAnimator speedAnimator = createSpeedAnimator(8000, 0);
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

        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(speedAnimator);
        animatorSet.setDuration(1500);
        animatorSet.setInterpolator(new LinearInterpolator());
        animatorSet.start();
    }


    @Override
    public void updateCarSpeed(String speedData) {

    }



    @Override
    protected void onBack() {
        super.onBack();
        menuViewPager.setCurrentItem(0, false);
    }


    ;


    @Override
    public void onResume() {
        super.onResume();
        if (getActivity() != null) {
            MeterActivity activity = (MeterActivity) getActivity();
            activity.bottomStatusView.showWaterTempLineView(true);
            activity.bottomStatusView.showOilLineView(true);
        }
        AMapNavi.destroy();
    }
}
