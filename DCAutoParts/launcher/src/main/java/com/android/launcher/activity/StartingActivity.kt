package com.android.launcher.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import androidx.lifecycle.Lifecycle
import com.android.launcher.base.BaseActivity
import dc.library.auto.manager.SerialPortInitTask
import dc.library.auto.task.XTask
import dc.library.auto.task.api.step.ConcurrentGroupTaskStep
import dc.library.auto.task.core.ITaskChainEngine
import dc.library.auto.task.core.param.ITaskResult
import dc.library.auto.task.core.step.impl.TaskChainCallbackAdapter
import dc.library.auto.task.core.step.impl.TaskCommand
import dc.library.auto.task.thread.pool.cancel.ICanceller
import dc.library.ui.base.app
import dc.library.utils.logcat.LogCat

/**
 * 启动页面（奔驰 LOGO 欢迎页面）
 * 同时处理部分模块的初始化
 * @author hf
 */
class StartingActivity : BaseActivity() {

    private var mTaskCancel: ICanceller? = null

    override fun stateChangeLogic(event: Lifecycle.Event) {
        LogCat.d("生命周期 == 进入了 ${event.name}")
        when (event) {
            Lifecycle.Event.ON_START -> startSomeInitTask()
            else -> {}
        }
    }

    private fun startSomeInitTask() {
        // 异步线程并行任务组
        val groupTaskStep = XTask.getConcurrentGroupTask().apply {
            addTask("任务:初始化常用全局数据量")
            addTask("任务:初始化 USB")
            addTask("任务:关掉蓝牙")
            addTask("任务:初始化声音播放器")
        }
        val engine = XTask.getTaskChain()
        engine.addTask(groupTaskStep)
        engine.addTask(SerialPortInitTask())
        engine.setTaskChainCallback(taskChainCallback(System.currentTimeMillis()))
        mTaskCancel = engine.start()
    }

    private fun taskChainCallback(timestampBegin: Long) = object : TaskChainCallbackAdapter() {

        override fun onTaskChainStart(engine: ITaskChainEngine) {
            LogCat.i("=== 启动任务链 -- begin")
        }

        override fun onTaskChainCompleted(engine: ITaskChainEngine, result: ITaskResult) {
            mTaskCancel?.cancel()
            LogCat.i("=== 结束任务链 -- Finish 总共耗时: ${System.currentTimeMillis() - timestampBegin} ms")
            gotoMainActivity()
        }

        override fun onTaskChainError(engine: ITaskChainEngine, result: ITaskResult) {
            LogCat.i("=== 任务链失败 -- Error 总共耗时: ${System.currentTimeMillis() - timestampBegin} ms")
            mTaskCancel?.cancel()
            restartApp(app)
        }
    }

    private fun gotoMainActivity() {
        val intent = Intent(this@StartingActivity, MainActivity::class.java)
        startActivity(intent)
        this@StartingActivity.finish()
    }

    private fun ConcurrentGroupTaskStep.addTask(des: String = "") {
        addTask(XTask.getTask(object : TaskCommand() {
            override fun run() {
                Thread.sleep(500)
            }
        }).apply { name = des })
    }

    private fun restartApp(context: Context) {
        LogCat.e("应用重启了 --- StartingActivity 触发")
        // 关闭当前应用的所有活动
        (context as Activity).finishAffinity()
        // 创建一个新的意图来重新启动应用
        val intent = Intent(context, StartingActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        // 启动新的任务栈，这将重新打开应用
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}