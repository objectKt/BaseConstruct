package com.android.launcher.meter.map;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher.R;
import com.android.launcher.meter.base.fragment.HomeMenuFragBase;
import com.android.launcher.util.DensityUtil;

/**
* @description:
* @createDate: 2023/9/24
*/
public class MapHomeFrag extends HomeMenuFragBase {

    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_menu_item_mile_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        TextView mileUnitTV = view.findViewById(R.id.mileUnitTV);
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) mileUnitTV.getLayoutParams();
        layoutParams.topMargin = DensityUtil.dip2px(getContext(),40);
    }
}
