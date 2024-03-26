package com.drake.serialize.sample.model

import java.io.Serializable

/**
 * 使用Java的[Serializable]进行序列化传递
 *
 * 1. 新增字段读取旧数据时会读取失败(除非使用serialVersionUID)
 * 2. 新增字段读取默认值默认值无效
 *
 * 最佳方案请使用[KotlinSerializableModel]
 */
@kotlinx.serialization.Serializable
data class SerializableModel(var name: String = "默认值") : Serializable {
    companion object {
        /**
         * 1. 保证新增字段依然可以读取到对象
         * 2. 该Id不能重复否则读取异常
         * 3. 新增字段的默认值无效, 例如新增`firstName = "default"`, 但读取到的默认值是null
         */
        private const val serialVersionUID = -7L
    }
}