package com.android.launcher.meter.sport;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;

import androidx.constraintlayout.widget.ConstraintLayout;

import com.android.launcher.R;
import com.android.launcher.meter.base.fragment.MediaFragmentBase;

/**
* @description:
* @createDate: 2023/9/21
*/
public  class SportMediaFrag extends MediaFragmentBase {
    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_classic_menu_item_media;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) musicIV.getLayoutParams();
        layoutParams.topMargin = (int) getResources().getDimension(R.dimen.dp_16);

        ConstraintLayout.LayoutParams layoutParams1 = (ConstraintLayout.LayoutParams) lyricTV.getLayoutParams();
        layoutParams1.topMargin = (int) getResources().getDimension(R.dimen.dp_28);
    }
}
