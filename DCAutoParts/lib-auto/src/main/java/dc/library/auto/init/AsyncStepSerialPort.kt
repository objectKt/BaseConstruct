package dc.library.auto.init

import dc.library.auto.manager.TTLSerialPortsManager
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType

/**
 * 串口初始化
 */
class AsyncStepSerialPort : SimpleTaskStep() {

    override fun doTask() {
        if (true) {
            try {
                Thread.sleep(1000)
            } catch (_: Exception) {
            }
        } else {
            TTLSerialPortsManager.initSerialPorts()
        }
    }

    override fun getName(): String {
        return "任务:初始化串口 TTL"
    }

    override fun getThreadType(): ThreadType {
        return ThreadType.ASYNC
    }
}