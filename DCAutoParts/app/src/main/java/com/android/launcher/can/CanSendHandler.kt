package com.android.launcher.can

import android.util.Log
import dc.library.auto.global.ConstVal
import dc.library.auto.task.XTask
import dc.library.auto.task.thread.pool.cancel.ICancelable
import dc.library.auto.task.thread.utils.CancelUtils
import dc.library.auto.util.FuncUtil
import java.util.concurrent.TimeUnit

object CanSendHandler : CanSendImpl {

    // 可取消执行接口的集合
    private val mCancelableList: MutableList<ICancelable> = mutableListOf()

    // MCU主体协议格式：引导码 0XAA +长度 + 帧ID + Data + checksum
    val DATA_HEAD = "AA0000"

    override fun sendCan(canId: Int) {
        when (canId) {
            CanCommand.Send.CAN3DC -> {
                // 发动机机油液位
                doPollingTaskFixedRate()
            }

            CanCommand.Send.CAN3F6 -> {
                //doCAN3F6TaskFixedRate()
            }

            CanCommand.Send.CAN37C -> {

            }

            CanCommand.Send.CAN045 -> {

            }

            CanCommand.Send.CAN0FD -> {

            }
        }
    }

    private fun doPollingTaskFixedRate(periodSeconds: Long = 2) {
        val cancelable: ICancelable = XTask.scheduleAtFixedRate({
            XTask.backgroundSubmit {
                Log.e(ConstVal.Log.TAG, "backgroundSubmit 1 执行了发动机机油液位发送指令..., thread:${Thread.currentThread().name} priority = ${Thread.currentThread().priority}")
                FuncUtil.mockProcess(500)
            }
            XTask.backgroundSubmit {
                Log.e(ConstVal.Log.TAG, "backgroundSubmit 2 执行了发动机机油液位发送指令..., thread:${Thread.currentThread().name} priority = ${Thread.currentThread().priority}")
                FuncUtil.mockProcess(500)
            }
            XTask.backgroundSubmit {
                Log.e(ConstVal.Log.TAG, "backgroundSubmit 3 执行了发动机机油液位发送指令..., thread:${Thread.currentThread().name} priority = ${Thread.currentThread().priority}")
                FuncUtil.mockProcess(500)
            }
        }, 0, periodSeconds, TimeUnit.SECONDS)
        mCancelableList.add(cancelable)
    }

    private fun doCAN3F6TaskFixedRate(periodSeconds: Long = 2) {
        val cancelable: ICancelable = XTask.scheduleAtFixedRate({
            Log.e(ConstVal.Log.TAG, "执行了CAN3F6Task发送指令..., thread:" + Thread.currentThread().name)
            FuncUtil.mockProcess(2000)
        }, 0, periodSeconds, TimeUnit.SECONDS)
        mCancelableList.add(cancelable)
    }

    fun cancelTask() {
        Log.e(ConstVal.Log.TAG, "关闭任务执行..., thread:" + Thread.currentThread().name)
        CancelUtils.cancel(mCancelableList)
    }
}