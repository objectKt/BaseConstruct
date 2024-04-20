package com.backaudio.android.driver.can;

import java.io.ByteArrayOutputStream;

import android.text.TextUtils;
import android.util.Log;

import com.backaudio.android.driver.Utils;
import com.backaudio.android.driver.beans.ClockInfo;
import com.backaudio.android.driver.manage.PubSysConst;
import com.backaudio.android.driver.manage.PublicEnum;
import com.backaudio.android.driver.manage.PublicEnum.EAUXOperate;
import com.backaudio.android.driver.manage.PublicEnum.EAirConCANPanelKey;
import com.backaudio.android.driver.manage.PublicEnum.EAirConPanelKey;
import com.backaudio.android.driver.manage.PublicEnum.EAlphardKey;
import com.backaudio.android.driver.manage.PublicEnum.ECarOtherSet;
import com.backaudio.android.driver.manage.PublicEnum.EControlSource;
import com.backaudio.android.driver.manage.PublicEnum.EModeInfo;
import com.backaudio.android.driver.manage.PublicEnum.EPorschePanelKey;
import com.backaudio.android.driver.manage.PublicEnum.ESControlKey;

import com.backaudio.android.driver.mcu.InitMCUSerialPort;
import com.drake.logcat.LogCat;

import lib.dc.utils.UtilByteArray;
import lib.dc.utils.UtilByteArrayJava;

/**
 * 发送CAN数据
 *
 * @author liuguojia
 */
public class SendCanData {

    private static SendCanData instance = null;

    private static final String TAG = SendCanData.class.getSimpleName();

    public static SendCanData getInstance() {
        if (instance == null) {
            synchronized (SendCanData.class) {
                if (instance == null) {
                    instance = new SendCanData();
                }
            }
        }
        return instance;
    }


