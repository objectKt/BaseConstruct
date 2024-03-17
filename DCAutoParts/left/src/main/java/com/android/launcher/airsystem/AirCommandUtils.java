package com.android.launcher.airsystem;

import android.text.TextUtils;

import com.android.launcher.can.Can1E5;
import com.android.launcher.entity.BinaryEntity;
import com.android.launcher.util.LogUtils;
import com.android.launcher.util.StringUtils;

import org.jetbrains.annotations.NotNull;

/**
 * 空调命令 气流控制命令用1E5, 温度和风速控制用20b
 */
public class AirCommandUtils {
    //can 1e5
    public static final String COMMAND_START_TAG_AIR_1E5 = "AA00000800000";
    //can 20b
    public static final String COMMAND_START_TAG_AIR_20B = "AA00000700000";
    private static String lastCopilotTemp = TempSizeType.TEMP_16;
    private static String lastMainDriverTemp = TempSizeType.TEMP_16;
    private static String lastCopilotWind = WindLevelType.LEVEL_1;
    private static String lastMainDriverWind = WindLevelType.LEVEL_1;

    /**
     * @param type                 气流分配类型
     * @param airDeviceStatusValue 空调设备当前的值
     * @description: 主驾气流分配
     * @createDate: 2023/5/1
     */
    public static synchronized String buildMainDriverAirflow(String airDeviceStatusValue, String type) {
        LogUtils.printI(AirCommandUtils.class.getName(), "airDeviceStatusValue=" + airDeviceStatusValue + ", type=" + type);
        String command = "";
        if (!TextUtils.isEmpty(airDeviceStatusValue) && airDeviceStatusValue.length() > 16) {
            // 1E582828AAC6664152CF
            // can的栈号 前八位有效   D0  D1  D2 D3 D4 D5  D6  D7
            // 1E5       8         28  28  AA C6 66 41  52  CF
            // tag: 1e5
            // D4 第二个
            String tag = airDeviceStatusValue.substring(0, 3);
            String left = airDeviceStatusValue.substring(4, 12);
            String frontWinddir = airDeviceStatusValue.substring(12, 13);
            LogUtils.printI(AirCommandUtils.class.getName(), "buildMainDriverAirflow---frontWinddir=" + frontWinddir + ", driverWinddir=" + type);
            String status = Can1E5.AIR_SYS_STATE_VALUE.substring(14, 16);
            BinaryEntity binaryEntity = new BinaryEntity(status);
            // 要把主驾风向自动改成手动
            binaryEntity.setB3(BinaryEntity.Value.NUM_0);
            String right = airDeviceStatusValue.substring(16);
            command = COMMAND_START_TAG_AIR_1E5 + tag + left + frontWinddir + type + binaryEntity.getHexData() + right;
        }
        LogUtils.printI(AirCommandUtils.class.getName(), "buildMainDriverAirflow----command=" + command + ", length=" + command.length());
        return command;
    }

    @NotNull
    public static String setAirWordMode(String airDeviceStatusValue, AirWorkMode airWorkMode) {
        //空调自动标识
        String airAutoHexTag = airDeviceStatusValue.substring(14, 16);
        String binary = StringUtils.hexString2binaryString(airAutoHexTag);
        String binaryNew = null;
        if (binary.length() >= 8) {
            String mainDriverAutoLeft = binary.substring(0, 3);
            String copilotAutoLeft = binary.substring(5);
            binaryNew = mainDriverAutoLeft + airWorkMode.getValue() + airWorkMode.getValue() + copilotAutoLeft;
        }
        LogUtils.printI(AirCommandUtils.class.getName(), "setAirWordMode---binary=" + binary + ", binaryNew=" + binaryNew);
        String airAutoHexTagNew;
        if (binaryNew == null) {
            airAutoHexTagNew = airAutoHexTag;
        } else {
            int number = Integer.parseInt(binaryNew, 2);//将二进制转为十进制
            airAutoHexTagNew = Integer.toHexString(number);//将十进制转为十六进制
        }
        LogUtils.printI(AirCommandUtils.class.getName(), "airDeviceStatusValue=" + airDeviceStatusValue + ", airAutoHexTag=" + airAutoHexTag + ", airAutoHexTagNew=" + airAutoHexTagNew);
        return airAutoHexTagNew;
    }

