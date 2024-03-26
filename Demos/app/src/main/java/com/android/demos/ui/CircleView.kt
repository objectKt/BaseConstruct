package com.android.demos.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

/**
 * 实现光圈从远到近扩大的动画特效
 * 使用 ValueAnimator 来改变一个圆形View的半径
 * 从而产生光圈扩大的效果
 */
class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    var radius = 0f

    init {
        isFocusable = true
        isClickable = true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    override fun onDraw(canvas: Canvas) {
        canvas.drawCircle(measuredWidth / 2f, measuredHeight / 2f, radius, circlePaint)
    }
}
