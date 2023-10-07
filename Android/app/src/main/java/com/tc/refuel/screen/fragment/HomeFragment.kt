package com.tc.refuel.screen.fragment

import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import com.github.fragivity.LaunchMode
import com.github.fragivity.navigator
import com.github.fragivity.push
import com.tc.refuel.screen.R
import com.tc.refuel.screen.base.BaseFragment
import com.tc.refuel.screen.databinding.FragmentHomeBinding
import com.tc.refuel.screen.model.Model

class HomeFragment : BaseFragment<FragmentHomeBinding>() {

    override fun initView() {
        binding.vbList.divider(R.drawable.divider_horizontal).linear().setup {
            addType<Model>(R.layout.list_item_test)
            R.id.item.onClick {
                when (itemViewType) {
                    R.layout.list_item_test -> {
                        navigator.push(SecondFragment::class) { launchMode = LaunchMode.SINGLE_TASK }
                    }
                }
            }
        }.models = getData()
    }

    private fun getData(): List<Model> {
        return mutableListOf<Model>().apply {
            add(Model().apply {
                name = "启动一个 Fragment"
            })
            add(Model().apply {
                name = "启动一个 Fragment"
            })
            add(Model().apply {
                name = "启动一个 Fragment"
            })
        }
    }
}