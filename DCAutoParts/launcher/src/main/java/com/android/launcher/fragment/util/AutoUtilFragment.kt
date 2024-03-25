package com.android.launcher.fragment.util

import dc.library.auto.R

/**
 * Fragment 相关 Util
 */
object AutoUtilFragment {

    // 仪表盘界面类型
    enum class Type { SPORT, CLASSIC, TECH, MAINTAIN, MAP }

    // 仪表盘界面类型描述和背景图片
    val DashboardType: Map<Pair<Type, String>, Int> =
        mapOf(
            (Type.SPORT to "运动") to R.mipmap.ic_menu_sport,
            (Type.CLASSIC to "经典") to R.mipmap.ic_menu_classic,
            (Type.TECH to "科技") to R.mipmap.ic_menu_tech,
            (Type.MAINTAIN to "保养") to R.mipmap.ic_menu_maintain,
            (Type.MAP to "地图") to R.mipmap.ic_menu_map,
        )
}