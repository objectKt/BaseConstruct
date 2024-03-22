package com.android.launcher.activity

import android.os.Bundle
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
    }

    override fun onDestroy() {
        TTLSerialPortsManager.closeAllPorts()
        super.onDestroy()
    }
}