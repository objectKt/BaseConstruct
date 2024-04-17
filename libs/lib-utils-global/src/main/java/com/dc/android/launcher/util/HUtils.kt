package com.dc.android.launcher.util

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.drawable.Drawable
import androidx.annotation.ArrayRes
import androidx.annotation.ColorRes
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.appcompat.content.res.AppCompatResources

interface HUtils {

    @SuppressLint("StaticFieldLeak")
    object AppGet {
        private var context: Context? = null

        @JvmStatic
        fun appContext(): Context {
            if (context == null) {
                throw RuntimeException("Please call [HUtils.AppGet.init(context)] first")
            } else {
                return context as Context
            }
        }

        @JvmStatic
        fun init(context: Context) {
            if (AppGet.context != null) return
            AppGet.context = context.applicationContext
        }
    }

    object Res {
        fun getDrawable(@DrawableRes resId: Int): Drawable {
            return AppCompatResources.getDrawable(AppGet.appContext(), resId)!!
        }

        fun getColor(@ColorRes resId: Int): Int {
            return AppGet.appContext().getColor(resId)
        }

        fun getString(@StringRes resId: Int): String {
            return AppGet.appContext().getString(resId)
        }

        fun getIntArray(@ArrayRes resId: Int): IntArray {
            return AppGet.appContext().resources.getIntArray(resId)
        }
    }

}