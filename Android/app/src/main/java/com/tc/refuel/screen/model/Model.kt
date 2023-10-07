package com.tc.refuel.screen.model

import androidx.databinding.BaseObservable
import com.drake.brv.item.ItemPosition

open class Model : BaseObservable(), ItemPosition {
    override var itemPosition: Int = 0

    var name: String = "名字"
        set(value) {
            field = value
            notifyChange()
        }
}