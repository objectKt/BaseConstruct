package dc.library.auto.carmanager.impl

import dc.library.auto.event.MessageEvent

/**
 * 汽车里程功能模块
 * @author hf
 */
interface ModuleMileageImpl : BaseModuleImpl {

    /**
     * 获取行程数据库
     */
    fun getDataBase()

    /**
     * 获取里程单位类型
     */
    fun getUnitType()
}
