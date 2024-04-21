package com.dc.android.launcher.util

import org.apache.commons.lang3.StringUtils


fun main(args: Array<String>) {
    // 测试工具函数
//    println(XXFormat.IntUtil.toHexOf4Bytes(1680247411))//64268A73
//    println(XXFormat.IntUtil.toHexOf4Bytes(100000))//000186A0
//    println(XXFormat.IntUtil.toHexOf2Bytes(41))//0029
//    println(XXFormat.IntUtil.toHexOf2Bytes(1))//0001
    println(1680247411.toString(16))//64268a73
    println("计算 % 符号 1009 % 1000 = ${1009 % 1000}")//计算 % 符号 1009 % 1000 = 9
    println("截取 = ${StringUtils.substring("FFFF000100010000FF", 4, 8)}")//截取 = 0001
    println("1015 toInt = ${"1015".toInt()}")//1015 toInt = 1015
//    println("XXFormat.StringUtil.hexOf2BytesToInt() = ${XXFormat.StringUtil.hexOf2BytesToInt("001C")}")//
//    println("XXFormat.StringUtil.hexOf2BytesToInt() = ${XXFormat.StringUtil.hexOf2BytesToInt("02")}")//
//    println("XXFormat.StringUtil.hexOf4BytesToInt() = ${XXFormat.StringUtil.hexOf4BytesToInt("00008686")}")//
//    val words = "one two three four five six seven eight nine ten".split(' ')
//    val chunks = words.chunked(10)
//    chunks.forEach { println(it) }
}

//class ClickableEditText : com.google.android.material.textfield.TextInputEditText {
//
//    constructor(context: Context?) : super(context!!)
//    constructor(context: Context?, attrs: AttributeSet?) : super(context!!, attrs)
//    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context!!, attrs, defStyleAttr)
//
//    @SuppressLint("ClickableViewAccessibility")
//    override fun onTouchEvent(event: MotionEvent?): Boolean {
//        return if (!isEnabled) false else super.onTouchEvent(event)
//    }
//}