    /**
     * CanBox 数据发送
     **/
    public void connectOrDisConnectCanBox(boolean iConnect) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0x81, 0x01, 0x00, 0x00};
        if (iConnect) {
            buffer[3] = 0x01;
        } else {
            buffer[3] = 0x00;
        }
        buffer[4] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	connectOrDisConnectCanbox:	" + iConnect);
    }

    /**
     * 请求控制信息
     *
     * @param source
     */
    public void getControlInfo(boolean isOriginal, EControlSource source) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0x82, 0x09, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        if (isOriginal) {
            buffer[3] = 0x02;// 原车
        } else {
            buffer[3] = 0x01;// 加装
        }
        buffer[4] = EControlSource.RADIO.getCode();
        buffer[12] = UtilByteArrayJava.checkCanSum(buffer);
        // InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	getControlInfo:isOriginal = " + isOriginal
                + "	EControlSource--" + source.toString());
    }

    /**
     * 请求控制信息
     */
    public void setMZDControlInfo(boolean isOriginal) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0x82, 0x09, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        if (isOriginal) {
            buffer[3] = 0x02;// 原车
        } else {
            buffer[3] = 0x01;// 加装
        }
        buffer[12] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	setMZDControlInfo = " + isOriginal
        );
    }

    /**
     * 请求模块信息
     */
    public void getModeInfo(EModeInfo mode) {
        Log.e(TAG, "................................................1");
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xFF, 0x01, 0x00, 0x00};
        buffer[3] = mode.getCode();
        buffer[4] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	getModeInfo:EModeInfo--" + mode.toString());
    }

    /**
     * 请求激活AUX
     */
    public void requestAUXOperate(EAUXOperate eOperate) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xA7, 0x04, 0x00,
                0x00, 0x00, 0x00, 0x00};
        buffer[3] = (byte) 0x59;
        buffer[4] = (byte) 0x54;
        buffer[5] = eOperate.getCode();
        buffer[7] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	requestAUXOperate --" + eOperate);
    }

    /**
     * @param enabled 是否有效
     * @param columns 列
     * @param rows    行
     */
    public void setCanExtraAudio(boolean enabled, int columns, int rows) {
        byte[] buffer;
        buffer = new byte[]{(byte) 0x2E, (byte) 0xB1, 0x04, 0x00, 0x00, 0x00,
                0x00, 0x00};

        if (enabled) {
            buffer[3] = 0x01;
            buffer[4] = (byte) columns;
            buffer[5] = (byte) rows;
        }
        buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>ZMYT	setCanExtraAudio-- enabled = " + enabled
                + ",columns = " + columns + ",rows = " + rows);
    }

    public void forceRequestBTAudio3(
            boolean hasNavi, int row) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xA6, 0x03, 0x01, 0x00,
                0x00, 0x00};
        buffer[4] = (byte) (hasNavi ? 2 : 1);
        buffer[5] = (byte) row;
        buffer[6] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>LY	forceRequestAUX,hasNavi = " + hasNavi + ",row = " + row);
    }


    /**
     * 奔驰S控制
     *
     * @param type
     */
    public void setSControlKey(ESControlKey type) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xA8, 0x02, 0x00,
                0x00, 0x00};
        buffer[3] = (byte) type.getCode();
        buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        LogCat.d(TAG + "setSControlKey---type=" + type.name());
    }

    /**
     * 其他设置
     *
     * @param type
     */
    public void setCarOtherSet(ECarOtherSet type) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xA9, 0x02, 0x00,
                0x00, 0x00};
        buffer[3] = (byte) type.getCode();
        buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);

        LogCat.d(TAG + "setCarOtherSet---type=" + type);
    }

    /**
     * 方控学习
     */
    public void setKeyBoardLearn(int type) {
        //if (CommonConfig.isSmartpie) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0x84, 0x02, 0x00,
                0x00, 0x00};
        buffer[3] = (byte) type;
        buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	setKeyBoardSLearn--type  = " + type);
        //}
    }

    /**
     * 设置方控AD类型
     */
    public void setWheelControlType(int type) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0x87, 0x01, 0x00,
                0x00};

        buffer[3] = (byte) type;
        buffer[4] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	setWheelControlType-- ");
    }

    /**
     * 设置兰酷时钟
     *
     * @param clockInfo
     */
    public void setClockType(ClockInfo clockInfo) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xA0, 0x08, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};

        buffer[3] = (byte) (clockInfo.isAcc ? 1 : 0);
        buffer[4] = (byte) (clockInfo.isLamplet ? 1 : 0);
        buffer[5] = (byte) clockInfo.backlight;
        if (clockInfo.type == 1) {
            buffer[6] = (byte) 0x80;
        } else if (clockInfo.type == 2) {
            buffer[6] = (byte) 0x81;
        } else if (clockInfo.type == 4) {
            buffer[6] = (byte) 0x83;
        } else {
            buffer[6] = (byte) 0xff;
        }
        buffer[7] = (byte) clockInfo.hour;
        buffer[8] = (byte) clockInfo.minute;
        buffer[9] = (byte) clockInfo.compassMSB;
        buffer[10] = (byte) clockInfo.compassLSB;
        buffer[11] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	setClockType-- ");
    }

    public void setLYClockType(ClockInfo clockInfo) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xB0, 0x05, 0x00,
                0x00, 0x00, 0x00, 0x00, 0x00};

        buffer[3] = (byte) PubSysConst.LANGUAGE;
        buffer[4] = (byte) clockInfo.backlight;
        if (clockInfo.type == 1) {
            buffer[5] = (byte) 0x80;
        } else if (clockInfo.type == 2) {
            buffer[5] = (byte) 0x81;
        } else if (clockInfo.type == 3) {
            buffer[5] = (byte) 0x82;
        } else if (clockInfo.type == 4) {
            buffer[5] = (byte) 0x83;
        } else {
            buffer[5] = (byte) 0xff;
        }
        buffer[6] = (byte) clockInfo.compassMSB;
        buffer[7] = (byte) clockInfo.compassLSB;
        buffer[8] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);

        Log.e(TAG, ">>>	setLYClockType-- ");
        buffer = new byte[]{(byte) 0x2E, (byte) 0xB1, 0x05, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        buffer[3] = (byte) (clockInfo.year > 2020 ? (clockInfo.year - 2000) : 21);
        buffer[4] = (byte) clockInfo.month;
        buffer[5] = (byte) clockInfo.day;
        buffer[6] = (byte) clockInfo.hour;
        buffer[7] = (byte) clockInfo.minute;
        buffer[8] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	setLYClockTime-- ");
    }


    public void forcePress() {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xA6, 0x02, 0x01, 0x00, 0x00};
        buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	forcePress-- ");
    }

    /**
     * 往can空调发送操作
     */
    public void setS223AirDataToCan(PublicEnum.S223AirConPanelKey eAirConPanelKey) {
        try {
            byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xAA, 0x02, 0x00, 0x00, 0x00};
            buffer[4] = (byte) eAirConPanelKey.getCode();
            buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
            InitMCUSerialPort.getInstance().writeCanbox(buffer);
            LogCat.i("发送空调信息 " + eAirConPanelKey.name() + ", data=" + Utils.byteArrayToHexString(buffer));
        } catch (Exception e) {
            LogCat.e("setS223AirDataToCan " + e.getMessage());
        }
    }


    /**
     * 设置大旋钮按键
     */
    public void setKnobKey(PublicEnum.KnobKey knobKey) {

        try {
            byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xAA, 0x02, 0x01, 0x00, 0x00};
            buffer[4] = (byte) knobKey.getCode();
            buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
            InitMCUSerialPort.getInstance().writeCanbox(buffer);
            LogCat.d(TAG + "setKnobKey--" + knobKey.name());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 往can空调发送操作
     */
    public void setAirDataToCan(EAirConPanelKey eAirConPanelKey) {
        EAirConCANPanelKey eAirConCANPanelKey = EAirConCANPanelKey.NONE;
        if (eAirConPanelKey == EAirConPanelKey.KEY_A_FRONT) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_FRONT;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_REAR) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_REAR;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_AUTO) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_AUTO;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_LEFT_TEMP_ADD) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_LEFT_TEMP_ADD;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_LEFT_TEMP_DEC) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_LEFT_TEMP_DEC;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_RIGHT_TEMP_ADD) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_RIGHT_TEMP_ADD;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_RIGHT_TEMP_DEC) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_RIGHT_TEMP_DEC;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_ON) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_ON;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_SPEED_ADD) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_SPEED_ADD;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_SPEED_DEC) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_SPEED_DEC;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_MODE) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_MODE;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_AC) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_AC;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_DUAL) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_DUAL;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_EXTERNAL_CIRCLE) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_EXTERNAL_CIRCLE;
        } else if (eAirConPanelKey == EAirConPanelKey.KEY_A_INTERNAL_CIRCLE) {
            eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_INTERNAL_CIRCLE;
        }

        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0x83, 0x02, 0x01,
                0x00, 0x00};
        buffer[3] = (byte) eAirConCANPanelKey.getCode();
        buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	setAirDataToCan-- eAirConCANPanelKey  = " + eAirConCANPanelKey);
    }

    /**
     * 往can空调发送旋钮操作
     */
    public void setAirKnobDataToCan(int type, boolean isLeft, int step) {
        EAirConCANPanelKey eAirConCANPanelKey = EAirConCANPanelKey.NONE;
        Log.e(TAG, ">>>	setAirKnobDataToCan-- step  = " + step);
        for (int i = 0; i < step; i++) {
            if (type == 0x03 && isLeft) {
                eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_LEFT_TEMP_DEC;
            } else if (type == 0x03 && !isLeft) {
                eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_LEFT_TEMP_ADD;
            } else if (type == 0x04 && !isLeft) {
                eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_RIGHT_TEMP_ADD;
            } else if (type == 0x04 && isLeft) {
                eAirConCANPanelKey = EAirConCANPanelKey.KEY_A_RIGHT_TEMP_DEC;
            }
            byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0x83, 0x02, 0x01,
                    0x00, 0x00};
            buffer[3] = (byte) eAirConCANPanelKey.getCode();
            buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
            InitMCUSerialPort.getInstance().writeCanbox(buffer);
            Log.e(TAG, ">>>	setAirKnobDataToCan-- eAirConCANPanelKey  = " + eAirConCANPanelKey);
        }
    }

    /**
     * 埃尔法模拟按键
     */
    public void setAlphardKeyToCan(EAlphardKey eAlphardKey) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0x83, 0x02, 0x01,
                0x00, 0x00};
        buffer[3] = (byte) eAlphardKey.getCode();
        buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	setAlphardKeyToCan-- eAlphardKey  = " + eAlphardKey);
    }

    /**
     * 保时捷按键下发
     *
     * @param type
     */
    public void setPorscheKey(EPorschePanelKey type) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0x83, 0x02, 0x00,
                0x00, 0x00};
        buffer[3] = (byte) type.getCode();
        buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	setPorscheKey--type  = " + type);
    }

    // 请求升级canbox
    public void requestUpgradeCanbox(int total_packet) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xE1, 0x02, 0x00,
                0x00, 0x00};
        String fmString = Integer.toHexString(total_packet);
        String h = "";
        String l = fmString;
        if (fmString.length() > 2) {
            h = fmString.substring(0, fmString.length() - 2);
            l = fmString.substring(fmString.length() - 2);
        }
        buffer[3] = TextUtils.isEmpty(h) ? 0x00 : (byte) Integer
                .parseInt(h, 16);
        buffer[4] = (byte) Integer.parseInt(l, 16);
        buffer[5] = UtilByteArrayJava.checkCanSum(buffer);
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
        Log.e(TAG, ">>>	requestUpgradeCanbox");
    }

    // 发送升级信息，升级包数据，一段一段的发送canbox
    public void sendUpdateCanboxInfoByIndex(int index, byte[] date) {

        ByteArrayOutputStream tempBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xE3, (byte) 0x82,
                0x00, 0x00};
        if (index >= 0) {
            String fmString = Integer.toHexString(index);
            String h = "";
            String l = fmString;
            if (fmString.length() > 2) {
                h = fmString.substring(0, fmString.length() - 2);
                l = fmString.substring(fmString.length() - 2);
            }
            buffer[3] = TextUtils.isEmpty(h) ? 0x00 : (byte) Integer.parseInt(
                    h, 16);
            buffer[4] = (byte) Integer.parseInt(l, 16);
        } else {
            buffer[3] = (byte) 0x1F;
            buffer[4] = (byte) 0x1F;
        }
        tempBuffer.write(buffer, 0, 5);
        if (date.length < 128) {
            tempBuffer.write(date, 0, date.length);
            byte[] tmp = new byte[128 - date.length];
            tempBuffer.write(tmp, 0, tmp.length);
        } else {
            tempBuffer.write(date, 0, 128);
        }
        tempBuffer.write(new byte[]{0}, 0, 1);
        byte temp = UtilByteArrayJava.checkCanSum(tempBuffer.toByteArray());
        byte[] tempByte = tempBuffer.toByteArray();
        tempByte[tempByte.length - 1] = temp;
        // tempBuffer.write(new byte[]{temp}, 0, 1);
        InitMCUSerialPort.getInstance().writeCanbox(tempByte);
        LogCat.d(TAG + "sendUpdateCanboxInfoByIndex---index=" + index);
    }

    // 设置单色氛围灯
    public void setAtmosphereSingleLight(String linDataHex, Boolean allowSend) {
        byte[] canHead = new byte[]{(byte) 0x2E, (byte) 0xAB, (byte) 0x0A};
        byte[] linData = UtilByteArray.INSTANCE.fromHex(linDataHex);
        byte linCheck = UtilByteArrayJava.checkLinData(linData);
        byte[] canBytes = UtilByteArray.INSTANCE.combineMultiBytes(canHead, linData, new byte[]{linCheck});
        byte canCheck = UtilByteArrayJava.checkCanSum(canBytes);
        byte[] buffer = UtilByteArray.INSTANCE.merge(canBytes, new byte[]{canCheck});
        LogCat.i("linDataHexSender => CAN " + UtilByteArray.INSTANCE.toHeXLog(buffer) + " linCheck " + UtilByteArray.INSTANCE.toHexFromByte(linCheck));
        if (allowSend) {
            InitMCUSerialPort.getInstance().writeCanbox(buffer);
        }
    }

    // 设置主题氛围灯
    public void setAtmosphereThemeLight(byte data0, byte data1, byte data2, byte data3, byte data4, byte data6) {
        byte[] buffer = new byte[]{(byte) 0x2E, (byte) 0xAB, (byte) 0x0A, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        buffer[3] = 0x10;  // 氛围灯LIN 数据id
        buffer[4] = data0;
        buffer[5] = data1;
        buffer[6] = data2;
        buffer[7] = data3;
        buffer[8] = data4;
        buffer[9] = (byte) 0xFF;
        buffer[10] = data6;
        buffer[11] = (byte) 0x00;
        buffer[12] = 0x00; //氛围灯LIN数据校验码
        buffer[buffer.length - 1] = UtilByteArrayJava.checkCanSum(buffer);
        LogCat.i("氛围灯 " + UtilByteArray.INSTANCE.toHeXLog(buffer));
        InitMCUSerialPort.getInstance().writeCanbox(buffer);
    }
}
