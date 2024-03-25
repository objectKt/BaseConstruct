package com.android.launcher.can.handler

import android.util.Log
import com.android.launcher.can.util.AutoUtilCan
import com.android.launcher.can.util.CanSendImpl
import dc.library.utils.global.ConstVal
import dc.library.auto.task.XTask
import dc.library.auto.task.thread.pool.cancel.ICancelable
import dc.library.auto.task.thread.utils.CancelUtils
import dc.library.utils.FuncUtil
import java.util.concurrent.TimeUnit

object CanSendHandler : CanSendImpl {

    // 可取消执行接口的集合
    private val mCancelableList: MutableList<ICancelable> = mutableListOf()

    // MCU主体协议格式：引导码 0XAA +长度 + 帧ID + Data + checksum
    val DATA_HEAD = "AA0000"

    override fun sendCan(canId: Int) {
        val send = AutoUtilCan.CommandSend
        when (canId) {
            send.CAN3DC -> {
                // 发动机机油液位
                doPollingTaskFixedRate()
            }

            send.CAN3F6 -> {
                //doCAN3F6TaskFixedRate()
            }

            send.CAN37C -> {

            }

            send.CAN045 -> {

            }

            send.CAN0FD -> {

            }
        }
    }

    private fun doPollingTaskFixedRate(periodSeconds: Long = 2) {
        val cancelable: ICancelable = XTask.scheduleAtFixedRate({
//            XTask.backgroundSubmit {
//                Log.e(ConstVal.Log.TAG, "backgroundSubmit 1 执行了发动机机油液位发送指令..., thread:${Thread.currentThread().name} priority = ${Thread.currentThread().priority}")
//                FuncUtil.mockProcess(500)
//            }
            Log.e(ConstVal.Log.TAG, "backgroundSubmit 1 执行了发动机机油液位发送指令..., thread:${Thread.currentThread().name} priority = ${Thread.currentThread().priority}")
            FuncUtil.mockProcess(500)
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