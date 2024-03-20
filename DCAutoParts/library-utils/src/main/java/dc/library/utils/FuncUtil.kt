package dc.library.utils

object FuncUtil {

    /**
     * 模拟执行耗时任务
     *
     * @param time 模拟执行所需要的时间
     */
    fun mockProcess(time: Long) {
        try {
            Thread.sleep(time)
        } catch (e: InterruptedException) {
            e.printStackTrace()
        }
    }
}