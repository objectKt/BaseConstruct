package com.android.launcher.can

open class CanSendHandler : CanSendImpl {

    // MCU主体协议格式：引导码 0XAA +长度 + 帧ID + Data + checksum
    val DATA_HEAD = "AA0000"

    override fun sendCan(canId: Int) {
        when (canId) {
            CanCommand.Send.CAN3DC -> {
                // 发动机机油液位
                engineOilLevel()
            }

            CanCommand.Send.CAN3F6 -> {

            }

            CanCommand.Send.CAN37C -> {

            }

            CanCommand.Send.CAN045 -> {

            }

            CanCommand.Send.CAN0FD -> {

            }
        }
    }

    private fun engineOilLevel() {
        thread {

        }
    }


    private fun thread(start: Boolean = true, isDaemon: Boolean = false, contextClassLoader: ClassLoader? = null, name: String? = null, priority: Int = -1, block: () -> Unit): Thread {
        val thread = object : Thread() {
            override fun run() {
                block()
            }
        }
        if (isDaemon) thread.isDaemon = true
        if (priority > 0) thread.priority = priority
        if (name != null) thread.name = name
        if (contextClassLoader != null) thread.contextClassLoader = contextClassLoader
        if (start) thread.start()
        return thread
    }
}