    /**
     * @param type                 气流分配类型
     * @param airDeviceStatusValue 空调设备当前的值
     * @description: 副驾气流分配
     * @createDate: 2023/5/1
     */
    public static synchronized String buildCopilotAirflow(String airDeviceStatusValue, String type) {
        LogUtils.printI(AirCommandUtils.class.getName(), "airDeviceStatusValue=" + airDeviceStatusValue + ", type=" + type);
        String command = "";
        if (!TextUtils.isEmpty(airDeviceStatusValue) && airDeviceStatusValue.length() > 16) {
            //1E582828AAC6664152CF
            // can的栈号 前八位有效   D0  D1  D2 D3 D4 D5  D6  D7
            // 1E5       8          28  28  AA C6 66 41  52  CF
            //tag: 1e5
            //D4 第一个
            String tag = airDeviceStatusValue.substring(0, 3);
            String left = airDeviceStatusValue.substring(4, 12);
            String driverWinddir = airDeviceStatusValue.substring(13, 14);
            LogUtils.printI(AirCommandUtils.class.getName(), "buildCopilotAirflow---frontWinddir=" + type + ", driverWinddir=" + driverWinddir);
            String status = Can1E5.AIR_SYS_STATE_VALUE.substring(14, 16);
            BinaryEntity binaryEntity = new BinaryEntity(status);
            //要把副驾风向自动改成手动
            binaryEntity.setB4(BinaryEntity.Value.NUM_0);
            String right = airDeviceStatusValue.substring(16);
            command = COMMAND_START_TAG_AIR_1E5 + tag + left + type + driverWinddir + binaryEntity.getHexData() + right;
        }
        LogUtils.printI(AirCommandUtils.class.getName(), "buildCopilotAirflow---command=" + command + ", length=" + command.length());
        return command;
    }

    /**
     * @description: 主驾风速
     * @createDate: 2023/5/1
     */
    public static synchronized String buildMainDriverWindLevel(String airDeviceStatusValue, String level) {
        LogUtils.printI(AirCommandUtils.class.getName(), "airDeviceStatusValue=" + airDeviceStatusValue + ", level=" + level);
        //D2 主驾驶风
        String command = "";
        if (!TextUtils.isEmpty(airDeviceStatusValue) && airDeviceStatusValue.length() > 14) {
            if (!WindLevelType.LEVEL_7.equals(level)) {
                lastMainDriverWind = level;
            }
            String copilotWind = airDeviceStatusValue.substring(10, 12);
            if (!WindLevelType.LEVEL_7.equals(copilotWind)) {
                lastCopilotWind = copilotWind;
            }
            //1E582828AAC6664152CF
            // can的栈号 前八位有效   D0  D1  D2 D3 D4 D5  D6  D7
            // 1E5       8          28  28  AA C6 66 41  52  CF
            //tag: 1e5
            //1e5 46 32 78 8C 2416ECF
            String tag = airDeviceStatusValue.substring(0, 3);
            String left = airDeviceStatusValue.substring(4, 8);
            String right = airDeviceStatusValue.substring(12);
            command = COMMAND_START_TAG_AIR_20B + tag + left + level + lastCopilotWind + right;
        }
        LogUtils.printI(AirCommandUtils.class.getName(), "command=" + command);
        return command;
    }

    /**
     * @description: 副驾风速
     * @createDate: 2023/5/1
     */
    public static synchronized String buildCopilotWindLevel(String airDeviceStatusValue, String level) {
        LogUtils.printI(AirCommandUtils.class.getName(), "airDeviceStatusValue=" + airDeviceStatusValue + ", level=" + level);
        //D3 副驾风速

        String command = "";
        if (!TextUtils.isEmpty(airDeviceStatusValue) && airDeviceStatusValue.length() > 14) {

            if (!WindLevelType.LEVEL_7.equals(level)) {
                lastCopilotWind = level;
            }

            String mainDriverWind = airDeviceStatusValue.substring(8, 10);
            if (!WindLevelType.LEVEL_7.equals(mainDriverWind)) {
                lastMainDriverWind = mainDriverWind;
            }

            //1E582828AAC6664152CF
            // can的栈号 前八位有效   D0  D1  D2 D3 D4 D5  D6  D7
            // 1E5       8          28  28  AA C6 66 41  52  CF
            //tag: 1e5
            String tag = airDeviceStatusValue.substring(0, 3);
            String left = airDeviceStatusValue.substring(4, 8);
            String right = airDeviceStatusValue.substring(12);

            command = COMMAND_START_TAG_AIR_20B + tag + left + lastMainDriverWind + level + right;
        }
        LogUtils.printI(AirCommandUtils.class.getName(), "command=" + command);
        return command;
    }

