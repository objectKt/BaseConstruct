package com.android.launcher.meter.view;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.type.GearsType;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @date： 2023/11/7
 * @author: 78495
 */
public class SpeedCenterView extends FrameLayout {

    private TextView speedTV;
    private TextView speedUnitTV;

    protected ScheduledExecutorService timerTask;


    public SpeedCenterView(@NonNull Context context) {
        this(context, null);
    }

    public SpeedCenterView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SpeedCenterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public SpeedCenterView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        timerTask = Executors.newSingleThreadScheduledExecutor();
        LayoutInflater.from(context).inflate(R.layout.layout_speed_center, this, true);
        speedTV = findViewById(R.id.speedTV);
        speedUnitTV = findViewById(R.id.speedUnitTV);
        LogUtils.printI(SpeedCenterView.class.getSimpleName(), "SpeedCenterView");
    }


    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        try {
            EventBus.getDefault().register(this);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        LogUtils.printI(SpeedCenterView.class.getSimpleName(), "onDetachedFromWindow---App.carSpeed=" + App.carSpeed);
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if(event.type == MessageEvent.Type.UPDATE_CAR_SPEED){
            try {
                if (speedTV != null) {
                    speedTV.setText(StringUtils.filterNULL(App.carSpeed));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

}
