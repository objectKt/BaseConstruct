package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.UnitUtils;

import org.greenrobot.eventbus.EventBus;

/**
 * @description: 行程-时速
 * @createDate: 2023/9/25
 */
public abstract class MileageSpeedFragBase extends MenuFragmentBase {

    protected TextView speedTV;
    protected TextView unitTV;

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.MILEAGE_SPEED;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        speedTV = view.findViewById(R.id.speedTV);
        unitTV = view.findViewById(R.id.unitTV);
        unitTV.setText(getResources().getString(R.string.speed_unit));

    }

    @Override
    protected void initData() {
        super.initData();
        try {
            speedTV.setText(App.carSpeed);
            if (MeterActivity.unitType == UnitType.KM.ordinal()) {
                unitTV.setText("km/h");
            } else {
                unitTV.setText("mph");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if(MeterActivity.currentMenuType == MenuType.MILEAGE_SPEED){
            if(event.type == MessageEvent.Type.UPDATE_SPEED){
                try {
//                    try {
//                        LogUtils.printI(TAG, "MessageEvent-----event="+event.type.name() +", data="+event.data);
//                    } catch (Exception e) {
//                        e.printStackTrace();
//                    }
                    if(event.data!=null){
                        String speedStr = (String) event.data;
                        int speed = Integer.parseInt(speedStr);
                        if(MeterActivity.unitType == UnitType.MI.ordinal()){
                            speed = (int) UnitUtils.kmSpeedToMiSpeed(speed);
                        }
                        speedTV.setText(speed);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }

    }

    @Override
    protected void onDown() {
        super.onDown();
        if(MeterActivity.currentMenuType == MenuType.MILEAGE_SPEED) {
            try {
                Fragment parentFragment = getParentFragment();
                if(parentFragment!=null){
                    if(parentFragment instanceof MileageHomeFragmentBase){
                        MileageHomeFragmentBase homeFragmentBase = (MileageHomeFragmentBase) parentFragment;
                        homeFragmentBase.setCurrentFragment(1);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onBack() {
        super.onBack();
        if(MileageHomeFragmentBase.currentPosition != 0){
            return;
        }
        if(MeterActivity.currentMenuType == MenuType.MILEAGE_SPEED) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.OPEN_HOME_FRAG));
        }
    }

    @Override
    protected void onOK() {
        super.onOK();
        if(MileageHomeFragmentBase.currentPosition != 0){
            return;
        }
        if(MeterActivity.currentMenuType == MenuType.MILEAGE_SPEED) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.OPEN_HOME_FRAG));
        }
    }
}
