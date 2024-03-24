package dc.library.auto.bus_usb.util

import android.content.Context
import android.hardware.usb.UsbManager
import dc.library.auto.bus_usb.ListItem
import dc.library.auto.bus_usb.driver.UsbSerialDriver
import dc.library.auto.bus_usb.driver.UsbSerialProber
import dc.library.ui.base.app
import dc.library.utils.logcat.LogCat

object UsbDevicesFinder {

    private var mDeviceItem: ListItem? = null
    private val listItems: MutableList<ListItem> = mutableListOf()

    fun findDevices() {
        val usbManager: UsbManager = app.getSystemService(Context.USB_SERVICE) as UsbManager
        val usbDefaultProber: UsbSerialProber = UsbSerialProber.getDefaultProber()
        val usbCustomProber: UsbSerialProber = CustomProber.getCustomProber()
        listItems.clear()
        usbManager.deviceList.values.forEach { device ->
            var driver: UsbSerialDriver? = usbDefaultProber.probeDevice(device)
            if (driver == null) {
                driver = usbCustomProber.probeDevice(device)
            }
            if (driver != null) {
                for (port in 0 until driver.getPorts().size) {
                    listItems.add(ListItem(device, port, driver))
                }
            } else {
                listItems.add(ListItem(device, 0, null))
            }
        }
        if (listItems.isEmpty()) {
            LogCat.e(CustomProber.NO_USB_DEVICES)
            return
        }
        listItems.forEach { item ->
            val message = if (item.driver == null) {
                "<no driver>"
            } else if (item.driver?.ports?.size == 1) {
                // 车载应用目前只有一个 USB Device，应该就是这个了
                mDeviceItem = item
                item.driver!!.javaClass.simpleName.replace("SerialDriver", "")
            } else {
                item.driver!!.javaClass.simpleName.replace("SerialDriver", "") + ", Port ${item.port}"
            }
            LogCat.i("USB Devices : $message")
        }
    }

    fun deviceItem(): ListItem? {
        return mDeviceItem
    }

}