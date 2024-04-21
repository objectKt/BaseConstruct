package com.dc.android.launcher.window

import android.accessibilityservice.AccessibilityService
import android.util.Log
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityEvent.TYPE_WINDOW_STATE_CHANGED
import com.dc.android.launcher.serialize.data.ConfigDataActivity

class HAccessibilityService : AccessibilityService() {

    companion object {
        var mAccessibilityService: HAccessibilityService? = null
        const val TAG = "HAccessibilityService"
    }

    override fun onCreate() {
        super.onCreate()
        Log.i(TAG, "$TAG 创建了无障碍服务")
        mAccessibilityService = this
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.i(TAG, "$TAG 销毁了无障碍服务")
        HUtilWindow.hideFloatWindow(applicationContext)
        mAccessibilityService = null
    }

    override fun onAccessibilityEvent(event: AccessibilityEvent?) {
        event?.let {
            when (it.eventType) {
                TYPE_WINDOW_STATE_CHANGED -> {
                    if (ConfigDataActivity.isHomeActVisible) {
                        HUtilWindow.hideFloatWindow(applicationContext)
                    } else {
                        HUtilWindow.showFloatWindow(applicationContext)
                    }
                }

                else -> Log.d(TAG, "$TAG AccessibilityEvent ${event.eventType.toString(16)}")
            }
        }
    }

    override fun onInterrupt() {
        Log.d(TAG, "$TAG Feedback was interrupted")
    }
}