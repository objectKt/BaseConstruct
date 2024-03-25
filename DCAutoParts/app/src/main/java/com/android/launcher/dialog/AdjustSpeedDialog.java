package com.android.launcher.dialog;

import android.content.Context;
import android.text.TextUtils;
import android.view.View;

import androidx.annotation.NonNull;

import com.android.launcher.R;
import com.android.launcher.util.StringUtils;
import com.lxj.xpopup.core.CenterPopupView;

/**
 * 时速调节对话框
 * @date： 2023/11/22
 * @author: 78495
*/
public class AdjustSpeedDialog extends CenterPopupView {

    public AdjustSpeedDialog(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate() {
        super.onCreate();


    }

    @Override
    protected int getImplLayoutId() {
        return R.layout.dialog_speed_adjust;
    }



}
