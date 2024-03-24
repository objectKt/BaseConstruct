package dc.library.auto.init

import dc.library.auto.event.EventTag
import dc.library.auto.event.ManagerEvent
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType

/**
 * 禁止使用蓝牙
 */
class SyncStepDisableBluetooth : SimpleTaskStep() {

    override fun doTask() {
        ManagerEvent.sendTagEvent(EventTag.BLUETOOTH_PERMISSION_HANDLE)
    }

    override fun getName(): String {
        return "任务:禁止使用蓝牙"
    }

    override fun getThreadType(): ThreadType {
        return ThreadType.ASYNC
    }
}