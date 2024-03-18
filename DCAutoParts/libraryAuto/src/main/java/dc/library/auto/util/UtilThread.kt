package dc.library.auto.util

object UtilThread {

    fun thread(start: Boolean = true, isDaemon: Boolean = false, contextClassLoader: ClassLoader? = null, name: String? = null, priority: Int = -1, block: () -> Unit): Thread {
        val thread = object : Thread() {
            override fun run() {
                block()
            }
        }
        if (isDaemon) thread.isDaemon = true
        if (priority > 0) thread.priority = priority
        if (name != null) thread.name = name
        if (contextClassLoader != null) thread.contextClassLoader = contextClassLoader
        if (start) thread.start()
        return thread
    }
}