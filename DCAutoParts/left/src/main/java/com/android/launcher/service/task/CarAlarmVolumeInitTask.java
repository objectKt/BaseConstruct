package com.android.launcher.service.task;

import android.content.Context;
import android.media.AudioManager;

import com.android.launcher.App;
import com.android.launcher.meter.MeterActivity;
import com.android.launcher.util.BY8302PCB;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.SPUtils;

/**
 * 警报音量初始化
 * @date： 2024/1/3
 * @author: 78495
*/
public class CarAlarmVolumeInitTask extends TaskBase{

    public CarAlarmVolumeInitTask(Context context) {
        super(context);
    }

    @Override
    public void run() {
        try {
//            Thread.sleep(2000);

//            int volume = SPUtils.getInt(App.getGlobalContext(), SPUtils.SP_CAR_ALARM_VOLUME, 25);
//            LogUtils.printI(TAG, "alerm volume=" + volume);
//            BY8302PCB.volumeSetup(volume);
//            try {
//                Thread.sleep(30);
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//            BY8302PCB.singleLoopMode();

            AudioManager audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);
            int maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC);
            int currentVolume = SPUtils.getInt(App.getGlobalContext(), SPUtils.SP_CAR_ALARM_VOLUME, 0);
            if (currentVolume == 0) {
                currentVolume = maxVolume;
            }
            LogUtils.printI(TAG, "maxVolume=" + maxVolume + ", currentVolume=" + currentVolume);
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
