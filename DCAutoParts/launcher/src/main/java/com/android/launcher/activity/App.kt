package com.android.launcher.activity

import android.app.Application
import com.android.launcher.BR
import com.drake.brv.utils.BRV
import com.tencent.mmkv.MMKV
import dc.library.auto.task.logger.TaskLogger
import dc.library.ui.base.Engine
import dc.library.utils.logcat.LogCat
import dc.library.utils.logcat.LogHook
import dc.library.utils.logcat.LogInfo
import dc.library.utils.serialize.hook.JsonSerializeHook
import dc.library.utils.serialize.serialize.Serialize

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        initLibraries()
    }

    private fun initLibraries() {
        MMKV.initialize(this)
        // 本地序列化存储的数据结构为Json
        Serialize.hook = JsonSerializeHook()
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