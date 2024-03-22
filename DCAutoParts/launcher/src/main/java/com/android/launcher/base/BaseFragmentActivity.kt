package com.android.launcher.base

import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.databinding.ViewDataBinding
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.NavHostFragment
import com.github.fragivity.loadRoot
import dc.library.ui.base.EngineActivity
import kotlin.reflect.KClass

open class BaseFragmentActivity<B : ViewDataBinding>(
    @LayoutRes activityLayoutId: Int = 0,
    @IdRes val rootFragmentHostId: Int = 0,
    private val rootFragment: KClass<out Fragment>
) : EngineActivity<B>(activityLayoutId) {

    override fun initView() {
        val navHostFragment = supportFragmentManager.findFragmentById(rootFragmentHostId) as NavHostFragment
        navHostFragment.loadRoot(rootFragment)
    }

    override fun initData() {

    }
}