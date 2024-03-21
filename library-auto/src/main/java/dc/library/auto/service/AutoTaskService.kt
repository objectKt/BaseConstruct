package dc.library.auto.service

import android.app.Service
import android.content.Intent
import android.os.IBinder
import android.util.Log
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dc.library.utils.global.ConstVal
import dc.library.auto.manager.SerialPortInitTask
import dc.library.auto.task.XTask
import dc.library.auto.task.core.ITaskChainEngine
import dc.library.auto.task.core.param.ITaskResult
import dc.library.auto.task.core.step.impl.TaskChainCallbackAdapter
import dc.library.auto.task.core.step.impl.TaskCommand
import dc.library.utils.ValUtil

/**
 * 用于执行后台任务的 Service
 */
class AutoTaskService : Service() {

    companion object {
        private const val CLASS_NAME = "TaskManagerService"
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        runBackgroundTasks()
        return START_STICKY
    }

    /**
     * 不提供绑定，因为不打算暴露任何方法给其他组件
     */
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    /**
     * 应用在后台运行时设备内存不足，系统可能会杀死应用以释放内存
     * onLowMemory 监听系统的低内存通知，并根据需要停止或清理后台任务
     */
    override fun onLowMemory() {
        super.onLowMemory()
        // 清理内存，停止后台任务等
    }

    private fun runBackgroundTasks() {
        val startTime = System.currentTimeMillis()
        Log.i(ConstVal.Log.TAG, "$CLASS_NAME 任务执行 --- 开始")
        val groupTaskStep = XTask.getConcurrentGroupTask()
        groupTaskStep.apply {
            addTask(XTask.getTask(object : TaskCommand() {
                override fun run() {
                    try {
                        Thread.sleep(1000)
                        Log.i(ConstVal.Log.TAG, "$CLASS_NAME 任务执行 --- 执行了 1 秒")
                    } catch (e: Exception) {
                        Log.e(ConstVal.Log.TAG, "$CLASS_NAME Thread.sleep --- Exception --- ${e.message}")
                    }
                }
            }))
            addTask(XTask.getTask(object : TaskCommand() {
                override fun run() {
                    try {
                        Thread.sleep(2000)
                        Log.i(ConstVal.Log.TAG, "$CLASS_NAME 任务执行 --- 执行了 2 秒")
                    } catch (e: Exception) {
                        Log.e(ConstVal.Log.TAG, "$CLASS_NAME Thread.sleep --- Exception --- ${e.message}")
                    }
                }
            }))
        }
        XTask.getTaskChain()
            .addTask(SerialPortInitTask())
            .setTaskChainCallback(object : TaskChainCallbackAdapter() {
                override fun onTaskChainCompleted(engine: ITaskChainEngine, result: ITaskResult) {
                    Log.i(ConstVal.Log.TAG, "$CLASS_NAME 任务执行 --- 完成 --- 总共耗时:" + (System.currentTimeMillis() - startTime) + "ms")
                    notifyAfterFinishInitTask()
                }
            }).start()
    }

    /**
     * 使用 LocalBroadcastManager 的好处是它不会触发 Activity 的重启，
     * 而且可以避免使用 startService 和 startActivity 之间的混淆，
     * 这在处理跨组件通信时是一个好的实践。
     */
    private fun notifyAfterFinishInitTask() {
        val initTaskFinishedIntent = Intent(ValUtil.ActionBroadcast.LOCAL_BROADCAST_FINISH_INIT_TASK)
        LocalBroadcastManager.getInstance(this@AutoTaskService).sendBroadcast(initTaskFinishedIntent)
    }
}