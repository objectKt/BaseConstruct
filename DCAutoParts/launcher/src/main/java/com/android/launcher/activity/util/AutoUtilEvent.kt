package com.android.launcher.activity.util

import com.android.launcher.can.util.AutoUtilCan
import com.drake.channel.sendEvent

interface AutoUtilEvent {

    data class EventSteerWheel(val type: AutoUtilCan.Type)
    data class EventDialogShow(val dialogType: Int, val dialogContent: String = "")

    @Suppress("ConstPropertyName")
    object Dialog {
        const val type_warning_info: Int = 1
    }

    @Suppress("ConstPropertyName")
    object Send {

        const val tag_event_steer_wheel = "tag_event_steer_wheel"

        fun event(type: EventSteerWheel, tag: String = tag_event_steer_wheel) {
            sendEvent(type, tag)
        }
    }
}