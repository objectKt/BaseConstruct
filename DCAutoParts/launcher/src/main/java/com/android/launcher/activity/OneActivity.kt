package com.android.launcher.activity

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import com.android.launcher.R
import com.drake.net.time.Interval
import dc.library.ui.base.app
import dc.library.utils.ValUtil
import dc.library.utils.logcat.LogCat
import java.util.concurrent.TimeUnit

@Deprecated("Deprecated in Java")
class OneActivity : AppCompatActivity() {

    private var mInterval: Interval? = null
    private var mStartingTime: Long = 0
    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        mStartingTime = System.currentTimeMillis()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_rights223)
        createBroadcast()
        registerBroadcast()
    }

    override fun onResume() {
        super.onResume()
        mInterval = Interval(1, TimeUnit.SECONDS)
        mInterval?.subscribe {
            LogCat.i("定时检查次数 .. $it")
            val interSpend = System.currentTimeMillis() - mStartingTime
            // 10秒 还没有收到页面跳转广播就重启 App
            if (interSpend > 10 * 1000) {
                mInterval?.stop()
                restartApp(app)
            }
        }?.start()
    }

    /**
     * onDestroy 方法中注销 BroadcastReceiver，以避免内存泄漏。
     */
    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadcast()
        mInterval?.stop()
    }

    /**
     * 创建 BroadcastReceiver
     */
    private fun createBroadcast() {
        broadcastReceiver = object : BroadcastReceiver() {
            override fun onReceive(context: Context, intent: Intent) {
                if (ValUtil.ActionBroadcast.LOCAL_BROADCAST_FINISH_INIT_TASK == intent.action) {
                    gotoMainActivity()
                }
            }
        }
    }

    /**
     * 注册 BroadcastReceiver
     */
    private fun registerBroadcast() {
        LocalBroadcastManager.getInstance(this@OneActivity).registerReceiver(broadcastReceiver, IntentFilter(ValUtil.ActionBroadcast.LOCAL_BROADCAST_FINISH_INIT_TASK))
    }

    /**
     * 注销 BroadcastReceiver
     */
    private fun unregisterBroadcast() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    private fun gotoMainActivity() {
        val intent = Intent(this@OneActivity, MainActivity::class.java)
        intent.putExtra("screenWhich", "left")
        intent.putExtra("carType", "s223")
        startActivity(intent)
        this@OneActivity.finish()
    }

    private fun restartApp(context: Context) {
        // 关闭当前应用的所有活动
        (context as Activity).finishAffinity()
        // 创建一个新的意图来重新启动应用
        val intent = Intent(context, OneActivity::class.java).apply {
            addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK or Intent.FLAG_ACTIVITY_NEW_TASK)
        }
        // 启动新的任务栈，这将重新打开应用
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        context.startActivity(intent)
    }
}