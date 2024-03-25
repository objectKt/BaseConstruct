package com.android.launcher.meter.sport;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher.R;
import com.android.launcher.meter.base.fragment.TyreFragmentBase;
import com.android.launcher.util.DensityUtil;

/**
* @description:
* @createDate: 2023/9/22
*/
public class SportTyreFrag extends TyreFragmentBase {
    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_classic_menu_item_tyre;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);
        TextView titleTV = view.findViewById(R.id.titleTV);
        ImageView leftLineIV = view.findViewById(R.id.leftLineIV);
        ImageView rightLineIV = view.findViewById(R.id.rightLineIV);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) titleTV.getLayoutParams();
        layoutParams.topMargin = DensityUtil.dip2px(getContext(),46);

        ConstraintLayout.LayoutParams leftLayoutParams = (ConstraintLayout.LayoutParams) leftLineIV.getLayoutParams();
        leftLayoutParams.leftMargin = DensityUtil.dip2px(getContext(),100);
        leftLayoutParams.rightMargin = DensityUtil.dip2px(getContext(),8);

        ConstraintLayout.LayoutParams rightLayoutParams = (ConstraintLayout.LayoutParams) rightLineIV.getLayoutParams();
        rightLayoutParams.rightMargin = DensityUtil.dip2px(getContext(),100);
        rightLayoutParams.leftMargin = DensityUtil.dip2px(getContext(),8);
    }
}
