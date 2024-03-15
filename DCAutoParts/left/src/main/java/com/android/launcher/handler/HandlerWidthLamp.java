package com.android.launcher.handler;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.android.launcher.meter.MeterFragmentBase;

/**
 * 车身灯光
 */
@Deprecated
public class HandlerWidthLamp implements HandlerInteface {
    public Handler handler = new Handler(Looper.getMainLooper())  ;
    @Override
    public void handlerData(final String msg) {

//        handler.postAtFrontOfQueue(new Runnable() {
//            @Override
//            public void run() {
//                String widthLamp = msg ;
//                if(widthLamp.equals("1")){
////                    MeterActivity.light_width_lamp.setVisibility(View.VISIBLE);
//
//                    MeterFragmentBase.light_width_lamp.setVisibility(View.VISIBLE);
//                }
//                //关闭
//                if(widthLamp.equals("2")){
////                    MeterActivity.light_width_lamp.setVisibility(View.INVISIBLE);
//
//                    MeterFragmentBase.light_width_lamp.setVisibility(View.INVISIBLE);
//                }
//                //示宽灯 + 雾灯1
//                if(widthLamp.equals("3")){
////                    MeterActivity.light_width_lamp.setVisibility(View.VISIBLE);
////                    MeterActivity.light_wd.setVisibility(View.VISIBLE);
//
//                    MeterFragmentBase.light_width_lamp.setVisibility(View.VISIBLE);
//                    MeterFragmentBase.light_wd.setVisibility(View.VISIBLE);
//                }
//                //示宽灯 + 雾灯2
//                if(widthLamp.equals("4")){
////                    MeterActivity.light_width_lamp.setVisibility(View.VISIBLE);
////                    MeterActivity.light_wd.setVisibility(View.VISIBLE);
////                    MeterActivity.light_hwd.setVisibility(View.VISIBLE);
//
//                    MeterFragmentBase.light_width_lamp.setVisibility(View.VISIBLE);
//                    MeterFragmentBase.light_wd.setVisibility(View.VISIBLE);
//                    MeterFragmentBase.light_hwd.setVisibility(View.VISIBLE);
//                }
//                //近光灯
//                if(widthLamp.equals("5")){
////                    MeterActivity.light_lb.setVisibility(View.VISIBLE);
//
//                    MeterFragmentBase.light_lb.setVisibility(View.VISIBLE);
//                }
//                //近光灯+雾灯1
//                if(widthLamp.equals("6")){
////                    MeterActivity.light_wd.setVisibility(View.VISIBLE);
////                    MeterActivity.light_lb.setVisibility(View.VISIBLE);
//
//                    MeterFragmentBase.light_wd.setVisibility(View.VISIBLE);
//                    MeterFragmentBase.light_lb.setVisibility(View.VISIBLE);
//                }
//                //近光灯+雾灯2
//                if(widthLamp.equals("7")){
////                    MeterActivity.light_wd.setVisibility(View.VISIBLE);
////                    MeterActivity.light_hwd.setVisibility(View.VISIBLE);
////                    MeterActivity.light_lb.setVisibility(View.VISIBLE);
//
//                    MeterFragmentBase.light_wd.setVisibility(View.VISIBLE);
//                    MeterFragmentBase.light_hwd.setVisibility(View.VISIBLE);
//                    MeterFragmentBase.light_lb.setVisibility(View.VISIBLE);
//                }
//                //全部关闭
//                if(widthLamp.equals("8")){
////                    MeterActivity.light_wd.setVisibility(View.INVISIBLE);
////                    MeterActivity.light_hwd.setVisibility(View.INVISIBLE);
////                    MeterActivity.light_lb.setVisibility(View.INVISIBLE);
////                    MeterActivity.light_hb.setVisibility(View.INVISIBLE);
//
//                    MeterFragmentBase.light_wd.setVisibility(View.INVISIBLE);
//                    MeterFragmentBase.light_hwd.setVisibility(View.INVISIBLE);
//                    MeterFragmentBase.light_lb.setVisibility(View.INVISIBLE);
//                    MeterFragmentBase.light_hb.setVisibility(View.INVISIBLE);
//                }
//
//            }
//        }) ;
    }
}
