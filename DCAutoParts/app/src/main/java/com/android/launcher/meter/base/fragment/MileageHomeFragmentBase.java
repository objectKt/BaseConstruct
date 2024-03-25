package com.android.launcher.meter.base.fragment;

import android.os.Bundle;
import android.view.View;

import androidx.viewpager2.widget.ViewPager2;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.base.IPresenter;
import com.android.launcher.meter.MenuFragmentAdapter;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.util.LogUtils;

import org.greenrobot.eventbus.EventBus;


/**
* @description: 行程
* @createDate: 2023/9/21
*/
public abstract class MileageHomeFragmentBase extends MenuFragmentBase{

    protected ViewPager2 viewPager;

    protected MenuFragmentAdapter menuFragmentAdapter;

    protected View dot0V;
    protected View dot1V;
    protected View dot2V;
    protected View dot3V;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;

    public static volatile int currentPosition = 0;

    @Override
    protected void initView(View view, Bundle savedInstanceState) {

        dot0V = view.findViewById(R.id.dot0V);
        dot1V = view.findViewById(R.id.dot1V);
        dot2V = view.findViewById(R.id.dot2V);
        dot3V = view.findViewById(R.id.dot3V);

        viewPager = view.findViewById(R.id.viewPager);
        viewPager.setVisibility(View.VISIBLE);
        viewPager.setOrientation(ViewPager2.ORIENTATION_VERTICAL);
        viewPager.setOffscreenPageLimit(4);

        updateDotStatus(viewPager.getCurrentItem());

        onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                super.onPageScrolled(position, positionOffset, positionOffsetPixels);
                LogUtils.printI(TAG,"onPageScrolled----position="+position);
                currentPosition = position;
            }
        };

        viewPager.registerOnPageChangeCallback(onPageChangeCallback);

    }



    @Override
    protected MenuType getCurrentMenuType() {
        return MenuType.MILEAGE_HOME;
    }

    @Override
    protected IPresenter createPresenter() {
        return null;
    }

    @Override
    public void disposeMessageEvent(MessageEvent event) {
        super.disposeMessageEvent(event);
        if(event.type == MessageEvent.Type.OPEN_DRIVE_DISTANCE_FRAG){
            setCurrentFragment(1);
        }else if(event.type == MessageEvent.Type.OPEN_MILEAGE_SPEED_FRAG){
            setCurrentFragment(0);
        }else if(event.type == MessageEvent.Type.OPEN_POST_LAUNCH_HOME_FRAG){
            setCurrentFragment(2);
        }else if(event.type == MessageEvent.Type.OPEN_AFTER_RECOVERY_HOME_FRAG){
            setCurrentFragment(3);
        }else if(event.type == MessageEvent.Type.OPEN_AFTER_RECOVERY_REST_FRAG){
            hideDot();
        }else if(event.type == MessageEvent.Type.OPEN_POST_LAUNCH_RESET_FRAG){
            hideDot();
        }else if(event.type == MessageEvent.Type.OPEN_AFTER_RECOVERY_DETAI_FRAG){
            showDot();
        }else if(event.type == MessageEvent.Type.OPEN_POST_LAUNCH_DETAIL_FRAG){
            showDot();
        }

    }

    private void hideDot() {
        if(dot0V!=null){
            dot0V.setVisibility(View.GONE);
            dot1V.setVisibility(View.GONE);
            dot2V.setVisibility(View.GONE);
            dot3V.setVisibility(View.GONE);
        }
    }

    private void showDot() {
        if(dot0V!=null){
            dot0V.setVisibility(View.VISIBLE);
            dot1V.setVisibility(View.VISIBLE);
            dot2V.setVisibility(View.VISIBLE);
            dot3V.setVisibility(View.VISIBLE);
        }
    }

    @Override
    protected void onBack() {
        super.onBack();
        if(MeterActivity.currentMenuType == MenuType.MILEAGE_HOME) {
            EventBus.getDefault().post(new MessageEvent(MessageEvent.Type.OPEN_HOME_FRAG));
        }
    }

    private void updateDotStatus(int currentItem) {
        try {
            if(currentItem == 0){
                dot0V.setBackgroundResource(R.drawable.dot_focused);
                dot1V.setBackgroundResource(R.drawable.dot_normal);
                dot2V.setBackgroundResource(R.drawable.dot_normal);
                dot3V.setBackgroundResource(R.drawable.dot_normal);
            }else if(currentItem == 1){
                dot0V.setBackgroundResource(R.drawable.dot_normal);
                dot1V.setBackgroundResource(R.drawable.dot_focused);
                dot2V.setBackgroundResource(R.drawable.dot_normal);
                dot3V.setBackgroundResource(R.drawable.dot_normal);
            }else if(currentItem  == 2){
                dot0V.setBackgroundResource(R.drawable.dot_normal);
                dot1V.setBackgroundResource(R.drawable.dot_normal);
                dot2V.setBackgroundResource(R.drawable.dot_focused);
                dot3V.setBackgroundResource(R.drawable.dot_normal);
            }else if(currentItem == 3){
                dot0V.setBackgroundResource(R.drawable.dot_normal);
                dot1V.setBackgroundResource(R.drawable.dot_normal);
                dot2V.setBackgroundResource(R.drawable.dot_normal);
                dot3V.setBackgroundResource(R.drawable.dot_focused);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void setCurrentFragment(int position) {
        if(viewPager!=null){
            viewPager.setCurrentItem(position,true);
            updateDotStatus(position);

        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        try {
            viewPager.unregisterOnPageChangeCallback(onPageChangeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
