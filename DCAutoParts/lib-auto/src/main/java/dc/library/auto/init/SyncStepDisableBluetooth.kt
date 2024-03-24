package dc.library.auto.init

import android.Manifest
import android.bluetooth.BluetoothAdapter
import android.bluetooth.BluetoothManager
import android.content.Context
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.ActivityCompat
import com.drake.channel.sendEvent
import com.permissionx.guolindev.PermissionX
import dc.library.auto.event.EventModel
import dc.library.auto.event.EventTag
import dc.library.auto.event.ManagerEvent
import dc.library.auto.task.api.step.SimpleTaskStep
import dc.library.auto.task.core.ThreadType
import dc.library.ui.base.app
import dc.library.utils.logcat.LogCat

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
        return ThreadType.SYNC
    }
}