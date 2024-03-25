package com.android.launcher.meter.sport;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher.R;
import com.android.launcher.meter.base.fragment.SpeedCalibrationFragBase;
import com.android.launcher.util.DensityUtil;

/**
* @description: 时速校准
* @createDate: 2023/9/22
*/
public class SportSpeedCalibrationFrag extends SpeedCalibrationFragBase {
    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_classic_menu_item_speed_calib;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        TextView titleTV = view.findViewById(R.id.titleTV);

        LinearLayout.LayoutParams layoutParams = (LinearLayout.LayoutParams) titleTV.getLayoutParams();
        layoutParams.topMargin = DensityUtil.dip2px(getContext(),46);
    }
}
