package dc.library.auto.singleton

import android.content.Context
import android.widget.Toast

class SingletonTest private constructor(private val context: Context) {

    companion object : BaseSafeSingleton<SingletonTest, Context>(::SingletonTest)

    fun doSomeThingsWithContext() {
        Toast.makeText(context, "测试安全单例的实现", Toast.LENGTH_SHORT).show()
    }

    fun doFinish() {
        Toast.makeText(context, "结束", Toast.LENGTH_SHORT).show()
    }
}