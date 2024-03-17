package com.android.launcher;

public class MessageEvent {

    public Type type;
    public Object data;

    public MessageEvent(Type type) {
        this.type = type;
    }

    public enum Type {
        WIFI_AP_OPEN,
        WIFI_AP_CLOSE,
        //下载进度
        APK_DOWNLOAD_PROGRESS,
        APK_DOWNLOAD_SHOW,
        APK_INSTALL,
        FINISH_METER,
        CALIBRATION_TIME, //时间校准
        RADAR_P_OFF, //雷达开关状态
        USB_CONNECTED, //USB已连接
        CRUISE_CONTROL_OFF, //定速巡航关闭
        CRUISE_CONTROL_ON, //定速巡航开启
        CRUISE_CONTROL_STOP, //定速巡航停止
        CRUISE_CONTROL_BREAKDOWN, //定速巡航出现故障
        CRUISE_CONTROL_WAIT_SET, //定速巡航等待设定
        SPEED_LIMITED_MODE, // 限速模式
        //锁车时自动合拢
        SET_CAN35D_D5,
        SET_AUTO_LOCK, //设置自动落锁
        SET_CAR_ASSIST, //设置上下车辅助状态
        SET_CAR_BG_LIGHTING,// 设置汽车背景照明开关
        SET_CAR_INNER_LIGHTING, //内部照明
        SET_CAR_OUTER_LIGHTING, //外部照明
        SET_CAR_ENVIR_LIGHTING, //环境照明
        CAN379_VIEW_REMOVE, //移除底盘升降View
        CAN379_VIEW_UPDATE, //更新底盘升降View
        KEY_GO_BACK, //返回
        ESP_SWITCH, //ESP开关
        SET_HEADREST_DOWN, //设置头枕放倒
        RUN_METER_ANIMATION, //开始执行仪表动画
        INIT_USB, //初始化USB
        RESTART_APP,
        UPDATE_ABS_STATUS, //更新ABS状态
        BELTSAFE_STATUS, //安全带状态
        UPDATE_LEFT_TURN, //左转向
        UPDATE_RIGHT_TURN, //右转向
        KEY_OK, //ok键
        CAR_TYRE, //汽车胎压
        STEER_WHEEL_TYPE, // 方向盘按键类型
        UPDATE_WATER_TEMP, //更新水温
        UPDATE_SPEED, //更新时速
        UPDATE_NAV_DIRECTION, //更新导航方向
        UPDATE_DOOR_HINT, //车门警告
        UPDATE_LEFT_RADAR, // 左侧雷达
        UPDATE_RIGHT_RADAR, //右侧雷达
        CANCEL_TIME, //取消时间
        DOOR_HINT_IS_SHOW, //是否显示
        CHANGE_METER_STYLE, //改变仪表盘样式
        START_METER_ANIMATION, //开始仪表动画
        CAR_AWAKEN, //汽车唤醒
        UPDATE_TOTAL_MILE, //更新总里程数
        RESUME_HEADREST_DOWN, //恢复头枕放倒
        SAFE_BELT_SWITCH,// 安全带收紧开关
        UPDATE_CMS, //更新CMS
        UPDATE_CAR_MESSAGE, //汽车消息
        UPDATE_ENGINE_OIL_STATUS, //机油液位
        UPDATE_TAIYAI, //更新胎压
        UPDATE_DRIVER_DIRECTION, //更新行驶方向
        RESYME_HEADREST_DOWN, //恢复头枕放倒
        UPDATE_CAR_TYPE_STATUS, //汽车钥匙状态
        COMPUTE_CAR_RUN_TIME, //开始计算汽车行驶的时间
        STOP_COMPUTE_CAR_RUN_TIME, //停止计算汽车行驶的时间
        CURRENT_ML, //当前剩余的汽油（百分比）
        NEED_MAINTAIN_MESSAGE, //需要保养的消息
        HOLD_STATUS, //HOLD状态
        OPEN_MESSAGE_FRAG,// 打开信息
        OPEN_HOME_FRAG, //首页（里程）fragment
        OPEN_MILEAGE_HOME_FRAG, //行程主页 fragment
        OPEN_MAINTAIN_HOME_FRAG, //保养home
        OPEN_TYRE_FRAG, //胎压fragment
        OPEN_MAINTAIN_DETAIL_FRAG, //保养详情fragment
        OPEN_COOLANT_LIQUID_FRAG, //冷却液fragment
        OPEN_SPEED_CALIBRATION_FRAG, //时速校准fragment
        OPEN_ENGINE_OIL_LEVEL_FRAG, //发动机机油液位fragment
        OPEN_POST_LAUNCH_DETAIL_FRAG, //启动后fragment
        OPEN_POST_LAUNCH_RESET_FRAG, //启动后复位fragment
        OPEN_AFTER_RECOVERY_REST_FRAG, //恢复后复位fragment
        OPEN_AFTER_RECOVERY_DETAI_FRAG,//恢复后详情fragment
        OPEN_DRIVE_DISTANCE_FRAG, //行驶距离fragment
        OPEN_MILEAGE_SPEED_FRAG, //行程时速fragment
        OPEN_POST_LAUNCH_HOME_FRAG, //行程-启动后主页fragment
        OPEN_AFTER_RECOVERY_HOME_FRAG, //行程-恢复后主页fragment
        CLOSE_DOOR_FRAG,//关闭车门Fragment
        SHOW_DOOR_FRAG,//显示车门Fragment
        UPDATE_WARN_INFO, //更新警告信息
        //更新驾驶模式
        UPDATE_DRIVE_MODE,
        UPDATE_GEARS_STATUS, //更新档位的状态
        PAUSE_CRUISE_CONTROL, //汽车钥匙状态
        COMPUTE_RUN_QTRIP, //计算运行时的油耗
        UPDATE_LYRIC, //更新歌词
        BLUETOOTH_DISCONNECTED, //蓝牙断开
        BLUETOOTH_CONNECTED, //蓝牙连接
        DIALOG_CHECK_OIL_LEVEL, //显示机检查油液位对话框
        UPDATE_RUNNING_DISTANCE,//计算可行驶距离
        UPDATE_DOOR_IMG,
        //显示蓄电池告警
        SHOW_STORAGE_BATTERY,
        //显示发动机故障
        SHOW_ENGINE_WARN,
        //隐藏所以警告标志
        HIDE_ALL_METER_WARING,
        //显示安全带
        SHOW_SAFETY_BELT,
        //显示安全气囊
        SHOW_GASSAFE,
        //显示有转向
        SHOW_RIGHT_TURN_SIGNAL,
        //显示左转向
        SHOW_LEFT_TURN_SIGNAL,
        //灯光显示
        SHOW_LAMP,
        //显示示宽灯
        SHOW_CLEARANCE_LAMP,
        //显示远光灯
        SHOW_HIGH_BEAM,
        //显示电子手刹异常警告
        SHOW_ELECTRICAL_PARK_BRAKE_WARNING,
        //显示电子手刹
        SHOW_ELECTRICAL_PARK_BRAKE,
        //更新汽车温度
        UPDATE_CAR_TEMP,
        //更新油量
        UPDATE_OIL_BOX,
        //下 按键
        ON_DOWN_KEY,
        //上按键
        ON_UP_KEY,
        ON_BACK_KEY,
        ON_RIGHT_KEY,
        ON_LEFT_KEY,
        //重置启动后数据
        REST_LAUNCHER_AFTER,
        //重置复位后数据
        REST_AFTER_RECOVERY,
        SHOW_RESTART_APP_DIALOG,
        //速度校准
        SPEED_ADJUST_SUBTRACT,
        SPEED_ADJUST_PLUS,
        //安全气囊故障
        SHOW_AIRBAG_FAILURE,
        //USB注册成功
        USB_REGISTER_SUCCESS,
        TO_METER_HOME,
        //音乐暂停
        MUSIC_STOP,
        //后舱盖高度限制
        SET_REAR_HATCH_COVER,
        //重启服务
        RESTART_SERVICE,
        //开始导航
        START_MAP_NAV,
        //打开导航菜单
        TO_NAV_MENU,
        //停止地图导航
        STOP_MAP_NAV,
        //打开第一个菜单
        TO_NAV_HOME,
        //下载离线地图：市
        DOWNLOAD_OFFLINE_MAP_CITY,
        //下载离线地图, 省
        DOWNLOAD_OFFLINE_MAP_PROVINCE,
        //下载离线地图, 省的城市数量
        DOWNLOAD_OFFLINE_MAP_PROVINCE_CITY_SIZE,
        //取消下载离线地图
        CANCEL_DOWNLOAD_OFFLINE_MAP,
        //更新转速
        UPDATE_ENGINE_SPEED,
        //更新车速
        UPDATE_CAR_SPEED,
        //更新驾驶模式视图
        UPDATE_DRIVE_MODE_VIEW,
        //打开日间行驶灯
        OPEN_DAY_RUN_LIGHT,
        //打开座椅功能调节
        OPEN_SEAT_FUNCTION_ADJUSTMENT,
        //打开ON键
        OPEN_O_N_KEY,
        //启动后的油量， 百分比
        LAUNCHER_OIL,
        //定位数据
        LOCATION,
        //关闭地图导航
        CLOSE_MAP_NAV,
        //更新时间
        UPDATE_TIME,
        //禁用原车仪表
        DISABLE_ORIGIN_METER,
        //显示告警消息
        SHOW_WARN_MESSAGE,
        //USB通信中断
        USB_INTERRUPT,
        //启动后数据更新
        UPDATE_LAUNCH_AFTER,
        //更新警报音量
        UPDATE_ALARM_VOLUME
    }
}
