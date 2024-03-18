package dc.library.auto.carmanager.impl

import dc.library.auto.event.MessageEvent

/**
 * 公共功能
 * @author hf
 */
interface BaseModuleImpl {

    /**
     * 周期任务执行间隔时间
     */
    val schedulePeriod: Long

    /**
     * 发送事件
     */
    fun postEvent(event: MessageEvent)
}


enum class UnitType {
    //公里
    KM,

    //英里
    MI;

//    companion object {
//        val defaultType: Int
//            get() = if (LanguageUtils.isCN()) {
//                KM.ordinal
//            } else {
//                MI.ordinal
//            }
//    }
}
