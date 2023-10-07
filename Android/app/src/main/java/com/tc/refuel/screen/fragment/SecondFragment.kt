package com.tc.refuel.screen.fragment

import com.drake.engine.utils.throttleClick
import com.github.fragivity.navigator
import com.github.fragivity.pop
import com.tc.refuel.screen.base.BaseFragment
import com.tc.refuel.screen.databinding.FragmentSecondBinding

class SecondFragment : BaseFragment<FragmentSecondBinding>() {

    override fun initView() {
        binding.vbBack.throttleClick {
            navigator.pop()
        }
        baseFakeLoading("这是一个 Loading Dialog")
    }
}