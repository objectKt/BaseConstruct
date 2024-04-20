package com.dc.android.launcher.protocol.smartpie.can

import java.util.concurrent.ArrayBlockingQueue

/**
 * 空调状态管理
 */
object AirCondition {

    private const val MAX_QUEUE_SIZE: Int = 100

    private val queueBuffer = ArrayBlockingQueue<ByteArray>(MAX_QUEUE_SIZE)

    fun addBuffer(bytes: ByteArray) {
        if (queueBuffer.size >= MAX_QUEUE_SIZE - 5) queueBuffer.poll()
        try {
            queueBuffer.add(bytes.clone())
        } catch (e: InterruptedException) {
            queueBuffer.clear()
        }
    }

    fun takeBuffer(): ByteArray? = queueBuffer.poll()

}