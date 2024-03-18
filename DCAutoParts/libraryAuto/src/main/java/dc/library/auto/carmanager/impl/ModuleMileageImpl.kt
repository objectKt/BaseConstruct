package dc.library.auto.carmanager.impl

import dc.library.auto.carmanager.entity.CarTravelTable
import dc.library.auto.event.MessageEvent

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
