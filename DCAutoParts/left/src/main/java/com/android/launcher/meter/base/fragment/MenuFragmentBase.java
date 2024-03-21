package com.android.launcher.meter.base.fragment;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.base.FragmentBase;
import com.android.launcher.base.IPresenter;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;

/**
* @description:
* @createDate: 2023/9/21
*/
public abstract class MenuFragmentBase extends FragmentBase<IPresenter> {


    @Override
    public void disposeMessageEvent(MessageEvent event) {

    }

    @Override
    public void onResume() {
        super.onResume();
        MeterActivity.currentMenuType = getCurrentMenuType();
        initData();
    }

    protected abstract MenuType getCurrentMenuType();



    protected  void initData(){};

    protected  void onDown(){

    };

    protected  void onUp(){};


    protected void onBack() {

    }

    protected void onLeft() {

    }

    protected void onRight() {

    }

    protected void onOK() {

    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }
}
