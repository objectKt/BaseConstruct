package com.android.launcher.meter.sport;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher.R;
import com.android.launcher.meter.base.fragment.FMFragBase;
import com.android.launcher.util.DensityUtil;

/**
* @description:
* @createDate: 2023/9/21
*/
public class SportFMFrag extends FMFragBase {
    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_classic_menu_item_fm;
    }


    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        TextView titleTV = view.findViewById(R.id.titleTV);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) titleTV.getLayoutParams();
        layoutParams.topMargin = DensityUtil.dip2px(getContext(),46);
    }
}
