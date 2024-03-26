package com.android.launcher

import androidx.lifecycle.MutableLiveData
import dc.library.utils.serialize.serialize.annotation.SerializeConfig
import dc.library.utils.serialize.serialize.serial
import dc.library.utils.serialize.serialize.serialLazy
import dc.library.utils.serialize.serialize.serialLiveData

/**
 * 统一配置整个 App 基本数据的单例对象
 * 且线程安全
 * 在某些场景下比数据库更方便(支持集合 / 主线程读取)
 * 比 SharePreference 更快速(内部使用 MKV 开源库)
 */
@SerializeConfig(mmapID = "dc_app_config_object")
object AppConfig {

    /** 懒读取, 每次写入都会更新内存/磁盘, 但是读取仅第一次会读取磁盘, 后续一直使用内存中, 有效减少ANR */
    var userId: String by serialLazy()

    /** 每次都读写磁盘 */
    var isFirstLaunch: Boolean by serial(false)

    /** 保存集合 */
    var host: List<String> by serial(emptyList())

    /** 只要集合元素属于可序列化对象 Serializable/Parcelable 即可保存到本地 */
    var users: List<KotlinSerializableModel> by serial(emptyList())

    var data: KotlinSerializableModel? by serialLazy()

    val liveData: MutableLiveData<String> by serialLiveData("默认值")
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