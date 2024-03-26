package com.android.launcher.fragment

import com.android.launcher.R
import com.android.launcher.databinding.FragmentUsbTestBinding
import dc.library.ui.base.EngineFragment
import dc.library.utils.logcat.LogCat

/**
 * 导航仪表
 */
class MapNavMeterFragment : EngineFragment<FragmentUsbTestBinding>(R.layout.fragment_usb_test) {

    override fun initView() {
        LogCat.i("进入了 MapNavMeterFragment")
    }

    override fun initData() {

    }
}