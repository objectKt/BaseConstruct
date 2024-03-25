@file:Suppress("SENSELESS_COMPARISON")

package dc.library.utils.logcat

import android.util.Log
import dc.library.utils.logcat.LogCat.Type.*
import dc.library.utils.logcat.LogCat.enabled
import dc.library.utils.logcat.LogCat.logHooks
import dc.library.utils.logcat.LogCat.tag
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import kotlin.math.min

@Suppress("MemberVisibilityCanBePrivate")
/**
 * @property tag 默认日志标签
 * @property enabled 日志全局开关
 * @property logHooks 日志拦截器
 * @author hf
 */
object LogCat {

    enum class Type {
        VERBOSE, DEBUG, INFO, WARN, ERROR, WTF
    }

    /** 日志默认标签 */
    var tag = "大川汽配"

    /** 是否启用日志 */
    var enabled = true

    /** 日志是否显示代码位置 */
    var traceEnabled = true

    /** 日志的Hook钩子 */
    val logHooks by lazy { ArrayList<LogHook>() }

    /**
     * @param enabled 是否启用日志
     * @param tag 日志默认标签
     */
    fun setDebug(enabled: Boolean = true, tag: String = this.tag) {
        this.enabled = enabled
        this.tag = tag
    }

    //<editor-fold desc="Hook">
    /**
     * 添加日志拦截器
     */
    fun addHook(hook: LogHook) {
        logHooks.add(hook)
    }

    /**
     * 删除日志拦截器
     */
    fun removeHook(hook: LogHook) {
        logHooks.remove(hook)
    }
    //</editor-fold>

    // <editor-fold desc="Log">

    @JvmOverloads
    @JvmStatic
    fun v(msg: Any?, tag: String = this.tag, tr: Throwable? = null, occurred: Throwable? = Exception()) {
        print(VERBOSE, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun i(msg: Any?, tag: String = this.tag, tr: Throwable? = null, occurred: Throwable? = Exception()) {
        print(INFO, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun d(msg: Any?, tag: String = this.tag, tr: Throwable? = null, occurred: Throwable? = Exception()) {
        print(DEBUG, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun w(msg: Any?, tag: String = this.tag, tr: Throwable? = null, occurred: Throwable? = Exception()) {
        print(WARN, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun e(msg: Any?, tag: String = this.tag, tr: Throwable? = null, occurred: Throwable? = Exception()) {
        print(ERROR, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun e(tr: Throwable?, tag: String = this.tag, occurred: Throwable? = Exception(), msg: Any? = "") {
        print(ERROR, msg, tag, tr, occurred)
    }

    @JvmOverloads
    @JvmStatic
    fun wtf(msg: Any?, tag: String = this.tag, tr: Throwable? = null, occurred: Throwable? = Exception()) {
        print(WTF, msg, tag, tr, occurred)
    }

    /**
     * 输出日志
     * 如果[msg]和[occurred]为空或者[tag]为空将不会输出日志, 拦截器
     * @param type 日志等级
     * @param msg 日志信息
     * @param tag 日志标签
     * @param occurred 日志异常
     */
    private fun print(type: Type = INFO, msg: Any? = null, tag: String = this.tag, tr: Throwable? = null, occurred: Throwable? = Exception()) {
        if (!enabled || msg == null) return
        var message = msg.toString()
        val info = LogInfo(type, message, tag, tr, occurred)
        for (logHook in logHooks) {
            logHook.hook(info)
            if (info.msg == null) return
        }
        if (traceEnabled && occurred != null) {
            occurred.stackTrace.getOrNull(1)?.run {
                message += " ...($fileName:$lineNumber)"
            }
        }
        val max = 3800
        val length = message.length
        if (length > max) {
            synchronized(this) {
                var startIndex = 0
                var endIndex = max
                while (startIndex < length) {
                    endIndex = min(length, endIndex)
                    val substring = message.substring(startIndex, endIndex)
                    log(type, substring, tag, tr)
                    startIndex += max
                    endIndex += max
                }
            }
        } else {
            log(type, message, tag, tr)
        }
    }

    /**
     * JSON格式化输出日志
     * @param tag 日志标签
     * @param msg 日志信息
     * @param type 日志类型
     * @param occurred 日志发生位置
     */
    @JvmOverloads
    @JvmStatic
    fun json(json: Any?, tag: String = this.tag, msg: String = "", type: Type = INFO, occurred: Throwable? = Exception()) {
        if (!enabled || json == null) return
        var message = json.toString()
        val occurredMsg = if (traceEnabled && occurred != null) {
            occurred.stackTrace.getOrNull(1)?.run { " ($fileName:$lineNumber)" }
        } else ""
        if (message.isBlank()) {
            print(type, "$msg$occurredMsg\n$message", tag, occurred = null)
            return
        }
        val tokener = JSONTokener(message)
        val obj = try {
            tokener.nextValue()
        } catch (e: Exception) {
            "Parse json error"
        }
        message = when (obj) {
            is JSONObject -> obj.toString(2)
            is JSONArray -> obj.toString(2)
            else -> obj.toString()
        }
        print(type, "$msg$occurredMsg\n$message", tag, occurred = null)
    }

    private fun log(type: Type, msg: String, tag: String, tr: Throwable?) {
        when (type) {
            VERBOSE -> Log.v(tag, msg, tr)
            DEBUG -> Log.d(tag, msg, tr)
            INFO -> Log.i(tag, msg, tr)
            WARN -> Log.w(tag, msg, tr)
            ERROR -> Log.e(tag, msg, tr)
            WTF -> Log.wtf(tag, msg, tr)
        }
    }
    // </editor-fold>
}