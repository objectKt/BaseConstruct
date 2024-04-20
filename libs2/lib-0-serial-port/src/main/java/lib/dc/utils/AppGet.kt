package lib.dc.utils

import android.annotation.SuppressLint
import android.content.Context

/**
 * 获取 ApplicationContext
 */
@SuppressLint("StaticFieldLeak")
object AppGet {

    private var context: Context? = null

    @JvmStatic
    fun appContext(): Context {
        if (context == null) {
            throw RuntimeException("Please call [AppGet.init(context)] first")
        } else {
            return context as Context
        }
    }

    @JvmStatic
    fun init(context: Context) {
        if (AppGet.context != null) return
        AppGet.context = context.applicationContext
    }
}