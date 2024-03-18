package dc.library.auto.carmanager

import dc.library.auto.carmanager.impl.ModuleMileageImpl
import dc.library.auto.global.ScreenCarType
import dc.library.auto.global.ScreenWhichSide
import dc.library.auto.singleton.BaseSafeSingleton

/**
 * 汽车各个功能模块管理类
 */
class CarModulesManager private constructor(private val screenCar: ScreenCarType, private val screenSide: ScreenWhichSide) {

    companion object : BaseSafeSingleton<CarModulesManager, ScreenCarType, ScreenWhichSide>(::CarModulesManager)

    // 汽车里程功能模块
    private var mileageImpl: ModuleMileageImpl? = null

    fun func() {

    }
}