package com.android.launcher.base;

import android.os.Bundle;

import com.android.launcher.MessageEvent;
import com.android.launcher.meter.MenuType;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.type.SteerWheelKeyType;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * @date： 2023/10/16
 * @author: 78495
*/
public abstract class CommonActivity extends ActivityBase {

    private ExecutorService taskService;
    private ScheduledExecutorService timerTaskService;

    @Override
    protected final void onCreate(Bundle savedInstanceState) {
        try {
            taskService = Executors.newCachedThreadPool();
            timerTaskService = Executors.newScheduledThreadPool(getTimerTaskCount());
            super.onCreate(savedInstanceState);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public int getTimerTaskCount() {
        return 2;
    }


    public void startTimerTask(Runnable runnable,long delay,long period){
        if(timerTaskService!=null){
            timerTaskService.scheduleAtFixedRate(runnable,delay,period, TimeUnit.MILLISECONDS);
        }
    }

    public void startTask(Runnable runnable){
        if(taskService!=null){
            taskService.execute(runnable);
        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        try {
            if(taskService!=null){
                taskService.shutdown();
            }

            if(timerTaskService!=null){
                timerTaskService.shutdown();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void disposeMessageEvent(MessageEvent event) {
        if(event.type == MessageEvent.Type.STEER_WHEEL_TYPE){
            if(event.data != null){
                if(MenuType.HOME == MeterActivity.currentMenuType){
                    int keyType = (int) event.data;
                    if(keyType == SteerWheelKeyType.KEY_BACK.ordinal()){
                        onSteerWheelBack();
                    }else if(keyType == SteerWheelKeyType.KEY_OK.ordinal()){
                        onSteerWheelOk();
                    }else if(keyType == SteerWheelKeyType.KEY_LEFT.ordinal()){
                        onSteerWheelLeft();
                    }else if(keyType == SteerWheelKeyType.KEY_RIGHT.ordinal()){
                        onSteerWheelRight();
                    }else if(keyType == SteerWheelKeyType.KEY_UP.ordinal()){
                        onSteerWheelUp();
                    }else if(keyType == SteerWheelKeyType.KEY_DOWN.ordinal()){
                        onSteerWheelDown();
                    }
                }
            }
        }
    }

    protected void onSteerWheelDown() {

    }

    protected void onSteerWheelUp() {

    }

    protected void onSteerWheelRight() {


    }

    protected void onSteerWheelLeft() {

    }

    protected void onSteerWheelOk() {

    }

    protected void onSteerWheelBack() {

    }

}
