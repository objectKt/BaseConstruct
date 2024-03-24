package dc.library.auto.init

import dc.library.auto.bus_usb.util.UsbDevicesFinder
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType

/**
 * 检测 USB
 */
class SyncStepFindUsb : SimpleTaskStep() {

    override fun doTask() {
        UsbDevicesFinder.findDevices()
    }

    override fun getName(): String {
        return "任务:查找 USB 接口设备"
    }

    override fun getThreadType(): ThreadType {
        return ThreadType.SYNC
    }
}