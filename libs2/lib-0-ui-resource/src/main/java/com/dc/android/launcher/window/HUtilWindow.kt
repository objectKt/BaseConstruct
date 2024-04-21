package com.dc.android.launcher.window

import android.content.Context
import android.content.Intent
import android.provider.Settings

object HUtilWindow {

    fun accessSettings(context: Context, callback: ICallbackEnter?) {
        val floatHelper = AccessFloatWindowHelper.getInit(context)
        floatHelper.setFloatWindowLayoutDelegate(object : AccessFloatWindowHelper.FloatWindowLayoutDelegate {
            override fun onHome() {
                callback?.onHome()
            }
        })
        if (HAccessibilityService.mAccessibilityService == null) {
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        } else {
            callback?.enterOtherApp()
        }
    }

    fun showFloatWindow(context: Context) {
        val floatHelper = AccessFloatWindowHelper.getInit(context)
        floatHelper.let {
            if (!it.isFloatWindowShowing()) {
                it.showFloatWindow()
            }
        }
    }

    fun hideFloatWindow(context: Context) {
        val floatHelper = AccessFloatWindowHelper.getInit(context)
        floatHelper.closeFloatWindow()
    }
}

interface ICallbackEnter {
    fun enterOtherApp()
    fun onHome()
}
