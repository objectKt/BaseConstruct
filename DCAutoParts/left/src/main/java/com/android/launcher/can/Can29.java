package com.android.launcher.can;

import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.view.View;

import io.reactivex.rxjava3.annotations.NonNull;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.meter.MeterFragmentBase;
import com.android.launcher.util.BY8302PCB;
import com.android.launcher.util.CommonUtil;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SoundPlayer;

import org.greenrobot.eventbus.EventBus;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

//双闪
public class Can29 implements CanParent {

    public  Timer timer  ;
    public volatile boolean show ;

    public static volatile String lastData ="";

    //双闪是否播放
    private boolean doubleIsPlay = false;
    //左转向或者右转向是否播放
    private boolean leftIsPlay = false;
    private boolean rightIsPlay = false;

    private MessageEvent messageEventLeft = new MessageEvent(MessageEvent.Type.SHOW_LEFT_TURN_SIGNAL);
    private MessageEvent messageEventRight = new MessageEvent(MessageEvent.Type.SHOW_RIGHT_TURN_SIGNAL);

    @Override
    public void handlerCan(List<String> msg) {

        Log.i("can29",msg.toString()+"-------------------") ;

        String emergencyFlash = msg.get(2) + msg.get(3);
        if (!emergencyFlash.equals(lastData)) {
            lastData = emergencyFlash;

            String status = getStatus(msg.get(2));

            LogUtils.printI(Can29.class.getSimpleName(), "emergencyFlash="+emergencyFlash);
            if (status.equals("11")) {
                if(!doubleIsPlay){
                    doubleIsPlay = true;
                    leftIsPlay = false;
                    rightIsPlay = false;

                    FuncUtil.SHUANGSHAN = true ;
                    FuncUtil.YOUXIZNG = true;
                    updateVisable() ;
                    BY8302PCB.play(SoundPlayer.Type.DOUBLE_FLASH, BY8302PCB.MusicType.TURN_SIGNAL);
                    //SoundPlayer.play(SoundPlayer.Type.DOUBLE_FLASH,"zx.mp3");

                    flash();
                }
            }
            else if(status.equals("01")) //左转向
            {
                if(!leftIsPlay){
                    leftIsPlay = true;
                    rightIsPlay = false;
                    doubleIsPlay = false;
                    CommonUtil.meterHandler.get("zuozhuanxiang").handlerData("01");
                    CommonUtil.meterHandler.get("youzhuanxiang").handlerData("00");
                    BY8302PCB.play(SoundPlayer.Type.LEFT_TURN, BY8302PCB.MusicType.TURN_SIGNAL);
                    //SoundPlayer.play(SoundPlayer.Type.LEFT_TURN,"zx.mp3");
                }

            }else if(status.equals("10")){ //右转向
                if(!rightIsPlay){
                    rightIsPlay = true;
                    leftIsPlay = false;
                    doubleIsPlay = false;
                    CommonUtil.meterHandler.get("youzhuanxiang").handlerData("01");
                    CommonUtil.meterHandler.get("zuozhuanxiang").handlerData("00");
                    BY8302PCB.play(SoundPlayer.Type.RIGHT_TURN, BY8302PCB.MusicType.TURN_SIGNAL);
                    //SoundPlayer.play(SoundPlayer.Type.RIGHT_TURN,"zx.mp3");
                }
            } else if (status.equals("00")) {
                FuncUtil.SHUANGSHAN = false ;
                FuncUtil.YOUXIZNG = false;

                if(leftIsPlay){
                    BY8302PCB.stopLeftTurn();
                    //SoundPlayer.stopLeftTurn();
                    CommonUtil.meterHandler.get("zuozhuanxiang").handlerData("00");
                    leftIsPlay = false;
                }
                if(rightIsPlay){
                    rightIsPlay = false;
                    BY8302PCB.stopRightTurn();
                    //SoundPlayer.stopRightTurn();
                    CommonUtil.meterHandler.get("youzhuanxiang").handlerData("00");
                }
                if(doubleIsPlay){
                    doubleIsPlay = false;
                    BY8302PCB.stopDoubleFlash();
                    //SoundPlayer.stopDoubleFlash();
                }
                if (timer!=null){
                    timer.cancel();
                    timer = null ;
                    show = false ;
                    updateShow(View.INVISIBLE);
                }
            }

        }
//
//            if (emergencyFlash.equals("0000")) {
//                zhuanxiang.stop();
//                SoundPlayer.stop();
//            }
//            //双闪
//            if (emergencyFlash.toUpperCase().equals("E021")) {
//                SoundPlayer.play();
//                zhuanxiang = new Zhuanxiang(2);
//                zhuanxiang.start();
//            }
//        }
    }

    @NotNull
    private String getStatus(String statusStr) {
        try {
            int value = Integer.parseInt(statusStr, 16);
            String statusBinary = Integer.toBinaryString(value);
            if(statusBinary.length() == 7){
                statusBinary = "0"+ statusBinary;
            }else if(statusBinary.length() == 6){
                statusBinary = "00"+ statusBinary;
            }else if(statusBinary.length() <= 5){
                statusBinary = "00000000";
            }
            String status = statusBinary.substring(0, 2);
            LogUtils.printI(Can29.class.getSimpleName(), "statusStr="+statusStr +", value="+value +", statusBinary="+statusBinary +", status="+status);
            return status;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }


    private void flash( ) {
        if (timer==null){
            timer = new Timer();
            updateShow(View.VISIBLE);
            show= true ;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                        if (show){
                            updateShow(View.INVISIBLE);
                            show = false ;
                        }else{
                            updateShow(View.VISIBLE);
                            show = true ;
                        }

                }
            },130,333);
        }
    }

    private void updateShow(int visible) {
        try {
            if(View.VISIBLE == visible){
                messageEventLeft.data = true;
                messageEventRight.data = true;
            }else{
                messageEventLeft.data = false;
                messageEventRight.data = false;
            }
            EventBus.getDefault().post(messageEventLeft);
            EventBus.getDefault().post(messageEventRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void updateVisable() {
        try {
            messageEventLeft.data = false;
            EventBus.getDefault().post(messageEventLeft);

            messageEventRight.data = false;
            EventBus.getDefault().post(messageEventRight);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
