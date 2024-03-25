package com.android.launcher.meter.sport;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.base.fragment.MenuFragmentBase;

/**
 * 发动机转速
 * @date： 2023/11/24
 * @author: 78495
*/
public class TechEngineSpeedFrag extends MenuFragmentBase {

    private TextView engineSpeedTV;

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.ENGINE_SPEED;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        engineSpeedTV = view.findViewById(R.id.engineSpeedTV);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_sport_engine_speed;
    }
}
