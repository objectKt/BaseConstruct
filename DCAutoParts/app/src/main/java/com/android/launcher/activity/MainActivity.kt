package com.android.launcher.activity

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import androidx.appcompat.app.AppCompatActivity
import com.android.launcher.R
import dc.library.auto.bus_can.CanSendHandler
import dc.library.auto.task.logger.TaskLogger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskLogger.i("进入了 MainActivity")
        setContentView(R.layout.activity_main_lefts223)
        checkDeviceStatus()
    }

    @Suppress("ControlFlowWithEmptyBody")
    private fun checkDeviceStatus() {
        val powerManager: PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        val isDeviceInteractive = powerManager.isInteractive
        // 根据设备否处于交互状态执行相应的操作
        if (!isDeviceInteractive) {
            // 设备可能处于挂起或锁屏状态，执行需要的操作，例如暂停游戏或视频播放
        } else {
            // 设备处于活动状态，执行你需要的操作，例如恢复游戏或视频播放
        }
    }

    override fun onResume() {
        super.onResume()
        // 获得焦点
        TaskLogger.i("MainActivity 获得焦点")
        //CanSendHandler.sendCan(CanCommand.Send.CAN3DC)
    }

    override fun onPause() {
        super.onPause()
        // 失去焦点
        TaskLogger.i("MainActivity 失去焦点")
        CanSendHandler.cancelTask()
    }
}