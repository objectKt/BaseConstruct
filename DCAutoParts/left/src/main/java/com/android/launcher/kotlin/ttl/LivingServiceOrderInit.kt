package com.android.launcher.kotlin.ttl

import dc.library.auto.task.XTask
import dc.library.auto.task.api.TaskChainEngine
import dc.library.auto.task.api.step.ConcurrentGroupTaskStep
import dc.library.auto.task.core.ITaskChainEngine
import dc.library.auto.task.core.param.ITaskResult
import dc.library.auto.task.core.step.impl.TaskChainCallbackAdapter
import dc.library.auto.task.core.step.impl.TaskCommand

object LivingServiceOrderInit {

    private var mInitEngine: TaskChainEngine? = null

    fun runInit() {
        mInitEngine = XTask.getTaskChain()
        mInitEngine?.let { engine ->

        }
    }
}