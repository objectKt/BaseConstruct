package com.android.launcher.can

interface CanCommand {

    object Send {

    }

    object Receive {
        const val CAN001: Int = 0x0001
        const val CAN2EE: Int = 0x02ee
        const val CAN2F3: Int = 0x02f3
    }
}

interface CanReceiveImpl {
    fun handlerCan(receiveCommand: Int, canMsg: List<String?>?)
}