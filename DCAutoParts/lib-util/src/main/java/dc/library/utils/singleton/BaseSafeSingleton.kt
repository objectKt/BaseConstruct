package dc.library.utils.singleton

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

    fun getInstance(a: A, b: B, c: C, d: D): T =
        instance ?: synchronized(this) {
            instance ?: constructor(a, b, c, d).also { instance = it }
        }
}