package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.launcher.R;
import com.android.launcher.meter.MenuType;

/**
 * @date： 2023/11/21
 * @author: 78495
*/
public abstract class FMFragBase extends MenuFragmentBase{
    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.FM;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        TextView titleTV = view.findViewById(R.id.titleTV) ;
        titleTV.setText(getResources().getString(R.string.fm_radio));
    }

}
