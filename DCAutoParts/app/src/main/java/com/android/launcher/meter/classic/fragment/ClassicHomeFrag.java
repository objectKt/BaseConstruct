package com.android.launcher.meter.classic.fragment;

import android.os.Bundle;
import android.view.View;

import com.android.launcher.R;
import com.android.launcher.meter.base.fragment.HomeMenuFragBase;

/**
* @description:
* @createDate: 2023/9/24
*/
public class ClassicHomeFrag extends HomeMenuFragBase {

    @Override
    protected int getContentLayoutId() {
        return R.layout.layout_menu_item_mile_home;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        super.initView(view, savedInstanceState);

        view.findViewById(R.id.rootCL).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
