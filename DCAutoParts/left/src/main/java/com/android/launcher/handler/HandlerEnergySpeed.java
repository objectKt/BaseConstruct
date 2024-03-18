package com.android.launcher.handler;//package com.android.launcher.handler;
//
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.animation.Animation;
//import android.view.animation.LinearInterpolator;
//import android.view.animation.RotateAnimation;
//
//import com.android.launcher.MeterActivity;
//import com.android.launcher.util.FuncUtil;
//
//import java.math.BigDecimal;
//
///**
// * 发动机转速
// */
//public class HandlerEnergySpeed implements HandlerInteface {
//
////    private Handler handler = new Handler(Looper.getMainLooper());
////
//    public String  startEngA="0.0",endEngA="0.0";
//
//    public boolean flg ;
//    @Override
//    public void handlerData(String msg) {
//
//        BigDecimal bignum3 = new BigDecimal(msg) ;
//        Log.i("zhuansu",bignum3+"---------------------") ;
//        final String f = getEngDegree(bignum3.floatValue());
//        setEnergySpeed(f);
//    }
//
//
//    /**
//     *  发送机转速指针转动
//     *
//     * @param f
//     */
//    public void setEnergySpeed( String f) {
//        BigDecimal fromd = new BigDecimal(startEngA) ;
//        endEngA= f  ;
//        BigDecimal tod = new BigDecimal(endEngA) ;
//        showEnergyPointer(fromd.floatValue(),tod.floatValue());
//        startEngA = endEngA ;
//    }
//
//
//    /**
//     * 指针转动
//     *
//     * @param fromDegrees
//     * @param toDegrees
//     */
//    public void showEnergyPointer(  Float fromDegrees, Float toDegrees){
//// 先
//        RotateAnimation rotate1  = new RotateAnimation(fromDegrees, toDegrees, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
//        LinearInterpolator lin = new LinearInterpolator() ;
//        rotate1.setInterpolator(lin);
//        rotate1.setDuration(150);//设置动画持续时间
//        rotate1.setRepeatCount(0);//设置重复次数
//        rotate1.setFillAfter(true);//动n画执行完后是否停留在执行完的状态
//        MeterActivity.imageViewright.startAnimation(rotate1);
////        handler.post(new Runnable() {
////            @Override
////            public void run() {
////                meterActivity.imageViewright.startAnimation(rotate1);
////            }
////        }) ;
//
//
//
//    }
//
//
//    private String getEngDegree(float f) {
//        BigDecimal bignum1 = new BigDecimal(f);
//        BigDecimal bignum2 = new BigDecimal("0.032");
//        BigDecimal bignum3 = bignum1.multiply(bignum2);
//        return bignum3.toString();
//    }
//}
