package com.android.launcher.meter.sport;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher.R;
import com.android.launcher.meter.base.fragment.MapFragmentBase;
import com.android.launcher.util.DensityUtil;

/**
* @description:
* @createDate: 2023/9/21
*/
public class SportMapFrag extends MapFragmentBase {
    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_tech_map;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        FrameLayout mapFL = view.findViewById(R.id.mapFL);

        FrameLayout.LayoutParams layoutParams = (FrameLayout.LayoutParams) mapFL.getLayoutParams();
        layoutParams.topMargin = DensityUtil.dip2px(getContext(),26);
    }
}
