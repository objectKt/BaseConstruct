package com.android.launcher.kotlin.ttl

import dc.library.auto.task.XTask
import dc.library.auto.task.api.TaskChainEngine

object LivingServiceOrderInit {

    private var mInitEngine: TaskChainEngine? = null

    fun runInit() {
        mInitEngine = XTask.getTaskChain()
        mInitEngine?.let { engine ->

        }
    }
}