package dc.library.auto.manage_car.impl

import dc.library.auto.manage_car.entity.CarTravelTable

/**
 * 汽车里程功能模块
 * @author hf
 */
interface ModuleMileageImpl : BaseModuleImpl {

    /**
     * 获取行程数据库
     */
    val mileageData: CarTravelTable

    /**
     * 获取里程单位类型
     */
    val unitType: Int
}
