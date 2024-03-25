package dc.library.auto.database

import dc.library.utils.global.ScreenCarType
import dc.library.utils.global.ScreenWhichSide
import dc.library.utils.singleton.BaseSafeSingleton

/**
 * 数据库统一管理类，所属车型+屏幕
 * @param screenCar     车屏幕类型（s222,s223, etc.）
 * @param screenSide    屏幕哪一边（Left,Right）
 * @param str           备用参数 String
 * @param value         备用参数 Int
 */
class ObjectBoxManager private constructor(
    private val screenCar: ScreenCarType,
    private val screenSide: ScreenWhichSide,
    private val str: String,
    private val value: Int
) {

    companion object : BaseSafeSingleton<ObjectBoxManager, ScreenCarType, ScreenWhichSide, String, Int>(::ObjectBoxManager)

    fun func() {

    }
}