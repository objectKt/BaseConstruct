package com.android.launcher

import android.app.Application
import dc.library.auto.global.ConstVal
import dc.library.auto.task.XTask
import dc.library.utils.ContextUtil

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextUtil.init(applicationContext)
        XTask.debug(ConstVal.Log.TAG)
    }
}