package dc.library.auto.singleton

/**
 * 线程安全的单例模式，在同步块中使用双重检查锁定来实例化实例，以消除多线程环境中竞争条件的可能性。
 * Thread-Safe Solution # Write Once; Use Many;
 * 通过使用对象声明和伴生对象来实现带参数的单例
 * companion object : BaseSafeSingleton<T, A>(::T)
 */
open class BaseSafeSingleton<out T, in A>(private val constructor: (A) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(arg: A): T =
        instance ?: synchronized(this) {
            instance ?: constructor(arg).also { instance = it }
        }
}