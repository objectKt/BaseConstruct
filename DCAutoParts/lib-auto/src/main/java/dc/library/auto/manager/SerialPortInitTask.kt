package dc.library.auto.manager

import dc.library.auto.config.Config
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType
import dc.library.auto.task.logger.TaskLogger
import dc.library.utils.logcat.LogCat

/**
 * 串口初始化
 */
class SerialPortInitTask : SimpleTaskStep() {

    override fun doTask() {
        if (Config.HIDE_SERIAL_PORT) {
            try {
                Thread.sleep(3000)
            } catch (_: Exception) {
            }
        } else {
            TTLSerialPortsManager.initSerialPorts()
        }
    }

    override fun getName(): String {
        return "SerialPortInitTask"
    }

    override fun getThreadType(): ThreadType {
        return ThreadType.ASYNC_IO
    }
}