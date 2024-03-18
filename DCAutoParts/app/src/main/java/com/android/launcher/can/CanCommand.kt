package com.android.launcher.can

interface CanCommand {

    object Send {
        const val CAN3DC: Int = 0x03DC
        const val CAN3F6: Int = 0x03F6
        const val CAN37C: Int = 0x037C
        const val CAN045: Int = 0x0045
        const val CAN0FD: Int = 0x00FD
    }

    object Receive {
        const val CAN001: Int = 0x0001
        const val CAN2EE: Int = 0x02ee
        const val CAN2F3: Int = 0x02f3
    }
}

interface CanReceiveImpl {
    fun canReceive(canId: Int, canMsg: List<String?>?)
}

interface CanSendImpl {
    fun sendCan(canId: Int)
}