package com.drake.serialize.sample

import android.os.Parcelable
import dc.library.utils.serialize.intent.bundle
import com.drake.serialize.sample.databinding.ActivityReceiveArgumentsBinding
import com.drake.serialize.sample.model.ParcelableModel
import com.drake.serialize.sample.model.SerializableModel
import dc.library.ui.base.EngineActivity

class ReceiveArgumentsActivity : EngineActivity<ActivityReceiveArgumentsBinding>(R.layout.activity_receive_arguments) {

    /**
     * Fragment使用方法一样
     * bundle等方法仅[com.drake.serialize.serialize.SerializeHook]自定义序列化
     * 仅支持实现[Serializable][Parcelable]接口对象或基础类型, 并支持其类型的集合/数组
     */
    private val parcelize: ParcelableModel? by bundle()
    private val parcelizeList: ArrayList<ParcelableModel>? by bundle()
    private val serialize: SerializableModel? by bundle()
    private val serializeList: ArrayList<SerializableModel>? by bundle()
    private val intArray: IntArray? by bundle()

    override fun initView() {
    }

    override fun initData() {
        binding.tvReceiveArguments.text = "parcelize = $parcelize" +
                "\nparcelizeList = $parcelizeList" +
                "\nserialize = $serialize" +
                "\nserializeList = $serializeList" +
                "\nintArrayOf = ${intArray?.get(0)}"
    }
}
