package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import dc.library.utils.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.service.LivingService;
import dc.library.utils.global.type.UnitType;
import com.android.launcher.util.AppUtils;
import com.android.launcher.util.DateUtils;
import dc.library.utils.IconUtils;
import com.android.launcher.util.NumberUtils;
import com.android.launcher.util.UnitUtils;

import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

/**
 * 启动后
 * @date： 2023/11/18
 * @author: 78495
*/
public abstract class LauncherAfterFragBase extends MenuFragmentBase{

    protected TextView userMileTV;
    protected TextView userTimeTV;
    protected TextView totalMileTV;
    protected TextView totalTimeTV;
    protected TextView qtripTV;
    protected TextView averageSpeedTV;

    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.LAUNCHER_AFTER;
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        TextView titleTV = view.findViewById(R.id.titleTV);
        TextView totalDistanceTV = view.findViewById(R.id.totalDistanceTV);
        titleTV.setText(getResources().getString(R.string.after_starting_up));
        totalDistanceTV.setText(getResources().getString(R.string.total_distance));

        userMileTV = view.findViewById(R.id.userMileTV);
        userTimeTV = view.findViewById(R.id.userTimeTV);
        totalMileTV = view.findViewById(R.id.totalMileTV);
        totalTimeTV = view.findViewById(R.id.totalTimeTV);
        qtripTV = view.findViewById(R.id.qtripTV);
        averageSpeedTV = view.findViewById(R.id.averageSpeedTV);
       ImageView  centerIV = view.findViewById(R.id.centerIV);
       ImageView  leftIV = view.findViewById(R.id.leftIV);
       ImageView  rightIV = view.findViewById(R.id.rightIV);
        IconUtils.setColor(centerIV,getResources().getColor(R.color.cl_21c1fe));
        IconUtils.setColor(leftIV,getResources().getColor(R.color.cl_21c1fe));
        IconUtils.setColor(rightIV,getResources().getColor(R.color.cl_21c1fe));

        loadData();
    }



    private void loadData() {
        startTask(() -> {
            try {
                CarTravelTable travelTable = CarTravelRepository.getInstance().getData(getContext(), AppUtils.getDeviceId(getContext()));
                getActivity().runOnUiThread(() -> updateLaunchAfterData(travelTable));
            } catch (Exception e) {
                e.printStackTrace();
            }

        });
    }

    private void updateLaunchAfterData(CarTravelTable table) {
        if(table == null){
            return;
        }

        try {
            String userTime = DateUtils.getMileHour(LivingService.launchCarRunTime);
            String totalTime = DateUtils.getMileHour(table.getTotalRunTime());

            if (MeterActivity.unitType == UnitType.KM.ordinal()) {
                userMileTV.setText(String.format("%skm", NumberUtils.floatRoundDTo(LivingService.launchAfterMile, 2)));
                userTimeTV.setText(String.format("%sh", userTime));
                totalMileTV.setText(String.format("%skm", NumberUtils.floatRoundDTo(table.getTotalMile(), 2)));
                totalTimeTV.setText(String.format("%sh", totalTime));
                qtripTV.setText(String.format("%sl/100km", table.getCurrentQtrip()));
                averageSpeedTV.setText(String.format("%skm/h", LivingService.launchAverageSpeed));
            } else {
                userMileTV.setText(String.format("%smi", NumberUtils.floatRoundDTo(UnitUtils.kmToMi(LivingService.launchAfterMile), 2)));
                userTimeTV.setText(String.format("%sh", userTime));
                totalMileTV.setText(String.format("%smi", NumberUtils.floatRoundDTo(UnitUtils.kmToMi(table.getTotalMile()), 2)));
                totalTimeTV.setText(String.format("%sh", totalTime));
                qtripTV.setText(String.format("%sl/100mi", NumberUtils.floatRoundDTo(UnitUtils.kmQtripToMiQtrip(table.getCurrentQtrip()), 1)));
                averageSpeedTV.setText(String.format("%smph", (int) UnitUtils.kmSpeedToMiSpeed(LivingService.launchAverageSpeed)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if(event.type == MessageEvent.Type.REST_LAUNCHER_AFTER){
            startTask(() -> {
                try {
                    LivingService.launchAverageSpeed = 0;
                    LivingService.launcherTime = 0;

                    CarTravelTable travelTable = CarTravelRepository.getInstance().getData(getContext(), AppUtils.getDeviceId(getContext()));
                    travelTable.setCurrentQtrip(0.0f);
                    CarTravelRepository.getInstance().updateData(getContext(),travelTable);
                    if(getActivity()!=null){
                        getActivity().runOnUiThread(() -> updateLaunchAfterData(travelTable));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }else if(event.type == MessageEvent.Type.UPDATE_TOTAL_MILE){
            loadData();
        }else if(event.type == MessageEvent.Type.UPDATE_LAUNCH_AFTER){
            if(event.data instanceof CarTravelTable){
                CarTravelTable travelTable = (CarTravelTable) event.data;
                updateLaunchAfterData(travelTable);
            }
        }
    }
}
