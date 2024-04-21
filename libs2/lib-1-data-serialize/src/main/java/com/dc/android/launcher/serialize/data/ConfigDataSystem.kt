package com.dc.android.launcher.serialize.data

import com.drake.serialize.serialize.annotation.SerializeConfig
import com.drake.serialize.serialize.serialLazy

/**
 * 系统设置
 * 音量
 * 屏幕亮度
 */
@SerializeConfig(mmapID = MAP_ID_CONFIG_DATA_SYSTEM)
object ConfigDataSystem {

    /* 中央显示屏亮度 */
    var centralDisplayLum: Int by serialLazy(default = 255)
}