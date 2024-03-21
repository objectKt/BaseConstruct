package dc.library.auto.manage_task

import android.util.Log
import dc.library.auto.global.ConstVal
import dc.library.auto.global.ScreenCarType
import dc.library.auto.global.ScreenWhichSide
import dc.library.auto.manage_ttl.impl.TTLImpl
import dc.library.auto.serial.SerialPortFinder
import dc.library.auto.serial.SerialPortManager
import dc.library.auto.serial.listener.OnSerialPortDataListener
import dc.library.auto.serial.listener.OnSerialPortOpenListener
import dc.library.auto.task.logger.TaskLogger
import dc.library.decode.SerialPortTTYS1Decoder
import dc.library.utils.ByteArrayUtil
import org.apache.commons.lang3.StringUtils
import java.io.File

/**
 * 串口模块统一管理类
 */
object TTLSerialPortsManager {

    // 储存所有串口
    private val mSerialPortManagerMapper: MutableMap<String, SerialPortManager> = mutableMapOf()

    fun initSerialPorts() {
        val portDevices: TTLImpl = object : TTLImpl {
            override val screenCar: ScreenCarType get() = ScreenCarType.RS223
            override val screenSide: ScreenWhichSide get() = ScreenWhichSide.LEFT
        }
        val ports = portDevices.portToBaudRate
        startSearchSerialDevices(ports)
    }

    private fun startSearchSerialDevices(ports: Map<String, Int>) {
        val devices = SerialPortFinder().devices
        if (devices.isEmpty()) {
            Log.e(ConstVal.Log.TAG, "安卓里没有设置串口设备")
        } else {
            for (device in devices) {
                ports.forEach { pb ->
                    val findPortName = pb.key
                    if (device.name == findPortName) {
                        Log.e(ConstVal.Log.TAG, "找到了匹配的串口设备：$findPortName")
                        mSerialPortManagerMapper[findPortName] = SerialPortManager()
                        mSerialPortManagerMapper[findPortName]?.let { manager ->
                            manager.setSerialPortOpenListener(portOpenCallback(findPortName))
                            manager.setSerialPortDataListener(portDataCallback(findPortName))
                            manager.openSerialPort(device.file, pb.value)
                        }
                        return@forEach
                    }
                }
            }
        }
    }

    private fun portDataCallback(portName: String?): OnSerialPortDataListener {
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
                    when (portName) {
                        "ttyS1" -> {
                            // 原来代码：SerialHelperTTLd
                            val hexDecoded = SerialPortTTYS1Decoder.decodeBytes(bytes)
                            // 11:59:12.102  ttys1 解码完整结果：      AABB 221710993551878 CCDD0
                            // 11:59:12.102  ttyS1 收到数据解码出内容        221710993551878
                            val resultDelHead = StringUtils.replace(hexDecoded, "AABB", "")
                            val resultData = StringUtils.replace(resultDelHead, "CCDD0", "")
                            Log.i("dc-auto-parts", "$portName 收到数据解码出内容 $resultData")
                        }

                        "ttyS3" -> {
                            // 原来代码：SerialHelperTTLd3
                        }

                        else -> {}
                    }
                }
            }

        }
    }

    private fun portOpenCallback(portName: String?): OnSerialPortOpenListener {
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
    fun closeSerialPortByName(portName: String) {
        if (mSerialPortManagerMapper.containsKey(portName)) {
            mSerialPortManagerMapper[portName]?.closeSerialPort()
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