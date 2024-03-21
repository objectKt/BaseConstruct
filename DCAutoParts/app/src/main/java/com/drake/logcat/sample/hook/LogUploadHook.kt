package com.drake.logcat.sample.hook

import dc.library.utils.logcat.LogHook
import dc.library.utils.logcat.LogInfo

/**
 * 上传日志拦截器
 * @author hf
 */
class LogUploadHook : LogHook {

    override fun hook(info: LogInfo) {
        info.msg?.let {
            // ... 上传或者保存到本地
        }
    }
}