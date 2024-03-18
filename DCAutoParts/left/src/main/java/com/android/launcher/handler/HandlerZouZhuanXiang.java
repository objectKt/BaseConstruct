package com.android.launcher.handler;


import android.util.Log;
import android.view.View;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.meter.MeterFragmentBase;
import com.android.launcher.util.BY8302PCB;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.SoundPlayer;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;


/**
 * @description: 左转向
 * @createDate: 2023/6/24
 */
public class HandlerZouZhuanXiang implements HandlerInteface {

    public String flg = "";
    public volatile boolean run;
    public Timer timer;
    public volatile boolean show;
    public int count = 1;

    private MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_LEFT_TURN_SIGNAL);

    @Override
    public void handlerData(String msg) {
        Log.i("zuozhuanx", flg + "==========1=======" + msg);

        if (msg.equals("01")) {
            updateUIRight();
            run = true;
            testFlash();
            FuncUtil.ZUOXIZNG = true;
            BY8302PCB.play(SoundPlayer.Type.LEFT_TURN, BY8302PCB.MusicType.TURN_SIGNAL);
//            SoundPlayer.play(SoundPlayer.Type.LEFT_TURN,"zx.mp3");
        }
        if (msg.equals("00")) {
            if (run) {
//                    SoundPlayer.stop("zx");
            }
            FuncUtil.ZUOXIZNG = false;
            run = false;
            show = false;
            BY8302PCB.stopLeftTurn();
//            SoundPlayer.stopLeftTurn();
        }
    }

    private void updateUILeft(int invisible) {
        if (View.VISIBLE == invisible) {
            messageEvent.data = true;
        } else {
            messageEvent.data = false;
        }
        EventBus.getDefault().post(messageEvent);
    }

    private void updateUIRight() {
        MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_RIGHT_TURN_SIGNAL);
        messageEvent.data = false;
        EventBus.getDefault().post(messageEvent);
    }


    private void testFlash() {
        if (timer == null) {
            timer = new Timer();
//            SoundPlayer.play("7E0541000145EF","zx");
            updateUILeft(View.VISIBLE);
            show = true;
            timer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!FuncUtil.SHUANGSHAN) {
                        if (count > 5) {
                            Log.i("zhuanxiangceshi", count + "===============进入第三次");
                            if (run) {
                                if (show) {
                                    updateUILeft(View.INVISIBLE);
                                    show = false;
                                } else {
                                    updateUILeft(View.VISIBLE);
                                    show = true;
                                }
                            } else {
                                updateUILeft(View.INVISIBLE);
                                show = false;
                                count = 0;
                                if (timer != null) {
                                    timer.cancel();
                                    timer = null;
                                    updateUILeft(View.INVISIBLE);
                                }
                            }
                        } else {
                            Log.i("zhuanxiangceshi", count + "================2");
                            if (show) {
                                updateUILeft(View.INVISIBLE);
                                show = false;
                            } else {
                                updateUILeft(View.VISIBLE);
                                show = true;
                            }
                        }
                        count++;
                    }
                }
            }, 110, 333);
        }
    }
}
