package com.android.launcher.handler;

import android.os.Handler;
import android.os.Looper;

import com.android.launcher.App;
import dc.library.utils.event.MessageEvent;
import com.android.launcher.util.BY8302PCB;
import com.android.launcher.util.SoundPlayer;

import org.greenrobot.eventbus.EventBus;

/**
 * 前雷达 左右
 */
public class HandlerRadarFront implements HandlerInteface {


    public volatile  boolean flg ;

    public  volatile  boolean sendStop ;
    public  volatile  boolean sendStop2 ;
    public String handlermsg=""  ;

    public Handler handler = new Handler(Looper.getMainLooper()) ;
    @Override
    public void handlerData(final String msg) {

        handler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                if (App.carSpeed!=null){
                    int speed = Integer.parseInt(App.carSpeed) ;
                    if (speed<19){
                        String left = msg.substring(0,1) ;
                        String right = msg.substring(1,2) ;
                        if (msg.contains("8")||msg.contains("9")){
                            if(!flg){
//                                playMusic();
                                sendStop=true;
                                sendStop2 =true;
                                flg=true ;
                                BY8302PCB.play(SoundPlayer.Type.RADAR, BY8302PCB.MusicType.RADAR);
                                //SoundPlayer.play(SoundPlayer.Type.RADAR,"radar.mp3");

                            }

                        }else{
                            flg=false ;
//                            stopVoice();
                            if (sendStop){
                                sendStop=false;
                                BY8302PCB.stopRadar();
                                //SoundPlayer.stopRadar();
                            }


                        }
                        leftRadar(right);
                        rightRadar(left);
                    }else if (speed>24){

                        leftRadar( "0");
                        rightRadar( "0");

                        if (sendStop2){
                            BY8302PCB.stopRadar();
                            //SoundPlayer.stopRadar();
                            sendStop2 = false ;
                        }
//                        stopVoice();
                    }
                }
            }
        }) ;
    }





    private void rightRadar( String msg) {
        try {
//            MeterFragmentBase.localMeterFragment.updateRightRadar(msg);

            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_RIGHT_RADAR);
            messageEvent.data = msg;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void leftRadar(  String msg) {

        try {
//            MeterFragmentBase.localMeterFragment.updateLeftRadar(msg);
            MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.UPDATE_LEFT_RADAR);
            messageEvent.data = msg;
            EventBus.getDefault().post(messageEvent);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
