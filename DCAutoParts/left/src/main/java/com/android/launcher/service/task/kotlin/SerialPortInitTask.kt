package com.android.launcher.service.task.kotlin

import com.android.launcher.kotlin.ttl.TTLSerialPortsManager
import dc.library.auto.global.ScreenCarType
import dc.library.auto.global.ScreenWhichSide
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType
import dc.library.auto.task.logger.TaskLogger

/**
 * 串口初始化
 */
class SerialPortInitTask : SimpleTaskStep() {

    override fun doTask() {
        TaskLogger.i("starting doTask $name")
        TTLSerialPortsManager.getInstance(
            a = ScreenCarType.RS223,
            b = ScreenWhichSide.LEFT,
            c = "ttyS1",
            d = 115200
        ).initExistDevice()
        try {
            Thread.sleep(500)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
        TTLSerialPortsManager.getInstance(
            a = ScreenCarType.RS223,
            b = ScreenWhichSide.LEFT,
            c = "ttyS3",
            d = 9600
        ).initExistDevice()
        TaskLogger.i("finish doTask $name")
    }

    override fun getName(): String {
        return "SerialPortInitTask"
    }

    override fun getThreadType(): ThreadType {
        // 任务优先级较高，执行有前后依赖，因此将任务放在第一位使用同步主线程执行
        return ThreadType.SYNC
    }
}