    /**
     * @description: 副驾温度
     * @createDate: 2023/5/1
     */
    public static synchronized String buildCopilotTempSize(String airDeviceStatusValue, String size) {
        LogUtils.printI(AirCommandUtils.class.getName(), "airDeviceStatusValue=" + airDeviceStatusValue + ", size=" + size);
        //D1  副驾温度

        String command = "";
        if (!TextUtils.isEmpty(airDeviceStatusValue) && airDeviceStatusValue.length() > 14) {

            String mainDriverTemp = airDeviceStatusValue.substring(4, 6);
            String copilotTemp = airDeviceStatusValue.substring(6, 8);
            LogUtils.printI(AirCommandUtils.class.getName(), "mainDriverTemp=" + mainDriverTemp + ", copilotTemp=" + copilotTemp);

            if (!TempSizeType.TEMP_LO.equals(mainDriverTemp) && !TempSizeType.TEMP_HI.equals(mainDriverTemp)) {
                lastMainDriverTemp = mainDriverTemp;
                LogUtils.printI(AirCommandUtils.class.getName(), "lastMainDriverTemp=" + lastMainDriverTemp);
            }

            //只要主驾和副驾温度有一边是LO或HI, 那么主驾和副驾就都是LO或HI。会出现异常,需要保存副驾温度
            if (!TempSizeType.TEMP_LO.equals(size) && !TempSizeType.TEMP_HI.equals(size)) {
                lastCopilotTemp = size;
            }

            // can的栈号 前八位有效   D0  D1  D2 D3 D4 D5  D6  D7
            // 1E5       8          28  28
            // AA C6 66 41  52  CF
            //tag: 1e5
            String tag = airDeviceStatusValue.substring(0, 3);
            String right = airDeviceStatusValue.substring(8);
            command = COMMAND_START_TAG_AIR_20B + tag + lastMainDriverTemp + size + right;

        }
        LogUtils.printI(AirCommandUtils.class.getName(), "command=" + command);
        return command;
    }

    /**
     * @description: 主驾温度
     * @createDate: 2023/5/1
     */
    public static synchronized String buildMainDriverTempSize(String airDeviceStatusValue, String size) {
        LogUtils.printI(AirCommandUtils.class.getName(), "airDeviceStatusValue=" + airDeviceStatusValue + ", size=" + size);
        //D0  主驾温度

        String command = "";
        if (!TextUtils.isEmpty(airDeviceStatusValue) && airDeviceStatusValue.length() > 14) {

            String mainDriverTemp = airDeviceStatusValue.substring(4, 6);
            String copilotTemp = airDeviceStatusValue.substring(6, 8);
            LogUtils.printI(AirCommandUtils.class.getName(), "mainDriverTemp=" + mainDriverTemp + ", copilotTemp=" + copilotTemp);


            //只要主驾和副驾温度有一边是LO或HI, 那么主驾和副驾就都是LO或HI。会出现异常,需要保存副驾温度
            if (!TempSizeType.TEMP_LO.equals(size) && !TempSizeType.TEMP_HI.equals(size)) {
                lastMainDriverTemp = size;
            }

            if (!TempSizeType.TEMP_LO.equals(copilotTemp) && !TempSizeType.TEMP_HI.equals(copilotTemp)) {
                lastCopilotTemp = copilotTemp;
                LogUtils.printI(AirCommandUtils.class.getName(), "lastCopilotTemp=" + lastCopilotTemp);
            }

            //1E582828AAC6664152CF
            // can的栈号 前八位有效   D0  D1  D2 D3 D4 D5  D6  D7
            // 1E5       8          28  28  AA C6 66 41  52  CF
            //tag: 1e5
            String tag = airDeviceStatusValue.substring(0, 3);
            String right = airDeviceStatusValue.substring(8);
            command = COMMAND_START_TAG_AIR_20B + tag + size + lastCopilotTemp + right;
        }
        LogUtils.printI(AirCommandUtils.class.getName(), "command=" + command);
        return command;
    }


    /**
     * @description: 气流模式(气候模式)
     * @createDate: 2023/5/2
     */
    public static String buildAirflowMode(String airDeviceStatusValue, String mode) {
        LogUtils.printI(AirCommandUtils.class.getName(), "airDeviceStatusValue=" + airDeviceStatusValue + ", mode=" + mode);

        String command = "";
        if (!TextUtils.isEmpty(airDeviceStatusValue) && airDeviceStatusValue.length() > 14) {
            //1E582828AAC6664152CF
            // can的栈号 前八位有效   D0  D1  D2 D3 D4 D5  D6  D7
            // 1E5       8          28  28  AA C6 66 41  52  CF
            //tag: 1e5
            String tag = airDeviceStatusValue.substring(0, 3);
            String left = airDeviceStatusValue.substring(4, 16);
            String right = airDeviceStatusValue.substring(18);
            command = COMMAND_START_TAG_AIR_1E5 + tag + left + mode + right;
        }

        LogUtils.printI(AirCommandUtils.class.getName(), "command=" + command);
        return command;
    }
}
