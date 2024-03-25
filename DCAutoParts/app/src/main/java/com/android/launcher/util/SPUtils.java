package com.android.launcher.util;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * @description: SharedPreferences工具类
 * @createDate: 2023/4/30
 */
public class SPUtils {

    //头枕放倒的次数
    public static final String SP_HEADREST_DOWN_COUNT = "headrest_down_count";
    //汽车类型
    public static final String SP_CAR_TYPE = "car_type";

    //行驶的里程
    public static final String SP_RUN_MILE = "run_mile";

    //百公里油耗
    public static final String SP_QTRIP = "qtrip";

    //消耗的汽油
    public static final String SP_CONSUME_OIL = "consume_oil";
    //安全带收紧开关
    public static final String SP_SAFE_BELT_SWITCH = "safe_belt_switch";

    //上次启动时的油量
    public static final String LAST_LAUNCHER_OIL = "last_launcher_oil";

    //汽车启动后运行的里程
    public static final String SP_CAR_LAUNCHER_RUN_MIL = "car_launcher_run_mil";


    private static final String PREFERENCE_NAME = "benz_left";
    private SPUtils() {}

    public static final String SP_RIGHT_DEVICE_ID = "device_id_right";

    public static final String SPEED_ADJUST = "speed_adjust";

    public static final String CAN35D_D1 = "can35d_d1";
    public static final String CAN35D_D2 = "can35d_d2";
    public static final String CAN35D_D3 = "can35d_d3";
    public static final String CAN35D_D4_AUTO_LOCK = "can35d_d4_auto_lock";
    public static final String CAN35D_D4_ASSIST = "can35d_d4_assist";
    public static final String CAN35D_D4_LIGHTING = "can35d_d4_lighting";
    public static final String CAN35D_D5 = "can35d_d5";

    //警报音量
    public static final String SP_CAR_ALARM_VOLUME = "car_alarm_volume";

    //头枕放倒
    public static final String SP_HEADREST_DOWN = "headrest_down";

    //仪表盘样式
    public static final String SP_METER_STYLE = "meter_style";

    //胎压
    public static final String SP_TAIYAI = "taiyai";

    //平均时速
    public static final String SP_AVERAGE_SPEED = "average_speed";

    //里程单位类型
    public static final String SP_UNIT_TYPE = "unit_type";

    //选择的语言
    public static final String SP_SELECT_LANGUAGE = "select_language";


    //开始计算的油量
    public static final String SP_START_COMPUTE_OIL = "current_oil";

    public static final String SP_DRIVE_MODE = "drive_mode";

    //Esp关闭
    public static final String SP_ESP_OFF = "esp_off";

    //发动机故障
    public static final String SP_ENGINE_WARN = "engine_warn";

    //气囊故障灯
    public static final String SP_AIRBAG_FAILURE = "airbag_failure";

    //操作原车仪表
    public static final String SP_ORIGIN_METER_ON = "origin_meter_on";

    //允许打开日行灯
    public static final String SP_DAY_RUN_LIGHT_ON = "day_run_light_on";

    public static final String SHOW_AIRBAG_FAILURE = "show_airbag_failure";

    public static final String SHOW_ENGINE_FAILURE = "show_engine_failure";

    /**
     * 获取SharedPreferences对象
     *
     * @param context 上下文
     * @return SharedPreferences对象
     */
    public static SharedPreferences getSharedPreferences(Context context) {
        SharedPreferences sharedPreferences = context.getSharedPreferences(PREFERENCE_NAME, Context.MODE_PRIVATE);
        return sharedPreferences;
    }

