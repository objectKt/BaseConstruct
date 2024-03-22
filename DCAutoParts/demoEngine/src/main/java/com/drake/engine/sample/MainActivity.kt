package com.drake.engine.sample

import com.drake.brv.utils.divider
import com.drake.brv.utils.linear
import com.drake.brv.utils.setup
import dc.library.ui.base.EngineToolbarActivity
import com.drake.engine.sample.databinding.ActivityMainBinding
import com.drake.engine.sample.model.Model


class MainActivity : EngineToolbarActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initView() {
        title = "Engine"
    }

    override fun initData() {
        binding.rv.divider(dc.library.ui.R.drawable.divider_horizontal).linear().setup {
            addType<Model>(R.layout.item_image)
        }.models = getData()
    }

    private fun getData(): MutableList<Model> {
        val list = mutableListOf<Model>()
        for (i in 0..40) {
            list.add(Model())
        }
        return list
    }
}





