package com.android.launcher.util;


import android.media.AudioAttributes;
import android.media.SoundPool;

import com.android.launcher.App;
import com.android.launcher.R;

import java.util.HashMap;
import java.util.Map;

public class SoundPlayer {
    private static final String TAG = SoundPlayer.class.getSimpleName();

    //转向灯是否正在播放
    public static boolean leftTurnPlay = false;
    public static boolean rightTurnPlay = false;
    //双闪
    public static boolean doubleFlashPlay = false;
    //雷达
    public static boolean radarPlay = false;
    //安全带
    public static boolean safetyBeltPlay = false;

    private static boolean isPlaying = false;

    private static SoundPool  mSoundPool;

    private  static Map<String, Integer> soundPoolMap = new HashMap<String, Integer>(); //这里我创建一个 hash 表，用于记录加载过的声音的ID，一般我们会定义一个常量作为检索该声音的KEY

    private static final String SOUND_ID_LEFTLIGHT = "leftLight";
    private static final String SOUND_ID_RADAR = "radar";
    private static final String SOUND_ID_SAFETYBELT = "safetyBelt";
    private static int streamID;

    public static void init(){
        try {
            AudioAttributes.Builder builder = new AudioAttributes.Builder();
            builder.setContentType(AudioAttributes.CONTENT_TYPE_MUSIC);
            builder.setUsage(AudioAttributes.USAGE_GAME);
            mSoundPool = new SoundPool.Builder()
                    .setMaxStreams(1)
                    .setAudioAttributes(builder.build())
                    .build();
            new Thread(() -> {
                soundPoolMap.clear();
                soundPoolMap.put(SOUND_ID_LEFTLIGHT, mSoundPool.load(App.getGlobalContext(), R.raw.zx, 1));
                soundPoolMap.put(SOUND_ID_RADAR, mSoundPool.load(App.getGlobalContext(), R.raw.radar, 1));//注意，这里 hash表里 记录
                soundPoolMap.put(SOUND_ID_SAFETYBELT, mSoundPool.load(App.getGlobalContext(), R.raw.safety_belt, 1));//注意，这里 hash表里 记录
                LogUtils.printI(TAG, "音频文件预加载完成");
            }).start();

        } catch (Exception e) {
            e.printStackTrace();
        }

        leftTurnPlay = false;
        rightTurnPlay = false;
        doubleFlashPlay = false;
        safetyBeltPlay =false;
        radarPlay = false;
        isPlaying = false;
    }


    public static synchronized void play(Type type, String fileName){
        LogUtils.printI(TAG,"play---type="+type.name() + ", fileName="+fileName + "leftTurnPlay="+leftTurnPlay +
                ", rightTurnPlay="+rightTurnPlay + ", doubleFlashPlay="+doubleFlashPlay + ", safetyBeltPlay="+safetyBeltPlay + ", radarPlay="+radarPlay + ", isPlaying="+isPlaying);
        if(type== Type.LEFT_TURN){
            if(doubleFlashPlay){
                leftTurnPlay = true;
                return;
            }
            if(!leftTurnPlay){
                if(isPlaying){
                    stop();
                }
                player(SOUND_ID_LEFTLIGHT);
                setLeftTurnPlayState();
            }
        }else if(type == Type.RIGHT_TURN){
            if(doubleFlashPlay){
                rightTurnPlay = true;
                return;
            }
            if(!rightTurnPlay){
                if(isPlaying){
                    stop();
                }
                player(SOUND_ID_LEFTLIGHT);
                setRightTurnPlayState();
            }
        }else if(type== Type.DOUBLE_FLASH){ //双闪优先级最高
            if(isPlaying){
                stop();
            }
            player(SOUND_ID_LEFTLIGHT);
            setDoubleFlashPlayState();
        }else{
            if(!turnSignalOpen()){ //双闪第一优先级， 转向灯第二优先级

                if(type == Type.SAFETY_BELT && !radarPlay){ //安全带优先级最低
                    if(isPlaying){
                        stop();
                    }
                    player(SOUND_ID_SAFETYBELT);
                    isPlaying = true;
                    safetyBeltPlay = true;
                }else if(type == Type.RADAR){
                    if(isPlaying){
                        stop();
                    }
                    player(SOUND_ID_RADAR);
                    isPlaying = true;
                    radarPlay = true;
                }
            }
        }
    }

