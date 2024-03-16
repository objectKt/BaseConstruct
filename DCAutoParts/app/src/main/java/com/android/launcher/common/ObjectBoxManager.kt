package com.android.launcher.common

import dc.library.auto.global.ScreenCarType
import dc.library.auto.global.ScreenWhichSide
import dc.library.auto.singleton.BaseSafeSingleton

/**
 * 数据库统一管理类，所属车型+屏幕
 * @param screenCar     车屏幕类型（s222,s223, etc.）
 * @param screenSide    屏幕哪一边（Left,Right）
 */
class ObjectBoxManager private constructor(private val screenCar: ScreenCarType, private val screenSide: ScreenWhichSide) {

    companion object : BaseSafeSingleton<ObjectBoxManager, ScreenCarType, ScreenWhichSide>(::ObjectBoxManager)

    fun func() {

    }
}