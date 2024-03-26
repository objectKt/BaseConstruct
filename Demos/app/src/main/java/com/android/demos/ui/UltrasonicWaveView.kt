package com.android.demos.ui

import android.content.Context
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Canvas
import android.util.AttributeSet
import android.view.View
import kotlin.math.min

class UltrasonicWaveView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : View(context, attrs, defStyleAttr) {

    private val wavePaint = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        color = Color.RED
        style = Paint.Style.FILL
    }

    var waveRadius = 0f

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        waveRadius = min(w, h) / 2f
    }

    override fun draw(canvas: Canvas) {
        super.draw(canvas)
        val centerX = measuredWidth / 2f
        val centerY = measuredHeight / 2f
        canvas.drawCircle(centerX, centerY, waveRadius, wavePaint)
    }
}
