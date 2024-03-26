package com.android.demos

import android.animation.ValueAnimator
import android.os.Bundle
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.demos.ui.CircleView
import com.android.demos.ui.UltrasonicWaveView

class DemoActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_demo)
        testAnimation2()
    }

    private fun testAnimation() {
        val ultrasonicWaveView = findViewById<UltrasonicWaveView>(R.id.ultrasonic_wave_view)
        createUltrasonicWaveAnimation(ultrasonicWaveView)
    }

    private fun testAnimation2() {
        val circleView = findViewById<CircleView>(R.id.circle_view)
        // 假设初始半径为50dp，结束半径为200dp
        val initialRadius = resources.getDimension(R.dimen.initial_radius)
        val endRadius = resources.getDimension(R.dimen.end_radius)
        // 启动动画
        startCircleExpandAnimation(circleView, initialRadius, endRadius)
    }

    private fun startCircleExpandAnimation(circleView: CircleView, startRadius: Float, endRadius: Float, duration: Long = 1000) {
        val animator = ValueAnimator.ofFloat(startRadius, endRadius)
        animator.interpolator = AccelerateDecelerateInterpolator()
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val newRadius = animation.animatedValue as Float
            circleView.radius = newRadius
            circleView.invalidate()
        }
        animator.start()
    }

    private fun createUltrasonicWaveAnimation(view: UltrasonicWaveView, duration: Long = 2000) {
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.interpolator = LinearInterpolator()
        animator.duration = duration
        animator.addUpdateListener { animation ->
            val scale = animation.animatedValue as Float
            view.waveRadius = scale * (view.waveRadius * 2 - view.waveRadius)
            view.invalidate()
        }
        animator.repeatCount = ValueAnimator.INFINITE
        animator.start()
    }
}