package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.NonNull;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.AppUtils;
import com.android.launcher.util.LanguageUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.UnitUtils;

import java.math.BigDecimal;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import module.module_db.entity.CarTravelTable;
import module.module_db.repository.CarTravelRepository;

/**
* @description: 菜单主页
* @createDate: 2023/9/24
*/
public abstract class HomeMenuFragBase extends MenuFragmentBase {

    private TextView userMileTV;
    private TextView totalMileTV;
    private  String deviceId;
    protected TextView mileUnitTV;

    private ScheduledExecutorService executorService;


    private Handler handler = new Handler(Looper.getMainLooper()) {
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            try {
                CarTravelTable travelTable = (CarTravelTable) msg.obj;
                LogUtils.printI(TAG, "handleMessage---travelTable=" + travelTable);

                float totalMile = travelTable.getTotalMile();
                float userMile = travelTable.getUserMile();
                setMileData(totalMile, userMile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    };


    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.HOME;
    }

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if(event.type == MessageEvent.Type.REST_AFTER_RECOVERY){
            if(userMileTV!=null){
                userMileTV.setText("0.0");
            }
        }else if(event.type == MessageEvent.Type.UPDATE_TOTAL_MILE){
            loadData();
        }
    }


    private void loadData() {
        startTask(() -> {
            try {
                CarTravelTable carTravelTable = CarTravelRepository.getInstance().getData(App.getGlobalContext(), deviceId);
                if (carTravelTable == null) {
                    return;
                }
                Message message = Message.obtain();
                message.obj = carTravelTable;
                handler.sendMessage(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        mileUnitTV = view.findViewById(R.id.mileUnitTV);
        mileUnitTV.setText(getResources().getString(R.string.mile_unit));
        userMileTV = view.findViewById(R.id.currentMileTV);
        totalMileTV = view.findViewById(R.id.totalMileTV);

        deviceId = AppUtils.getDeviceId(getContext());


        try {
            executorService = Executors.newSingleThreadScheduledExecutor();

            //5秒更新一次数据
            executorService.scheduleAtFixedRate(() -> {
                try {
                    CarTravelTable carTravelTable = CarTravelRepository.getInstance().getData(App.getGlobalContext(), deviceId);
                    if (carTravelTable == null) {
                        return;
                    }
                    Message message = Message.obtain();
                    message.obj = carTravelTable;
                    handler.sendMessage(message);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            },1000,5000, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMileData(float totalMile, float userMile) {
        try {
            if (MeterActivity.unitType == UnitType.MI.ordinal()) {
                totalMile = UnitUtils.kmToMi(totalMile);
                userMile = UnitUtils.kmToMi(userMile);
            } else {
                if (!LanguageUtils.isCN()) {
                    totalMile = UnitUtils.kmToMi(totalMile);
                    userMile = UnitUtils.kmToMi(userMile);
                }
            }

            BigDecimal totalbigDecimal = new BigDecimal(totalMile);
            BigDecimal userbigDecimal = new BigDecimal(userMile);
            totalbigDecimal = totalbigDecimal.setScale(0, BigDecimal.ROUND_HALF_UP);
            userbigDecimal = userbigDecimal.setScale(1, BigDecimal.ROUND_HALF_UP);
            totalMileTV.setText(totalbigDecimal.toString());
            userMileTV.setText(userbigDecimal.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            executorService.shutdown();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
