package com.android.launcher

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.drake.net.time.Interval
import dc.library.auto.singleton.SingletonTest
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {

    private lateinit var interval: Interval // 轮询器

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main_lefts223)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
        interval = Interval(
            0,
            1,
            TimeUnit.SECONDS,
            3
        ).life(this)
        interval.subscribe {
            SingletonTest.getInstance(applicationContext).doSomeThingsWithContext()
        }.finish {
            SingletonTest.getInstance(applicationContext).doFinish()
        }.start()
    }
}