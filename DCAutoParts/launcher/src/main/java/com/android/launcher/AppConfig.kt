package com.android.launcher

import androidx.lifecycle.MutableLiveData
import dc.library.utils.serialize.serialize.annotation.SerializeConfig
import dc.library.utils.serialize.serialize.serial
import dc.library.utils.serialize.serialize.serialLazy
import dc.library.utils.serialize.serialize.serialLiveData

/**
 * 统一配置整个 App 的各类状态变量
 */
@SerializeConfig(mmapID = "dc_app_config_object")
object AppConfig {
    var name: String by serial()
    var data: KotlinSerializableModel? by serialLazy()
    var amount: String by serial("默认值", "自定义键名")
    val liveData: MutableLiveData<String> by serialLiveData("默认值")
    var userId: String = "0123"
    var balance: String by serial("0.0", { "balance-$userId" })
}

/**
 * kotlin 官方序列化框架[kotlinx.serialization]
 * https://github.com/Kotlin/kotlinx.serialization
 * 1. 使用转换为 Json 或者 Protobuf 等数据格式后存储
 * 2. 即使增删字段也不会导致读取失败
 * 3. 读写性能更高
 */
@kotlinx.serialization.Serializable
data class KotlinSerializableModel(var name: String = "默认值")