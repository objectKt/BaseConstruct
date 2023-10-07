package com.tc.refuel.screen.base

import android.app.Application
import com.drake.brv.utils.BRV
import com.drake.engine.base.Engine
import com.tc.refuel.screen.BR

class App : Application() {

    override fun onCreate() {
        super.onCreate()
        Engine.initialize(this)
        BRV.modelId = BR.m
    }
}