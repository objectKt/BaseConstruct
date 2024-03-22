package com.android.launcher.activity

import android.content.Context
import android.os.PowerManager
import com.android.launcher.R
import com.android.launcher.base.BaseFragmentActivity
import com.android.launcher.databinding.ActivityMainLefts223Binding
import com.android.launcher.fragment.DashboardFragment
import dc.library.utils.logcat.LogCat

/**
 * LeftS223 主页面
 */
class MainActivity : BaseFragmentActivity<ActivityMainLefts223Binding>(
    activityLayoutId = R.layout.activity_main_lefts223,
    rootFragmentHostId = R.id.idNavHostLeft223,
    rootFragment = DashboardFragment::class
) {

    override fun initView() {
        super.initView()
        checkDeviceStatus()
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