package dc.library.auto.util

import android.annotation.SuppressLint
import android.content.Context

/**
 * 获取 ApplicationContext
 */
@SuppressLint("StaticFieldLeak")
object UtilContext {
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
        if (UtilContext.context != null) return
        UtilContext.context = context.applicationContext
    }
}