package com.android.launcher.activity

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.android.launcher.can.R
import dc.library.auto.task.XTask
import dc.library.auto.task.logger.TaskLogger
import dc.library.auto.task.thread.pool.cancel.ICancelable

class StartingActivity : AppCompatActivity() {

    private var cancelable: ICancelable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_starting)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }
    }

    override fun onResume() {
        super.onResume()
        // 获得焦点
        TaskLogger.i("StartingActivity 获得焦点")
        testDoTask()
    }

    private fun testDoTask() {
        TaskLogger.i("任务等待跳转到 MainActivity")
        cancelable = XTask.postToMainDelay({
            TaskLogger.i("任务执行完毕，开始跳转到 MainActivity")
            cancelable?.cancel()
            startActivity(Intent(this, MainActivity::class.java))
        }, 5000L)
    }
}