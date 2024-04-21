package com.dc.android.launcher.protocol.lin

import com.dc.android.launcher.serialize.data.ConfigDataLinProtocol
import com.dc.android.launcher.util.HUtils

/**
 * 氛围灯协议数据控制
 * 备注：休眠模式，当原车停止LIN数据发送，2分钟后模块进入休眠
 *      唤醒方式只需要发送任意LIN数据，即可唤醒
 * @author hf
 */
object AtmosphereCtrl {

    private const val LIN_ID_50: String = "50" // LIN ID
    private const val AIR_B1B2_3014 = "30 14" // 空调模式
    private const val AIR_UP_B0_28 = "28" // 空调模式 - 升温
    private const val AIR_DOWN_B0_7A = "7A" // 空调模式 - 降温
    private const val B5_FF = "FF" // 通用 B5
    private const val B6_3F = "7F" // 普通模式
    private const val ALL_AIR_B6_05 = "05" // 整车空调模式
    private const val LEFT_AIR_B6_1C = "1C" // 左侧独立空调模式
    private const val RIGHT_AIR_B6_23 = "23" // 右侧独立空调模式
    private const val LIN_END_00 = "00" // LIN 数据尾

    // 获取同步设置的氛围灯亮度级别
    @Suppress("FunctionName")
    private fun B3B4(): String {
        val level = ConfigDataLinProtocol.brightLevel
        val levelLst = AtmosphereConst.brightLevelB3B4
        if (level > levelLst.size) {
            return AtmosphereConst.brightLevelB3B4[20]
        }
        return AtmosphereConst.brightLevelB3B4[level]
    }

    // 64 个单色  B0 = （0x00 ~ 0x3F） ,B2 = 0x64 ,B6 = 0x3F
    @Suppress("LocalVariableName")
    fun combineSingleLightB0(id: Int): String {
        if (id in 0..63) {
            val B0: String = AtmosphereConst.singleColorB0[id]
            val combineResult = "$LIN_ID_50 $B0 30 64 ${B3B4()} $B5_FF $B6_3F $LIN_END_00"
            return HUtils.Str.replaceBlanks(combineResult)
        }
        return HUtils.Str.replaceBlanks("$LIN_ID_50 3F 30 64 ${B3B4()} $B5_FF $B6_3F $LIN_END_00") // 默认值
    }

    // 10 个主题色  B0 = （0x50 ~ 0x59） ,B2 = 0x64 ,B6 = 0x3F
    @Suppress("LocalVariableName")
    private fun combineThemeLightB0(id: Int): String? {
        if (id in 0..9) {
            val B0: String = AtmosphereConst.themeColorB0[id]
            return HUtils.Str.replaceBlanks("$LIN_ID_50 $B0 30 64 ${B3B4()} $B5_FF $B6_3F $LIN_END_00")
        }
        return HUtils.Str.replaceBlanks("$LIN_ID_50 59 30 64 ${B3B4()} $B5_FF $B6_3F $LIN_END_00")
    }

    // 欢迎模式 (在模块上电3s内发送，超时指令无效), B0 = 0x1C, B2 = 0x14, B6 = 0x3F
    private val atmosphereLightWelcome: String =
        HUtils.Str.replaceBlanks("$LIN_ID_50 1C 30 14 ${B3B4()} $B5_FF $B6_3F $LIN_END_00")

    // 盲点辅助模式 (该功能，不能被应用于中控部位), B0 = 0x01, B2 = 0x64, B6 = 0x25
    private val atmosphereLightBlind: String =
        HUtils.Str.replaceBlanks("$LIN_ID_50 01 30 64 ${B3B4()} $B5_FF 25 $LIN_END_00")

    // 全体空调模式, B0=0x7A, B2=0x14, B6=0x05  降温
    private val atmosphereAirConditionTempDown: String =
        HUtils.Str.replaceBlanks("$LIN_ID_50 $AIR_DOWN_B0_7A $AIR_B1B2_3014 ${B3B4()} $B5_FF $ALL_AIR_B6_05 $LIN_END_00")

    // 右边独立空调模式, B0=0x7A, B2=0x14, B6=0x23  降温
    private val atmosphereRightAirConditionTempDown: String =
        HUtils.Str.replaceBlanks("$LIN_ID_50 $AIR_DOWN_B0_7A $AIR_B1B2_3014 ${B3B4()} $B5_FF $RIGHT_AIR_B6_23 $LIN_END_00")

    // 左边独立空调模式, B0=0x7A, B2=0x14, B6=0x1C  降温
    private val atmosphereLeftAirConditionTempDown: String =
        HUtils.Str.replaceBlanks("$LIN_ID_50 $AIR_DOWN_B0_7A $AIR_B1B2_3014 ${B3B4()} $B5_FF $LEFT_AIR_B6_1C $LIN_END_00")

    // 全体空调模式, B0=0x28, B2=0x14, B6=0x05  升温
    private val atmosphereAirConditionTempUp: String =
        HUtils.Str.replaceBlanks("$LIN_ID_50 $AIR_UP_B0_28 $AIR_B1B2_3014 ${B3B4()} $B5_FF $ALL_AIR_B6_05 $LIN_END_00")

    // 右边独立空调模式 B0=0x28,B2=0x14,B6=0x23  升温
    private val atmosphereRightAirConditionTempUp: String =
        HUtils.Str.replaceBlanks("$LIN_ID_50 $AIR_UP_B0_28 $AIR_B1B2_3014 ${B3B4()} $B5_FF $RIGHT_AIR_B6_23 $LIN_END_00")

    // 左边独立空调模式 B0=0x28,B2=0x14,B6=0x1C  升温
    private val atmosphereLeftAirConditionTempUp: String =
        HUtils.Str.replaceBlanks("$LIN_ID_50 $AIR_UP_B0_28 $AIR_B1B2_3014 ${B3B4()} $B5_FF $LEFT_AIR_B6_1C $LIN_END_00")
}