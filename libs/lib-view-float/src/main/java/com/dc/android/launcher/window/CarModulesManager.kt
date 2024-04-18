package com.dc.android.launcher.window

import android.content.Context


class CarModulesManager private constructor(private val context: Context) {

    companion object : ActivitySingleton<CarModulesManager, Context>(::CarModulesManager)

}