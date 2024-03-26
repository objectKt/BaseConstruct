package com.drake.serialize.sample

import androidx.lifecycle.MutableLiveData
import com.drake.serialize.sample.model.KotlinSerializableModel
import dc.library.utils.serialize.serialize.annotation.SerializeConfig
import dc.library.utils.serialize.serialize.serial
import dc.library.utils.serialize.serialize.serialLazy
import dc.library.utils.serialize.serialize.serialLiveData

@SerializeConfig(mmapID = "dc_object_vars")
object ManageVarsSerialize {
    var name: String by serial()
    var data: KotlinSerializableModel? by serialLazy()
    var amount: String by serial("默认值", "自定义键名")
    val liveData: MutableLiveData<String> by serialLiveData("默认值")
    var userId: String = "0123"
    var balance: String by serial("0.0", { "balance-$userId" })
}