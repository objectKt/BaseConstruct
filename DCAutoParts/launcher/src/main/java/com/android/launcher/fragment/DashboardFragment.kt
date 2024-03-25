package com.android.launcher.fragment

import android.os.Bundle
import androidx.fragment.app.viewModels
import com.android.launcher.R
import com.android.launcher.databinding.FragmentDashboardBinding
import com.android.launcher.model.Model
import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import dc.library.ui.base.EngineFragment
import dc.library.utils.logcat.LogCat

class DashboardFragment : EngineFragment<FragmentDashboardBinding>(R.layout.fragment_dashboard) {

    private val viewModel: ViewModelDashboard by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initView() {
        LogCat.i("进入了 DashboardFragment")
    }

    override fun initData() {
        binding.rv.divider(dc.library.auto.R.drawable.divider_horizontal).linear().setup {
            addType<Model>(R.layout.item_image)
        }.models = mutableListOf<Model>().apply {
            for (i in 0..40) {
                add(Model())
            }
        }
    }
}