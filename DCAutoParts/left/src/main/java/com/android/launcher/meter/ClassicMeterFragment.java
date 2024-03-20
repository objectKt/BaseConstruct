package com.android.launcher.meter;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.amap.api.navi.AMapNavi;
import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.classic.fragment.ClassicAfterRecoveryFrag;
import com.android.launcher.meter.classic.fragment.ClassicAttentionAidSysFrag;
import com.android.launcher.meter.classic.fragment.ClassicFMFrag;
import com.android.launcher.meter.classic.fragment.ClassicHomeFrag;
import com.android.launcher.meter.classic.fragment.ClassicLauncherAfterFrag;
import com.android.launcher.meter.classic.fragment.ClassicMediaFrag;
import com.android.launcher.meter.classic.fragment.ClassicSpeedCalibrationFrag;
import dc.library.ui.view.dashboard.OilMeterView;
import dc.library.ui.view.dashboard.WaterTempMeterView;
import com.android.launcher.service.LivingService;
import com.android.launcher.type.UnitType;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.AnimationUtils;
import com.android.launcher.util.CarConstants;
import com.android.launcher.util.IconUtils;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @description: 经典仪表盘
 * @createDate: 2023/6/20
 */
public class ClassicMeterFragment extends MeterFragmentBase {

    // 仪表
    public WaterTempMeterView watertempMeterView;

    public ImageView waterTempIV;


    public ImageView rightMeterPointer;
    public ImageView leftMeterPointer;

    //
    public ACache aCache = ACache.get(App.getGlobalContext());


    public String startEngA = "0.0", endEngA = "0.0";

    public OilMeterView oilMeterView;

    public ArrayList<String> tempArr = new ArrayList<>();

