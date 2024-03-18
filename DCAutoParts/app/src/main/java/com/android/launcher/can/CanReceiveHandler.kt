package com.android.launcher.can

object CanReceiveHandler : CanReceiveImpl {

    override fun canReceive(canId: Int, canMsg: List<String?>?) {
        canMsg?.let { msg ->
            when (canId) {
                CanCommand.Receive.CAN001 -> {
                    // 车钥匙启动车关闭车
                    carDoorKeyHandle(msg[2])
                }

                CanCommand.Receive.CAN2EE -> {
                    // 雷达
                    radarHandle(msg[5])
                }

                CanCommand.Receive.CAN2F3 -> {}
            }
        }
    }

    private fun radarHandle(s: String?) {

    }

    private fun carDoorKeyHandle(dat: String?) {

    }
}