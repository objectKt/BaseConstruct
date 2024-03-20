package dc.library.auto.global

/**
 * 全局常量管理器
 */
interface ConstVal {

    object Log {
        const val TAG: String = "dc-auto-parts"
    }

    object Type {
        const val TYPE_SAMPLE: Int = 0
    }

    object IntentAction {
        const val BOOT_COMPLETED: String = "android.intent.action.BOOT_COMPLETED"
    }

    object ReceiverAction {
        const val USB_LEFT = "com.car.left.usb1"
    }

    object SP {
        const val SP_NAME_LOCALE: String = "dc_language_setting"
        const val TAG_LANGUAGE: String = "dc_language_select"
        const val TAG_SYSTEM_LANGUAGE: String = "dc_system_language"
    }
}