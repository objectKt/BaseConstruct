package dc.library.auto.bus_usb

import android.annotation.SuppressLint
import android.app.Activity
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.hardware.usb.UsbDevice
import android.hardware.usb.UsbDeviceConnection
import android.hardware.usb.UsbManager
import android.os.Build
import dc.library.auto.bus_usb.driver.UsbSerialDriver
import dc.library.auto.bus_usb.driver.UsbSerialPort
import dc.library.auto.bus_usb.driver.UsbSerialProber
import dc.library.auto.bus_usb.util.CustomProber
import dc.library.auto.bus_usb.util.HexDump
import dc.library.auto.bus_usb.util.SerialInputOutputManager
import dc.library.auto.bus_usb.util.UsbDevicesFinder
import dc.library.ui.base.app
import dc.library.utils.ValUtil
import dc.library.utils.logcat.LogCat
import dc.library.utils.singleton.ActivitySingleton
import java.io.IOException

class UsbDeviceConnectManager private constructor(
    // 注意，这里单例模式的传参 activity 不可以变，用于单 Activity 多 Fragment 框架
    private val activity: Activity
) : SerialInputOutputManager.Listener {

    companion object : ActivitySingleton<UsbDeviceConnectManager, Activity>(::UsbDeviceConnectManager) {
        private const val WRITE_WAIT_MILLIS = 2000
    }

    enum class UsbPermission { Unknown, Requested, Granted, Denied }

    private var mUsbSerialPort: UsbSerialPort? = null
    var mUsbPermission: UsbPermission = UsbPermission.Unknown
    private var mUsbIoManager: SerialInputOutputManager? = null
    private var mConnected = false

    fun sendUsb(msg: String) {
        if (!mConnected) {
            LogCat.e("USB not connected. --- send failed")
            return
        }
        try {
            val data = (msg + '\n').toByteArray()
            LogCat.i("USB Send ${data.size} 个字节：${HexDump.dumpHexString(data)}")
            mUsbSerialPort?.write(data, WRITE_WAIT_MILLIS)
        } catch (e: Exception) {
            onRunError(e)
        }
    }

    /**
     * Serial Receive
     */
    override fun onNewData(data: ByteArray?) {
        if (data != null && data.isNotEmpty()) {
            LogCat.i("USB receive : ${data.size} 个字节：")
            LogCat.i("--- ${HexDump.dumpHexString(data)}")
        } else {
            LogCat.e("USB receive : 0 个字节")
        }
    }

    override fun onRunError(e: Exception?) {
        LogCat.e("USB connection Lost: ${e?.message}")
        disconnectUsb()
    }

    // ----------- usb connect send receive ↓ -----------

    @SuppressLint("ObsoleteSdkInt")
    fun connectUsb(baudRate: Int = 115200, withIoManager: Boolean = true) {
        val deviceItem = UsbDevicesFinder.deviceItem()
        if (deviceItem == null) {
            LogCat.e("connectUsb() mDeviceItem null ${CustomProber.NO_USB_DEVICES}")
            return
        }
        var device: UsbDevice? = null
        val usbManager: UsbManager = activity.getSystemService(Context.USB_SERVICE) as UsbManager
        val devices = usbManager.deviceList.values
        devices.forEach { v ->
            if (v.deviceId == deviceItem.device.deviceId) {
                device = v
            }
        }
        if (device == null) {
            LogCat.e("USB connection failed: device not found")
            return
        }
        var driver = UsbSerialProber.getDefaultProber().probeDevice(device)
        if (driver == null) {
            driver = CustomProber.getCustomProber().probeDevice(device)
        }
        if (driver == null) {
            LogCat.e("USB connection failed: no driver for device")
            return
        }
        if (driver.ports.size < deviceItem.port) {
            LogCat.e("USB connection failed: not enough ports at device")
            return
        }
        mUsbSerialPort = driver.ports[deviceItem.port]
        val usbConnection: UsbDeviceConnection? = usbManager.openDevice(driver.device)
        if (usbConnection == null && mUsbPermission == UsbPermission.Unknown && !usbManager.hasPermission(driver.device)) {
            mUsbPermission = UsbPermission.Requested
            val flags = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) PendingIntent.FLAG_MUTABLE else 0
            val intent = Intent(ValUtil.Action.INTENT_ACTION_GRANT_USB)
            intent.setPackage(app.packageName)
            val usbPermissionIntent: PendingIntent = PendingIntent.getBroadcast(activity, 0, intent, flags)
            usbManager.requestPermission(driver.device, usbPermissionIntent)
            return
        }
        if (usbConnection == null) {
            val messageFailed = if (!usbManager.hasPermission(driver.device)) {
                "connection failed: permission denied"
            } else {
                "connection failed: open failed"
            }
            LogCat.e("USB $messageFailed")
            return
        }
        try {
            mUsbSerialPort?.open(usbConnection)
            try {
                mUsbSerialPort?.setParameters(baudRate, 8, 1, UsbSerialPort.PARITY_NONE)
            } catch (e: UnsupportedOperationException) {
                LogCat.e("USB unSupport setParameters")
            }
            if (withIoManager) {
                mUsbIoManager = SerialInputOutputManager(mUsbSerialPort, this)
                mUsbIoManager?.start()
            }
            LogCat.i("--- USB connected ---")
            mConnected = true
            // controlLines.start();
        } catch (e: Exception) {
            LogCat.e("USB connection failed: ${e.message}")
            disconnectUsb()
        }
    }

    private fun disconnectUsb() {
        mConnected = false
        // controlLines.stop();
        if (mUsbIoManager != null) {
            mUsbIoManager?.listener = null
            mUsbIoManager?.stop()
            mUsbIoManager = null
        }
        try {
            mUsbSerialPort?.close()
        } catch (_: IOException) {
        }
        mUsbSerialPort = null
    }

    fun resumeConnect() {
        if (!mConnected && (mUsbPermission == UsbPermission.Unknown || mUsbPermission == UsbPermission.Granted)) {
            connectUsb()
        }
    }

    fun pauseDisconnect() {
        if (mConnected) {
            LogCat.w("USB pauseDisconnect()")
            disconnectUsb()
        }
    }
}

data class ListItem(var device: UsbDevice, var port: Int, var driver: UsbSerialDriver? = null)
