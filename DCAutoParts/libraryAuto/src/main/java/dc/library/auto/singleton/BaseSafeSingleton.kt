package dc.library.auto.singleton

/**
 * 线程安全的单例模式，在同步块中使用双重检查锁定来实例化实例，以消除多线程环境中竞争条件的可能性。
 * Thread-Safe Solution # Write Once; Use Many;
 * 通过使用对象声明和伴生对象来实现带参数的单例
 * companion object : BaseSafeSingleton<T, A, B, C, D>(::T)
 * @author hf
 */
open class BaseSafeSingleton<out T, in A, in B, in C, in D>(private val constructor: (A, B, C, D) -> T) {

    @Volatile
    private var instance: T? = null

    fun getInstance(arg1: A, arg2: B, arg3: C, arg4: D): T =
        instance ?: synchronized(this) {
            instance ?: constructor(arg1, arg2, arg3, arg4).also { instance = it }
        }
}