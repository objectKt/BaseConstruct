package com.android.launcher.handler;

import android.util.Log;
import android.view.View;

import dc.library.utils.event.MessageEvent;

import com.android.launcher.util.BY8302PCB;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.SoundPlayer;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 右转向
 */
public class HandlerYouZhuanXiang implements HandlerInteface {

    public String flg = "" ;
    public volatile  boolean run  ;
    public  Timer timer  ;
    public  volatile  boolean show ;
    public int count=1 ;

    private MessageEvent messageEventRight = new MessageEvent(MessageEvent.Type.SHOW_RIGHT_TURN_SIGNAL);


    @Override
    public void handlerData( String msg) {
        Log.i("youzhuanxiang",msg+"---------------");
        if (msg.equals("01")){
            updateUILeft();
            testFlash();
            run= true ;
                FuncUtil.YOUXIZNG = true;
            BY8302PCB.play(SoundPlayer.Type.RIGHT_TURN, BY8302PCB.MusicType.TURN_SIGNAL);
//            SoundPlayer.play(SoundPlayer.Type.RIGHT_TURN, "zx.mp3");
        }
        if (msg.equals("00")){
            if (run){
//                SoundPlayer.stop("zx");
            }
            FuncUtil.YOUXIZNG = false;
            run = false ;
            show = false ;
            BY8302PCB.stopRightTurn();
//            SoundPlayer.stopRightTurn();
        }
    }

    private void updateUIRight( int invisible) {
        if(View.VISIBLE == invisible){
            messageEventRight.data = true;
        }else{
            messageEventRight.data = false;
        }
        EventBus.getDefault().post(messageEventRight);
    }


    private void updateUILeft() {
        MessageEvent messageEventLeft = new MessageEvent(MessageEvent.Type.SHOW_LEFT_TURN_SIGNAL);
        messageEventLeft.data = false;
        EventBus.getDefault().post(messageEventLeft);
    }

    private void testFlash() {

        if (timer==null){
            timer = new Timer();
//            SoundPlayer.play("7E0541000145EF","zx");
            updateUIRight(View.VISIBLE);
            show = true ;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    Log.i("zhuanxiangceshi","====1=============="+ FuncUtil.SHUANGSHAN) ;
                    if (!FuncUtil.SHUANGSHAN){
                        if (count>5){
                            Log.i("zhuanxiangceshi",count+"===============进入第三次") ;
                            if (run){
                                if (show){
                                    updateUIRight(View.INVISIBLE);
                                    show = false ;
                                }else{
                                    updateUIRight(View.VISIBLE);
                                    show = true ;
                                }
                            }else{
                                updateUIRight(View.INVISIBLE);
                                show = false ;
                                count=0 ;
                                if (timer!=null){
//
                                    timer.cancel();
                                    timer = null ;
                                    updateUIRight(View.INVISIBLE) ;
                                }
                            }
                        }else{
                            Log.i("zhuanxiangceshi",count+"================2") ;
                            if (show){
                                updateUIRight(View.INVISIBLE);
                                show = false ;
                            }else{
                                updateUIRight(View.VISIBLE);
                                show = true ;
                            }
                        }
                        count++ ;
                    }
                }
            },110,333);
        }
    }
}
