package dc.library.auto.manage_ttl.impl

import dc.library.auto.global.ScreenCarType
import dc.library.auto.global.ScreenWhichSide

interface TTLImpl {
    /**
     * 车屏幕类型（s222,s223, etc.）
     */
    val screenCar: ScreenCarType

    /**
     *  屏幕哪一边（Left,Right）
     */
    val screenSide: ScreenWhichSide

    /**
     * 串口名称（ttyS1,ttyS3）to 对应的波特率
     */
    val portToBaudRate: Map<String, Int>
        get() = mapOf("ttyS1" to 115200, "ttyS3" to 9600)
}