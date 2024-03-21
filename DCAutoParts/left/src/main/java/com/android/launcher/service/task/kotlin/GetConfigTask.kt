package com.android.launcher.service.task.kotlin

import com.android.launcher.service.LivingService
import dc.library.utils.global.type.UnitType
import com.android.launcher.util.SPUtils
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType
import dc.library.auto.task.logger.TaskLogger
import dc.library.utils.ContextUtil

/**
 * 获取配置
 */
class GetConfigTask : SimpleTaskStep() {

    override fun doTask() {
        try {
            val context = ContextUtil.getApplicationContext()
            LivingService.unitType = SPUtils.getInt(context, SPUtils.SP_UNIT_TYPE, UnitType.getDefaultType())
            LivingService.operateOriginMeter = SPUtils.getBoolean(context, SPUtils.SP_ORIGIN_METER_ON, false)
            LivingService.enableOpenDayRunLight = SPUtils.getBoolean(context, SPUtils.SP_DAY_RUN_LIGHT_ON, false)
            TaskLogger.i("GetConfigTask operateOriginMeter=" + LivingService.operateOriginMeter + ", enableOpenDayRunLight=" + LivingService.enableOpenDayRunLight)
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    override fun getName(): String {
        return "GetConfigTask"
    }

    override fun getThreadType(): ThreadType {
        // 任务的优先级不高，使用异步子线程执行
        // 耗时任务，比如第三方依赖库的初始化、大数据的预加载、磁盘读写操作等
        return ThreadType.ASYNC
    }
}