package dc.library.auto.bus_usb.util

import dc.library.auto.bus_usb.driver.FtdiSerialDriver
import dc.library.auto.bus_usb.driver.ProbeTable
import dc.library.auto.bus_usb.driver.UsbSerialProber

object CustomProber {

    const val NO_USB_DEVICES: String = "<no USB devices found>"

    fun getCustomProber(): UsbSerialProber {
        val customTable = ProbeTable()
        customTable.addProduct(0x1234, 0x0001, FtdiSerialDriver::class.java) // e.g. device with custom VID+PID
        customTable.addProduct(0x1234, 0x0002, FtdiSerialDriver::class.java) // e.g. device with custom VID+PID
        return UsbSerialProber(customTable)
    }

}