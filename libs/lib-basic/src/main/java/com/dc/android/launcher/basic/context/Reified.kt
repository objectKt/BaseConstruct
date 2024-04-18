package com.dc.android.launcher.basic.context

import android.content.Context
import android.content.Intent

inline fun <reified T> startActivityX(context: Context, block: Intent.() -> Unit = {}) =
    context.startActivity(Intent(context, T::class.java).apply(block))