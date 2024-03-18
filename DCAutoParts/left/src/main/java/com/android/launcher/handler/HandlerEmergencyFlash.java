package com.android.launcher.handler;//package com.android.launcher.handler;
//
//import android.media.MediaPlayer;
//import android.os.CountDownTimer;
//import android.os.Handler;
//import android.os.Looper;
//import android.util.Log;
//import android.view.View;
//
//import com.android.launcher.MeterActivity;
//import com.android.launcher.util.SoundPlayer;
//
//import java.util.Timer;
//import java.util.TimerTask;
//
///**
// * 左右转向灯 双闪
// */
//public class HandlerEmergencyFlash implements HandlerInteface {
////
////    public boolean run  ;
//    public  Timer timer  ;
//    public boolean show ;
//
//    private Handler handler = new Handler(Looper.getMainLooper()) ;
//
//
//    @Override
//    public void handlerData(MeterActivity meterActivity, String msg) {
//
//        // 双闪开关
//        String onOrOff = msg ;
//
//        Log.i("flash",onOrOff+"===========================");
//        //双闪开
//        if(onOrOff.equals("on")){
//            testFlash(meterActivity);
////            playMusic();
//            run= true ;
//        }else{
//            run= false ;
//            if (timer!=null){
//                updateUI(meterActivity,View.INVISIBLE);
//                SoundPlayer.stop();
//                timer.cancel();
//                timer = null ;
//
//            }
//        }
//    }
//
//    private void updateUI(MeterActivity meterActivity,int show) {
//        meterActivity.runOnUiThread(new Runnable() {
//            @Override
//            public void run() {
//                meterActivity.signalright.setVisibility(show);
//                meterActivity.signalleft.setVisibility(show);
//            }
//        });
//    }
//
//
//    private void testFlash(MeterActivity meterActivity) {
//        if (timer==null){
//            timer = new Timer();
//            SoundPlayer.play();
//            timer.schedule(new TimerTask() {
//                @Override
//                public void run() {
//                    if (run){
//                        handler.post(new Runnable() {
//                            @Override
//                            public void run() {
//                                if (show){
//                                    updateUI(meterActivity,View.INVISIBLE);
//
//                                    show = false ;
//                                }else{
//                                    updateUI(meterActivity,View.VISIBLE);
//                                    show = true ;
//                                }
//                            }
//                        });
//                    }else{
//                        updateUI(meterActivity,View.INVISIBLE);
//                        show = false ;
//                    }
//                }
//            },0,500);
//        }
//    }
//}
