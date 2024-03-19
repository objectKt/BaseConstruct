package com.android.launcher.kotlin.ttl

import dc.library.auto.global.ScreenCarType
import dc.library.auto.global.ScreenWhichSide
import dc.library.auto.serial.SerialPortFinder
import dc.library.auto.serial.SerialPortManager
import dc.library.auto.serial.entity.BeanSerialDevice
import dc.library.auto.serial.listener.OnSerialPortDataListener
import dc.library.auto.serial.listener.OnSerialPortOpenListener
import dc.library.auto.singleton.BaseSafeSingleton
import dc.library.auto.task.logger.TaskLogger
import dc.library.auto.util.ByteArrayUtil
import java.io.File

/**
 * 串口模块统一管理类
 * @param screenCar     车屏幕类型（s222,s223, etc.）
 * @param screenSide    屏幕哪一边（Left,Right）
 * @param portName      串口名称（ttyS1,ttyS4）
 * @author hf
 */
class TTLSerialPortsManager private constructor(
    private val screenCar: ScreenCarType,
    private val screenSide: ScreenWhichSide,
    private val portName: String,
    private val portBaudRate: Int
) {

    companion object : BaseSafeSingleton<TTLSerialPortsManager, ScreenCarType, ScreenWhichSide, String, Int>(::TTLSerialPortsManager)

    // 储存所有串口
    private val mSerialPortManagerMapper: MutableMap<String, SerialPortManager> = mutableMapOf()

    public fun initExistDevice(): BeanSerialDevice? {
        val devices = SerialPortFinder().devices
        for (device in devices) {
            if (device.name == portName) {
                mSerialPortManagerMapper[portName] = SerialPortManager()
                mSerialPortManagerMapper[portName]?.setSerialPortOpenListener(portOpenCallback())
                mSerialPortManagerMapper[portName]?.setSerialPortDataListener(portDataCallback())
                mSerialPortManagerMapper[portName]?.openSerialPort(device.file, portBaudRate)
            }
        }
        if (mSerialPortManagerMapper.containsKey(portName)) {
            mSerialPortManagerMapper.remove(portName)
        }
        return null
    }

    private fun portDataCallback(): OnSerialPortDataListener {
        return object : OnSerialPortDataListener {
            /**
             * SendThread 回调
             */
            override fun onDataSent(bytes: ByteArray?) {
                bytes?.let { TaskLogger.i("已成功发送 $portName 串口数据：${ByteArrayUtil.toHeX(it)}") }
            }

            /**
             * ReadThread 回调
             */
            override fun onDataReceived(bytes: ByteArray?) {
                bytes?.let {
                    val hex = ByteArrayUtil.toHeX(it)
                    TaskLogger.i("portName = $portName onDataReceived $hex")
                    when (portName) {
                        "ttyS1" -> {
                            // 原来代码：SerialHelperTTLd
                        }
                        "ttyS3" -> {
                            // 原来代码：SerialHelperTTLd3
                        }
                    }
                }
            }

        }
    }

    private fun portOpenCallback(): OnSerialPortOpenListener {
        return object : OnSerialPortOpenListener {
            /**
             * 开启串口成功回调
             */
            override fun onSuccess(device: File?) {
                TaskLogger.i("portName = $portName OnSerialPortOpenListener onSuccess ${device?.name}")
            }

            /**
             * 开启串口失败回调
             */
            override fun onFail(device: File?, status: OnSerialPortOpenListener.Status?) {
                val failureReason = when (status) {
                    OnSerialPortOpenListener.Status.WITHOUT_READ_WRITE_PERMISSION -> "${device?.absolutePath} 沒有讀寫權限，串口打開失敗"
                    else -> "${device?.absolutePath} 串口打开失败 portName = $portName"
                }
                TaskLogger.e("OnSerialPortOpenListener 连接失败原因： $failureReason")
            }
        }
    }

    /**
     * 关闭串口名对应的串口设备，例如 ttyS1
     */
    fun closeSerialPortByName() {
        mSerialPortManagerMapper[portName]?.closeSerialPort()
        if (mSerialPortManagerMapper.containsKey(portName)) {
            mSerialPortManagerMapper.remove(portName)
        }
    }

    /**
     * 关闭所有串口且清除内存数据
     */
    fun closeAllPorts() {
        mSerialPortManagerMapper.forEach {
            it.value.closeSerialPort()
        }
        mSerialPortManagerMapper.clear()
    }
}