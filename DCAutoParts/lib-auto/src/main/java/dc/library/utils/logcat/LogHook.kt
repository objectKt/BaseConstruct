package dc.library.utils.logcat

/**
 * 拦截日志
 * @author hf
 */
interface LogHook {
    fun hook(info: LogInfo)
}