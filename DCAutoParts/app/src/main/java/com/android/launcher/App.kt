package com.android.launcher

import android.app.Application
import dc.library.auto.util.UtilContext

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        UtilContext.init(applicationContext)
    }
}