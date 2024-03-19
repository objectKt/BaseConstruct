package com.android.launcher.service.task.kotlin

import com.android.launcher.util.AppUtils
import com.dc.auto.library.module.module_db.repository.CarTravelRepository
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType
import dc.library.auto.task.logger.TaskLogger
import dc.library.auto.util.ContextUtil

/**
 * 重置启动后数据任务
 */
class ResetLaunchAfterDataTask : SimpleTaskStep() {

    override fun doTask() {
        try {
            val context = ContextUtil.getApplicationContext()
            val carTravelTable = CarTravelRepository.getInstance().getData(context, AppUtils.getDeviceId(context))
            if (carTravelTable != null) {
                carTravelTable.currentQtrip = 0.0f
                CarTravelRepository.getInstance().updateData(context, carTravelTable)
            }
            TaskLogger.i("ResetLaunchAfterDataTask run---")
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getName(): String {
        return "ResetLaunchAfterDataTask"
    }

    override fun getThreadType(): ThreadType {
        // 任务的优先级不高，使用异步子线程执行
        // 耗时任务，比如第三方依赖库的初始化、大数据的预加载、磁盘读写操作等
        return ThreadType.ASYNC
    }
}