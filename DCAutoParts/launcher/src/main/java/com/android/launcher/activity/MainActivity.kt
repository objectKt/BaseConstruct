package com.android.launcher.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.proxyFragmentFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.android.launcher.R
import com.android.launcher.fragment.DashboardFragment
import com.github.fragivity.loadRoot
import dc.library.auto.manager.TTLSerialPortsManager
import dc.library.utils.logcat.LogCat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import com.android.launcher.databinding.ActivityMainLefts223Binding

class MainActivity : AppCompatActivity() {

    private lateinit var viewModel: ViewModelMain
    private lateinit var binding: ActivityMainLefts223Binding

    override fun onCreate(savedInstanceState: Bundle?) {
        proxyFragmentFactory()
        super.onCreate(savedInstanceState)
        val rootView = layoutInflater.inflate(R.layout.activity_main_lefts223, null)
        binding = DataBindingUtil.bind(rootView)!!
        setContentView(rootView)
        activityLifecycle()
        setActivityWithFragments()
        doViewModelLogic()
    }

    private fun activityLifecycle() {
        lifecycle.addObserver(object : LifecycleEventObserver {
            override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
                LogCat.i("MainActivity Lifecycle 调用了 ${event.name}")
                when (event) {
                    Lifecycle.Event.ON_DESTROY -> TTLSerialPortsManager.closeAllPorts()
                    else -> {}
                }
            }
        })
    }

    private fun setActivityWithFragments() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.idNavHostLeft223) as NavHostFragment
        navHostFragment.loadRoot(DashboardFragment::class)
    }

    private fun doViewModelLogic() {
        viewModel = ViewModelProvider(this)[ViewModelMain::class.java]
        viewModel.loginStatus.observe(this) {
            LogCat.i("observe loginStatus $it")
        }
    }
}