package com.android.launcher.service.task.kotlin

import com.android.launcher.util.LogcatHelper
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType
import dc.library.auto.util.ContextUtil

/**
 * 清除多余日志任务
 */
class ClearUnnecessaryLogTask : SimpleTaskStep() {

    override fun doTask() {
        try {
            Thread.sleep(3000)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        try {
            LogcatHelper.getInstance(ContextUtil.getApplicationContext()).clearUnnecessaryLog()
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getName(): String {
        return "ClearUnnecessaryLogTask"
    }

    override fun getThreadType(): ThreadType {
        // 任务的优先级不高，使用异步子线程执行
        // 耗时任务，比如第三方依赖库的初始化、大数据的预加载、磁盘读写操作等
        return ThreadType.ASYNC
    }
}