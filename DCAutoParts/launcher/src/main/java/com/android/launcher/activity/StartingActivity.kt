package com.android.launcher.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.drake.net.time.Interval
import dc.library.auto.manager.SerialPortInitTask
import dc.library.auto.task.XTask
import dc.library.auto.task.api.step.ConcurrentGroupTaskStep
import dc.library.auto.task.core.ITaskChainEngine
import dc.library.auto.task.core.param.ITaskResult
import dc.library.auto.task.core.step.impl.TaskChainCallbackAdapter
import dc.library.auto.task.core.step.impl.TaskCommand
import dc.library.ui.base.app
import dc.library.utils.logcat.LogCat
import java.util.concurrent.TimeUnit

/**
 * 启动页面（奔驰 LOGO 欢迎页面）
 * 同时处理部分模块的初始化
 * @author hf
 */
class StartingActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startSomeInitTask()
    }

    override fun onResume() {
        super.onResume()
        val timestampBegin = System.currentTimeMillis()
    }

    override fun onStop() {
        super.onStop()
        LogCat.i("onStop .. 停止")
    }

    private fun startSomeInitTask() {
        LogCat.i("=== 启动任务链 -- begin")
        val timestampBegin = System.currentTimeMillis()
        val groupTaskStep = XTask.getConcurrentGroupTask()
        groupTaskStep.apply {
            addTask("初始化常用全局数据量")
            addTask("初始化 USB")
            addTask("关掉蓝牙")
            addTask("初始化声音播放器")
        }
        XTask.getTaskChain()
            .addTask(groupTaskStep)
            // 初始化串口 TTL
            .addTask(SerialPortInitTask())
            .setTaskChainCallback(object : TaskChainCallbackAdapter() {
                override fun onTaskChainCompleted(engine: ITaskChainEngine, result: ITaskResult) {
                    LogCat.i("=== 结束任务链 -- Finish 总共耗时: ${System.currentTimeMillis() - timestampBegin} ms")
                    gotoMainActivity()
                }
            }).start()
    }

    private fun ConcurrentGroupTaskStep.addTask(des: String = "") {
        addTask(XTask.getTask(object : TaskCommand() {
            override fun run() {
                Thread.sleep(500)
            }
        }).apply { name = des })
    }

    private fun gotoMainActivity() {
        val intent = Intent(this@StartingActivity, MainActivity::class.java)
        startActivity(intent)
        this@StartingActivity.finish()
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