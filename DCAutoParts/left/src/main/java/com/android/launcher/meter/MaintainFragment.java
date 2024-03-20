package com.android.launcher.meter;

import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.amap.api.navi.AMapNavi;
import com.android.launcher.App;
import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.base.CommonFragmentBase;
import com.android.launcher.entity.EngineOilMenuEntity;
import dc.library.ui.view.dashboard.CoolingLiquidView;
import com.android.launcher.service.LivingService;
import com.android.launcher.type.UnitType;
import com.android.launcher.util.AppUtils;
import com.android.launcher.util.BigDecimalUtils;
import com.android.launcher.util.FastJsonUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.NumberUtils;
import com.android.launcher.util.SPUtils;
import dc.library.utils.StringUtils;
import com.android.launcher.util.UnitUtils;
import com.dc.auto.library.module.module_db.entity.CarTravelTable;
import com.dc.auto.library.module.module_db.repository.CarTravelRepository;

import java.util.List;

/**
 * 保养页面
 */
public class MaintainFragment extends CommonFragmentBase {

    //机油液位正常
    private static final String NORMAL_LABEL = "38080713060C020400C5";
    //机油液位过高
    private static final String TOO_HIGH_LABEL = "38080713060C020700C2";

    private TextView warnMessageTV;
    private TextView maintainInfoTV;
    private CoolingLiquidView coolingLiquidView;
    private TextView oilLevelTV;
    private TextView frontRightTireTv;
    private TextView frontLeftTireTv;
    private TextView rearRightTireTv;
    private TextView rearLeftTireTv;

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        try {
            if (event.type == MessageEvent.Type.UPDATE_WATER_TEMP) { //水温
                LogUtils.printI(TAG, "UPDATE_WATER_TEMP-----data=" + event.data);
                try {
                    if (event.data != null) {
                        int temp = (int) event.data;
                        coolingLiquidView.setProgress(temp);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.UPDATE_ENGINE_OIL_STATUS) {
                try {
                    if (event.data instanceof EngineOilMenuEntity) {
                        EngineOilMenuEntity entity = (EngineOilMenuEntity) event.data;

                        LogUtils.printI(TAG, "UPDATE_ENGINE_OIL_STATUS---data=" + entity);

                        if (entity.getData().equalsIgnoreCase(NORMAL_LABEL)) {
                            if (oilLevelTV != null) {
                                oilLevelTV.setText(getResources().getString(R.string.oilLevelInfo1));
                            }
                        } else if (entity.getData().equalsIgnoreCase(TOO_HIGH_LABEL)) {
                            if (oilLevelTV != null) {
                                oilLevelTV.setText(getResources().getString(R.string.oilLevelInfo2));
                            }
                        } else if (entity.getData().startsWith("38080713060C02")) {
                            if (oilLevelTV != null) {
                                oilLevelTV.setText(getResources().getString(R.string.oilLevelInfo5));
                            }
                        }
//                        else if (entity.getStatus1().equals("10") && entity.getStatus2().equalsIgnoreCase("0F")) {
//
//                        }
                        else {
                            if (oilLevelTV != null) {
                                oilLevelTV.setText(getResources().getString(R.string.oilLevelInfo1));
                            }
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } else if (event.type == MessageEvent.Type.UPDATE_TAIYAI) {
                if (event != null && event.data instanceof String) {
                    String data = (String) event.data;
                    updateTire(data);
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        frontRightTireTv = view.findViewById(R.id.frontRightTireTv);
        frontLeftTireTv = view.findViewById(R.id.frontLeftTireTv);
        rearRightTireTv = view.findViewById(R.id.rearRightTireTv);
        rearLeftTireTv = view.findViewById(R.id.rearLeftTireTv);

        warnMessageTV = view.findViewById(R.id.warnMessageTV);
        maintainInfoTV = view.findViewById(R.id.maintainInfoTV);
        coolingLiquidView = view.findViewById(R.id.coolingLiquidView);
        oilLevelTV = view.findViewById(R.id.oilLevelTV);
        oilLevelTV.setText(getResources().getString(R.string.oilLevelInfo1));
        loadData();
    }

    private void loadData() {
        startTask(new Runnable() {

            private float userMile;

            @Override
            public void run() {
                try {
                    CarTravelTable travelTable = CarTravelRepository.getInstance().getData(getContext(), AppUtils.getDeviceId(getContext()));
                    if (travelTable != null) {
                        userMile = travelTable.getUserMile();

                        if (MeterActivity.unitType == UnitType.MI.ordinal()) {
                            userMile = UnitUtils.kmToMi(userMile);
                        }

                        getActivity().runOnUiThread(() -> {
                            try {
                                float denominator = 10000f;
                                String unit = "km";
                                if (MeterActivity.unitType == UnitType.MI.ordinal()) {
                                    unit = "mi";
                                    denominator = UnitUtils.kmToMi(10000f);
                                }

                                double diff = BigDecimalUtils.div(String.valueOf(userMile), String.valueOf(denominator), 1);

                                LogUtils.printI(TAG, "userMile=" + userMile + ", diff=" + diff);

                                if (diff <= 1.1 && diff >= 0.9) {
                                    String maintainHint = getResources().getString(R.string.maintain_hint);
                                    maintainHint = maintainHint.replace("unit", unit);
                                    maintainInfoTV.setText(maintainHint);
                                } else if (userMile > denominator) {
                                    float value = userMile - denominator;
                                    value = NumberUtils.floatRoundDTo(value, 4);
                                    maintainInfoTV.setText(getResources().getString(R.string.exceed) + "\n" + value + " " + unit + getResources().getString(R.string.timely_maintenance));
                                } else {
                                    float km = userMile - ((int) (userMile / denominator)) * denominator;
                                    float nextMainTainKm = denominator - km;
                                    LogUtils.printI(TAG, "km=" + km + ", nextMainTainKm=" + nextMainTainKm);
                                    maintainInfoTV.setText(getResources().getString(R.string.next_scheduled_maintenance) + "\n" + nextMainTainKm + " " + unit + getResources().getString(R.string.after_mile));
                                }
                            } catch (Exception e) {
                                e.printStackTrace();
                            }
                        });
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        startTask(new Runnable() {
            @Override
            public void run() {
                try {
                    String data = SPUtils.getString(App.getGlobalContext(), SPUtils.SP_TAIYAI, null);
                    LogUtils.printI(TAG, "data=" + data);
                    if (!TextUtils.isEmpty(data)) {
                        getActivity().runOnUiThread(() -> updateTire(data));
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        new Handler().postDelayed(() -> {
            if (coolingLiquidView != null) {
                coolingLiquidView.setProgress(LivingService.currentWaterTemp);
            }
        }, 500);

    }

    private void updateTire(String tireData) {
        try {
            if (frontLeftTireTv != null) {
                List<String> message = FastJsonUtils.fromList(tireData, String.class);
                String fl = message.get(5);
                String fr = message.get(6);
                String bl = message.get(7);
                String br = message.get(8);

                String frontLeft = (Integer.parseInt(fl, 16) - 70) + "";
                String frontRight = (Integer.parseInt(fr, 16) - 70) + "";
                String rearLeft = (Integer.parseInt(bl, 16) - 70) + "";
                String rearRight = (Integer.parseInt(br, 16) - 70) + "";

                final String unit = "kPa";
                frontLeftTireTv.setText(StringUtils.filterNULL(frontLeft) + unit);
                frontRightTireTv.setText(StringUtils.filterNULL(frontRight) + unit);
                rearLeftTireTv.setText(StringUtils.filterNULL(rearLeft) + unit);
                rearRightTireTv.setText(StringUtils.filterNULL(rearRight) + unit);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_maintain;
    }


    @Override
    public void onResume() {
        super.onResume();
        try {
            if (getActivity() != null) {
                MeterActivity activity = (MeterActivity) getActivity();
                activity.bottomStatusView.showWaterTempLineView(true);
                activity.bottomStatusView.showOilLineView(true);

                oilLevelTV.setText(getResources().getString(R.string.oilLevelInfo4));
                activity.startTestEngineOil();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        AMapNavi.destroy();

    }

    @Override
    public void onRight() {

    }

    @Override
    public void onLeft() {

    }

    @Override
    public void onOk() {

    }

    @Override
    public void onUp() {

    }

    @Override
    public void onDown() {

    }

    @Override
    public void onBack() {

    }

    @Override
    public void onStart() {
        super.onStart();
        MeterActivity.currentFragmentType = MeterFragmentType.MAINTAIN;
    }
}
