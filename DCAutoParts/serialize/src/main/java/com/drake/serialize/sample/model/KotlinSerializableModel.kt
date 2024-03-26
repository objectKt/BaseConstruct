package com.drake.serialize.sample.model

/**
 * 使用kotlin官方序列化框架[kotlinx.serialization](https://github.com/Kotlin/kotlinx.serialization)
 *
 * 1. 使用转换为Json或者Protobuf等数据格式后存储
 * 2. 即使增删字段也不会导致读取失败
 * 3. 读写性能也更高
 */
@kotlinx.serialization.Serializable
data class KotlinSerializableModel(var name: String = "默认值")