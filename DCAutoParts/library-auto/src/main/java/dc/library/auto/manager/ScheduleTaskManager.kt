package dc.library.auto.manager

import dc.library.auto.task.XTask
import dc.library.auto.task.api.step.ConcurrentGroupTaskStep
import dc.library.auto.task.core.ITaskChainEngine
import dc.library.auto.task.core.param.ITaskResult
import dc.library.auto.task.core.step.impl.TaskChainCallbackAdapter
import dc.library.auto.task.logger.TaskLogger

object ScheduleTaskManager {

    fun runInitTask(groupTaskStep: ConcurrentGroupTaskStep, startTime: Long) {
        TaskLogger.i("TASK --- Init 任务 starting")
        XTask.getTaskChain()
            .addTask(SerialPortInitTask())
            .addTask(groupTaskStep) //单独的任务，没有执行上的先后顺序. 例如：非核心数据的加载。
            .setTaskChainCallback(object : TaskChainCallbackAdapter() {
                override fun onTaskChainCompleted(engine: ITaskChainEngine, result: ITaskResult) {
                    TaskLogger.w("TASK --- Init 任务 Finish Well，总共耗时:" + (System.currentTimeMillis() - startTime) + "ms")
                }
            }).start()
    }
}