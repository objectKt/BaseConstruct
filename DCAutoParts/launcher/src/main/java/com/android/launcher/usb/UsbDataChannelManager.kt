package com.android.launcher.usb

import android.content.Context
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import com.hoho.android.usbserial.driver.UsbSerialDriver
import com.hoho.android.usbserial.driver.UsbSerialPort
import com.hoho.android.usbserial.driver.UsbSerialProber
import dc.library.auto.task.logger.TaskLogger
import dc.library.utils.ContextUtil
import dc.library.utils.HexUtil
import java.io.IOException
import java.util.concurrent.ExecutorService
import java.util.concurrent.LinkedTransferQueue

/**
 * USB 数据通道管理
 */
object UsbDataChannelManager {

    private var mPort: UsbSerialPort? = null
    private val CLASS_SELF = UsbDataChannelManager::class.java.getSimpleName()
    var mSendQueue = LinkedTransferQueue<ByteArray>()
    private val readTask: ExecutorService? = null
    private val writeTask: ExecutorService? = null

    fun startRun() {
        val usbManager: UsbManager = ContextUtil.getApplicationContext().getSystemService(Context.USB_SERVICE) as UsbManager
        val allDrivers = UsbSerialProber.getDefaultProber().findAllDrivers(usbManager)
        TaskLogger.i("$CLASS_SELF startRun----allDrivers = $allDrivers")
        if (allDrivers.isNullOrEmpty()) {
            TaskLogger.e("$CLASS_SELF startRun---- allDrivers == null or empty")
            return
        }
        val driver: UsbSerialDriver = allDrivers[0] // 只有一个USB
        TaskLogger.i("$CLASS_SELF UsbSerialDriver----$driver")
        val connection: UsbDeviceConnection? = usbManager.openDevice(driver.device)
        if (connection == null) {
            TaskLogger.e("$CLASS_SELF UsbDeviceConnection is null， add UsbManager.requestPermission(driver.getDevice(), ..) handling here")
            return
        }
        // Most devices have just one port (port 0)
        if (driver.ports.isNullOrEmpty() || driver.ports[0] == null) {
            TaskLogger.e("$CLASS_SELF driver.ports.isNullOrEmpty() ||  mPort is null")
            return
        }
        try {
            mPort = driver.ports[0]
            mPort?.open(connection)
            mPort?.setParameters(115200, 8, UsbSerialPort.STOPBITS_1, UsbSerialPort.PARITY_NONE)
            TaskLogger.i("$CLASS_SELF port 开始读写数据")
            mSendQueue.clear()
            readTask?.execute(readThread)

        } catch (e: Exception) {
            TaskLogger.e("$CLASS_SELF port.open(connection) Exception $e")
        }

    }

    private val readThread: Runnable = object : Runnable {
        val buffer = ByteArray(32)
        override fun run() {
            TaskLogger.i("$CLASS_SELF readThread-----port isOpen=" + mPort?.isOpen)
            while (mPort?.isOpen() == true) {
                try {
                    val len: Int? = mPort?.read(buffer, 2000)
                    if (len != null) {
                        if (len > 0) {
                            val data = ByteArray(len)
                            System.arraycopy(buffer, 0, data, 0, len)
                            val ss1: String = HexUtil.toHexString(data, data.size)
                            //BenzHandlerData.handlerCan(ss1)
                        }
                    }
                } catch (e: IOException) {
                    e.printStackTrace()
                    TaskLogger.e("$CLASS_SELF IOException ${e.message}, livingServerStop = App.livingServerStop")
                    if ("USB get_status request failed" == e.message) {
//                        if (!App.livingServerStop) {
//                            close()
//                            EventBus.getDefault().post(MessageEvent(MessageEvent.Type.USB_INTERRUPT))
//                        }
                    }
                } catch (e: java.lang.Exception) {
                    e.printStackTrace()
                }
            }
        }
    }
}