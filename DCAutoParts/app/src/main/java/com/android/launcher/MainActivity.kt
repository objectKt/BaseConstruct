package com.android.launcher

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.launcher.can.CanCommand
import com.android.launcher.can.CanSendHandler

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_lefts223)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        CanSendHandler.sendCan(CanCommand.Send.CAN3DC)
        CanSendHandler.sendCan(CanCommand.Send.CAN3F6)
    }

    override fun onDestroy() {
        super.onDestroy()
        CanSendHandler.cancelTask()
    }
}