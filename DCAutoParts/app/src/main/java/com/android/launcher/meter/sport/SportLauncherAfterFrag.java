package com.android.launcher.meter.sport;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher.R;
import com.android.launcher.meter.base.fragment.LauncherAfterFragBase;
import com.android.launcher.util.DensityUtil;

/**
 * @date： 2023/11/21
 * @author: 78495
*/
public class SportLauncherAfterFrag extends LauncherAfterFragBase {

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_classic_launcher_after_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        TextView titleTV = view.findViewById(R.id.titleTV);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) titleTV.getLayoutParams();
        layoutParams.topMargin = DensityUtil.dip2px(getContext(),46);

    }
}