    /**
    * @description: 双闪播放状态
    * @createDate: 2023/5/22
    */
    private static void setDoubleFlashPlayState() {
        isPlaying = true;
        doubleFlashPlay = true;
        radarPlay = false;
        safetyBeltPlay = false;
    }

    /**
    * @description: 右转向播放状态
    * @createDate: 2023/5/22
    */
    private static void setRightTurnPlayState() {
        isPlaying = true;
        rightTurnPlay = true;
        leftTurnPlay = false;
        doubleFlashPlay = false;
        radarPlay = false;
        safetyBeltPlay = false;
    }

    /**
    * @description: 左转向播放状态
    * @createDate: 2023/5/22
    */
    private static void setLeftTurnPlayState() {
        isPlaying = true;
        leftTurnPlay = true;
        rightTurnPlay = false;
        doubleFlashPlay = false;
        radarPlay = false;
        safetyBeltPlay = false;
    }

    public static synchronized void stop(){
        try {
            if(mSoundPool!=null){
                mSoundPool.stop(streamID);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void destroy(){
        if(mSoundPool!=null)
        {
            try {
                mSoundPool.release();
                mSoundPool = null;
                soundPoolMap.clear();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
    * @description: 转向，双闪是否开启
    * @createDate: 2023/5/22
    */
    public static boolean turnSignalOpen() {
        return leftTurnPlay || rightTurnPlay || doubleFlashPlay;
    }


    /**
    * @description: 停止安全带
    * @createDate: 2023/5/22
    */
    public static void stopSafetyBelt(){
        LogUtils.printI(TAG, "stopSafetyBelt----radarPlay="+radarPlay + ", turnSignalOpen="+ SoundPlayer.turnSignalOpen());
        if(!SoundPlayer.radarPlay && !SoundPlayer.turnSignalOpen()){
            SoundPlayer.stop();
        }
        SoundPlayer.safetyBeltPlay = false;
    }

    /**
    * @description: 停止雷达
    * @createDate: 2023/5/22
    */
    public static void stopRadar() {
        LogUtils.printI(TAG, "stopRadar----radarPlay="+radarPlay);
        if(SoundPlayer.radarPlay && !SoundPlayer.turnSignalOpen()){
            SoundPlayer.stop();
            SoundPlayer.radarPlay = false;
        }
    }

    /**
    * @description: 停止双闪
    * @createDate: 2023/5/22
    */
    public static void stopDoubleFlash() {
        if(SoundPlayer.doubleFlashPlay){
            SoundPlayer.stop();
            SoundPlayer.doubleFlashPlay = false;

//            if(leftTurnPlay){
//                leftTurnPlay = false;
//                SoundPlayer.play(Type.LEFT_TURN,"zx.mp3");
//            }else if(rightTurnPlay){
//                rightTurnPlay = false;
//                SoundPlayer.play(Type.RIGHT_TURN,"zx.mp3");
//            }
        }
    }

    public static void stopLeftTurn() {
        if(SoundPlayer.leftTurnPlay){
            SoundPlayer.stop();
            SoundPlayer.leftTurnPlay = false;
        }
    }

    public static void stopRightTurn() {
        if(SoundPlayer.rightTurnPlay){
            SoundPlayer.stop();
            SoundPlayer.rightTurnPlay = false;
        }
    }

    public static enum  Type{
        NONE,
        LEFT_TURN, //左转向
        RIGHT_TURN, //右转向
        DOUBLE_FLASH,//双闪
        SAFETY_BELT, //安全带
        RADAR //雷达
    }



    private static void player(String key){
        try {
            //注意，这里从hash表里取出了具体的ID
            Integer soundId = soundPoolMap.get(key);

            streamID = mSoundPool.play(soundId, 1, 1, 255, -1, 1);
//        mSoundPool.setVolume(streamID,1.0f,1.0f);
            LogUtils.printI(SoundPlayer.class.getSimpleName(),"player----soundId="+soundId +", streamID="+streamID);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
