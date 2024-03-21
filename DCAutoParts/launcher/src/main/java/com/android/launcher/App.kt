package com.android.launcher

import android.app.Application
import android.content.Intent
import dc.library.auto.service.AutoTaskService
import dc.library.utils.ContextUtil

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextUtil.init(applicationContext)
        // 启动需要持续运行的 Service
        startService(Intent(this@App, AutoTaskService::class.java))
    }
}