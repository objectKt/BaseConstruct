/*
 * Copyright (C) 2018 Drake, Inc. https://github.com/liangjingkanji
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

package com.drake.engine.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatCheckBox
import com.drake.engine.R

/**
 * 默认禁言点击改变选中状态, 使用[filter]或者[checkable]配置是否可选中
 */
class FilterCheckBox : AppCompatCheckBox {

    var checkable = false

    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet? = null) : super(context, attrs) {
        val attributes = context.obtainStyledAttributes(attrs, R.styleable.FilterCheckBox)
        checkable = attributes.getBoolean(R.styleable.FilterCheckBox_filter_checkable, false)
        attributes.recycle()
    }

    private var filter: FilterCheckBox.() -> Boolean = { checkable }

    fun filter(block: FilterCheckBox.() -> Boolean) {
        this.filter = block
    }

    override fun toggle() {
        if (filter(this)) {
            super.toggle()
        }
    }

}
