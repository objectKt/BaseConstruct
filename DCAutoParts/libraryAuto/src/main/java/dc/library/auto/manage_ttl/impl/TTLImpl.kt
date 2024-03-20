package dc.library.auto.manage_ttl.impl

import dc.library.auto.global.ScreenCarType
import dc.library.auto.global.ScreenWhichSide
import dc.library.auto.serial.listener.OnSerialPortDataListener
import dc.library.auto.serial.listener.OnSerialPortOpenListener

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

//    /**
//     * 设置串口打开监听回调
//     */
//    fun openSerialPort(listener: OnSerialPortOpenListener)
//
//    /**
//     * 设置串口数据接发线程监听回调
//     */
//    fun observeSerialPortData(listener: OnSerialPortDataListener)
}