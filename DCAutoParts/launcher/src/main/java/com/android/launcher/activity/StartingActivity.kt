package com.android.launcher.activity

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.localbroadcastmanager.content.LocalBroadcastManager
import dc.library.utils.ValUtil

/**
 * 启动页面（奔驰 LOGO 欢迎页面）
 * @author hf
 */
class StartingActivity : AppCompatActivity() {

    private lateinit var broadcastReceiver: BroadcastReceiver

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        createBroadcast()
        registerBroadcast()
    }

    /**
     * onDestroy 方法中注销 BroadcastReceiver，以避免内存泄漏。
     */
    override fun onDestroy() {
        super.onDestroy()
        unregisterBroadcast()
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
        LocalBroadcastManager.getInstance(this@StartingActivity).registerReceiver(broadcastReceiver, IntentFilter(ValUtil.ActionBroadcast.LOCAL_BROADCAST_FINISH_INIT_TASK))
    }

    /**
     * 注销 BroadcastReceiver
     */
    private fun unregisterBroadcast() {
        LocalBroadcastManager.getInstance(this).unregisterReceiver(broadcastReceiver)
    }

    private fun gotoMainActivity() {
        val intent = Intent(this@StartingActivity, MainActivity::class.java)
        intent.putExtra("screenWhich", "left")
        intent.putExtra("carType", "s223")
        startActivity(intent)
        this@StartingActivity.finish()
    }
}