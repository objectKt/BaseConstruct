package com.android.launcher.activity

import android.content.Context
import android.os.Bundle
import android.os.PowerManager
import androidx.fragment.app.proxyFragmentFactory
import androidx.navigation.fragment.NavHostFragment
import com.android.launcher.R
import com.android.launcher.databinding.ActivityMainLefts223Binding
import com.android.launcher.fragment.DashboardFragment
import com.github.fragivity.loadRoot
import dc.library.auto.manager.TTLSerialPortsManager
import dc.library.ui.base.EngineActivity
import dc.library.utils.logcat.LogCat

/**
 * LeftS223 主页面
 */
class MainActivity : EngineActivity<ActivityMainLefts223Binding>(R.layout.activity_main_lefts223) {

    override fun onCreate(savedInstanceState: Bundle?) {
        proxyFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.idNavHostLeft223) as NavHostFragment
        navHostFragment.loadRoot(DashboardFragment::class)
    }

    override fun initView() {
        LogCat.i("进入了 MainActivity")
    }

    override fun initData() {
        checkDeviceStatus()
    }

    override fun onDestroy() {
        TTLSerialPortsManager.closeAllPorts()
        super.onDestroy()
    }

    private fun checkDeviceStatus() {
        val powerManager: PowerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        val isDeviceInteractive = powerManager.isInteractive
        // 根据设备否处于交互状态执行相应的操作
        if (!isDeviceInteractive) {
            // 设备可能处于挂起或锁屏状态，执行需要的操作，例如暂停视频播放
            LogCat.i("--- isDeviceInteractive false ---")
        } else {
            // 设备处于活动状态，执行需要的操作，例如恢复视频播放
            LogCat.i("--- isDeviceInteractive true ---")
        }
    }
}