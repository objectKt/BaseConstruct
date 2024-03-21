package com.android.launcher.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.android.launcher.R
import dc.library.auto.bus_can.CanSendHandler
import dc.library.auto.task.logger.TaskLogger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskLogger.i("进入了 MainActivity")
        setContentView(R.layout.activity_main_lefts223)
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