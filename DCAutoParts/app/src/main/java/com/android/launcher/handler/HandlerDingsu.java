package com.android.launcher.handler;//package com.car.left.handler;
//
//import android.view.View;
//
//import com.android.launcher.MeterPresentation;
//
//public class HandlerDingsu implements HandlerInteface {
//
//    @Override
//    public void handlerData(MeterPresentation meterPresentation, String msg) {
//
//        if (msg.equals("off")){
//            meterPresentation.frame_dingsu.setVisibility(View.INVISIBLE);
//        }else{
//            String[] da = msg.split("-") ;
//            meterPresentation.frame_dingsu.setVisibility(View.VISIBLE);
//            meterPresentation.frame_dingsu_speed.setText(da[1]+"km/h");
//        }
//    }
//}
