package com.android.launcher.meter;

/**
* @description: 菜单页面类型
* @createDate: 2023/9/21
*/
public enum MenuType {
    HOME,
    NAVIGATION, //导航页面
    MAINTAIN_HOME,//保养主菜单页面
    MAINTAIN_HOME_LIST, //保养主菜单列表
    MAINTAIN_DETAIL, //保养详情
    MAP, //地图
    MEDIA, //媒体
    FM, //FM收音机
    MILEAGE_HOME, //行程
    PHONE, //电话
    SPEED_CALIBRATION, //速度校准
    TYRE, //胎压
    ENGINE_OIL, //发动机机油液位
    COOLING_LIQUID, //冷却液
    MESSAGE,// 消息
    ATTENTION, //注意力辅助系统
    THEME, //主题
    CHASSIS_LIFT, //底盘升降
    DOOR, //车门
    WARN, //警告
    MILEAGE_SPEED, //行程-时速
    DRIVING_DISTANCE, //行程-可行驶距离
    POST_LAUNCH_HOME, //行程-启动后主页
    AFTER_RECOVERY_HOME, //行程-恢复后
    POST_LAUNCH_RESET, //启动后-复位
    AFTER_RECOVERY_RESET, //恢复后-复位页
    POST_LAUNCH_DETAIL, //启动后-详情
    AFTER_RECOVERY_DETAIL, //恢复后-详情页
    //启动后
    LAUNCHER_AFTER,
    //复位后
    AFTER_RECOVERY,
    ENGINE_SPEED
}
