package com.android.launcher.handler;

import android.os.Handler;
import android.os.Looper;
import android.util.Log;
import android.view.View;

import com.android.launcher.App;
import com.android.launcher.MessageEvent;
import com.android.launcher.util.BY8302PCB;
import com.android.launcher.util.FuncUtil;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SoundPlayer;
import com.android.launcher.meter.view.StatusBarView;

import org.greenrobot.eventbus.EventBus;

import java.util.Timer;
import java.util.TimerTask;

/**
 * 安全带
 */
public class HandlerAn implements HandlerInteface {

    public Timer safeTimer;
    public volatile boolean status;
    public Timer checkTimer;
    public volatile boolean play;
    public volatile boolean stop;
    public int count;
    private Handler handler = new Handler(Looper.getMainLooper());

    private MessageEvent messageEvent = new MessageEvent(MessageEvent.Type.SHOW_SAFETY_BELT);


    public void handler3() {
        handler.postAtFrontOfQueue(new Runnable() {
            @Override
            public void run() {
                status = true;
                count = 0;
                play = false;
                stop = false;
//                SoundPlayer.stop("an");
                BY8302PCB.stopSafetyBelt();
                //SoundPlayer.stopSafetyBelt();
//                MeterActivity.beltsafe.setVisibility(View.INVISIBLE);

                messageEvent.data = false;
                EventBus.getDefault().post(messageEvent);
            }
        });

    }

    @Override
    public void handlerData(String msg) {


        String safetyBeltStatus = msg;

        //30 两边都有人 都系安全带
        //34 主有人复有人  副不系安全带
        //35 两边都未系安全带
        // 31 主未系 副系

        //安全带
        LogUtils.printI(HandlerAn.class.getSimpleName(), "safetyBeltStatus=" + safetyBeltStatus + " carSpeed=" + App.carSpeed);
//        if (beltsafe.startsWith("f")) {
//            String belt = beltsafe.replaceAll("f", "");
//            LogUtils.printI(HandlerAn.class.getSimpleName(), "belt=" + belt);


        //0035: 主驾有人不插安全带，副驾没人 (或主驾副驾都没人) ---显示
        //0034: 主驾有人插上安全带，副驾没人---不显示
        //1134: 主驾有人插上安全带，副驾有人不插安全带--显示
        //1135: 驾驶位有人不插安全带，副驾有人不插安全带(驾驶位没人，副驾有人不插安全带)--显示
        if ("1134".equals(safetyBeltStatus) || "1135".equals(safetyBeltStatus) || "0035".equals(safetyBeltStatus)) {
            if (safeTimer == null) {
                safeTimer = new Timer();
                status = false;
                start();
                safeTimer.schedule(new TimerTask() {// 不间断的更新播放进度
                    @Override
                    public void run() {
                        if (StatusBarView.showSafetyBelt) {
                            messageEvent.data = false;
                            EventBus.getDefault().post(messageEvent);
                        } else {
                            messageEvent.data = true;
                            EventBus.getDefault().post(messageEvent);
                        }
                    }
                }, 0, 400);
            }

        } else {
            if (safeTimer != null) {
                safeTimer.cancel();
                safeTimer = null;
            }
            handler3();
        }
    }
//            if (beltsafe.equals("31") || beltsafe.equals("34") || beltsafe.equals("35")) {
//                start();
//                status = false;
//                if (safeTimer == null) {
//                    if (App.isCompleteBody) {
//                        BY8302PCB.play(SoundPlayer.Type.SAFETY_BELT, BY8302PCB.MusicType.SAFETY_BELT);
//                    } else {
//                        SoundPlayer.play(SoundPlayer.Type.SAFETY_BELT, "safety_belt.mp3");
//                    }
//                    safeTimer = new Timer();
//                    safeTimer.schedule(new TimerTask() {// 不间断的更新播放进度
//                        @Override
//                        public void run() {
//
//                            if (StatusBarView.showSafetyBelt) {
//                                messageEvent.data = false;
//                                EventBus.getDefault().post(messageEvent);
//                            } else {
//                                messageEvent.data = true;
//                                EventBus.getDefault().post(messageEvent);
//                            }
//
//                        }
//                    }, 0, 400);
//                }
//            }
//        }
//}

    private void start() {
        //todo  luojizhengli
        if (checkTimer == null) {
            checkTimer = new Timer();
            checkTimer.schedule(new TimerTask() {
                @Override
                public void run() {
                    if (!status) {//判断是否安全带插入标志 没有插入则进入 插入后取消定时器else部分

                        //判断车辆是否正在运行中 如果是在运行中则开始安全带声音 如果不在运行中则发送关闭声音
                        if (App.carSpeed != null) {
                            int speed = Integer.parseInt(App.carSpeed);
                            if (speed > 0 && !FuncUtil.degree) {
                                if (!play) { //是否发送过播放如果没有则进行播放
                                    play = true;
                                    BY8302PCB.play(SoundPlayer.Type.SAFETY_BELT, BY8302PCB.MusicType.SAFETY_BELT);
                                    //SoundPlayer.play(SoundPlayer.Type.SAFETY_BELT, "safety_belt.mp3");
                                }

                                if (count > 30) {// 判断是否是持续了30秒钟 如果是持续了则进行关闭 。
                                    if (!stop) {
                                        count = 0;
                                        stop = true;
                                        play = false;//
                                        FuncUtil.degree = true;
                                    }
                                    BY8302PCB.stopSafetyBelt();
//                                    SoundPlayer.stopSafetyBelt();

                                }
                                count++;
                            } else {//没有速度则不进行播放只发送一次
                                if (play) { //是否发送过播放如果没有则进行播放
                                    play = false;
                                    stop = false;
                                    count = 0;
                                }
                                BY8302PCB.stopSafetyBelt();
//                                SoundPlayer.stopSafetyBelt();
                            }
                        }


                    } else {
                        if (checkTimer != null) {
                            checkTimer.cancel();
                            checkTimer = null;
                        }
                    }
                }
            }, 0, 1000);
        }
    }
}