    /**
     * 获取Editor对象
     *
     * @param context 上下文
     * @return Editor对象
     */
    public static SharedPreferences.Editor getEditor(Context context) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        return editor;
    }


    /**
     * 将文本数据存入本地, 同步提交
     *
     * @param context 上下文
     * @param key     key值
     * @param value   存入本地的字符串
     */
    public static synchronized void putString(Context context, String key, String value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putString(key, value);
        editor.commit();
    }

    /**
     * 通过key获取SharedPreferences指定的字符串
     *
     * @param context 上下文
     * @param key     key值
     * @return 本地存储的字符串
     * @see #getString(Context, String, String)
     */
    public static String getString(Context context, String key) {
        return getString(context, key, "");
    }

    /**
     * 通过key获取SharedPreferences指定的字符串
     *
     * @param context      上下文
     * @param key          key值
     * @param defaultValue 默认的返回值
     * @return 本地存储的字符串或默认值
     */
    public static String getString(Context context, String key, String defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getString(key, defaultValue);
    }

    /**
     * put int preferences
     *
     * @param context 上下文
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static void putInt(Context context, String key, int value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putInt(key, value);
        editor.commit();
    }

    /**
     * get int preferences
     *
     * @param context 上下文
     * @param key     key值
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a int
     * @see #getInt(Context, String, int)
     */
    public static int getInt(Context context, String key) {
        return getInt(context, key, -1);
    }

    /**
     * get int preferences
     *
     * @param context      context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a int
     */
    public static int getInt(Context context, String key, int defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getInt(key, defaultValue);
    }

    /**
     * put long preferences
     *
     * @param context 上下文
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static void putLong(Context context, String key, long value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putLong(key, value);
        editor.apply();
    }

    /**
     * get long preferences
     *
     * @param context 上下文
     * @param key     key值
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a long
     * @see #getLong(Context, String, long)
     */
    public static long getLong(Context context, String key) {
        return getLong(context, key, -1);
    }

    /**
     * get long preferences
     *
     * @param context      context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a long
     */
    public static long getLong(Context context, String key, long defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getLong(key, defaultValue);
    }

    /**
     * put float preferences
     *
     * @param context 上下文
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putFloat(Context context, String key, float value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putFloat(key, value);
        return editor.commit();
    }

    /**
     * get float preferences
     *
     * @param context 上下文
     * @param key     key值
     * @return The preference value if it exists, or -1. Throws ClassCastException if there is a preference with this
     * name that is not a float
     * @see #getFloat(Context, String, float)
     */
    public static float getFloat(Context context, String key) {
        return getFloat(context, key, -1);
    }

    /**
     * get float preferences
     *
     * @param context      context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a float
     */
    public static float getFloat(Context context, String key, float defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getFloat(key, defaultValue);
    }

    /**
     * put boolean preferences
     *
     * @param context 上下文
     * @param key     The name of the preference to modify
     * @param value   The new value for the preference
     * @return True if the new values were successfully written to persistent storage.
     */
    public static boolean putBoolean(Context context, String key, boolean value) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.putBoolean(key, value);
        return editor.commit();
    }

    /**
     * get boolean preferences, default is false
     *
     * @param context 上下文
     * @param key     key值
     * @return The preference value if it exists, or false. Throws ClassCastException if there is a preference with this
     * name that is not a boolean
     * @see #getBoolean(Context, String, boolean)
     */
    public static boolean getBoolean(Context context, String key) {
        return getBoolean(context, key, false);
    }

    /**
     * get boolean preferences
     *
     * @param context      context
     * @param key          The name of the preference to retrieve
     * @param defaultValue Value to return if this preference does not exist
     * @return The preference value if it exists, or defValue. Throws ClassCastException if there is a preference with
     * this name that is not a boolean
     */
    public static boolean getBoolean(Context context, String key, boolean defaultValue) {
        SharedPreferences sharedPreferences = getSharedPreferences(context);
        return sharedPreferences.getBoolean(key, defaultValue);
    }

    /**
     * 清空SharedPreferences缓存
     * @param context 上下文
     */
    public static void clear(Context context) {
        SharedPreferences.Editor editor = getEditor(context);
        editor.clear();
        editor.apply();
    }

}

