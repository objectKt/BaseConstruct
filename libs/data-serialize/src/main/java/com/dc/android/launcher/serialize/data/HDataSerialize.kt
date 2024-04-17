package com.dc.android.launcher.serialize.data

import com.drake.serialize.serialize.annotation.SerializeConfig
import com.drake.serialize.serialize.serialLazy

@SerializeConfig(mmapID = "dc_lin_atmosphere_light")
object LinAtmosphereLight {
    var lightLevel: Int by serialLazy(default = 20)
}

@SerializeConfig(mmapID = "dc_model_application")
object ModelApplication {
    var propCar: Boolean by serialLazy(default = false) // 暂时无用，双系统使用
    var currentActivityStr: String by serialLazy(default = "")
    var appVersionNameShow: String by serialLazy(default = "")
}

@SerializeConfig(mmapID = "dc_model_living_service")
object ModelLivingService {
    var notificationChannelId: String by serialLazy(default = "notification_channel_id_01") // 唯一的通知通道的 ID
    var notificationId: Int by serialLazy(default = 1111)
    var isDownloading: Boolean by serialLazy(default = false) // APK 正在版本更新状态
    var isSilence: Boolean by serialLazy(default = false) // 静音
    var isUsbOtgStatusIn: Boolean by serialLazy(default = false) // U 盘是插入状态
    var mcuVersion: String by serialLazy(default = "")
    var mcuFilePath: String by serialLazy(default = "sdcard/smart_pie/mcu/BZS_MCU.BIN")
    var canVersion: String by serialLazy(default = "")
    var canFilePath: String by serialLazy(default = "sdcard/smart_pie/can/can_app.bin")
}