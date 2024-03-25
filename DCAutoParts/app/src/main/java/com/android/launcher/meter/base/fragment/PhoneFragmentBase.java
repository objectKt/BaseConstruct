package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.adapter.PhoneMidAdapter;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.vo.PhoneVo;

import org.greenrobot.eventbus.EventBus;

import java.util.List;

/**
* @description:
* @createDate: 2023/9/21
*/
public abstract class PhoneFragmentBase extends MenuFragmentBase {

    public ListView phonelist;
    List<PhoneVo> phoneVos;

    public PhoneMidAdapter phoneAdapter ;
    public int phonelistIndex;
    public FrameLayout frameLayout_phone_alert ,frameLayout_phone_list ;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        TextView titleTV = view.findViewById(R.id.titleTV);
        titleTV.setText(getResources().getString(R.string.phonebook));

        TextView hintTV = view.findViewById(R.id.hintTV);
        hintTV.setText(getResources().getString(R.string.phonebook_hint));

        phonelist = view.findViewById(R.id.phone_nav_list);
        frameLayout_phone_alert = view.findViewById(R.id.frameLayout_phone_alert) ;
        frameLayout_phone_list = view.findViewById(R.id.frameLayout_phone_list) ;
    }

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.PHONE;
    }

    @Override
    protected void onUp() {
        super.onUp();
        try {
            if (phoneVos.size() > 0) {
                phonelistIndex--;
                if (phonelistIndex < 0) {
                    phonelistIndex = 0;
                }
                phoneAdapter.setSelectedPosition(phonelistIndex);
                phoneAdapter.notifyDataSetInvalidated();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDown() {
        super.onDown();
        try {
            if (phoneVos.size() > 0) {

                phonelistIndex++;
                if (phonelistIndex > phoneVos.size() - 1) {
                    phonelistIndex = phoneVos.size() - 1;
                }
                phoneAdapter.setSelectedPosition(phonelistIndex);
                phoneAdapter.notifyDataSetInvalidated();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
