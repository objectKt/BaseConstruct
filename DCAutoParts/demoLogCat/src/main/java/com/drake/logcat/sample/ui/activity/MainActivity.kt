package com.drake.logcat.sample.ui.activity

import androidx.core.view.GravityCompat
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController
import dc.library.ui.base.EngineActivity
import com.drake.logcat.sample.R
import com.drake.logcat.sample.databinding.ActivityMainBinding

class MainActivity : EngineActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initView() {
        setSupportActionBar(binding.toolbar)
        val navController = findNavController(R.id.nav)
        binding.toolbar.setupWithNavController(
            navController,
            AppBarConfiguration(binding.navView.menu, binding.drawer)
        )
        binding.navView.setupWithNavController(navController)
    }

    override fun initData() {
    }

    override fun onBackPressed() {
        if (binding.drawer.isDrawerOpen(GravityCompat.START)) {
            binding.drawer.closeDrawers()
        } else super.onBackPressed()
    }
}
