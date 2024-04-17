package com.dc.android.launcher.window

import android.content.Context
import android.content.Intent
import android.provider.Settings

object HUtilWindow {

    fun accessShow(context: Context) {
        if (HAccessibilityService.mAccessibilityService == null) {
            Intent(Settings.ACTION_ACCESSIBILITY_SETTINGS).apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
                context.startActivity(this)
            }
        } else {
            HFloatAccessibilityWindowHelper.getInstance(context)?.showFloatWindow()
        }
    }
}