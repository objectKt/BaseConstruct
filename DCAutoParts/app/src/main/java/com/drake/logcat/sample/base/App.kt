package com.drake.logcat.sample.base

import android.app.Application
import dc.library.utils.logcat.LogCat
import dc.library.utils.logcat.LogHook
import dc.library.utils.logcat.LogInfo

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        LogCat.setDebug()
        LogCat.addHook(object : LogHook {
            /*
             * 上传日志拦截器
             */
            override fun hook(info: LogInfo) {
                info.msg?.let {
                    // ... 上传或者保存到本地
                }
            }
        })
    }
}