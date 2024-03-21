package com.drake.engine.widget

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import androidx.appcompat.widget.AppCompatSeekBar
import com.drake.engine.R

/**
 * 通过过滤器可以判断是否支持手势划动
 *
 * @property stroke 是否允许划动
 */
class FilterSeekBar : AppCompatSeekBar {

    var stroke = true

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FilterSeekBar)
        stroke = attributes.getBoolean(R.styleable.FilterSeekBar_filter_stroke, true)
        attributes.recycle()
    }

    private var filter: FilterSeekBar.() -> Boolean = { stroke }

    fun filter(block: FilterSeekBar.() -> Boolean) {
        this.filter = block
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return filter(this)
    }
}
