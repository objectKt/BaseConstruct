package dc.library.auto.carmanager.impl

import dc.library.auto.event.MessageEvent

interface BaseModuleImpl {

    /**
     * 周期任务执行间隔时间
     */
    fun setScheduleTaskPeriod(seconds: Long)

    /**
     * 发送事件
     */
    fun postEvent(event: MessageEvent)
}