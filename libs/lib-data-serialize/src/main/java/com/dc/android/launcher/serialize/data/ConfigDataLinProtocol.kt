package com.dc.android.launcher.serialize.data

import com.drake.serialize.serialize.annotation.SerializeConfig
import com.drake.serialize.serialize.serialLazy

/**
 * LIN 通信数据
 * @author hf
 */
@SerializeConfig(mmapID = MAP_ID_CONFIG_DATA_LIN_PROTOCOL)
object ConfigDataLinProtocol {
    var singleColorSelected: String by serialLazy(default = "#ff7800") // 单色颜色
    var polychromeType: Int by serialLazy(default = 4) // 0 ~ 9 // 多色模式
    var selectedColorMode: Int by serialLazy(default = 0) // 颜色模式：单色彩-0  多色彩-1
    var brightLevel: Int by serialLazy(default = 20) // 修改氛围灯亮度等级 0 ~20
}