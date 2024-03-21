package com.drake.engine.base

import android.annotation.SuppressLint
import android.app.Application
import android.content.Context
import androidx.appcompat.app.AppCompatActivity

@SuppressLint("StaticFieldLeak")
lateinit var app: Context

object Engine {
    /*
     * Engine会使用AppStartup默认初始化无需执行本函数
     * 但是多进程应用要求在Application中初始化
     */
    fun initialize(app: Application) {
        com.drake.engine.base.app = app
        app.registerActivityLifecycleCallbacks(activityCallbacks)
    }

    /** 当前activity */
    @JvmStatic
    val currentActivity: AppCompatActivity?
        get() = activityCallbacks.currentActivityWeak?.get()

    /** 默认的生命周期回调 */
    @JvmStatic
    var activityCallbacks: EngineActivityCallbacks = EngineActivityCallbacks()
}