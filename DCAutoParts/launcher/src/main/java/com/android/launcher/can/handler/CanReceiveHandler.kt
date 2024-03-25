package com.android.launcher.can.handler

import com.android.launcher.can.util.AutoUtilCan
import com.android.launcher.can.util.CanReceiveImpl

object CanReceiveHandler : CanReceiveImpl {

    override fun canReceive(canId: Int, canMsg: List<String?>?) {
        canMsg?.let { msg ->
            val receive = AutoUtilCan.CommandReceive
            when (canId) {
                receive.CAN001 -> {
                    // 车钥匙启动车关闭车
                    carDoorKeyHandle(msg[2])
                }

                receive.CAN2EE -> {
                    // 雷达
                    radarHandle(msg[5])
                }

                receive.CAN2F3 -> {}
            }
        }
    }

    private fun radarHandle(s: String?) {

    }

    private fun carDoorKeyHandle(dat: String?) {

    }
}