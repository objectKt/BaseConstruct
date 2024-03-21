package com.drake.engine.base

import android.graphics.Color
import android.view.MotionEvent
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import com.drake.engine.swipeback.SwipeBackHelper

abstract class EngineSwipeActivity<B : ViewDataBinding>(@LayoutRes contentLayoutId: Int = 0) : EngineActivity<B>(contentLayoutId) {

    private var swipeBackHelper: SwipeBackHelper? = null

    /**
     * 关闭侧滑
     */
    var swipeEnable = true
        set(value) {
            field = value
            swipeBackHelper?.setEnable(field)
        }

    override fun init() {
        swipeBackHelper = SwipeBackHelper(this)
        swipeBackHelper?.setBackgroundColor(Color.WHITE)
        super.init()
    }

    override fun dispatchTouchEvent(event: MotionEvent): Boolean {
        swipeBackHelper?.dispatchTouchEvent(event)
        return super.dispatchTouchEvent(event)
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        swipeBackHelper?.onTouchEvent(event)
        return super.onTouchEvent(event)
    }
}