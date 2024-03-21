package com.drake.engine.utils

import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.widget.EditText

/** 显示或隐藏密码 */
fun EditText.showPassword(enabled: Boolean) {
    transformationMethod = if (enabled) {
        HideReturnsTransformationMethod.getInstance()
    } else PasswordTransformationMethod.getInstance()
    setSelection(text.length)
}