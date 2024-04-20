package com.backaudio.android.driver.mcu;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

import android.text.TextUtils;
import android.util.Log;

import com.backaudio.android.driver.CommonConfig;
import com.backaudio.android.driver.Utils;
import com.backaudio.android.driver.beans.EQValue;
import com.backaudio.android.driver.manage.PubSysConst;
import com.backaudio.android.driver.manage.PublicEnum.ECarLayer;
import com.backaudio.android.driver.manage.PublicEnum.ELanguage;
import com.backaudio.android.driver.manage.PublicEnum.EMCUAudioType;
import com.backaudio.android.driver.manage.PublicEnum.EReverse360;
import com.backaudio.android.driver.manage.PublicEnum.EReverserMediaSet;
import com.backaudio.android.driver.type.ParkingType;
import com.drake.logcat.LogCat;


/**
 * 发送 MCU 数据
 *
 * @author liuguojia
 */
public class SendMCUData {

    private static final String TAG = "SendMCUData";

    private static SendMCUData instance = null;

    // 打开功放电源
    private boolean isAMPopen = true;
    public StringBuffer soundValue = new StringBuffer();

    public static SendMCUData getInstance() {
        if (instance == null) {
            synchronized (SendMCUData.class) {
                if (instance == null) {
                    instance = new SendMCUData();
                }
            }
        }
        return instance;
    }

