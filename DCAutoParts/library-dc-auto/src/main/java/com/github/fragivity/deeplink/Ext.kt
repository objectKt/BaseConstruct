package com.github.fragivity.deeplink

import android.content.Intent
import android.content.Intent.ACTION_VIEW
import android.os.Handler
import androidx.annotation.MainThread
import androidx.navigation.fragment.NavHostFragment
import com.github.fragivity.getOrCreateNode
import com.github.fragivity.navigator

private val isRouteInit by lazy(LazyThreadSafetyMode.NONE) {
    val c = Class.forName("com.github.fragivity._RouteLoaderKt")
    c.getDeclaredMethod("initRoute").invoke(null)
    true
}

@MainThread
fun NavHostFragment.handleDeepLink(intent: Intent) = with(intent) {
    if (ACTION_VIEW == action && isRouteInit) {
        getFragmentInfo(data.toString())?.let {
            navigator.getOrCreateNode(it)
            //post用来确保HomeFragment已进入fragmentManager，才能获取并对其进行hide
            Handler().post { navController.navigate(data!!) }
        }
    }
}