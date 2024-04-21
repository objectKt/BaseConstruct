package com.dc.android.launcher.serialize.data

import com.drake.serialize.serialize.annotation.SerializeConfig
import com.drake.serialize.serialize.serialLazy

const val MAP_ID_CONFIG_DATA_ACTIVITY: String = "dc_cfg_activity"
const val MAP_ID_CONFIG_DATA_LIN_PROTOCOL: String = "dc_cfg_lin"
const val MAP_ID_CONFIG_DATA_SYSTEM: String = "dc_cfg_sys"
const val MAP_ID_CONFIG_DATA_BLUETOOTH: String = "dc_cfg_blue"

/**
 * 互动页面数据
 */
@SerializeConfig(mmapID = MAP_ID_CONFIG_DATA_ACTIVITY)
object ConfigDataActivity {
    var isHomeActVisible: Boolean by serialLazy(default = false)
    var isNavigation: Boolean by serialLazy(default = false) // 是否正在导航
}