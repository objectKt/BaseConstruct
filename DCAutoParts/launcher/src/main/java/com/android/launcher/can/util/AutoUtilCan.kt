package com.android.launcher.can.util

/**
 * Can 通信 相关 Util
 */
interface AutoCanUtil {

    // 方向盘按键类型
    enum class Type { UP, DOWN, LEFT, RIGHT, OK, BACK }

    object CommandSend {
        const val CAN3DC: Int = 0x03DC
        const val CAN3F6: Int = 0x03F6
        const val CAN37C: Int = 0x037C
        const val CAN045: Int = 0x0045
        const val CAN0FD: Int = 0x00FD
    }

    object CommandReceive {
        const val CAN001: Int = 0x0001
        const val CAN2EE: Int = 0x02ee
        const val CAN2F3: Int = 0x02f3

        // 方向盘按键类型对应的 Key 值
        val SteerWheelKey: Map<Type, Int> = mapOf(
            Type.UP to 5, Type.DOWN to 6, Type.LEFT to 7,
            Type.RIGHT to 8, Type.OK to 9, Type.BACK to 10
        )
    }
}

interface CanReceiveImpl {
    fun canReceive(canId: Int, canMsg: List<String?>?)
}

interface CanSendImpl {
    fun sendCan(canId: Int)
}