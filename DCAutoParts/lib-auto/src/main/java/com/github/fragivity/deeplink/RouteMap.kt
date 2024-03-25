package com.github.fragivity.deeplink

import androidx.fragment.app.Fragment
import kotlin.reflect.KClass

private val routeMap = mutableMapOf<String, KClass<out Fragment>>()

internal fun getRouteUri(clazz: KClass<out Fragment>): String? =
    routeMap.toList().firstOrNull { it.second == clazz }?.first

internal fun getFragmentInfo(uri: String): KClass<out Fragment>? =
    routeMap[uri]

/**
 *  Add URI info for Fragment
 */
fun addRoute(uriStr: String, clazz: KClass<out Fragment>) {
    check(routeMap.getOrPut(uriStr) { clazz } == clazz) {
        """Deep links "$uriStr" duplicated !!"""
    }
}