package com.dc.android.launcher.basic.utilx

import android.util.Log
import java.util.concurrent.ArrayBlockingQueue

/**
 * 线程安全的通信数据队列
 * @author hf
 */
object XxQueue {

    private const val MAX_QUEUE_SIZE: Int = 100

    private val queueTTLReceive = ArrayBlockingQueue<ByteArray>(MAX_QUEUE_SIZE)

    private val queueTTLString = ArrayBlockingQueue<String>(MAX_QUEUE_SIZE)

    fun putBytes(bytes: ByteArray) {
        if (queueTTLReceive.size >= MAX_QUEUE_SIZE - 5) {
            queueTTLReceive.poll()
        }
        try {
            queueTTLReceive.add(bytes.clone()) // clone() 保证了线程安全
        } catch (e: InterruptedException) {
            Log.e(XxConst.TAG, "处理中断异常 ${e.message}")
            queueTTLReceive.clear()
        }
    }

    fun putString(str: String) {
        if (queueTTLString.size >= MAX_QUEUE_SIZE - 5) {
            queueTTLString.poll()
        }
        try {
            queueTTLString.add(str)
        } catch (e: InterruptedException) {
            Log.e(XxConst.TAG, "处理中断异常 ${e.message}")
            queueTTLString.clear()
        }
    }

    fun takeBytes(): ByteArray? = queueTTLReceive.poll()

    fun takeString(): String? = queueTTLString.poll()
}