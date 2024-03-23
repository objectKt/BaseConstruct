package com.android.launcher.util

import dc.library.utils.logcat.LogCat

object Func {

    /**
     * 生命周期相关的信息打印
     */
    fun logLifecycle(msg: String) {
        LogCat.i("生命周期 == 进入了 $msg")
    }
}