package com.dc.android.launcher.window

import android.content.Context
import android.content.Intent
import android.provider.Settings

object HUtilWindow {

    fun accessSettings(context: Context, targetActivityClass: Class<*>) {
        val floatHelper = HFloatAccessibilityWindowHelper.getInit(context)
        floatHelper.setFloatWindowLayoutDelegate(object : HFloatAccessibilityWindowHelper.FloatWindowLayoutDelegate {
            override fun onHome() {
                val intent = Intent(context, targetActivityClass)
                context.startActivity(intent)
            }
        })
        if (HAccessibilityService.mAccessibilityService == null) {
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        } else {
            Intent(Settings.ACTION_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        }
    }

    fun showFloatWindow(context: Context) {
        val floatHelper = HFloatAccessibilityWindowHelper.getInit(context)
        floatHelper.let {
            if (!it.isFloatWindowShowing()) {
                it.showFloatWindow()
            }
        }
    }

    fun hideFloatWindow(context: Context) {
        val floatHelper = HFloatAccessibilityWindowHelper.getInit(context)
        floatHelper.closeFloatWindow()
    }
}