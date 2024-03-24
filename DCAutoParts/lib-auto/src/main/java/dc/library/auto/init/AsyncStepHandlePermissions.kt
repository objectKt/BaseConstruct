package dc.library.auto.init

import dc.library.auto.event.EventTag
import dc.library.auto.event.ManagerEvent
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType

class AsyncStepHandlePermissions : SimpleTaskStep() {

    override fun doTask() {
        ManagerEvent.sendTagEvent(EventTag.BLUETOOTH_PERMISSION_HANDLE)
    }

    override fun getName(): String {
        return "任务:处理必要的权限"
    }

    override fun getThreadType(): ThreadType {
        return ThreadType.ASYNC
    }
}