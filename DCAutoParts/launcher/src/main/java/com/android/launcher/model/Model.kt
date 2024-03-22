package com.android.launcher.model

import androidx.databinding.BaseObservable
import com.drake.brv.item.ItemPosition

data class Model(var url: Any? = null) : BaseObservable(), ItemPosition {
    var name = "Model"
    override var itemPosition: Int = 0
}