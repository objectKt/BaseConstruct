package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.adapter.WarnInfoAdapter;
import com.android.launcher.entity.Mile;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.util.ACache;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

/**
 * @description: 警告
 * @createDate: 2023/9/25
 */
@Deprecated
public abstract class WarnInfoFragBase extends MenuFragmentBase {

    protected RecyclerView contentRV;

    protected WarnInfoAdapter warnInfoAdapter = new WarnInfoAdapter(new ArrayList<>());

    public Mile mile;
    public volatile boolean flg;
    public ACache aCache = ACache.get(App.getGlobalContext());


    public Handler handler = new Handler(Looper.getMainLooper());

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        contentRV = view.findViewById(R.id.contentRV);

        contentRV.setLayoutManager(new LinearLayoutManager(getContext()));
        contentRV.setAdapter(warnInfoAdapter);

//        main_tain_info_alert = view.findViewById(R.id.main_tain_info_alert);
//        main_tain_info_done = view.findViewById(R.id.main_tain_info_done);
//
//        main_tain_info_detail = view.findViewById(R.id.main_tain_info_detail);
//        main_tain_info_button = view.findViewById(R.id.main_tain_info_button);
//        main_tain_info_alert_detail = view.findViewById(R.id.main_tain_info_alert_detail);
//        main_tain_info_alert_img = view.findViewById(R.id.main_tain_info_alert_img);

    }

    @Override
    protected void initData() {
        super.initData();

    }

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.WARN;
    }

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);


    }

    private void updateData(AlertVo alertVo) {
        if (alertVo != null && warnInfoAdapter != null) {
            warnInfoAdapter.addData(alertVo);
            FuncUtil.infoCount = warnInfoAdapter.getItemCount();
        }
    }

    @Override
    protected void onBack() {
        super.onBack();
        try {
            if (MeterActivity.currentMenuType == MenuType.WARN) {
                if (warnInfoAdapter.getItemCount() > 1) {
                    warnInfoAdapter.remove(warnInfoAdapter.getItemCount() - 1);
                    FuncUtil.infoCount = warnInfoAdapter.getItemCount();
                } else {
                    EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.OPEN_HOME_FRAG));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onOK() {
        super.onOK();
        try {
            if (MeterActivity.currentMenuType == MenuType.WARN) {
                EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.OPEN_HOME_FRAG));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
