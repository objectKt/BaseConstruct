package com.android.launcher.can;

import java.util.List;

/**
 * 空调控制前后
 */
@Deprecated
public class Can1E5 implements CanParent {
    public static final String COMMAND_1E5_STAT = "AA00000800000";
    public static List<String> E5;
    public static String AIR_SYS_STATE_VALUE;// 空调系统状态数据
    public static volatile String lastData = "";

    @Override
    public void handlerCan(List<String> msg) {
    }

    public static synchronized void setStateValue(String senddata) {
        if (!senddata.equals(AIR_SYS_STATE_VALUE)) {
            AIR_SYS_STATE_VALUE = senddata;
        }
    }
}