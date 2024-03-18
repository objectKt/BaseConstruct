package com.android.launcher.meter;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.base.FragmentBase;
import com.android.launcher.base.IView;
import com.android.launcher.can.Can105;
import com.android.launcher.can.Can30d;
import com.android.launcher.meter.view.MenuPointView;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.LogUtils;

import java.util.List;

/**
 * @description:
 * @createDate: 2023/6/20
 */
public abstract class MeterFragmentBase extends FragmentBase<MeterPresenter> implements IView {

    protected String TAG;

    protected boolean oilAnimationStart = false;

    protected MenuPointView menuPointView;


    //油箱
//    public ImageView oil_icon;

    //
    public ACache aCache = ACache.get(App.getGlobalContext());


    public static volatile boolean meterLauncherAnimateFinish = false;


    protected MenuFragmentAdapter menuFragmentAdapter;

    protected ViewPager2 menuViewPager;

    protected ViewPager2.OnPageChangeCallback onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {

        @Override
        public void onPageSelected(int position) {
            super.onPageSelected(position);
            LogUtils.printI(TAG, "onPageSelected---position=" + position);
            if (menuPointView != null) {
                if (position != 0) {
                    menuPointView.setVisibility(View.VISIBLE);
                } else {
                    menuPointView.setVisibility(View.INVISIBLE);
                }
                menuPointView.updatePosition(position);
            }
            onMenuPositionChange(position);
            delayHideMenuPoint();
        }
    };

    public void onMenuPositionChange(int position) {

    }

    private void delayHideMenuPoint() {
        try {
            menuPointHandler.removeCallbacks(menuPointTask);
            menuPointHandler.postDelayed(menuPointTask, 3000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Handler menuPointHandler = new Handler(Looper.getMainLooper());

    private Runnable menuPointTask = new Runnable() {
        @Override
        public void run() {
            if (menuPointView != null) {
                menuPointView.setVisibility(View.INVISIBLE);
            }
        }
    };


    @Override
    protected MeterPresenter createPresenter() {
        return new MeterPresenter(getContext(), this);
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        TAG = this.getClass().getSimpleName();
//        //保持Fragment实例
//        setRetainInstance(true);
        try {
//            Bundle arguments = getArguments();
//            meterLauncherAnimateFinish = arguments.getBoolean("meterLauncherAnimateFinish", false);
//            LogUtils.printI(TAG, "initView----meterLauncherAnimateFinish=" + meterLauncherAnimateFinish);

            loadView(view);
            hideAllTroubleLight();
        } catch (Exception e) {
            e.printStackTrace();

        }
    }

    private void loadView(View view) {
        menuViewPager = view.findViewById(R.id.menuVP);
//        oil_icon = view.findViewById(R.id.oil_icon);
        menuPointView = view.findViewById(R.id.menuPointView);

        menuFragmentAdapter = new MenuFragmentAdapter(this, getMenuFragmentList());
        menuViewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        menuViewPager.setAdapter(menuFragmentAdapter);

        menuPointView.setPointNumber(menuFragmentAdapter.getItemCount());
        menuPointView.setVisibility(View.INVISIBLE);

        menuViewPager.registerOnPageChangeCallback(onPageChangeCallback);
    }

    protected abstract List<Fragment> getMenuFragmentList();

    protected void showAllTroubleLight() {
        LogUtils.printI(MeterFragmentBase.class.getSimpleName(), "showAllTroubleLight-----");
    }

    protected void hideAllTroubleLight() {
        LogUtils.printI(MeterFragmentBase.class.getSimpleName(), "hideAllTroubleLight-----");
    }


    @Override
    public void disposeMessageEvent(MessageEvent event) {
        if (event.type == MessageEvent.Type.SET_HEADREST_DOWN) { //头枕放倒,预留功能

        } else if (event.type == MessageEvent.Type.UPDATE_WATER_TEMP) { //水温
            try {
                LogUtils.printI(TAG, "event=" + event.type.name() + ", data=" + event.data);
                if (event.data != null) {
                    int temp = (int) event.data;
                    updateWaterTemp(temp);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.type == MessageEvent.Type.UPDATE_OIL_BOX) {
            if (event.data instanceof Float) {
                float oilPercent = (float) event.data;
                drawOil(oilPercent);
            }
        } else if (event.type == MessageEvent.Type.ON_BACK_KEY) {
            onBack();
        } else if (event.type == MessageEvent.Type.START_METER_ANIMATION) {
            try {
                LogUtils.printI(TAG,"startMeterAnimation---");
                startMeterAnimation();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.type == MessageEvent.Type.TO_NAV_MENU) {
            if (menuViewPager != null) {
                //打开导航菜单
                menuViewPager.setCurrentItem(3, false);
            }
        } else if (event.type == MessageEvent.Type.TO_NAV_HOME) {

        }else if(event.type == MessageEvent.Type.UPDATE_CAR_SPEED){
            try {
                updateCarSpeed(App.carSpeed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(event.type == MessageEvent.Type.UPDATE_ENGINE_SPEED){
            try {
                updateEngineSpeed(Can105.engineSpeed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    protected void onDown() {
        if (menuViewPager != null && menuViewPager.getCurrentItem() < (menuFragmentAdapter.getItemCount() - 1)) {
            int currentItem = menuViewPager.getCurrentItem();
            menuViewPager.setCurrentItem(++currentItem, true);
        }
    }


    protected void onUp() {
        if (menuViewPager != null && menuViewPager.getCurrentItem() > 0) {
            int currentItem = menuViewPager.getCurrentItem();
            menuViewPager.setCurrentItem(--currentItem, true);
        }
    }


    protected void onBack() {
        if (menuViewPager != null && menuFragmentAdapter != null && menuFragmentAdapter.getItemCount() > 0) {
            menuViewPager.setCurrentItem(0, false);
        }
    }

    protected void onLeft() {

    }

    protected void onRight() {

    }

    protected void onOK() {

    }

    @Override
    public void onStart() {
        super.onStart();

    }

    @Override
    public void onStop() {
        super.onStop();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        try {
            menuViewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        hideAllTroubleLight();
    }

    @Override
    public void onResume() {
        super.onResume();
        LogUtils.printI(TAG, "onResume-----meterLauncherAnimateFinish=" + meterLauncherAnimateFinish);
//        if(!meterLauncherAnimateFinish){
//            new Handler(Looper.getMainLooper()).postDelayed(() -> startMeterAnimation(),2000);
//        }
    }


    public void drawOil(float percent) {
        if (!meterLauncherAnimateFinish) {
            return;
        }
        try {
            LogUtils.printI(TAG, "drawOil----percent=" + percent + ", oilAnimationStart=" + oilAnimationStart);
            updateOilProgress(percent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected abstract void updateOilProgress(float percent);


    //更新水温
    public abstract void updateWaterTemp(int temp);

    //更新发动机转速
    public abstract void updateEngineSpeed(int engineSpeed);


    /**
     * @description: 开始仪表动画
     * @createDate: 2023/6/21
     */
    public abstract void startMeterAnimation();

    public abstract void updateCarSpeed(String speedData);


    public void meterAnimationEnd() {
        try {
            drawOil(Float.parseFloat(App.oilPercent));
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {
            updateWaterTemp(Can30d.currentWaterTemp);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
