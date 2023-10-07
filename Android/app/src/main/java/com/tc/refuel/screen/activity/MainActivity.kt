package com.tc.refuel.screen.activity

import android.os.Bundle
import androidx.fragment.app.proxyFragmentFactory
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.NavHostFragment
import com.github.fragivity.loadRoot
import com.tc.refuel.screen.R
import com.tc.refuel.screen.base.BaseActivity
import com.tc.refuel.screen.fragment.HomeFragment
import com.tc.refuel.screen.base.MainViewModel
import com.tc.refuel.screen.databinding.ActivityMainBinding

class MainActivity : BaseActivity<ActivityMainBinding>() {

    private lateinit var vm: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        proxyFragmentFactory()
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host) as NavHostFragment
        navHostFragment.loadRoot(HomeFragment::class)
        vm = ViewModelProvider(this@MainActivity)[MainViewModel::class.java]
        vm.initMainActivity()
    }
}