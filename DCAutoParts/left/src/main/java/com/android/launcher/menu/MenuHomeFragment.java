package com.android.launcher.menu;

import android.os.Bundle;
import android.os.Handler;
import android.view.View;

import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.android.launcher.MessageEvent;
import com.android.launcher.R;
import com.android.launcher.base.CommonFragmentBase;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.meter.MeterFragmentType;
import com.android.launcher.util.DensityUtil;
import com.android.launcher.util.LogUtils;

import java.util.ArrayList;
import java.util.List;

public class MenuHomeFragment extends CommonFragmentBase {

    private ViewPager2 menuVP;

    private MenuAdapter menuAdapter = new MenuAdapter(new ArrayList<>());

    private int selectPosition;
    private ViewPager2.OnPageChangeCallback onPageChangeCallback;

    @Override
    public void disposeMessageEvent(MessageEvent event) {

    }

    @Override
    protected void initView(View view, Bundle savedInstanceState) {
        menuVP = view.findViewById(R.id.menuVP);
        initMenuViewPager();

        Bundle arguments = getArguments();
        if(arguments!=null){
            selectPosition = arguments.getInt("position", 2);
        }
        LogUtils.printI(TAG, "selectPosition="+selectPosition);
    }

    @Override
    protected int getContentLayoutId() {
        return R.layout.fragment_menu_home;
    }

    private void initMenuViewPager() {
        menuVP.setOrientation(ViewPager2.ORIENTATION_HORIZONTAL);
        menuVP.setOffscreenPageLimit(5);


        List<MenuItem> items = new ArrayList<>();
        items.add(new MenuItem(MenuItem.MenuId.Tech_METER, getResources().getString(R.string.tech), R.drawable.ic_menu_tech));
        items.add(new MenuItem(MenuItem.MenuId.SPORT_METER, getResources().getString(R.string.sport), R.drawable.ic_menu_sport));
        items.add(new MenuItem(MenuItem.MenuId.CLASSIS_METER, getResources().getString(R.string.classic), R.drawable.ic_menu_classic));
        items.add(new MenuItem(MenuItem.MenuId.MAP, getResources().getString(R.string.map), R.drawable.ic_menu_map));
        items.add(new MenuItem(MenuItem.MenuId.MAINTAIN, getResources().getString(R.string.maintain), R.drawable.ic_menu_maintain));
//        items.add(new MenuItem(MenuItem.MenuId.FM, getResources().getString(R.string.fm), R.drawable.ic_menu_test));
//        items.add(new MenuItem(MenuItem.MenuId.PHONE, getResources().getString(R.string.phonebook), R.drawable.ic_menu_test));
//        items.add(new MenuItem(MenuItem.MenuId.MEDIA, getResources().getString(R.string.music), R.drawable.ic_menu_test));

        menuAdapter.setNewData(items);

        menuVP.setAdapter(menuAdapter);

        menuVP.setPageTransformer((page, position) -> {
//            LogUtils.printI(TAG, "PageTransformer---position="+position);

            page.setPivotX((float) page.getWidth() / 2);
            page.setPivotY((float) page.getHeight() / 2.0F);
            if (position == 0) {

                page.setScaleX(1.3f);
                page.setScaleY(1.3f);
            } else {
                page.setScaleX(1.0f);
                page.setScaleY(1.0f);
                page.setElevation(0);
            }
        });


        onPageChangeCallback = new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                super.onPageSelected(position);

            }
        };
        menuVP.registerOnPageChangeCallback(onPageChangeCallback);

//        int padding = (int) (DensityUtil.getScreenWidth(getActivity()) / 2.6f);
        int padding = (int) (DensityUtil.getScreenWidth(getActivity()) / 2.65f);
        LogUtils.printI(TAG, "padding=" + padding);
        //一屏多页
        View recyclerView = menuVP.getChildAt(0);
        if (recyclerView instanceof RecyclerView) {
            recyclerView.setPadding(padding, 0, padding, 0);
            ((RecyclerView) recyclerView).setClipToPadding(false);
        }

//        menuAdapter.setOnItemClickListener((adapter, view, position) -> {
//            currentPosition = position;
//            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SET_FRAGMENT);
//            messageEvent.data = FragmentType.CLASSIC.getValue();
//            EventBus.getDefault().post(messageEvent);
//        });

        new Handler().postDelayed(() -> {
            int width = menuVP.getWidth();
            int height = menuVP.getHeight();
            LogUtils.printI(TAG,"menuVP---width="+width +", height="+height);
            menuVP.setCurrentItem(selectPosition,true);
        },200);


    }

    @Override
    public void onStart() {
        super.onStart();
        MeterActivity.currentFragmentType = MeterFragmentType.MENU;
    }

    @Override
    public void onRight() {
        if(menuVP!=null){
            int currentItem = menuVP.getCurrentItem();
            if(currentItem < (menuAdapter.getItemCount()-1)){
                selectPosition = ++currentItem;
                menuVP.setCurrentItem(selectPosition,true);
            }
        }
    }

    @Override
    public void onLeft() {
        if(menuVP!=null){
            int currentItem = menuVP.getCurrentItem();
            if(currentItem > 0){
                selectPosition = --currentItem;
                menuVP.setCurrentItem(selectPosition,true);
            }
        }
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

    public int getSelectPosition() {
        return selectPosition;
    }

    @Override
    public void onDestroy() {
        try {
            menuVP.unregisterOnPageChangeCallback(onPageChangeCallback);
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        if(getActivity()!=null){
            MeterActivity activity = (MeterActivity) getActivity();
            activity.bottomStatusView.showWaterTempLineView(true);
            activity.bottomStatusView.showOilLineView(true);
        }
    }
}
