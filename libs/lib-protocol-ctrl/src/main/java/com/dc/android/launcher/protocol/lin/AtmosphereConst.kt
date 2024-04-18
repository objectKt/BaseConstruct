package com.dc.android.launcher.protocol.lin

import com.dc.android.launcher.util.HUtils

object AtmosphereConst {

    // 氛围灯 亮度级别: 0 ~ 20 B3 B4
    val brightLevelB3B4: List<String> = listOf(
        "00 F8", "50 F8", "60 F8", "70 F8", "80 F8", "90 F8", "A0 F8",
        "C0 F8", "E0 F8", "00 F9", "20 F9", "40 F9", "60 F9", "80 F9",
        "A0 F9", "E0 F9", "80 FA", "20 FB", "C0 FB", "00 FD", "40 FE"
    )

    // 氛围灯 64 个单色  B0 = （0x00 ~ 0x3F） ,B2 = 0x64 ,B6 = 0x3F
    val singleColorB0: List<String> = mutableListOf<String>().apply {
        val arrange = HUtils.Byte.byteToUInt(0x3F.toByte())
        for (id in 0..arrange) {
            add(HUtils.Byte.byteToHex(id.toByte()))
        }
    }

    // 氛围灯 10 个主题色  B0 = （0x50 ~ 0x59） ,B2 = 0x64 ,B6 = 0x3F
    val themeColorB0: List<String> = mutableListOf<String>().apply {
        val start = HUtils.Byte.byteToUInt(0x50.toByte())
        val end = HUtils.Byte.byteToUInt(0x59.toByte())
        for (id in start..end) {
            add(HUtils.Byte.byteToHex(id.toByte()))
        }
    }
}