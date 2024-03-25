package dc.library.auto.manager

import android.util.Log
import dc.library.auto.manager.impl.TTLImpl
import dc.library.auto.serial.SerialPortFinder
import dc.library.auto.serial.SerialPortManager
import dc.library.auto.serial.listener.OnSerialPortDataListener
import dc.library.auto.serial.listener.OnSerialPortOpenListener
import dc.library.auto.task.logger.TaskLogger
import dc.library.utils.ByteArrayUtil
import dc.library.utils.ValUtil
import dc.library.utils.decoder.SerialPortTTYS1Decoder
import dc.library.utils.global.ConstVal
import dc.library.utils.global.ScreenCarType
import dc.library.utils.global.ScreenWhichSide
import dc.library.utils.logcat.LogCat
import java.io.File
import java.util.concurrent.LinkedTransferQueue

/**
 * 串口模块统一管理类
 */
object ManagerTTLSerialPorts {

    // 储存所有串口
    private val mSerialPortManagerMapper: MutableMap<String, SerialPortManager> = mutableMapOf()
    private var mSendQueueTtyS1: LinkedTransferQueue<ByteArray> = LinkedTransferQueue<ByteArray>()
    private var mSendQueueTtyS3: LinkedTransferQueue<ByteArray> = LinkedTransferQueue<ByteArray>()

    fun initSerialPorts() {
        val portDevices: TTLImpl = object : TTLImpl {
            override val screenCar: ScreenCarType get() = ScreenCarType.RS223
            override val screenSide: ScreenWhichSide get() = ScreenWhichSide.LEFT
        }
        val ports = portDevices.portToBaudRate
        startSearchSerialDevices(ports)
        startSendThread(ValUtil.Ttl.ttys1)
        startSendThread(ValUtil.Ttl.ttys3)
    }

    fun putBytesToTtys1(bytes: ByteArray?) {
        if (bytes != null && bytes.isNotEmpty()) {
            mSendQueueTtyS1.add(bytes)
        }
    }

    fun putBytesToTtys3(bytes: ByteArray?) {
        if (bytes != null && bytes.isNotEmpty()) {
            mSendQueueTtyS3.add(bytes)
        }
    }


    private fun startSendThread(portName: String) {
        mSendQueueTtyS1.clear()
        mSendQueueTtyS3.clear()
        Thread {
            while (mSerialPortManagerMapper.isNotEmpty() && mSerialPortManagerMapper.containsKey(portName)) {
                try {
                    val bytes: ByteArray? = when (portName) {
                        ValUtil.Ttl.ttys1 -> mSendQueueTtyS1.take()
                        ValUtil.Ttl.ttys3 -> mSendQueueTtyS3.take()
                        else -> null
                    }
                    if (bytes != null) {
                        mSerialPortManagerMapper[portName]?.send(bytes)
                    } else {
                        LogCat.e("startSendThread bytes == null")
                    }
                } catch (_: Exception) {
                }
            }
        }
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
                            val dataReceive = SerialPortTTYS1Decoder.decodeBytes(bytes)
                            // 11:59:12.102  ttys1 解码完整结果：      AABB 221710993551878 CCDD0
                            // 11:59:12.102  ttyS1 收到数据解码出内容        221710993551878
                            Log.i("dc-auto-parts", "$portName 收到数据解码出内容 $dataReceive")
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
     * 关闭所有串口且清除内存数据
     */
    fun closeAllPorts() {
        mSerialPortManagerMapper.forEach {
            it.value.closeSerialPort()
            LogCat.w("已关闭串口 ${it.key}")
        }
        mSerialPortManagerMapper.clear()
        LogCat.w("已清理 mSerialPortManagerMapper")
    }
}