    public long ll;
    public String startA = "0.0", endA = "0.0";
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
        } else if (event.type == MessageEvent.Type.UPDATE_OIL_BOX) {
            try {
                if (event.data != null) {
                    float oilPercent = (float) event.data;
                    updateOilProgress(oilPercent);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onOK() {
        LogUtils.printI(TAG, "onOK---currentMenuFragment=" + MeterActivity.currentMenuType.name());
    }


    @Override
    protected void onBack() {
        super.onBack();
        menuViewPager.setCurrentItem(0, false);
    }

    @Override
    public void updateWaterTemp(int temp) {
        if (temp < 50) {
            temp = 51;
        }
        if (watertempMeterView != null) {
            watertempMeterView.setProgress(temp);
            if (temp > 110) {
                waterTempIV.setImageResource(R.drawable.tmp_alert);
            } else {
                waterTempIV.setImageResource(R.drawable.tmp);
            }
        }
    }

    @Override
    public void updateEngineSpeed(int speed) {
        if (meterLauncherAnimateFinish) {
            BigDecimal bignum3 = new BigDecimal(speed);
            final String f = getEngDegree(bignum3.floatValue());
            BigDecimal fromd = new BigDecimal(startEngA);
            endEngA = f;
            BigDecimal tod = new BigDecimal(endEngA);
            startEngA = endEngA;
            imageViewrightUpdate(fromd, tod);
        }
    }

    private String getEngDegree(float f) {
        BigDecimal bignum1 = new BigDecimal(f);
        BigDecimal bignum2 = new BigDecimal("0.032");
        BigDecimal bignum3 = bignum1.multiply(bignum2);
        return bignum3.toString();
    }


    // can105 更新动画。
    public synchronized void imageViewrightUpdate(final BigDecimal fromd, final BigDecimal tod) {
        if (!meterLauncherAnimateFinish) {
            return;
        }
        RotateAnimation engineRotateAnimation = new RotateAnimation(fromd.floatValue(), tod.floatValue(), Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        LinearInterpolator lin = new LinearInterpolator();
        engineRotateAnimation.setInterpolator(lin);
        engineRotateAnimation.setDuration(500);//设置动画持续时间

        engineRotateAnimation.setRepeatCount(0);//设置重复次数
        engineRotateAnimation.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        if (rightMeterPointer != null) {
            rightMeterPointer.startAnimation(engineRotateAnimation);
        }
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        TextView mileUnitTV = view.findViewById(R.id.mileUnitTV);


        distanceToEmptyIV = view.findViewById(R.id.distanceToEmptyIV);
        leftMeterPointer = view.findViewById(R.id.left_pointer);
        rightMeterPointer = view.findViewById(R.id.right_pointer);
        watertempMeterView = view.findViewById(R.id.watertempMeterView);
        waterTempIV = view.findViewById(R.id.waterTempIV);

        distanceToEmptyTV = view.findViewById(R.id.distanceToEmptyTV);
        distanceToEmptyUnitTV = view.findViewById(R.id.distanceToEmptyUnitTV);


        oilMeterView = view.findViewById(R.id.oilMeterView);

        menuViewPager.setVisibility(View.VISIBLE);

        oilMeterView.setProgress(0.0f);

        if (MeterActivity.unitType == UnitType.KM.ordinal()) {
            mileUnitTV.setText("km/h");
            distanceToEmptyUnitTV.setText("km");
        } else {
            mileUnitTV.setText("mph");
            distanceToEmptyUnitTV.setText("mp");
        }
        try {
            oilAnimationStart = false;
            IconUtils.setColor(distanceToEmptyIV, 0);
            oilAnimationStart = true;
            distanceToEmptyIV.clearAnimation();
        } catch (Exception e) {
            e.printStackTrace();
        }

        watertempMeterView.setProgress(LivingService.currentWaterTemp);

        updateOilProgress(LivingService.currentPercentOil);

    }

    @Override
    protected List<Fragment> getMenuFragmentList() {
        List<Fragment> fragments = new ArrayList<>();
        fragments.add(new ClassicHomeFrag());
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
        return R.layout.fragment_meter_classic;
    }

    @Override
    public void startMeterAnimation() {
        try {
            showPointerStart(0f, 255f);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateCarSpeed(String speedData) {
        String carDegree = getDegree(new BigDecimal(speedData).floatValue());
        speedDegree(carDegree);
    }


    /**
     * 获取角度
     *
     * @param v
     * @return
     */
    private String getDegree(float v) {

        String result = "0.0";
        if (v < 60.0f) {
            BigDecimal bignum1 = new BigDecimal(v);
            result = bignum1.toString();
        } else if (v > 60.0f && v < 180.0f || new BigDecimal(v).compareTo(new BigDecimal(180.0)) == 0) {
            BigDecimal bignum1 = new BigDecimal(v);
            BigDecimal bignum2 = new BigDecimal(2.0f);
            BigDecimal bignum3 = bignum1.subtract(bignum2);
            result = bignum3.toString();
        } else if (v > 180.0f && v < 210.0f || new BigDecimal(v).compareTo(new BigDecimal(210.0)) == 0) {
            BigDecimal bignum1 = new BigDecimal(v);
            BigDecimal bignum2 = new BigDecimal(3.0f);
            BigDecimal bignum3 = bignum1.subtract(bignum2);
            result = bignum3.toString();
        } else if (v > 210.0f && v < 220.0f || new BigDecimal(v).compareTo(new BigDecimal(220.0)) == 0) {
            BigDecimal bignum1 = new BigDecimal(v);
            BigDecimal bignum2 = new BigDecimal(4.0f);
            BigDecimal bignum3 = bignum1.subtract(bignum2);
            result = bignum3.toString();
        } else if (v > 220.0f && v < 230.0f || new BigDecimal(v).compareTo(new BigDecimal(230.0)) == 0) {
            BigDecimal bignum1 = new BigDecimal(v);
            BigDecimal bignum2 = new BigDecimal(5.0f);
            BigDecimal bignum3 = bignum1.subtract(bignum2);
            result = bignum3.toString();
        } else if (v > 230.0f && v < 260.0f || new BigDecimal(v).compareTo(new BigDecimal(260.0)) == 0) {
            BigDecimal bignum1 = new BigDecimal(v);
            BigDecimal bignum2 = new BigDecimal(6.0f);
            BigDecimal bignum3 = bignum1.subtract(bignum2);
            result = bignum3.toString();
        } else if (v > 260.0f) {
            v = 260.0f;
            BigDecimal bignum1 = new BigDecimal(v);
            BigDecimal bignum2 = new BigDecimal(6.0f);
            BigDecimal bignum3 = bignum1.subtract(bignum2);
            result = bignum3.toString();
        }
        return result;
    }


    /**
     * 里程角度变化
     *
     * @param endDegree
     */
    public void speedDegree(String endDegree) {
        BigDecimal fromd = new BigDecimal(startA);
        endA = endDegree;
        if (!endA.equals("0.0")) {
            BigDecimal tod = new BigDecimal(endA);
//            Log.i("start",startA+"-----------------------"+endA) ;
            showPointer(fromd.floatValue(), tod.floatValue());
            startA = endA;
        }
    }

    /**
     * 指针转动
     *
     * @param fromDegrees
     * @param toDegrees
     */
    public void showPointer(Float fromDegrees, Float toDegrees) {
        if (meterLauncherAnimateFinish) {
            RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            LinearInterpolator lin = new LinearInterpolator();
//        Log.i("AFDFS",ll+"------------------------") ;
//        LogUtils.printI(TAG, "showPointer---fromDegrees=" + fromDegrees + ", toDegrees=" + toDegrees + ", ll=" + ll);

            rotate.setInterpolator(lin);
            rotate.setDuration(500);//设置动画持续时间
            rotate.setRepeatCount(0);//设置重复次数
            rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
            if (leftMeterPointer != null) {
                leftMeterPointer.startAnimation(rotate);
            }
        }
    }

    /**
     * 指针转动
     *
     * @param fromDegrees
     * @param toDegrees
     */
    public void showPointerStart(Float fromDegrees, Float toDegrees) {

        final RotateAnimation rotatest = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AccelerateDecelerateInterpolator lin = new AccelerateDecelerateInterpolator();
        rotatest.setInterpolator(lin);
        rotatest.setDuration(1000);//设置动画持续时间
        rotatest.setRepeatCount(0);//设置重复次数
        rotatest.setFillAfter(true);//动画执行完后是否停留在执行完的状态

        rotatest.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
                showAllTroubleLight();
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                try {
                    showPointerEnd(255f, 0f);
                } catch (Exception e) {
                    e.printStackTrace();
                }
                LogUtils.printI(TAG, "imageView----onAnimationEnd");
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
                LogUtils.printI(TAG, "imageView----onAnimationRepeat");
            }
        });


        leftMeterPointer.startAnimation(rotatest);

        RotateAnimation rotate2 = new RotateAnimation(0, 255f, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AccelerateDecelerateInterpolator lin2 = new AccelerateDecelerateInterpolator();
        rotate2.setInterpolator(lin2);
        rotate2.setDuration(900);//设置动画持续时间
        rotate2.setRepeatCount(0);//设置重复次数
        rotate2.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        Log.i("activityMeter", "==============右侧开始执行");
        rightMeterPointer.startAnimation(rotate2);

    }

    /**
     * 指针转动
     *
     * @param fromDegrees
     * @param toDegrees
     */
    public void showPointerEnd(Float fromDegrees, Float toDegrees) {
        final RotateAnimation rotate = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AccelerateDecelerateInterpolator lin = new AccelerateDecelerateInterpolator();
        rotate.setInterpolator(lin);
        rotate.setDuration(1000);//设置动画持续时间
        rotate.setRepeatCount(0);//设置重复次数
        rotate.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        leftMeterPointer.startAnimation(rotate);

        final RotateAnimation rotate1 = new RotateAnimation(255f, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        AccelerateDecelerateInterpolator lin1 = new AccelerateDecelerateInterpolator();
        rotate1.setInterpolator(lin1);
        rotate1.setDuration(900);//设置动画持续时间
        rotate1.setRepeatCount(0);//设置重复次数
        rotate1.setFillAfter(true);//动画执行完后是否停留在执行完的状态
        rotate1.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationStart(Animation animation) {
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                LogUtils.printI(MeterActivity.class.getSimpleName(), "imageViewright----onAnimationEnd");
                hideAllTroubleLight();
                meterLauncherAnimateFinish = true;
                meterAnimationEnd();

                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.HIDE_ALL_METER_WARING));
            }

            @Override
            public void onAnimationRepeat(Animation animation) {
            }
        });
        rightMeterPointer.startAnimation(rotate1);
    }


    @Override
    protected void updateOilProgress(float percent) {
        LogUtils.printI(TAG, "updateOilProgress---percent=" + percent);
        oilMeterView.setProgress(percent);
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
    public void onDestroyView() {
        super.onDestroyView();
        rightMeterPointer = null;
        leftMeterPointer = null;

    }

    @Override
    protected void onRight() {
        super.onRight();

    }


    @Override
    protected void onLeft() {
        super.onLeft();

    }

    @Override
    protected void onDown() {
        super.onDown();

    }

    @Override
    public void onStart() {
        super.onStart();
        MeterActivity.currentFragmentType = MeterFragmentType.CLASSIC;
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
    protected void onUp() {
        super.onUp();
    }

}
