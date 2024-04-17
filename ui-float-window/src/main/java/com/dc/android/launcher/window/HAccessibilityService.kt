package com.dc.android.launcher.window

import android.accessibilityservice.AccessibilityService
import android.view.accessibility.AccessibilityEvent

class HAccessibilityService : AccessibilityService() {

    companion object {
        var mAccessibilityService: HAccessibilityService? = null
    }

    override fun onDestroy() {
        super.onDestroy()
        mAccessibilityService = null
    }

    override fun onCreate() {
        super.onCreate()
        mAccessibilityService = this
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {

    }

    override fun onInterrupt() {

    }
}