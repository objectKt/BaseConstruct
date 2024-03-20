package com.android.launcher

import android.app.Application
import android.content.Intent
import com.android.launcher.service.TaskManagerService
import dc.library.auto.global.ConstVal
import dc.library.auto.task.XTask
import dc.library.utils.ContextUtil

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextUtil.init(applicationContext)
        // 启动需要持续运行的 Service
        startService(Intent(this@App, TaskManagerService::class.java))
    }
}