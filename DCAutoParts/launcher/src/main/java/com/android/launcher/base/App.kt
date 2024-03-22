package com.android.launcher.base

import android.app.Application
import com.android.launcher.BR
import com.drake.brv.utils.BRV
import dc.library.auto.config.Config
import dc.library.ui.base.Engine
import dc.library.utils.logcat.LogCat
import dc.library.utils.logcat.LogHook
import dc.library.utils.logcat.LogInfo

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLibraries()
    }

    private fun initLibraries() {
        // 是否隐藏串口
        //Config.setHideSerialPort(true)
        // 初始化基础库
        Engine.initialize(this)
        // 初始化 BindingAdapter 的默认绑定 ID
        BRV.modelId = BR.m
        // 启用日志系统
        LogCat.setDebug()
        // 日志上传拦截器
        LogCat.addHook(object : LogHook {
            override fun hook(info: LogInfo) {
                info.msg?.let {
                    // ... 上传或者保存到本地
                }
            }
        })
    }
}