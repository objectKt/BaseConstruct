package com.android.launcher.activity

import android.os.Bundle
import androidx.fragment.app.proxyFragmentFactory
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
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

    private lateinit var viewModel: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        proxyFragmentFactory()
        super.onCreate(savedInstanceState)
        LogCat.i("进入了 MainActivity onCreate")
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.idNavHostLeft223) as NavHostFragment
        navHostFragment.loadRoot(DashboardFragment::class)
        viewModel = ViewModelProvider(this)[ViewModelMain::class.java]
        viewModel.loginStatus.observe(this) {
            LogCat.i("observe loginStatus $it")
        }
    }

    override fun initView() {
        LogCat.i("进入了 MainActivity initView")
    }

    override fun initData() {
    }

    override fun onDestroy() {
        TTLSerialPortsManager.closeAllPorts()
        super.onDestroy()
    }
}