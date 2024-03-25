package dc.library.utils

interface ValUtil {

    object Action {
        const val LOCAL_BROADCAST_FINISH_INIT_TASK: String = "com.android.launcher.service.LOCAL_BROADCAST_FINISH_INIT_TASK"
        const val INTENT_ACTION_GRANT_USB: String = "com.android.launcher.GRANT_USB"
    }

    object Permission {
        const val REQUEST_CODE_BLUETOOTH_CONNECT = 2023001
    }

    object Log {
        const val TAG: String = "dc-auto-parts"
    }

    @Suppress("ConstPropertyName")
    object Ttl {
        const val ttys1: String = "ttyS1"
        const val ttys1BaudRate: Int = 115200
        const val ttys3: String = "ttyS3"
        const val ttys3BaudRate: Int = 9600
    }
}