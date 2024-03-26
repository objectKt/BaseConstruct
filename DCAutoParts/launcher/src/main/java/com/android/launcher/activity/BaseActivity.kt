package com.android.launcher.activity


import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.android.launcher.activity.util.AutoUtilEvent
import com.android.launcher.can.util.AutoUtilCan
import com.drake.channel.receiveEvent
import dc.library.utils.logcat.LogCat
import kotlinx.coroutines.Job

abstract class BaseActivity : AppCompatActivity() {

    private lateinit var mReceive: Job

    protected abstract fun stateChangeLogic(event: Lifecycle.Event)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                stateChangeLogic(event)
                handleEventFromSteerWheel(event)
            }
        })
    }

    private fun handleEventFromSteerWheel(event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                mReceive = receiveEvent<AutoUtilEvent.EventSteerWheel>(AutoUtilEvent.Send.tag_event_steer_wheel) {
                    val type = it.type
                    steerWheelKeyboard(type)
                }
            }

            Lifecycle.Event.ON_DESTROY -> mReceive.cancel()
            else -> {}
        }

    }

    fun restartApp(context: Context) {
        LogCat.e("应用重启了")
        // to do clean anything in app
        // 关闭当前应用的所有活动
        (context as Activity).finishAffinity()
        // 创建一个新的意图来重新启动应用
        val intent = Intent(context, StartingActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        // 启动新的任务栈，这将重新打开应用
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }

    abstract fun steerWheelKeyboard(type: AutoUtilCan.Type)
}