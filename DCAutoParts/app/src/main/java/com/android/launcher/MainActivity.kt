package com.android.launcher

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.launcher.can.CanCommand
import com.android.launcher.can.CanSendHandler
import dc.library.auto.global.ConstVal
import dc.library.auto.task.logger.TaskLogger

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        TaskLogger.i("进入了 MainActivity")
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_lefts223)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        // 获得焦点
        TaskLogger.i("MainActivity 获得焦点")
        CanSendHandler.sendCan(CanCommand.Send.CAN3DC)
    }

    override fun onPause() {
        super.onPause()
        // 失去焦点
        TaskLogger.i("MainActivity 失去焦点")
        CanSendHandler.cancelTask()
    }
}