package com.android.launcher.meter.base.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.ACache;
import com.android.launcher.util.AlertMessage;
import com.android.launcher.util.AppUtils;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.UnitUtils;
import com.android.launcher.vo.AlertVo;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import module.module_db.entity.CarTravelTable;
import module.module_db.repository.CarTravelRepository;

/**
* @description:
* @createDate: 2023/9/21
*/
public abstract class CarMessageFragBase extends MenuFragmentBase {

    private FrameLayout main_tain_info_alert, main_tain_info_done;

    private TextView main_tain_info_alert_detail, main_tain_info_alert_img, main_tain_info_detail, main_tain_info_button;


    private volatile boolean flg;
    private ACache aCache = ACache.get(App.getGlobalContext());

    private int showindex;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        main_tain_info_alert = view.findViewById(R.id.main_tain_info_alert);
        main_tain_info_done = view.findViewById(R.id.main_tain_info_done);

        main_tain_info_detail = view.findViewById(R.id.main_tain_info_detail);
        main_tain_info_button = view.findViewById(R.id.main_tain_info_button);
        main_tain_info_alert_detail = view.findViewById(R.id.main_tain_info_alert_detail);
        main_tain_info_alert_img = view.findViewById(R.id.main_tain_info_alert_img);
    }

    @Override
    protected void initData() {
        if (MeterActivity.infoList.size() > 0) {
            initAlertShow();
            Log.i("alertInfoshow", "---------------2222--" + MeterActivity.infoList.size());
        } else {
            initInfoShow();
        }
    }

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.MESSAGE;
    }

    public void initAlertShow() {

        main_tain_info_done.setVisibility(View.INVISIBLE);
        main_tain_info_alert.setVisibility(View.VISIBLE);

        showindex = MeterActivity.infoList.size();
        AlertVo alertVo = new ArrayList<>(MeterActivity.infoList).get(MeterActivity.infoList.size() - 1);
//        Log.i("alertInfoshow","---------------3333--"+alertVo.toString()) ;
        if (alertVo.getAlertMessage() != null) {
            main_tain_info_alert_detail.setText(alertVo.getAlertMessage());
        }
        if (alertVo.getAlertImg() != 0) {
            Drawable drawable = App.getGlobalContext().getResources().getDrawable(alertVo.getAlertImg(), null);
            main_tain_info_alert_img.setBackground(drawable);
        }

    }

    /**
     * 没有告警信息的时候显示保养内容
     */
    private void initInfoShow() {

        new Thread(() -> {
            try {
                CarTravelTable carTravelTable = CarTravelRepository.getInstance().getData(getContext(), AppUtils.getDeviceId(getContext()));
                if(carTravelTable!=null){
                    getActivity().runOnUiThread(() -> {
                        try {
                            float userMile = carTravelTable.getUserMile();
                            float maintainDistance = 5000f;
                            if(MeterActivity.unitType == UnitType.MI.ordinal()){
                                userMile = UnitUtils.kmToMi(userMile);
                                maintainDistance = UnitUtils.kmToMi(maintainDistance);
                            }
                            if (userMile > maintainDistance) {
                                flg = true;
                                main_tain_info_detail.setText(getContext().getResources().getString(R.string.message2) + userMile + getContext().getResources().getString(R.string.mile_unit)+getContext().getResources().getString(R.string.message3));
                                main_tain_info_button.setVisibility(View.GONE);
                                main_tain_info_detail.setVisibility(View.VISIBLE);
                            } else {
                                flg = false;
                                // 判断 显示内容
                                main_tain_info_button.setText(getContext().getResources().getString(R.string.message1));
                                main_tain_info_button.setVisibility(View.VISIBLE);
                                main_tain_info_detail.setVisibility(View.GONE);
                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }

                    });
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();

    }

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        try {
            if (event.type == MessageEvent.Type.UPDATE_CAR_MESSAGE) {
                LogUtils.printI(TAG, "onMessageEvent-----UPDATE_CAR_MESSAGE----"+event.data);
                AlertVo alertVo = (AlertVo) event.data;

                Map<String,String> param = new HashMap<>()  ;
                param.put("color",alertVo.getAlertColor()) ;
                param.put("Img",alertVo.getAlertImg()+"") ;
                param.put("message",alertVo.getAlertMessage());
                updateData(param);
            }else if(event.type == MessageEvent.Type.NEED_MAINTAIN_MESSAGE){
                Map<String,String> param = new HashMap<>()  ;
                param.put("color", AlertMessage.TEXT_WHITE+"") ;
                param.put("Img",0+"") ;
                param.put("message",(String) event.data);
                updateData(param);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void updateData(Map<String, String> params) {
        if (params != null) {
            Log.i("maininfo", params.toString() + "--------------------");
            if (params.containsKey("back")) {
                String back = params.get("back");
                Log.i("alertInfo", "是否为空" + "===========================" + back);
                if (back != null) {
                    Log.i("alertInfo", "是否为空" + "==============list长度=============" + showindex);
                    if (showindex > 1) {
                        showindex = showindex - 1;
                        final AlertVo alertVo = new ArrayList<>(MeterActivity.infoList).get(showindex - 1);
                        main_tain_info_alert_detail.setText(alertVo.getAlertMessage());
                    }
                }
            } else {

                if (!FuncUtil.ALERTHIDDEN) {
                    final AlertVo alertVo = new AlertVo();
                    alertVo.setAlertMessage(params.get("message"));
                    alertVo.setAlertColor(params.get("color"));
                    alertVo.setAlertImg(Integer.parseInt(params.get("Img")));

                    main_tain_info_done.setVisibility(View.INVISIBLE);
                    main_tain_info_alert.setVisibility(View.VISIBLE);

                    if (alertVo.getAlertMessage() != null) {
                        main_tain_info_alert_detail.setText(alertVo.getAlertMessage());
                        if (alertVo.getAlertColor() != null && !alertVo.getAlertColor().equals("-1")) {
                            main_tain_info_alert_detail.setTextColor(Color.parseColor(alertVo.getAlertColor()));
                        }
                    }

                    if (alertVo.getAlertImg() != 0) {
                        Drawable drawable = App.getGlobalContext().getResources().getDrawable(alertVo.getAlertImg(), null);
                        main_tain_info_alert_img.setBackground(drawable);
                    }

                    showindex = MeterActivity.infoList.size();
                }
            }
        }
    }

    @Override
    protected void onBack() {
        super.onBack();
        if(MeterActivity.currentMenuType == MenuType.MESSAGE){
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.OPEN_MAINTAIN_HOME_FRAG));
        }
    }
}
