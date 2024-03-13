package dc.library.auto.global

/**
 * 全局常量管理器
 */
interface ConstVal {

    object Type {
        const val TYPE_SAMPLE: Int = 0
    }

    object IntentAction {
        const val BOOT_COMPLETED: String = "android.intent.action.BOOT_COMPLETED"
    }
}