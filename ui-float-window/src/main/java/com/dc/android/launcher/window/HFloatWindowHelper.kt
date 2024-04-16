package com.dc.android.launcher.window

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.graphics.PixelFormat
import android.net.Uri
import android.provider.Settings
import android.util.DisplayMetrics
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
 * 悬浮窗菜单
 * @author hf
 */
class HFloatWindowHelper(context: Context) {

    /**
     * 支持TYPE_TOAST悬浮窗的最高API版本
     */
    private val OP_SYSTEM_ALERT_WINDOW = 24
    private var mContext: Context? = null
    private var mViewRoot: ConstraintLayout? = null

    ///private var mLayoutDisplayContainer: RelativeLayout? = null
    private var idMenuRoot: AppCompatImageView? = null
    private var idMenuHome: AppCompatImageView? = null
    private var idMenuBack: AppCompatImageView? = null
    private var idMenuSettings: AppCompatImageView? = null

    /**
     * 悬浮窗窗口管理器
     */
    private var mWindowManager: WindowManager? = null

    /**
     * 悬浮窗布局参数
     */
    private var mWindowParams: WindowManager.LayoutParams? = null
    private var mWindowMode: Int = WINDOW_MODE_FULL
    private var mFloatWindowLayoutDelegate: FloatWindowLayoutDelegate? = null

    private var mStartTimestamp = 0L

    class FloatWindowRect(var x: Int, var y: Int, var width: Int, var height: Int)

    fun setFloatWindowLayoutDelegate(floatWindowLayoutDelegate: FloatWindowLayoutDelegate?) {
        mFloatWindowLayoutDelegate = floatWindowLayoutDelegate
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun initView(context: Context) {
        mContext = context
        mViewRoot = LayoutInflater.from(context).inflate(R.layout.layout_float_menu, null) as ConstraintLayout
        ///mLayoutDisplayContainer = mViewRoot?.findViewById(R.id.rl_display_container)
        idMenuRoot = mViewRoot?.findViewById(R.id.idMenuRoot)
        idMenuHome = mViewRoot?.findViewById(R.id.idMenuHome)
        idMenuBack = mViewRoot?.findViewById(R.id.idMenuBack)
        idMenuSettings = mViewRoot?.findViewById(R.id.idMenuSettings)
        mViewRoot?.setOnTouchListener(FloatingOnTouchListener())
        idMenuRoot?.setOnTouchListener { _, _ -> false }// 允许触摸事件继续传递
        idMenuHome?.setOnClickListener { hideSubMenus() }
        idMenuBack?.setOnClickListener { hideSubMenus() }
        idMenuSettings?.setOnClickListener { hideSubMenus() }
        idMenuRoot?.visibility = View.VISIBLE
        initFloatWindow()
    }

    private fun hideSubMenus() {
        idMenuHome?.isVisible = false
        idMenuBack?.isVisible = false
        idMenuSettings?.isVisible = false
    }

    fun showFloatWindow(): Boolean {
        if (!requestPermission(mContext, OP_SYSTEM_ALERT_WINDOW)) {
            Toast.makeText(mContext, "请手动打开悬浮窗口权限", Toast.LENGTH_SHORT).show()
            return false
        }
        try {
            // 设置悬浮窗口位置和大小
            mWindowParams?.width = 400
            mWindowParams?.height = 600
            mWindowManager!!.addView(mViewRoot, mWindowParams)
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(mContext, "悬浮播放失败", Toast.LENGTH_SHORT).show()
            return false
        }
        mWindowMode = WINDOW_MODE_FLOAT
        return true
    }

    val isFloatMode: Boolean get() = mWindowMode == WINDOW_MODE_FLOAT

    fun setStartTimestamp(timestamp: Long) {
        mStartTimestamp = timestamp
        mWindowMode = WINDOW_MODE_FULL
    }

    val timeCount: Int
        get() {
            val timeCount = (System.currentTimeMillis() - mStartTimestamp) / 1000
            return timeCount.toInt()
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

    fun closeFloatWindow(): Boolean {
        if (!sEnableFloatWindow) {
            return false
        }
        if (mViewRoot?.isAttachedToWindow == true) {
            //mLayoutDisplayContainer?.removeAllViews()
            mWindowManager?.removeView(mViewRoot)
        }
        if (mFloatWindowLayoutDelegate != null) {
            mFloatWindowLayoutDelegate!!.onClose()
        }
        mWindowMode = WINDOW_MODE_FULL
        return true
    }

    private fun getScreenWidth(): Int {
        val metric = DisplayMetrics()
        val wm = mContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        wm.defaultDisplay.getMetrics(metric)
        return metric.widthPixels
    }

    private fun initFloatWindow() {
        //屏幕宽度
        val screenWidth: Int = getScreenWidth()
        val rect = FloatWindowRect(screenWidth - 400, 0, 400, 600)
        mWindowManager = mContext?.applicationContext?.getSystemService(Context.WINDOW_SERVICE) as WindowManager
        mWindowParams = WindowManager.LayoutParams()
        mWindowParams?.let {
            //设置层级
            it.type = WindowManager.LayoutParams.TYPE_APPLICATION_OVERLAY
            it.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
            it.gravity = Gravity.CENTER_VERTICAL
            it.format = PixelFormat.TRANSLUCENT
            it.x = rect.x
            it.y = rect.y
            it.width = rect.width
            it.height = rect.height
        }
    }

    /**
     * 检查悬浮窗权限
     *
     * API <18，默认有悬浮窗权限，不需要处理。无法接收无法接收触摸和按键事件，不需要权限和无法接受触摸事件的源码分析
     * API >= 19 ，可以接收触摸和按键事件
     * API >=23，需要在manifest中申请权限，并在每次需要用到权限的时候检查是否已有该权限，因为用户随时可以取消掉。
     * API >25，TYPE_TOAST 已经被谷歌制裁了，会出现自动消失的情况
     */
    private fun requestPermission(context: Context?, op: Int): Boolean {
        if (!Settings.canDrawOverlays(context)) {
            val intent = Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION)
            intent.data = Uri.parse("package:" + context!!.packageName)
            intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
            context.startActivity(intent)
            return false
        }
        return true
    }

    fun checkOverlayPermission(context: Context?): Boolean {
        return requestPermission(context, OP_SYSTEM_ALERT_WINDOW)
    }

    private inner class FloatingOnTouchListener : OnTouchListener {
        private var startX = 0
        private var startY = 0
        private var x = 0
        private var y = 0

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
                    //closeFloatWindow(false)
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
        /**
         * 点击悬浮窗中的关闭按钮等会回调该通知
         */
        fun onClose()
    }

    companion object {
        private const val TAG = "FloatWindowLayout"

        const val WINDOW_MODE_FULL = 1 // 全屏播放

        const val WINDOW_MODE_FLOAT = 2 // 悬浮窗播放

        /**
         * 是否开启悬浮窗模式
         */
        private const val sEnableFloatWindow = true

        @SuppressLint("StaticFieldLeak")
        private var sInstance: HFloatWindowHelper? = null

        fun getInstance(context: Context): HFloatWindowHelper? {
            if (sInstance == null) {
                synchronized(HFloatWindowHelper::class.java) {
                    if (sInstance == null) {
                        sInstance = HFloatWindowHelper(context.applicationContext)
                    }
                }
            }
            return sInstance
        }
    }

    init {
        initView(context)
    }
}