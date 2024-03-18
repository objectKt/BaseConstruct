package dc.library.auto.carmanager

import dc.library.auto.carmanager.entity.CarTravelTable
import dc.library.auto.carmanager.impl.ModuleMileageImpl
import dc.library.auto.carmanager.impl.ModuleObserveSpeedImpl
import dc.library.auto.carmanager.impl.UnitType
import dc.library.auto.event.MessageEvent
import dc.library.auto.global.ScreenCarType
import dc.library.auto.global.ScreenWhichSide
import dc.library.auto.singleton.BaseSafeSingleton
import dc.library.auto.task.XTask
import dc.library.auto.task.thread.pool.cancel.ICancelable
import java.util.concurrent.TimeUnit

/**
 * 汽车各个功能模块管理类
 * @param screenCar     车屏幕类型（s222,s223, etc.）
 * @param screenSide    屏幕哪一边（Left,Right）
 * @author hf
 */
class CarModulesManager private constructor(private val screenCar: ScreenCarType, private val screenSide: ScreenWhichSide) {

    companion object : BaseSafeSingleton<CarModulesManager, ScreenCarType, ScreenWhichSide>(::CarModulesManager)

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
        // 汽车里程功能模块
        mileageImpl = object : ModuleMileageImpl {
            override val mileageData: CarTravelTable
                get() = CarTravelTable.updateData()

            override val unitType: Int
                get() = UnitType.KM.ordinal

            override val schedulePeriod: Long
                get() = 2L

            override fun postEvent(event: MessageEvent) {

            }
        }
        // 定时观察发动机转速和车速更新
        observeSpeedImpl = object : ModuleObserveSpeedImpl {
            override val schedulePeriod: Long
                get() = 2L

            override fun postEvent(event: MessageEvent) {

            }
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