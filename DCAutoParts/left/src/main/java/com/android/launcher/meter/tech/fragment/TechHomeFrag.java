package com.android.launcher.meter.tech.fragment;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher.R;
import com.android.launcher.meter.base.fragment.HomeMenuFragBase;

/**
* @description:
* @createDate: 2023/9/24
*/
public class TechHomeFrag extends HomeMenuFragBase {

    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_menu_item_mile_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mileUnitTV.getLayoutParams();
        layoutParams.topMargin = 0;
        mileUnitTV.setLayoutParams(layoutParams);
    }
}
