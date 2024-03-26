package com.android.demos.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View

class CircleView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val circlePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }
    private var radius = 0f

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
