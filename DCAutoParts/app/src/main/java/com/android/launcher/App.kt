package com.android.launcher

import android.app.Application
import dc.library.auto.util.ContextUtil

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        ContextUtil.init(applicationContext)
    }
}