package dc.library.auto.manager

import dc.library.auto.database.s223entity.left.CarTravelTable
import dc.library.utils.event.MessageEvent
import dc.library.utils.global.ScreenCarType
import dc.library.utils.global.ScreenWhichSide
import dc.library.utils.singleton.BaseSafeSingleton
import dc.library.auto.task.XTask
import dc.library.auto.task.thread.pool.cancel.ICancelable
import java.util.concurrent.TimeUnit

/**
 * 汽车各个功能模块管理类
 * @param screenCar     车屏幕类型（s222,s223, etc.）
 * @param screenSide    屏幕哪一边（Left,Right）
 * @param str           备用参数 String
 * @param value         备用参数 Int
 * @author hf
 */
class CarModulesManager private constructor(
    private val screenCar: ScreenCarType,
    private val screenSide: ScreenWhichSide,
    private val str: String,
    private val value: Int
) {

    companion object : BaseSafeSingleton<CarModulesManager, ScreenCarType, ScreenWhichSide, String, Int>(::CarModulesManager)

    // 可取消执行接口的集合
    private val mCancelableList: MutableList<ICancelable> = mutableListOf()

    private var mileageImpl: ModuleMileageImpl? = null
    private var observeSpeedImpl: ModuleObserveSpeedImpl? = null

    /**
     * 汽车模块定时任务接口统一管理
     * 增加新功能，只要增加或修改接口就可以了
     * @author hf
     */
    fun manageModulesScheduleTasks() {
        mileageImpl = object : ModuleMileageImpl {
            // 汽车里程功能模块
            override val mileageData: CarTravelTable
                get() {
                    return CarTravelTable.updateData()
                }
            override val unitType: Int
                get() {
                    return UnitType.KM.ordinal
                }
            override val schedulePeriod: Long get() = 2L
            override val messageEvent: MessageEvent? get() = null
        }
        observeSpeedImpl = object : ModuleObserveSpeedImpl {
            // 定时观察发动机转速和车速更新
            override val schedulePeriod: Long get() = 2L
            override val messageEvent: MessageEvent? get() = null
        }
        startDoScheduleTasks()
    }

    private fun startDoScheduleTasks() {
        mileageImpl?.let {
            scheduleTask(mileageImpl?.schedulePeriod!!) {
                // Runnable
            }
        }
        observeSpeedImpl?.let {
            scheduleTask(observeSpeedImpl?.schedulePeriod!!) {
                // Runnable

            }
        }
    }

    private fun scheduleTask(period: Long, task: Runnable) {
        val cancel: ICancelable = XTask.scheduleAtFixedRate({
            XTask.backgroundSubmit(task)
        }, 0, period, TimeUnit.SECONDS)
        mCancelableList.add(cancel)
    }
}