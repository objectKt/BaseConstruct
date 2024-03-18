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

    // 汽车里程功能模块
    private var mileageImpl: ModuleMileageImpl? = null

    // 定时观察发动机转速和车速更新
    private var observeSpeedImpl: ModuleObserveSpeedImpl? = null

    fun manageModules() {
        mileageImpl = mileageImplInit()
        observeSpeedImpl = observeSpeedImplInit()
        startDoScheduleTasks()
    }

    private fun startDoScheduleTasks() {
        mileageImpl?.let {
            doScheduleTasks(mileageImpl?.schedulePeriod!!) {
                // Runnable
            }
        }
        observeSpeedImpl?.let {
            doScheduleTasks(observeSpeedImpl?.schedulePeriod!!) {
                // Runnable
            }
        }
    }

    private fun doScheduleTasks(period: Long, task: Runnable) {
        val cancel: ICancelable = XTask.scheduleAtFixedRate({
            XTask.backgroundSubmit(task)
        }, 0, period, TimeUnit.SECONDS)
        mCancelableList.add(cancel)
    }

    private fun observeSpeedImplInit(): ModuleObserveSpeedImpl {
        return object : ModuleObserveSpeedImpl {
            override val schedulePeriod: Long
                get() = 2L

            override fun postEvent(event: MessageEvent) {

            }
        }
    }

    private fun mileageImplInit(): ModuleMileageImpl {
        return object : ModuleMileageImpl {
            override val mileageData: CarTravelTable
                get() = CarTravelTable.updateData()

            override val unitType: Int
                get() = UnitType.KM.ordinal

            override val schedulePeriod: Long
                get() = 2L

            override fun postEvent(event: MessageEvent) {

            }
        }
    }
}