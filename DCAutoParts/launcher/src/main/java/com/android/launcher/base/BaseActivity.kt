package com.android.launcher.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.android.launcher.util.Func
import dc.library.ui.base.Engine
import dc.library.utils.logcat.LogCat

abstract class BaseActivity : AppCompatActivity() {

    protected abstract fun stateChangeLogic(event: Lifecycle.Event)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                LogCat.d("${Engine.currentActivity?.javaClass?.simpleName} 生命周期 == 进入了 ${event.name}")
                stateChangeLogic(event)
            }
        })
    }

}