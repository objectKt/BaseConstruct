package dc.library.auto.init

import dc.library.auto.event.EventTag
import dc.library.auto.event.ManagerEvent
import dc.library.auto.task.core.step.impl.AbstractTaskStep

/**
 * 禁止使用蓝牙
 */
class SyncStepDisableBluetooth : AbstractTaskStep() {

    override fun doTask() {
        // 需手动通知执行结果
        ManagerEvent.sendTagEvent(EventTag.BLUETOOTH_PERMISSION_HANDLE)
    }

    override fun getName(): String {
        return "任务:禁止使用蓝牙"
    }

}