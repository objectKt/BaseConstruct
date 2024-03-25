package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.util.BigDecimalUtils;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

import org.greenrobot.eventbus.EventBus;

import java.text.DecimalFormat;

/**
 * @description: 时速校准
 * @createDate: 2023/9/22
 */
public abstract class SpeedCalibrationFragBase extends MenuFragmentBase {

    private TextView speedTV;
    private TextView currentSpeedTV;
    private TextView adjustSpeedTV;
    private int originalSpeed;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        TextView titleTV = view.findViewById(R.id.titleTV);
        titleTV.setText(getResources().getString(R.string.speed_calibration));


        speedTV = view.findViewById(R.id.speedAdjustTV);
        currentSpeedTV = view.findViewById(R.id.currentSpeedTV);
        adjustSpeedTV = view.findViewById(R.id.adjustSpeedTV);

        currentSpeedTV.setText(getResources().getString(R.string.read_speed) + " " + App.originalSpeed + getResources().getString(R.string.speed_unit));
    }

    @Override
    protected void initData() {
        try {
            originalSpeed = App.originalSpeed;
            float adjustValue = SPUtils.getFloat(speedTV.getContext(), SPUtils.SPEED_ADJUST, 1.00f);
            DecimalFormat df = new DecimalFormat("0.00");
            speedTV.setText(df.format(adjustValue));

            int adjustSpeed = (int) (App.originalSpeed * adjustValue);
            adjustSpeedTV.setText(getResources().getString(R.string.calibrated_speed) + " " + adjustSpeed + getResources().getString(R.string.speed_unit));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.SPEED_CALIBRATION;
    }


    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if (event.type == MessageEvent.Type.SPEED_ADJUST_SUBTRACT) {
            if (speedTV == null) {
                return;
            }
            try {
                float adjustValue = SPUtils.getFloat(speedTV.getContext(), SPUtils.SPEED_ADJUST, 1.00f);
                if (adjustValue <= 0.90f) {
                    adjustValue = 0.90f;
                } else {
                    adjustValue = BigDecimalUtils.sub(String.valueOf(adjustValue), String.valueOf(0.01f));
                }

                DecimalFormat df = new DecimalFormat("0.00");
                speedTV.setText(df.format(adjustValue));

                SPUtils.putFloat(speedTV.getContext(), SPUtils.SPEED_ADJUST, adjustValue);

                int adjustSpeed = (int) (originalSpeed * adjustValue);
                adjustSpeedTV.setText(getResources().getString(R.string.calibrated_speed) + " " + adjustSpeed + getResources().getString(R.string.speed_unit));
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.type == MessageEvent.Type.SPEED_ADJUST_PLUS) {
            if (speedTV == null) {
                return;
            }
            try {
                float adjustValue = SPUtils.getFloat(speedTV.getContext(), SPUtils.SPEED_ADJUST, 1.00f);
                LogUtils.printI(TAG, "adjustValue=" + adjustValue);
                if (adjustValue >= 1.10f) {
                    adjustValue = 1.10f;
                } else {
                    adjustValue = BigDecimalUtils.add(String.valueOf(adjustValue), String.valueOf(0.01f));
                }
                DecimalFormat df = new DecimalFormat("0.00");
                speedTV.setText(df.format(adjustValue));

                SPUtils.putFloat(speedTV.getContext(), SPUtils.SPEED_ADJUST, adjustValue);
                int adjustSpeed = (int) (originalSpeed * adjustValue);
                adjustSpeedTV.setText(getResources().getString(R.string.calibrated_speed) + " " + adjustSpeed + getResources().getString(R.string.speed_unit));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
