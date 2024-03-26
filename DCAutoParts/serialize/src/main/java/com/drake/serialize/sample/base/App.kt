package com.drake.serialize.sample.base

import android.app.Application
import com.drake.serialize.sample.hook.JsonSerializeHook
import com.tencent.mmkv.MMKV
import dc.library.utils.serialize.serialize.Serialize

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        // 可选的初始化配置
        MMKV.initialize(this)
        // MMKV.initialize(cacheDir.absolutePath, MMKVLogLevel.LevelInfo) // 参数1: 存储路径, 参数2: 日志等级
        Serialize.hook = JsonSerializeHook() // 请根据自己项目定义或者选择
    }
}