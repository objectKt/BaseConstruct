package com.android.launcher.activity.util

import com.android.launcher.can.util.AutoUtilCan
import com.drake.channel.sendEvent

interface AutoUtilActivityEvent {

    data class EventSteerWheel(
        val type: AutoUtilCan.Type,
        val otherParam: String = ""
    )

    @Suppress("ConstPropertyName")
    object Send {

        const val tag_event_steer_wheel = "tag_event_steer_wheel"

        fun event(type: EventSteerWheel, tag: String = tag_event_steer_wheel) {
            sendEvent(type, tag)
        }

    }
}