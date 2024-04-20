package com.backaudio.android.driver.mcu

import com.drake.channel.sendEvent
import com.drake.tooltip.toast

object EventHelper {

    fun send(str: String) {
        toast("收到 MCU isBack: buffer[5] = 1")
        sendEvent(str)
    }

}