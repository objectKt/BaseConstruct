package com.dc.android.launcher.window

import android.accessibilityservice.AccessibilityService
import android.annotation.SuppressLint
import android.content.Context
import android.graphics.PixelFormat
import android.util.DisplayMetrics
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.WindowManager
import android.widget.Toast
import androidx.appcompat.widget.AppCompatImageView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.isVisible
import kotlin.math.abs

/**
 * 悬浮窗(使用无障碍服务)
 */
class HFloatAccessibilityWindowHelper private constructor(private val context: Context) {

    companion object : ActivitySingleton<HFloatAccessibilityWindowHelper, Context>(::HFloatAccessibilityWindowHelper)

    private val TAG = "HFloatWindowHelper"

    val WINDOW_MODE_FULL = 1 // 全屏播放

    val WINDOW_MODE_FLOAT = 2 // 悬浮窗播放

    private  val sEnableFloatWindow = true

    private var mViewRoot: ConstraintLayout? = null
    private var idMenuRoot: AppCompatImageView? = null
    private var idMenuHome: AppCompatImageView? = null
    private var idMenuBack: AppCompatImageView? = null
    private var idMenuSettings: AppCompatImageView? = null

    /**
     * 悬浮窗窗口管理器
     */
    private var mWindowManager: WindowManager? = null
    private var isFloatWindowShowing: Boolean = false

    /**
     * 悬浮窗布局参数
     */
    private var mWindowParams: WindowManager.LayoutParams? = null
    private var mFloatWindowLayoutDelegate: FloatWindowLayoutDelegate? = null

    class FloatWindowRect(var x: Int, var y: Int, var width: Int, var height: Int)

    fun setFloatWindowLayoutDelegate(floatWindowLayoutDelegate: FloatWindowLayoutDelegate?) {
        mFloatWindowLayoutDelegate = floatWindowLayoutDelegate
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView() {
        Log.w("HAccessibilityService", "initView")
        mViewRoot = LayoutInflater.from(context).inflate(R.layout.layout_float_menu, null) as ConstraintLayout
        idMenuRoot = mViewRoot?.findViewById(R.id.idMenuRoot)
        idMenuHome = mViewRoot?.findViewById(R.id.idMenuHome)
        idMenuBack = mViewRoot?.findViewById(R.id.idMenuBack)
        idMenuSettings = mViewRoot?.findViewById(R.id.idMenuSettings)
        mViewRoot?.setOnTouchListener(FloatingOnTouchListener())
        idMenuRoot?.setOnTouchListener { _, _ -> false } // 允许触摸事件继续传递
        idMenuHome?.setOnClickListener {
            hideSubMenus()
            if (mFloatWindowLayoutDelegate != null) {
                mFloatWindowLayoutDelegate?.onHome()
            }
        }
        idMenuBack?.setOnClickListener {
            hideSubMenus()
            val accessService = HAccessibilityService.mAccessibilityService
            accessService?.performGlobalAction(AccessibilityService.GLOBAL_ACTION_BACK) ?: Toast.makeText(context, "未开启无障碍服务，无法使用系统返回键接口", Toast.LENGTH_LONG).show()
        }
        idMenuSettings?.setOnClickListener {
            hideSubMenus()
        }
        idMenuRoot?.visibility = View.VISIBLE
        initFloatWindow()
    }

    fun isFloatWindowShowing() = isFloatWindowShowing

    fun showFloatWindow(): Boolean {
        initView()
        val mAccessibilityService = HAccessibilityService.mAccessibilityService
        if (mAccessibilityService == null) {
            Toast.makeText(context, "无障碍未开启", Toast.LENGTH_SHORT).show()
            isFloatWindowShowing = false
            return false
        }
        try {
            mViewRoot?.visibility = View.VISIBLE
            mWindowParams?.width = 400
            mWindowParams?.height = 500
            if (mViewRoot?.isAttachedToWindow == false) {
                if (mWindowManager == null) {
                    Log.e("HAccessibilityService", "mWindowManager = null")
                } else {
                    mWindowManager?.addView(mViewRoot, mWindowParams)
                }
            }
        } catch (e: Exception) {
            Log.e("HAccessibilityService", "e ${e.message}")
            e.printStackTrace()
            isFloatWindowShowing = false
            return false
        }
        isFloatWindowShowing = true
        return true
    }

    fun closeFloatWindow(): Boolean {
        if (!sEnableFloatWindow) {
            return false
        }
        if (mViewRoot?.isAttachedToWindow == true) {
            mWindowManager?.removeView(mViewRoot)
        }
        isFloatWindowShowing = false
        return true
    }

    fun updateFloatWindowSize(rect: FloatWindowRect) {
        if (mViewRoot != null) {
            mWindowParams?.x = rect.x
            mWindowParams?.y = rect.y
            mWindowParams?.width = rect.width
            mWindowParams?.height = rect.height
            mWindowManager?.updateViewLayout(mViewRoot, mWindowParams)
        }
    }

    private fun getScreenWidth(): Int {
        val metric = DisplayMetrics()
        val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }

    private fun initFloatWindow() {
        Log.w("HAccessibilityService", "initFloatWindow")
        val mAccessibilityService = HAccessibilityService.mAccessibilityService
        if (mAccessibilityService == null) {
            Toast.makeText(context, "无障碍未开启", Toast.LENGTH_SHORT).show()
            return
        }
        val screenWidth: Int = getScreenWidth()
        val rect = FloatWindowRect(screenWidth - 400, 0, 400, 600)
        mWindowManager = mAccessibilityService.getSystemService(Context.WINDOW_SERVICE) as WindowManager//mContext?.applicationContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowParams = WindowManager.LayoutParams()
        mWindowParams?.let {
            it.type = WindowManager.LayoutParams.TYPE_ACCESSIBILITY_OVERLAY
            it.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            it.gravity = Gravity.CENTER_VERTICAL
            it.format = PixelFormat.TRANSLUCENT
            it.x = rect.x
            it.y = rect.y
            it.width = rect.width
            it.height = rect.height
        }
    }

    private fun hideSubMenus() {
        idMenuHome?.isVisible = false
        idMenuBack?.isVisible = false
        idMenuSettings?.isVisible = false
    }

    private inner class FloatingOnTouchListener : OnTouchListener {
        private var startX = 0
        private var startY = 0
        private var x = 0
        private var y = 0

        @SuppressLint("ClickableViewAccessibility")
        override fun onTouch(view: View, event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    x = event.rawX.toInt()
                    y = event.rawY.toInt()
                    startX = x
                    startY = y
                }

                MotionEvent.ACTION_MOVE -> {
                    val nowX = event.rawX.toInt()
                    val nowY = event.rawY.toInt()
                    val movedX = nowX - x
                    val movedY = nowY - y
                    x = nowX
                    y = nowY
                    mWindowParams?.x = mWindowParams?.x?.plus(movedX)
                    mWindowParams?.y = mWindowParams?.y?.plus(movedY)
                    mWindowManager?.updateViewLayout(view, mWindowParams)
                }

                MotionEvent.ACTION_UP -> if (abs(x - startX) < 5 && abs(y - startY) < 5) {
                    //手指没有滑动视为点击
                    idMenuHome?.isVisible = !idMenuHome?.isVisible!!
                    idMenuBack?.isVisible = !idMenuBack?.isVisible!!
                    idMenuSettings?.isVisible = !idMenuSettings?.isVisible!!
                }

                else -> {
                }
            }
            return true
        }
    }

    interface FloatWindowLayoutDelegate {
        fun onHome()
    }
}