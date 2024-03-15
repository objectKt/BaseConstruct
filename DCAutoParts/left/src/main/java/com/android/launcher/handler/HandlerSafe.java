package com.android.launcher.handler;

import android.os.Handler;
import android.os.Looper;

import com.android.launcher.MessageEvent;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.meter.view.StatusBarView;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 安全气囊
 */
public class HandlerSafe implements HandlerInteface {

    public Timer safeTimer ;

    private Handler handler = new Handler(Looper.getMainLooper()) ;


     @Override
    public void handlerData(final String msg) {
//         handler.postAtFrontOfQueue(new Runnable() {
//             @Override
//             public void run() {
//                 FuncUtil.doorStatus = true ;
//                 String safety = msg;
//                 //安全气囊
//                 if(safety.equals("8")){
//                     if (safeTimer==null){
//                         safeTimer = new Timer();
//                         safeTimer.schedule(new TimerTask() {// 不间断的更新播放进度
//                             @Override
//                             public void run() {
//                                 handler.post(new Runnable() {
//                                     @Override
//                                     public void run() {
//                                         if (FuncUtil.doorStatus){
//                                             if(StatusBarView.showGasSafe){
//                                                 messageEvent.data = false;
//                                                 EventBus.getDefault().post(messageEvent);
//                                             }else{
//                                                 messageEvent.data = true;
//                                                 EventBus.getDefault().post(messageEvent);
//                                             }
//                                         }
//                                     }
//                                 }) ;
//                             }
//                         }, 0, 300);
//                     }
//                 }else{
//
//                     if(safeTimer!=null){
//                         safeTimer.cancel();
//                         safeTimer = null ;
//                     }
//                     if (safety.equals("3")){
//                         messageEvent.data = false;
//                         EventBus.getDefault().post(messageEvent);
//
//                     }
//                     if (safety.equals("10")){
//
//                         messageEvent.data = false;
//                         EventBus.getDefault().post(messageEvent);
//                     }
//                     if(safety.equals("4")){
//
//                         messageEvent.data = true;
//                         EventBus.getDefault().post(messageEvent);
//                     }
//                 }
//             }
//         });
    }
}
