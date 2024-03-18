package com.android.launcher.meter.view;

import android.content.Context;
import android.content.res.Resources;
import android.os.Handler;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

import dc.library.auto.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import static com.android.launcher.meter.MeterActivity.isCruiseControlStop;

/**
 * 定速巡航布局
 */
public class CruiseControlView extends FrameLayout {

    private static final String TAG = CruiseControlView.class.getSimpleName();

    //定速巡航
    protected ImageView cruiseControlIV;
    protected TextView speedTV;

    protected String speedUnit = "km/h";

    public CruiseControlView(@NonNull Context context) {
        this(context, null);
    }

    public CruiseControlView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CruiseControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr, 0);
    }

    public CruiseControlView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setupInit(context);
    }

    private void setupInit(Context context) {
        LayoutInflater.from(context).inflate(R.layout.layout_cruise_control, this, true);

        cruiseControlIV = findViewById(R.id.cruiseControlIV);
        speedTV = findViewById(R.id.speedTV);

        speedTV.setVisibility(View.INVISIBLE);
        cruiseControlIV.setVisibility(View.INVISIBLE);

        speedUnit = getResources().getString(R.string.speed_unit);

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
        try {
            EventBus.getDefault().unregister(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent event) {
        if (event.type == MessageEvent.Type.CRUISE_CONTROL_OFF) {
            try {
                setupCruiseControlOff();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.type == MessageEvent.Type.CRUISE_CONTROL_ON) {
            try {
                int speed = (int) event.data;
                setupCruiseControlOn(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.type == MessageEvent.Type.CRUISE_CONTROL_STOP) {
            try {
                cruiseControlStop();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.type == MessageEvent.Type.CRUISE_CONTROL_BREAKDOWN) {
            try {
                cruiseControlBreakdown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.type == MessageEvent.Type.CRUISE_CONTROL_WAIT_SET) {
            try {
                int speed = (int) event.data;
                cruiseControlWaitSet(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (event.type == MessageEvent.Type.SPEED_LIMITED_MODE) {
            try {
                int speed = (int) event.data;
                speedLimitedMode(speed);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * @description: 设置限速模式
     * @createDate: 2023/6/1
     */
    private void setupSpeedLimitedModeViewStatus(int setupSpeed) {
        LogUtils.printI(TAG, "setupSpeedLimitedModeViewStatus---setupSpeed=" + setupSpeed);
        if (cruiseControlIV != null) {
            try {
                cruiseControlIV.setVisibility(View.GONE);
                speedTV.setVisibility(View.VISIBLE);
                cruiseControlIV.setImageResource(R.drawable.ic_cruise_control_breakdown1);
                speedTV.setTextColor(getResources().getColor(R.color.colorYellow));
                speedTV.setText("LIM " + setupSpeed + speedUnit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param setupSpeed
     * @description: 定速巡航等待设定状态view的显示
     * @createDate: 2023/5/31
     */
    private void setupCruiseControlWaitSetViewStatus(int setupSpeed) {
        LogUtils.printI(TAG, "setupCruiseControlWaitSetViewStatus---setupSpeed=" + setupSpeed);
        if (cruiseControlIV != null) {
            try {
                cruiseControlIV.setVisibility(View.VISIBLE);
                speedTV.setVisibility(View.VISIBLE);
                cruiseControlIV.setImageResource(R.drawable.ic_cruise_control);
                speedTV.setTextColor(getResources().getColor(R.color.cl_67e712));
                speedTV.setText(getResources().getString(R.string.cruise_control_wait_set) + speedUnit);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    /**
     * @description: 定速巡航故障状态view的显示
     * @createDate: 2023/5/31
     */
    protected void setupCruiseControlBreakdownViewStatus() {
        LogUtils.printI(TAG, "setupCruiseControlBreakdownViewStatus---");
        if (cruiseControlIV != null) {
            try {
                cruiseControlIV.setVisibility(View.VISIBLE);
                speedTV.setVisibility(View.VISIBLE);
                cruiseControlIV.setImageResource(R.drawable.ic_cruise_control_breakdown);
                speedTV.setTextColor(getResources().getColor(R.color.colorRed));
                speedTV.setText(getResources().getString(R.string.cruise_control_breakdown));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description: 定速巡航停止状态 view的显示
     * @createDate: 2023/5/31
     */
    protected void setupCruiseControlStopViewStatus() {
        LogUtils.printI(TAG, "setupCruiseControlStopViewStatus---");
        if (cruiseControlIV != null) {
            try {
                cruiseControlIV.setVisibility(View.VISIBLE);
                speedTV.setVisibility(View.VISIBLE);
                cruiseControlIV.setImageResource(R.drawable.ic_cruise_control_breakdown1);
                speedTV.setTextColor(getResources().getColor(R.color.colorYellow));
                speedTV.setText(getResources().getString(R.string.cruise_control_stop));
            } catch (Resources.NotFoundException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @description: 定速巡航运行状态View的状态
     * @createDate: 2023/5/31
     */
    protected void setupCruiseControlViewRunStatus() {
        LogUtils.printI(TAG, "setupCruiseControlViewRunStatus---");
        cruiseControlIV.setVisibility(View.VISIBLE);
        speedTV.setVisibility(View.VISIBLE);
        cruiseControlIV.setImageResource(R.drawable.ic_cruise_control);
        speedTV.setTextColor(getResources().getColor(R.color.cl_67e712));
    }


    /**
     * @description: 关闭定速巡航
     * @createDate: 2023/6/24
     */
    public void setupCruiseControlOff() {
        LogUtils.printI(TAG, "setupCruiseControlOff---");
        if (!isCruiseControlStop) {
            if (cruiseControlIV != null) {
                cruiseControlIV.setVisibility(View.GONE);
                speedTV.setVisibility(View.GONE);
            }
        }
    }

    /**
     * @description: 开启定速巡航
     * @createDate: 2023/6/24
     */
    public void setupCruiseControlOn(int speed) {
        LogUtils.printI(TAG, "setupCruiseControlOn---speed=" + speed);
        if (!isCruiseControlStop) {
            if (cruiseControlIV != null) {
                try {
                    setupCruiseControlViewRunStatus();
                    speedTV.setText(speed + speedUnit);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * @description: 定速巡航停止显示
     * @createDate: 2023/6/24
     */
    public void cruiseControlStop() {
        LogUtils.printI(TAG, "cruiseControlStop---isCruiseControlStop=" + isCruiseControlStop);
        if (!isCruiseControlStop) {
            isCruiseControlStop = true;
            setupCruiseControlStopViewStatus();
            new Handler().postDelayed(new Runnable() {
                @Override
                public void run() {
                    LogUtils.printI(TAG, "onMessageEvent-----CRUISE_CONTROL_STOP 停止显示");
                    isCruiseControlStop = false;
                    if (cruiseControlIV != null) {
                        cruiseControlIV.setVisibility(View.GONE);
                        speedTV.setVisibility(View.GONE);
                    }
                }
            }, 10000);
        }
    }

    /**
     * @description: 定速巡航出现故障
     * @createDate: 2023/6/24
     */
    public void cruiseControlBreakdown() {
        LogUtils.printI(TAG, "cruiseControlBreakdown---isCruiseControlStop=" + isCruiseControlStop);
        if (!isCruiseControlStop) {
            setupCruiseControlBreakdownViewStatus();
        }
    }

    /**
     * @description: 定速巡航等待设定
     * @createDate: 2023/6/24
     */
    public void cruiseControlWaitSet(int setupSpeed) {
        LogUtils.printI(TAG, "cruiseControlWaitSet----isCruiseControlStop=" + isCruiseControlStop);
        if (isCruiseControlStop) {
            return;
        }
        setupCruiseControlWaitSetViewStatus(setupSpeed);
    }

    /**
     * @description: 限速模式
     * @createDate: 2023/6/24
     */
    public void speedLimitedMode(int setupSpeed) {
        LogUtils.printI(TAG, "cruiseControlWaitSet----isCruiseControlStop=" + isCruiseControlStop + ", setupSpeed=" + setupSpeed);
        if (isCruiseControlStop) {
            return;
        }
        setupSpeedLimitedModeViewStatus(setupSpeed);
    }

}
