package com.dc.android.launcher.serialize.data

import com.drake.serialize.serialize.annotation.SerializeConfig
import com.drake.serialize.serialize.serialLazy

/**
 * 车载蓝牙
 */
@SerializeConfig(mmapID = MAP_ID_CONFIG_DATA_BLUETOOTH)
object ConfigDataBT {
    var currentEquipName: String by serialLazy(default = "currentEquipName")

    var blueVersion: String by serialLazy(default = "")

    // 通讯录下载状态：1、开始下载，2，下载完成，-1、停止下载
    var downloadContactsFlag: Int by serialLazy(default = -1)

    // 电话本下载类型 0、通讯录，1、通话记录
    var contactsType: Int by serialLazy(default = 0)

    // 上一次点击通讯录下载的时间（防止多次重复点击）
    var lastPhoneBookDownloadTime: Long by serialLazy(default = 0L)

    // 当前蓝牙音乐的状态
    var musicPlayState: Int by serialLazy(default = 1)
    var iPlayingAuto: Boolean by serialLazy(default = false)
    var isMixSound: Boolean by serialLazy(default = false)

    // 蓝牙通信状态
    var bluetoothStatus: Int by serialLazy(default = EPhoneStatusInt.UN_CONNECT_INT)
    var isBlueConnectState: Boolean by serialLazy(default = false)
    var isOnPairingMode: Boolean by serialLazy(default = false)

    // 当前电话功能状态
    var currentCallingType: Int by serialLazy(default = CallingType.NONE)

    // 是否是来电中
    var isCallingInButNoTalkingState: Boolean by serialLazy(default = false)

    // 是否声音在来电或者去电后
    var isAfterInOrOutCall: Boolean by serialLazy(default = false)

    // 已通话标志
    var isTalking: Boolean by serialLazy(default = false)

    // 是否在来电状态，去电状态、通话中。不在拨号界面显示Dialog
    var showDialogFlag: Boolean by serialLazy(default = false)

    var phoneNumber: String by serialLazy(default = "")
    var phoneName: String by serialLazy(default = "")
    var address: String by serialLazy(default = "")

    var isInOriginal: Boolean by serialLazy(default = false)

    // 是人为手动暂停
    var isPersonPause: Boolean by serialLazy(default = false)

    // 有蓝牙音乐焦点
    var blueMusicFocus: Boolean by serialLazy(default = false)
    var iHoldFocus: Boolean by serialLazy(default = false)
}

object CallingType {
    const val NONE = 0
    const val ONGOING_NOTIFICATION = 1400
    const val START_PAIR_MODE: Int = 1401
    const val END_PAIR_MODE = 1402
    const val UPDATE_NAME = 1406
    const val BOOK_LIST_START_LOAD = 1407
    const val BOOK_LIST_CANCEL_LOAD = 1408
    const val DRIVER_BOOK_LIST = 1409
    const val DEVICE_SWITCH = 1410
    const val CALL_TALKING = 1411
    const val CALL_TYPE_IN = 1412
    const val CALL_TYPE_OUT = 1413
    const val CALL_TYPE_MISS = 1414
    const val UPDATE_PHONE_NUM = 1415
    const val HANGUP_PHONE = 1416
    const val TIME_FLAG = 1417
    const val PLAY_STATE_PLAYING = 1418
    const val PLAY_STATE_PAUSE = 1419
    const val PLAY_STATE_STOP = 1420
    const val MEDIAINFO = 1421
    const val OUT_CALL_FLOAT = 1422
}

/*映射enum EPhoneStatus*/
object EPhoneStatusInt {
    const val UN_CONNECT_INT: Int = 0
    const val CONNECTED_INT: Int = 1
    const val CONNECTING_INT: Int = 2
    const val INCOMING_CALL_INT: Int = 3
    const val CALLING_OUT_INT: Int = 4
    const val TALKING_INT: Int = 5
    const val MULTI_TALKING_INT: Int = 6
    const val INITIALIZING_INT: Int = 7
}