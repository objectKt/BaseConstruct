package com.android.launcher.meter.view;

import android.content.Context;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.FrameLayout;
import android.widget.TextView;

import io.reactivex.rxjava3.annotations.NonNull;
import io.reactivex.rxjava3.annotations.Nullable;

import com.android.launcher.App;
import dc.library.utils.event.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.type.GearsType;
import com.dc.auto.library.launcher.util.ACache;
import com.android.launcher.util.FuncUtil;
import dc.library.utils.StringUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class GearsView extends FrameLayout {

    protected TextView degree_r, degree_n, degree_d, degree_p;

    //驾驶模式
    protected TextView car_mode;

    //
    protected ACache aCache = ACache.get(App.getGlobalContext());


    public GearsView(@NonNull Context context) {
        this(context,null);
    }

    public GearsView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public GearsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        this(context, attrs, defStyleAttr,0);
    }

    public GearsView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);

        LayoutInflater.from(context).inflate(R.layout.layout_gears,this,true);

        degree_r = findViewById(R.id.degree_r);
        degree_n = findViewById(R.id.degree_n);
        degree_d = findViewById(R.id.degree_d);
        degree_p = findViewById(R.id.degree_p);
        car_mode = findViewById(R.id.car_mode);

        try {
            //切换运动模式
            String mode = aCache.getAsString("CMS");
            car_mode.setText(mode);

        } catch (Exception e) {
            e.printStackTrace();
        }
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
        if(event.type == MessageEvent.Type.UPDATE_DRIVE_MODE_VIEW){
            try {
                if(car_mode != null){
                    String driveMode = (String) event.data;
                    updateDriveStatus(driveMode);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }else if(event.type == MessageEvent.Type.UPDATE_GEARS_STATUS){
            try {
                if(degree_d!=null){
                    GearsType gearsType = (GearsType) event.data;
                    updateGearsStatus(gearsType);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    /**
     * 更新档位状态
     *
     * @date： 2023/10/9
     * @author: 78495
     */
    private void updateGearsStatus(GearsType gearsType) {
        if (gearsType == GearsType.TYPE_P) {
            FuncUtil.degree = false;
            if(degree_p!=null){
                degree_d.setText("D");
                setGearsSelected(degree_p);
                setGearsUnselected(degree_d);
                setGearsUnselected(degree_n);
                setGearsUnselected(degree_r);
            }
        } else if (gearsType == GearsType.TYPE_R) {
            if(degree_p!=null){
                degree_d.setText("D");
                setGearsUnselected(degree_p);
                setGearsUnselected(degree_d);
                setGearsUnselected(degree_n);
                setGearsSelected(degree_r);
            }
        } else if (gearsType == GearsType.TYPE_N) {
            if(degree_p!=null){
                degree_d.setText("D");
                setGearsUnselected(degree_p);
                setGearsUnselected(degree_d);
                setGearsSelected(degree_n);
                setGearsUnselected(degree_r);
            }
        } else if (isDGear(gearsType)) {
            if(degree_p!=null){
                if(gearsType == GearsType.TYPE_D || gearsType == GearsType.TYPE_D_OTHER){
                    degree_d.setText("D");
                }else if(gearsType == GearsType.TYPE_D1){
                    degree_d.setText("D1");
                }else if(gearsType == GearsType.TYPE_D2){
                    degree_d.setText("D2");
                }else if(gearsType == GearsType.TYPE_D3){
                    degree_d.setText("D3");
                }else if(gearsType == GearsType.TYPE_D4){
                    degree_d.setText("D4");
                }else if(gearsType == GearsType.TYPE_D5){
                    degree_d.setText("D5");
                }else if(gearsType == GearsType.TYPE_D6){
                    degree_d.setText("D6");
                }

                setGearsUnselected(degree_p);
                setGearsSelected(degree_d);
                setGearsUnselected(degree_n);
                setGearsUnselected(degree_r);


            }
        }
    }

    private void setGearsSelected(TextView textView) {
        textView.setTextSize(20);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
        textView.setBackgroundResource(R.drawable.bg_gears);
    }

    private void setGearsUnselected(TextView textView) {
        textView.setTextSize(14);
        textView.setTypeface(Typeface.defaultFromStyle(Typeface.NORMAL));
        textView.setBackgroundResource(0);
    }

    /**
     * 是否是D挡
     *
     * @date： 2023/10/9
     * @author: 78495
     */
    private boolean isDGear(GearsType gearsType) {
        return gearsType == GearsType.TYPE_D || gearsType == GearsType.TYPE_D1
                || gearsType == GearsType.TYPE_D_OTHER || gearsType == GearsType.TYPE_D2
                || gearsType == GearsType.TYPE_D3 || gearsType == GearsType.TYPE_D4
                || gearsType == GearsType.TYPE_D5 || gearsType == GearsType.TYPE_D6;
    }

    public void updateDriveStatus(String driveMode) {
        if (car_mode != null) {
            car_mode.setText(StringUtils.filterNULL(driveMode));
        }
    }
}
