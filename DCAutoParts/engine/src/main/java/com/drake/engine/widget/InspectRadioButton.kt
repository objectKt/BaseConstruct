package com.drake.engine.widget

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatRadioButton

/** 可以在RadioButton点击后判断是否拦截, 如果拦截则不会产生切换效果 */
class InspectRadioButton(
    context: Context,
    attrs: AttributeSet? = null,
) : AppCompatRadioButton(context, attrs) {

    private var interceptor: Interceptor? = null

    /** 拦截器 */
    fun setInterceptor(listener: Interceptor) {
        this.interceptor = listener
    }

    override fun toggle() {
        if (interceptor?.onIntercept() != true) super.toggle()
    }

    /** 切换状态前拦截点击事件 */
    interface Interceptor {
        /** 返回true表示拦截 */
        fun onIntercept(): Boolean
    }

}