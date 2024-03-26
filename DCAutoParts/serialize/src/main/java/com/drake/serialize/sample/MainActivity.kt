package com.drake.serialize.sample

import android.os.SystemClock
import android.util.Log
import android.view.View
import com.drake.serialize.sample.constant.AppConfig
import com.drake.serialize.sample.databinding.ActivityMainBinding
import com.drake.serialize.sample.model.KotlinSerializableModel
import com.drake.serialize.sample.model.ParcelableModel
import com.drake.serialize.sample.model.SerializableModel
import com.drake.tooltip.toast
import dc.library.ui.base.EngineActivity
import dc.library.utils.serialize.intent.openActivity
import kotlin.system.measureTimeMillis

class MainActivity : EngineActivity<ActivityMainBinding>(R.layout.activity_main) {

    override fun initView() {
        binding.v = this
    }

    override fun initData() {
        // 监听本地数据变化
        ManageVarsSerialize.liveData.observe(this) {
            toast("观察到本地数据: $it")
        }
    }

    override fun onClick(v: View) {
        when (v) {
            // 可观察本地数据
            binding.llObserve -> {
                ManageVarsSerialize.liveData.value = SystemClock.elapsedRealtime().toString()
            }
            // 写入
            binding.cardWriteField -> {
                ManageVarsSerialize.name = "https://github.com/liangjingkanji/Serialize"
                toast("写入数据: ${ManageVarsSerialize.name} 到磁盘")
            }
            // 读取
            binding.cardReadField -> {
                toast("读取本地数据为: ${ManageVarsSerialize.name}")
            }
            // 打开页面
            binding.cardOpenPage -> {
                // startActivity 同样可以传递数据
                openActivity<ReceiveArgumentsActivity>(
                    "serialize" to SerializableModel(),
                    "serializeList" to listOf(SerializableModel(), SerializableModel()),
                    "parcelize" to ParcelableModel(),
                    "parcelizeList" to listOf(ParcelableModel(), ParcelableModel()),
                    "intArray" to intArrayOf(1, 3, 4),
                )
                // ReceiveArgumentsFragment().withArguments("parcelize" to ParcelableModel()) // Fragment传递数据
            }
            // 应用配置数据
            binding.cardConfig -> {
                Log.d("日志", "isFirstLaunch = ${AppConfig.isFirstLaunch}")
                AppConfig.isFirstLaunch = false
            }
            // 读取100w次
            binding.cardBigRead -> {
                val measureTimeMillis = measureTimeMillis {
                    repeat(100000) {
                        val name = ManageVarsSerialize.data?.name
                    }
                }
                binding.tvBigReadTime.text = "${measureTimeMillis} ms"
            }
            // 写入100w次
            binding.cardBigWrite -> {
                val measureTimeMillis = measureTimeMillis {
                    repeat(100000) {
                        ManageVarsSerialize.data = KotlinSerializableModel("第${it}次")
                    }
                }
                binding.tvBigWriteTime.text = "${measureTimeMillis} ms"
            }
        }
    }
}
