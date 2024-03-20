package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.launcher.App;
import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import dc.library.ui.view.dashboard.AttentionAidSysView;
import com.android.launcher.service.LivingService;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.DateUtils;

/**
 * @date： 2023/11/21
 * @author: 78495
 */
public abstract class AttentionAidSysFragBase extends MenuFragmentBase {

    //休息时间
    private TextView restTimeTV;

    private ACache aCache = ACache.get(App.getGlobalContext());
    private TextView systemStandbyTV;
    private AttentionAidSysView attentionAidSysView;

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.ATTENTION;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        restTimeTV = view.findViewById(R.id.restTimeTV);
        systemStandbyTV = view.findViewById(R.id.systemStandbyTV);
        attentionAidSysView = view.findViewById(R.id.attentionAidSysView);

    }

    @Override
    protected void initData() {
        super.initData();
        try {
//            if(App.startAss){
//                systemStandbyTV.setVisibility(View.GONE);
//                String time = aCache.getAsString("MidAttention")  ;
//                String alertTime = FuncUtil.getTwoDateDifferenceFomatter(FuncUtil.getCurrentDate(),time) ;
//                restTimeTV.setText(getContext().getResources().getString(R.string.last_break)+" "+alertTime+" "+getContext().getResources().getString(R.string.hours_ago));
//            }else{
//                systemStandbyTV.setVisibility(View.VISIBLE);
//                restTimeTV.setText(getResources().getString(R.string.attention_assist_system_not_activate));
//            }

            systemStandbyTV.setVisibility(View.GONE);


//            String time = aCache.getAsString("MidAttention")  ;
//            String alertTime = FuncUtil.getTwoDateDifferenceFomatter(FuncUtil.getCurrentDate(),time) ;

            float hour = DateUtils.computeHourNumber(LivingService.launchCarRunTime);
            if (hour < 3.0f) {
                //变绿， 注意力最高
                attentionAidSysView.changeColor(R.color.attention_aid_sys_green);
            } else if (hour >= 3.0f && hour < 4.0f) {
                //变黄， 注意力低
                attentionAidSysView.changeColor(R.color.oilLineSelect);
            } else {
                //变红，注意力很低
                attentionAidSysView.changeColor(R.color.colorRed);
            }

            String mileHour = DateUtils.getMileHour(LivingService.launchCarRunTime);
            restTimeTV.setText(getContext().getResources().getString(R.string.last_break) + " " + mileHour + " " + getContext().getResources().getString(R.string.hours_ago));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
