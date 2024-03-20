package dc.library.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * 获取 ApplicationContext
 */
@SuppressLint("StaticFieldLeak")
object ContextUtil {
    private var context: Context? = null

    @JvmStatic
    fun getApplicationContext(): Context {
        if (context == null) {
            throw RuntimeException("Please call [UtilContext.init(context)] first")
        } else {
            return context as Context
        }
    }

    @JvmStatic
    fun init(context: Context) {
        if (ContextUtil.context != null) return
        ContextUtil.context = context.applicationContext
    }
}