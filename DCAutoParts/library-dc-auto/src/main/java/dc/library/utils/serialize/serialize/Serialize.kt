/*
 * Copyright (C) 2018 Drake, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package dc.library.utils.serialize.serialize

import com.tencent.mmkv.MMKV


object Serialize {
    /** 全局序列化/反序列化接口 */
    var hook: SerializeHook = SerializeHook

    /** 全局mmkv实例 */
    var mmkv: MMKV = MMKV.defaultMMKV()
}

//<editor-fold desc="写入">
/**
 * 将键值数据序列化存储到磁盘
 */
fun serialize(vararg params: Pair<String, Any?>) {
    Serialize.mmkv.serialize(*params)
}

/**
 * 将键值数据序列化存储到磁盘
 */
fun MMKV.serialize(vararg params: Pair<String, Any?>) {
    params.forEach {
        when (val value = it.second) {
            null -> remove(it.first)
            else -> encode(it.first, Serialize.hook.serialize(it.first, value::class.java, value))
        }
        return@forEach
    }
}
//</editor-fold>

//<editor-fold desc="读取">
/**
 * 根据[name]读取磁盘数据, 假设磁盘没有则返回[defValue]指定的默认值
 */
inline fun <reified T> deserialize(name: String, defValue: T? = null): T {
    return Serialize.mmkv.deserialize(name, defValue)
}

/** 根据[name]读取磁盘数据, 假设磁盘没有则返回[defValue]指定的默认值 */
inline fun <reified T> MMKV.deserialize(name: String, defValue: T? = null): T {
    return (deserialize(T::class.java, name) ?: defValue) as T
}

/** 根据[name]读取磁盘数据, 即使读取的是基础类型磁盘不存在的话也会返回null */
fun <T> MMKV.deserialize(type: Class<T>, name: String, defValue: T? = null): T {
    val byteArray = decodeBytes(name) ?: return defValue as T
    return (Serialize.hook.deserialize(name, type, byteArray) ?: defValue) as T
}
//</editor-fold>