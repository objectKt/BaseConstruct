package com.dc.android.launcher.window

import android.content.Context
import android.content.Intent

/**

 */
inline fun <reified T> startActivityFloat(context: Context, block: Intent.() -> Unit = {}) =
    context.startActivity(Intent(context, T::class.java).apply(block))