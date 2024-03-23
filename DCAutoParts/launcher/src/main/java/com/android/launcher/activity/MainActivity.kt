package com.android.launcher.activity

import android.os.Bundle
import androidx.fragment.app.proxyFragmentFactory
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.android.launcher.R
import com.android.launcher.base.BaseActivity
import com.android.launcher.fragment.DashboardFragment
import com.android.launcher.util.Func
import com.github.fragivity.loadRoot
import dc.library.auto.manager.TTLSerialPortsManager
import dc.library.utils.logcat.LogCat

class MainActivity : BaseActivity() {

    private lateinit var viewModel: ViewModelMain

    override fun onCreate(savedInstanceState: Bundle?) {
        Func.logLifecycle("生命周期 == 进入了 onCreate 1")
        proxyFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main_lefts223)
        Func.logLifecycle("生命周期 == 进入了 onCreate 3")
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.idNavHostLeft223) as NavHostFragment
        navHostFragment.loadRoot(DashboardFragment::class)
        doViewModelLogic()
    }

    override fun stateChangeLogic(event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_CREATE -> {
                Func.logLifecycle("生命周期 == 进入了 onCreate 2")
            }

            Lifecycle.Event.ON_DESTROY -> TTLSerialPortsManager.closeAllPorts()
            else -> {}
        }
    }

    private fun doViewModelLogic() {
        viewModel = ViewModelProvider(this)[ViewModelMain::class.java]
        viewModel.loginStatus.observe(this) {
            LogCat.i("observe loginStatus $it")
        }
    }
}