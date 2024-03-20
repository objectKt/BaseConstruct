package com.android.launcher.service.task.kotlin

import android.content.Context
import android.media.AudioManager
import com.android.launcher.App
import com.android.launcher.util.SPUtils
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType
import dc.library.auto.task.logger.TaskLogger
import dc.library.utils.ContextUtil

/**
 * 警报音量初始化
 */
class CarAlarmVolumeInitTask : SimpleTaskStep() {

    override fun doTask() {
        TaskLogger.i("starting doTask $name")
        try {
            val audioManager: AudioManager = ContextUtil.getApplicationContext().getSystemService(Context.AUDIO_SERVICE) as AudioManager
            val maxVolume = audioManager.getStreamMaxVolume(AudioManager.STREAM_MUSIC)
            var currentVolume = SPUtils.getInt(App.getGlobalContext(), SPUtils.SP_CAR_ALARM_VOLUME, 0)
            if (currentVolume == 0) {
                currentVolume = maxVolume
            }
            TaskLogger.i("CarAlarmVolumeInitTaskKotlin maxVolume = $maxVolume, currentVolume = $currentVolume")
            audioManager.setStreamVolume(AudioManager.STREAM_MUSIC, currentVolume, 0)
        } catch (e: java.lang.Exception) {
            e.printStackTrace()
        }
        TaskLogger.i("finish doTask $name")
    }

    override fun getName(): String {
        return "CarAlarmVolumeInitTaskKotlin"
    }

    override fun getThreadType(): ThreadType {
        // 任务的优先级不高，使用异步子线程执行
        // 耗时任务，比如第三方依赖库的初始化、大数据的预加载、磁盘读写操作等
        return ThreadType.ASYNC
    }
}