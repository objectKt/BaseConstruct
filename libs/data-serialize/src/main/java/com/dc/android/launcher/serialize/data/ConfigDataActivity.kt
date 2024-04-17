package com.dc.android.launcher.serialize.data

import com.drake.serialize.serialize.annotation.SerializeConfig
import com.drake.serialize.serialize.serialLazy

@SerializeConfig(mmapID = "dc_config_activity")
object ConfigDataActivity {
    var isHomeActVisible: Boolean by serialLazy(default = false)
}