    public void enterIntoAcc() {
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x02, 0x01, 0x01, 0x00};
        buffer[5] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
    }

    public void wakeupMcu() {
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x02, 0x01, 0x02, 0x00};
        buffer[5] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
    }

    public void getBackState() {
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x03, 0x01, 0x04, (byte) 0xFF, 0x00};
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
    }

    /**
     * 打开关闭媒体功放
     *
     * @param iOpen 是否打开 后装
     */
    public void openOrCloseMediaRelay(boolean iOpen) {
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x03, 0x01, 0x3F, 0x01, 0x00};
        buffer[5] = iOpen ? (byte) 0x01 : (byte) 0x02;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	openOrCloseMediaRelay:	" + iOpen);
    }

    /**
     * 打开关闭继电器
     *
     * @param iOpen 是否打开
     */
    public void openOrCloseRelay(boolean iOpen) {
        if (PubSysConst.forceAUX) {
            return;
        }
        if (CommonConfig.isTAXI) {
            return;
        }
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x03, 0x01, 0x40, 0x01, 0x00};
        buffer[5] = iOpen ? (byte) 0x01 : (byte) 0x02;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	openOrCloseRelay:	" + iOpen);
    }

    public void powerAudio(boolean isOpen) {
        if (isAMPopen == isOpen) {
            return;
        }
        isAMPopen = isOpen;
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x03, 0x01, 0x0E, 0x00, 0x00};
        buffer[5] = isOpen ? (byte) 0x08 : (byte) 0x07;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        enterOrOutMute(!isOpen);
        Log.e(TAG, ">>>	powerAudio isOpen = " + isOpen);
    }

    /**
     * 进入或者退出静音
     *
     * @param iEnter 进入静音
     */
    public void enterOrOutMute(boolean iEnter) {
        //引导码+长度+帧ID+Data+checksum
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x03, 0x01, 0x41, 0x01, 0x00};
        buffer[5] = iEnter ? (byte) 0x01 : (byte) 0x00;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	enterOrOutMute:	" + iEnter);
    }

    /**
     * 设置均衡器
     *
     * @param fLeft  前左 小喇叭
     * @param fRight 前右
     * @param rLeft  后左 AUX左声道
     * @param rRight 后右 （范围0-30）AUX右声道
     */
    public void setAllHornSoundValue(int main, int fLeft, int fRight, int rLeft, int rRight) {
		/*StringBuffer soundValue1 = new StringBuffer();
		soundValue1 = soundValue1.append(main).append(fLeft).append(fRight)
				.append(rLeft).append(rRight);
		boolean isupdate = TextUtils.equals(soundValue.toString(),
				soundValue1.toString());
		Log.e(MCU, ">>>	setAllHornSoundValue: isupdate = " + isupdate
				+ ",soundValue.toString() = " + soundValue.toString());
		if (!isupdate) {
			soundValue = soundValue1;
		} else {
			return; //MCU有做去重判断
		}*/
        if ((!CommonConfig.isHLA && !PubSysConst.isAMP && !CommonConfig.isTAXI) && rLeft > 0) {
            fLeft = 0;
        }
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x07, 0x01, 0x3C, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        buffer[5] = (byte) 0x25;
        buffer[6] = (byte) fLeft;
        buffer[7] = (byte) fRight;
        buffer[8] = (byte) rLeft;
        buffer[9] = (byte) rRight;
        buffer[10] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);

        LogCat.d(TAG +"setAllHornSoundValue---main = " + main + ",fLeft = "
                + fLeft + ",fRight = " + fRight + ",rLeft = " + rLeft
                + ",rRight = " + rRight + ",PubSysConst.isAMP = " + PubSysConst.isAMP);
    }

    /**
     * 设置视频输入音量
     *
     * @param fLeft  前左
     * @param fRight 前右
     * @param rLeft  后左
     * @param rRight 后右 （范围0-20）
     */
    public void setDVSoundValue(int main, int fLeft, int fRight, int rLeft, int rRight) {
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x07, 0x01, 0x63, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        buffer[5] = (byte) main;
        buffer[6] = (byte) fLeft;
        buffer[7] = (byte) fRight;
        buffer[8] = (byte) rLeft;
        buffer[9] = (byte) rRight;
        buffer[10] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setDVSoundValue:	DV main = " + main + ",fLeft = "
                + fLeft + ",fRight = " + fRight + ",rLeft = " + rLeft
                + ",rRight = " + rRight);
    }

    /**
     * 设置高中低音
     */
    public void setSoundTypeValue(int trebleValue, int midtonesValue, int bassValue) {
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x05, 0x01, 0x5C, 0x01, 0x00, 0x00, 0x00};
        buffer[5] = (byte) trebleValue;
        buffer[6] = (byte) midtonesValue;
        buffer[7] = (byte) bassValue;

        buffer[8] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setSoundTypeValue:	trebleValue = " + trebleValue
                + ",midtonesValue = " + midtonesValue + ",bassValue = "
                + bassValue);
    }

    /**
     * 获取均衡器
     */
    public void getAllHornSoundValue() {
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x03, 0x01, 0x3D, 0x01, 0x00};
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
    }

    /**
     * 关闭或者打开屏幕，这里跟原车无关
     */
    public static boolean isScreenClose = false;

    public void closeOrOpenScreen(boolean iClose) {
        isScreenClose = iClose;
        if (iClose) {
            InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x12, 0x05, (0x03 + 0x01 + 0x12 + 0x05)});
        } else {
            InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x12, 0x04, (0x03 + 0x01 + 0x12 + 0x04)});
        }
        Log.e(TAG, ">>>	closeOrOpenScreen:	" + iClose);
    }

    /**
     * 通知MCU，安卓做好准备进入待机模式
     */
    public void readyToStandby() {
        soundValue = new StringBuffer();
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x00, 0x03, (0x03 + 0x01 + 0x00 + 0x03)});
        Log.e(TAG, ">>>	readyToStandby");
    }

    /**
     * Android启动后，通知mcu已经做好准备
     */
    public void readyToWork() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x00, 0x04, (0x03 + 0x01 + 0x00 + 0x04)});
        Log.e(TAG, ">>>	readyToWork");
    }

    /**
     * 获取MCU版本日期
     */
    public void getMCUVersionDate() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x10, 0x01, (0x03 + 0x01 + 0x10 + 0x01)});
        Log.e(TAG, ">>>	getMCUVersionDate");
    }

    /**
     * 获取MCU版本号
     */
    public void getMCUVersionNumber() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x11, 0x01, (0x03 + 0x01 + 0x11 + 0x01)});
        Log.e(TAG, ">>>	getMCUVersionNumber");
    }

    /**
     * 获取泊车使能开关
     */
    public void getParking() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0x85, (byte) 0xFF, (byte) (0x03 + 0x01 + 0x85 + 0xFF)});
        Log.e(TAG, ">>>	getParking");
    }

    /**
     * 通知MCU，显示隐藏原车图像、行车记录仪
     */
    public void showCarLayer(ECarLayer eCarLayer) {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x4A, eCarLayer.getCode(), (byte) (0x03 + 0x01 + 0x4A + eCarLayer.getCode())});
        Log.e(TAG, ">>>	showCarLayer == " + eCarLayer);
    }

    /**
     * 通知MCU，收到倒车状态指令
     */
    public void sendRecordToMcu() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x02, 0x01, 0x54, (byte) (0x02 + 0x01 + 0x54)});
    }

    /**
     * 通知MCU触摸坐标
     */
    public void sendAndroidCoordinateToMcu(int x, int y, int status) {
        if (x < 0 || y < 0 || x > 800 || y > 480) {
            return;
        }
        byte data0 = (byte) (x >> 8);
        byte data1 = (byte) (x % 256);
        byte data2 = (byte) (y >> 8);
        byte data3 = (byte) (y % 256);
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{
                (byte) 0xAA,
                0x55,
                0x07,
                0x01,
                0x49,
                data0,
                data1,
                data2,
                data3,
                (byte) status,
                (byte) (0x07 + 0x01 + 0x49 + data0 + data1 + data2 + data3 + status)});

        Log.e(TAG, ">>>	sendCoordinateToMcu x1 = " + x + ",y1 = " + y
                + ",data0 = " + data0 + ",data1 = " + data1 + ",data2 = "
                + data2 + ",data3 = " + data3 + ",data4 = " + status);
    }

    /**
     * 获取背光亮度
     */
    public void getBrightnessValue() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x02, (byte) 0xFF, (0x03 + 0x01 + 0x02 + (byte) 0xFF)});
        Log.e(TAG, ">>>	getBrightnessValue");
    }


    /**
     * 设置 IO
     */
    public void setScreenType(int value) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0xDD, 0x00, 0x00};
        buffer[5] = (byte) value;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setScreenType value = " + value);
    }

    /**
     * 设置升级灯
     */
    public void setUpdateLight(int value) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0xDF, 0x00, 0x00};
        buffer[5] = (byte) value;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setUpdateLight value = " + value);
    }

    /**
     * 通知MCU,设置背光权重
     */
    public void setBrightnessWeightToMcu(int value) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x04, 0x00, 0x00};
        buffer[5] = (byte) value;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setBrightnessToMcu value = " + value);
    }

    /**
     * 通知MCU,设置背光亮度
     */
    public void setBrightnessToMcu(int value) {
        // 亮度值：  0x00-0x64
        LogCat.d(TAG +"setBrightnessToMcu---value=" + value + ", value=" + value);
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x02, 0x00, 0x00};
        buffer[5] = (byte) value;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
    }

    /**
     * 获取倒车背光亮度
     */
    public void getReverseBrightnessValue() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0x87, (byte) 0xFF, (0x03 + 0x01 + (byte) 0x87 + (byte) 0xFF)});
        Log.e(TAG, ">>>	getReverseBrightnessValue");
    }

    /**
     * 通知MCU,设置倒车背光亮度
     */
    public void setReverseBrightnessToMcu(int value) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0x87, 0x00, 0x00};
        buffer[5] = (byte) value;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setReverseBrightnessToMcu value = " + value);
    }

    /**
     * 通知MCU,设置香氛开关
     */
    public void setFragranceSwitch(boolean open) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x07, 0x01, (byte) 0x97, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        buffer[5] = (byte) (open ? 1 : 0);
        buffer[10] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setFragranceSwitch open = " + open);
    }

    /**
     * 通知MCU,设置360类型
     */
    public void setView360Type(int value) {
        byte[] buffer;
        if (PubSysConst.MODEL.contains("lexus")) {
            buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x54, 0x00, 0x00};
        } else {
            buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x7D, 0x00, 0x00};
        }
        buffer[5] = (byte) value;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setView360Type value = " + value);
    }

    /**
     * 通知MCU,更改途乐倒车设置
     */
    public void sendPatrolReverseSetToMcu(int parkingType) {
        InitMCUSerialPort
                .getInstance()
                .writeToMcu(
                        new byte[]{
                                (byte) 0xAA,
                                0x55,
                                0x04,
                                0x01,
                                0x5B,
                                0x01,
                                (byte) parkingType,
                                (byte) (0x04 + 0x01 + 0x5B + 0x01 + (byte) parkingType)});
        Log.e(TAG, ">>>	sendReverseSetToMcu type = " + parkingType);
    }

    /**
     * 通知MCU,更改倒车设置
     */
    public void sendReverseSetToMcu(ParkingType parkingType) {
        if (parkingType == ParkingType.ORIGINAL) {
            InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x04, 0x01, 0x5B, 0x01, 0x00, (byte) (0x04 + 0x01 + 0x5B + 0x01 + 0x00)});
        } else if (parkingType == ParkingType.AHD) {
            InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x04, 0x01, 0x5B, 0x01, 0x01, (byte) (0x04 + 0x01 + 0x5B + 0x01 + 0x01)});
        } else if (parkingType == ParkingType.CVBS) {
            InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x04, 0x01, 0x5B, 0x01, 0x02, (byte) (0x04 + 0x01 + 0x5B + 0x01 + 0x02)});
        }
    }

    /**
     * 获取倒车数据
     */
    public void getReverseDispalyToMcu() {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x51, 0x00, 0x00};
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	getReverseDispalyToMcu ");
    }

    /**
     * 通知MCU,开启倒车轨迹
     *
     * @param isTraOpen   轨迹显示
     * @param isRadarOpen 雷达显示
     */
    public void setReverseDispalyToMcu(boolean isTraOpen, boolean isRadarOpen) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x05, 0x01, 0x51, 0x01, 0x00, 0x00, 0x00};
        buffer[6] = (byte) (isTraOpen ? 1 : 0);
        buffer[7] = (byte) (isRadarOpen ? 1 : 0);
        buffer[8] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setReverseDispalyToMcu isTraOpen = " + isTraOpen + ",isRadarOpen = " + isRadarOpen);
    }

    /**
     * 通知MCU,更改倒车前视
     */
    public void sendReverseFrontViewSetToMcu(int type) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x42, 0x00, 0x00};
        buffer[5] = (byte) type;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	sendReverseFrontViewSetToMcu type = " + type);
    }

    /**
     * 通知MCU,更改倒车媒体设置
     */
    public void sendReverseMediaSetToMcu(int type) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x67, 0x00, 0x00};
        EReverserMediaSet source = EReverserMediaSet.NOMAL;
        for (EReverserMediaSet temp : EReverserMediaSet.values()) {
            if (temp.getCode() == type) {
                source = temp;
                break;
            }
        }
        buffer[5] = source.getCode();
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	sendReverseMediaSetToMcu type = " + source);
    }

    /**
     * 通知MCU,更改语言设置
     */
    public void sendLanguageSetToMcu(int type) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x6E, 0x00, 0x00};
        ELanguage source = ELanguage.US;
        for (ELanguage temp : ELanguage.values()) {
            if (temp.getCode() == type) {
                source = temp;
                break;
            }
        }
        buffer[5] = source.getCode();
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	sendLanguageSetToMcu type = " + source);
    }

    /**
     * 通知MCU,查询语言设置
     */
    public void getLanguageSetFromMcu() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x6E, (byte) 0xFF, (byte) (0x03 + 0x01 + 0x6E + 0xFF)});
        Log.e(TAG, ">>>	getLanguageSetFromMcu ");
    }

    /**
     * 从MCU获取小灯比例
     */
    public void getLampletFromMcu() {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x7B, (byte) 0xFF, 0x00};
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	getLampletFromMcu");
    }

    /**
     * 从MCU获取360类型
     */
    public void get360TypeFromMcu() {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x7D, (byte) 0xFF, 0x00};
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	get360TypeFromMcu ");
    }

    /**
     * 从MCU获取ACC延迟时间
     */
    public void getACCTimeFromMcu() {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0x80, (byte) 0xFF, 0x00};
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	getACCTimeFromMcu ");
    }

    /**
     * 通知MCU,更改原车小灯比例
     */
    public void setACCTimeToMcu(int value) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0x80, 0x00, 0x00};
        buffer[5] = (byte) value;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setACCTimeToMcu value = " + value);
    }

    /**
     * 通知MCU,更改原车显示设置
     */
    public void sendScreenStyleMcu(int type) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x77, 0x00, 0x00};
        buffer[5] = (byte) type;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	sendScreenStyleMcu type = " + type);
    }

    /**
     * 通知MCU,更改原车显示按键设置
     */
    public void sendScreenKeyStyleMcu(int type) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0x89, 0x00, 0x00};
        buffer[5] = (byte) type;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	sendScreenKeyStyleMcu type = " + type);
    }

    /**
     * 通知MCU,更改原车小灯比例
     */
    public void setLampletToMcu(int value) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x7B, 0x00, 0x00};
        buffer[5] = (byte) value;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setLampletToMcu type = " + value);
    }

    /**
     * 通知MCU,查询原车显示设置
     */
    public void getScreenStyleFromMcu() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x77, (byte) 0xFF, (byte) (0x03 + 0x01 + 0x77 + 0xFF)});
        Log.e(TAG, ">>>	getScreenStyleFromMcu ");
    }

    /**
     * 通知MCU,查询倒车设置
     */
    public void getReverseSetFromMcu() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x5B, 0x00, (byte) (0x03 + 0x01 + 0x5B + 0x00)});
        Log.e(TAG, ">>>	getReverseSetFromMcu ");
    }

    /**
     * 通知MCU,查询倒车媒体设置
     */
    public void getReverseMediaSetFromMcu() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x67, (byte) 0xFF, (byte) (0x03 + 0x01 + 0x67 + 0xFF)});
        Log.e(TAG, ">>>	getReverseMediaSetFromMcu ");
    }

    /**
     * 打开360全景
     */
    public void setReverse360(EReverse360 reverse360) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0x84, 0x00, 0x00};
        buffer[5] = reverse360.getCode();
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setReverse360 reverse360 = " + reverse360);
    }

    public void getDSPValues() {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0x93, 0x01, 0x00};
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	getDSPValues");
    }

    public void setDSPValues(int[] values) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x12, 0x01,
                (byte) 0x92, 0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
                0x00, 0x00, 0x00, 0x00,
                0x00};
        for (int i = 0; i < values.length; i++) {
            buffer[5 + i] = (byte) values[i];
        }
        buffer[21] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setDSPValues values = " + Arrays.toString(values));
    }

    /**
     * 获取奔驰原车分辨率
     */
    public void getCurrentVolumeValue() {
        byte[] buffer = {(byte) 0xAA, 0x55, 0x03, 0x01, 0x6C, (byte) 0x01, 0x00};
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        LogCat.d(TAG + "getCurrentVolumeValue----");
    }

    /**
     * 设置8836
     */
    public void setMCU8836(String index, String value) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x05, 0x01, (byte) 0xD8, 0x00, 0x00, 0x00, 0x00};
        String h = "";
        String l = index;
        if (index.length() > 2) {
            h = index.substring(0, index.length() - 2);
            l = index.substring(index.length() - 2);
        }
        buffer[5] = TextUtils.isEmpty(h) ? 0x00 : (byte) Integer.parseInt(h, 16);
        buffer[6] = (byte) Integer.parseInt(l, 16);
        buffer[7] = (byte) Integer.parseInt(value, 16);
        buffer[8] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setMCU8836 index = " + index);
    }

    /**
     * 获取8836
     */
    public void getMCU8836(String fmString) {
        byte hbit, lbit;
        String h = "";
        String l = fmString;
        if (fmString.length() > 2) {
            h = fmString.substring(0, fmString.length() - 2);
            l = fmString.substring(fmString.length() - 2);
        }
        hbit = TextUtils.isEmpty(h) ? 0x00 : (byte) Integer.parseInt(h, 16);
        lbit = (byte) Integer.parseInt(l, 16);
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x04, 0x01, (byte) 0xD9, hbit, lbit, (byte) (0x04 + 0x01 + 0xD9 + hbit + lbit)});
        Log.e(TAG, ">>>	getMCU8836 ");
    }

    /**
     * 设置画像，0XFE：测试图 0x00：原车画像
     */
    public void setBenzSize(int benzSize) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x69, 0x00, 0x00};
        buffer[5] = (byte) benzSize;
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
    }

    /**
     * 获取奔驰型号
     */
    public void getBenzType() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x64, 0x00, (byte) (0x03 + 0x01 + 0x64 + 0x00)});
        Log.e(TAG, ">>>	getBenzType ");
    }

    public void getScreenDPI() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0xDB, 0x01, (byte) (0x03 + 0x01 + 0xDB + 0x01)});
        Log.e(TAG, ">>>	getScreenDPI ");
    }

    /**
     * 获取奔驰原车分辨率
     */
    public void getBenzSize() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x69, (byte) 0xFF, (byte) (0x03 + 0x01 + 0x69 + 0xFF)});
        Log.e(TAG, ">>>	getBenzSize ");
    }

    /**
     * 通知MCU,设置原车功放
     */
    public void sendSetAMP(boolean isAMP) {
        PubSysConst.isAMP = isAMP;
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x79, 0x00, 0x00};
        buffer[5] = (byte) (isAMP ? 1 : 0);
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	sendSetAMP isAMP = " + isAMP);
    }

    /**
     * 通知MCU,设置DV通道
     */
    public void setDVAudio(EMCUAudioType eAudioType) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x05, 0x00, 0x00};
        buffer[5] = eAudioType.getCode();
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setDVAudio eAudioType = " + eAudioType);
    }

    /**
     * 设置泊车使能开关
     */
    public void setParking(boolean hasParking) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, (byte) 0x85, 0x00, 0x00};
        buffer[5] = (byte) (hasParking ? 1 : 0);
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setParking hasParking = " + hasParking);
    }

    /**
     * 从MCU读取配置
     */
    public void getStoreDataFromMcu() {
        InitMCUSerialPort.getInstance().writeToMcu(new byte[]{(byte) 0xAA, 0x55, 0x03, 0x01, 0x60, 0x01, (byte) (0x03 + 0x01 + 0x60 + 0x01)});
        Log.e(TAG, ">>>	getStoreDataFromMcu  ");
    }

    /**
     * 发送存储数据
     */
    public void sendStoreDataToMcu(byte[] date) {
        ByteArrayOutputStream tempBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, (byte) 0x82, 0x01, 0x61};
        tempBuffer.write(buffer, 0, 5);
        if (date.length < 128) {
            tempBuffer.write(date, 0, date.length);
            byte[] tmp = new byte[128 - date.length];
            tempBuffer.write(tmp, 0, tmp.length);
        } else {
            tempBuffer.write(date, 0, 128);
        }
        tempBuffer.write(new byte[]{0}, 0, 1);
        byte temp = getCheckMcuByte(tempBuffer.toByteArray());
        byte[] tempByte = tempBuffer.toByteArray();
        tempByte[tempByte.length - 1] = temp;
        InitMCUSerialPort.getInstance().writeToMcu(tempByte);
        Log.e(TAG, ">>>	sendStoreDataToMcu data = " + Utils.byteArrayToHexString(tempByte));
    }

    /**
     * 设置音效
     */
    public void setEffect(int[] data) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x09, 0x01, 0x71, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        for (int i = 0; i < data.length; i++) {
            buffer[5 + i] = (byte) data[i];
        }
        buffer[12] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setEffect data = " + Arrays.toString(data));
    }

    /**
     * 设置音效
     */
    public void setEffect(EQValue eqValue) {
        byte[] buffer = new byte[]{(byte) 0xAA, 0x55, 0x09, 0x01, 0x74, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00, 0x00};
        for (int i = 0; i < eqValue.getdata().length; i++) {
            buffer[5 + i] = (byte) eqValue.getdata()[i];
        }
        buffer[12] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	setEffect eqValue = " + eqValue);
    }

    // 请求升级mcu
    public void requestUpgradeMcu(byte[] updateInfo) {
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x05, (byte) 0xE1, 0x00, 0x00, 0x00, 0x00, 0x00};
        buffer[4] = updateInfo[0];
        buffer[5] = updateInfo[1];
        buffer[6] = updateInfo[2];
        buffer[7] = updateInfo[3];
        buffer[8] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        Log.e(TAG, ">>>	requestUpgradeMCU");
    }

    // 发送升级头信息，升级包长度mcu
    public void sendUpgradeMcuHeaderInfo(int length) {
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, 0x03, (byte) 0xE5, 0x00, 0x00, 0x00};
        String fmString = Integer.toHexString(length);
        String h = "";
        String l = fmString;
        if (fmString.length() > 2) {
            h = fmString.substring(0, fmString.length() - 2);
            l = fmString.substring(fmString.length() - 2);
        }
        buffer[4] = TextUtils.isEmpty(h) ? 0x00 : (byte) Integer
                .parseInt(h, 16);
        buffer[5] = (byte) Integer.parseInt(l, 16);
        buffer[6] = getCheckMcuByte(buffer);
        InitMCUSerialPort.getInstance().writeToMcu(buffer);
        LogCat.d(SendMCUData.class.getSimpleName(), "sendUpgradeMcuHeaderInfo---data=" + Utils.byteArrayToHexString(buffer));
    }

    // 发送升级信息，升级包数据，一段一段的发送mcu
    public void sendUpdateMcuInfoByIndex(int index, byte[] date) {
        ByteArrayOutputStream tempBuffer = new ByteArrayOutputStream();
        byte[] buffer = new byte[]{(byte) 0xAA, (byte) 0x55, (byte) 0x83, (byte) 0xE3, 0x00, 0x00};
        if (index >= 0) {
            String fmString = Integer.toHexString(index);
            String h = "";
            String l = fmString;
            if (fmString.length() > 2) {
                h = fmString.substring(0, fmString.length() - 2);
                l = fmString.substring(fmString.length() - 2);
            }
            buffer[4] = TextUtils.isEmpty(h) ? 0x00 : (byte) Integer.parseInt(
                    h, 16);
            buffer[5] = (byte) Integer.parseInt(l, 16);
        } else {
            buffer[4] = (byte) 0x1F;
            buffer[5] = (byte) 0x1F;
        }
        tempBuffer.write(buffer, 0, 6);
        if (date.length < 128) {
            tempBuffer.write(date, 0, date.length);
            byte[] tmp = new byte[128 - date.length];
            tempBuffer.write(tmp, 0, tmp.length);
        } else {
            tempBuffer.write(date, 0, 128);
        }
        tempBuffer.write(new byte[]{0}, 0, 1);
        byte temp = getCheckMcuByte(tempBuffer.toByteArray());
        byte[] tempByte = tempBuffer.toByteArray();
        tempByte[tempByte.length - 1] = temp;
        InitMCUSerialPort.getInstance().writeToMcu(tempByte);
        Log.e(TAG, ">>>	sendUpdateMcuInfoByIndex;	index-- " + index);
    }

    private byte getCheckMcuByte(byte[] buffer) {
        byte check = buffer[2];
        for (int i = 3; i < buffer.length - 1; i++) {
            check += buffer[i];
        }
        return check;
    }

    private byte[] wrapLamp(byte[] buffer) {
        if (buffer == null) {
            return null;
        }
        byte[] mcuProtocol = new byte[buffer.length + 6];
        mcuProtocol[0] = (byte) 0xAA;
        mcuProtocol[1] = 0x55;
        mcuProtocol[2] = (byte) (buffer.length + 2);
        mcuProtocol[3] = 0x01;
        mcuProtocol[4] = (byte) 0x8E;
        byte check = (byte) (mcuProtocol[2] + mcuProtocol[3] + mcuProtocol[4]);
        for (int i = 0; i < buffer.length; i++) {
            mcuProtocol[5 + i] = buffer[i];
            check = (byte) (check + buffer[i]);
        }
        mcuProtocol[buffer.length + 5] = check;
        return mcuProtocol;
    }

    public synchronized void writeLamp(byte[] buff) {
        byte[] canboxProtocol = wrapLamp(buff);
        if (canboxProtocol != null) {
            Log.d("driverlog", "canboxProtocol write = " + Utils.byteArrayToHexString(canboxProtocol));
            InitMCUSerialPort.getInstance().writeToMcu(canboxProtocol);
        }
    }
}
