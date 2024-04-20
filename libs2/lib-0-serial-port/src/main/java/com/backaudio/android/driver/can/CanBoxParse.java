package com.backaudio.android.driver.can;

import android.os.Handler;
import android.util.Log;

import lib.dc.enums.WindDireType;

import com.backaudio.android.driver.CommonConfig;
import com.backaudio.android.driver.Utils;
import com.backaudio.android.driver.beans.BackupBean;
import com.backaudio.android.driver.beans.CarBaseInfo;
import com.backaudio.android.driver.beans.CarRadarInfo;
import com.backaudio.android.driver.beans.CarRunInfo;
import com.backaudio.android.driver.beans.SCarPanelInfo;
import com.backaudio.android.driver.manage.PubSysConst;
import com.backaudio.android.driver.manage.PublicEnum.EAUXStutas;
import com.backaudio.android.driver.manage.PublicEnum.EBtnStateEnum;
import com.backaudio.android.driver.manage.PublicEnum.ECanboxUpgrade;
import com.backaudio.android.driver.manage.PublicEnum.EControlSource;
import com.backaudio.android.driver.manage.PublicEnum.EIdriverEnum;
import com.backaudio.android.driver.mcu.MCUParse;
import com.backaudio.android.driver.beans.ACTable;
import com.dc.android.launcher.util.HUtils;
import com.drake.logcat.LogCat;

import java.io.ByteArrayOutputStream;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.util.Arrays;

/**
 * CAN解析
 *
 * @author liuguojia
 */
public class CanBoxParse {

    private static final String TAG = CanBoxParse.class.getSimpleName();

    ByteArrayOutputStream canProtocolBuffer = new ByteArrayOutputStream();
    int canProtocolLength = 0;
    int canCurrentProtocolLength = 0;
    private static CanBoxParse instance;

    public static CanBoxParse getInstance() {
        if (instance == null) {
            synchronized (CanBoxParse.class) {
                if (instance == null) {
                    instance = new CanBoxParse();
                }
            }
        }
        return instance;
    }

    public void setEventHandler(Handler handler) {

    }

    private CanBoxParse() {

    }

    private synchronized void analyzeCanBox(byte[] buf) {
        for (int i = 5; i < buf.length - 1; i++) {// 去除aa55xx0150
            try {
                byte b = buf[i];
                switch (canCurrentProtocolLength) {
                    case 0:
                        // 还没有数据
                        if (b == (byte) 0x2E || b == (byte) 0x2D) {
                            canProtocolBuffer.write(new byte[]{b}, 0, 1);// 协议第一个字节
                            canCurrentProtocolLength++;
                        } else {
                            String canboxErrString = "CanBox: require 0x2E or 0xFF but " + Integer.toHexString(b) + "\n";
                            Log.e(TAG, canboxErrString);
                        }
                        break;
                    case 1:
                        canProtocolBuffer.write(new byte[]{b}, 0, 1);
                        canCurrentProtocolLength++;
                        break;
                    case 2:
                        // 收到第三个字节，为长度位
                        canProtocolLength = b;
                        if (canProtocolLength < 0) {
                            canProtocolLength += 256;
                        }
                        canProtocolLength += 4;// 协议头1字节，帧协议命令码1字节，长度1字节，校验1字节
                        canProtocolBuffer.write(new byte[]{b}, 0, 1);
                        canCurrentProtocolLength++;
                        break;
                    default:
                        // 已经获取完整协议头，长度
                        if (canCurrentProtocolLength < canProtocolLength) {
                            canProtocolBuffer.write(new byte[]{b}, 0, 1);
                            canCurrentProtocolLength++;
                            if (canCurrentProtocolLength == canProtocolLength) {
                                canProtocol(canProtocolBuffer.toByteArray());
                                canProtocolBuffer.reset();
                                canCurrentProtocolLength = 0;
                                canProtocolLength = 0;
                            }
                        } else {
                            if (canCurrentProtocolLength >= canProtocolLength) {
                                String err = "CanBox: shit:" + Integer.toHexString(b) + "\n";
                                Log.e(TAG, err);
                                LogCat.d(TAG + err);
                            }
                            canProtocolBuffer.reset();
                            canCurrentProtocolLength = 0;
                            canProtocolLength = 0;
                        }
                        break;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 放入读取过来的原始数据流
     */
    public void pushCanBox(byte[] buffer) {
        synchronized (MCUParse.class) {
            analyzeCanBox(buffer);
        }
    }

    /**
     * 生成了一条完整的 CanBox 协议
     */
    private void canProtocol(byte[] buffer) {
        if (buffer == null || buffer.length < 5) {
            LogCat.e("CanBoxParse invalid CanBox buffer, drop");
            return;
        }
        if (!checkCanbox(buffer)) {
            LogCat.e("Error checkCanBox(buffer)");
            return;
        }

        if (PubSysConst.isDecoder2 && buffer[1] == (byte) 0x01) {
            return;
        }
        switch (buffer[1]) {
            case (byte) 0x01:
                // 车辆基本信息
                LogCat.i(TAG + " analyzeCarBasePro " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                analyzeCarBasePro(buffer);
                break;
            case (byte) 0x02:
                // IDriver
                analyzeIdriverPro(buffer);
                break;
            case (byte) 0x03:
                // 空调信息
                if (buffer[2] == 0x07) {
                    LogCat.i(TAG + " analyzeACTable " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                    analyzeACTable(buffer);
                } else {
                    LogCat.i(TAG + " buffer[1] =0x03 buffer[2] !=0x07" + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                }
                break;
            case (byte) 0x04:// 后雷达数据
            case (byte) 0x05:// 前雷达数据
                LogCat.i(TAG + " analyzeRadarInfo " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                analyzeRadarInfo(buffer);
                break;
            case (byte) 0x06:// 车门基本信息
                analyzeCarBasePro1(buffer);
                break;
            case (byte) 0x07:// 速度
                analyzeCarRunningInfoPro1(buffer);
                break;
            case (byte) 0x08:// 时间
                analyzeTimePro(buffer);
                break;
            case (byte) 0x0A:// 方向盘转角
                LogCat.i(TAG + " analyzeSteelWheelAngle " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                analyzeSteelWheelAngle(buffer);
                break;
            case (byte) 0x0C:// 触摸板按键
                analyzeCarTouchBroad(buffer);
                break;
            case (byte) 0x12:// 原车界面（原车cd上其他按钮）
                LogCat.i(TAG + " analyzeOriginalCarPro " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                analyzeOriginalCarPro(buffer);
                break;
            case (byte) 0x2C:// 原车主机 AUX 激活控制状态反馈
                analyzeAUXStatus(buffer);
                break;
            case (byte) 0x35:// 车辆运行信息
                LogCat.i(TAG + " analyzeCarRunningInfoPro " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                analyzeCarRunningInfoPro(buffer);
                break;
            case (byte) 0x36:// 车辆动态信息
                analyzeCarRunningInfoPro2(buffer);
                break;
            case (byte) 0x37:// S级面板车身信息
                analyzeSCarPanelInfo(buffer);
                break;
            case (byte) 0x40:// 倒车雷达信息
                LogCat.i(TAG + " analyzeBackup " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                analyzeBackup(buffer);
                break;
            case (byte) 0x30:// canboxInfo
            case (byte) 0x7F:// canboxInfo
                LogCat.i(TAG + " analyzeCanBoxPro " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                analyzeCanBoxPro(buffer);
                break;
            case (byte) 0xE2:// 反馈升级状态
                LogCat.i(TAG + " analyzeUpgradeStatePro " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                analyzeUpgradeStatePro(buffer);
                break;
            case (byte) 0xE4:// 请求下一个升级数据包
                LogCat.i(TAG + " analyzeUpgradeDateByindexPro " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
                analyzeUpgradeDateByindexPro(buffer);
                break;
            case (byte) 0x18:// 原车触摸板坐标
                analyzeCarTouchBroad(buffer);
                break;
            case (byte) 0x2F:// 底部按键
                analyzeBottomBarPanel(buffer);
                break;
            default:
                break;
        }
    }

    //方向盘转角
    public void analyzeSteelWheelAngle(byte[] buffer) {
        if (buffer.length < 6) {
            return;
        }
        if (canEventListener != null) {
            canEventListener.onSteerWheelAngle(buffer);
        }
    }

    //底部按钮条
    public void analyzeBottomBarPanel(byte[] buffer) {
        LogCat.i(TAG + " analyzeBottomBarPanel " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
        if (buffer.length < 4) {
            return;
        }

        int data = buffer[3] & 0xFF;
        String key = Integer.toHexString(data);
        if (key.length() == 1) {
            key = "0" + key;
        }
        LogCat.d(TAG + "key=" + key);
        if (canEventListener != null) {
            canEventListener.onBottomBarPanel(key);
        }
    }

    /**
     * 检查协议完整性
     *
     * @param buffer
     * @return
     */
    private boolean checkCanbox(byte[] buffer) {
        if (buffer.length < 5) {
            Log.e(TAG, "canboxParse_checkMcu buffer too short");
            return false;
        }
        if (buffer[2] != (byte) (buffer.length - 4)) {
            return false;
        }
        byte checksum = buffer[1];
        for (int i = 2; i < buffer.length - 1; i++) {
            checksum += buffer[i];
        }
        byte compCheck = (byte) (checksum ^ 0xFF);
        if (compCheck == buffer[buffer.length - 1]) {
            return true;
        } else {
            return false;
        }
    }

    private CarRunInfo runInfo = new CarRunInfo();
    private CarBaseInfo baseInfo = new CarBaseInfo();
    // private CarBaseInfo lastBaseInfo;
    private boolean iAirMenu = false;
    private boolean isLeftRudder = true;
    private ICANEventListener canEventListener = null;

    public void setCANEventListener(ICANEventListener canEventListener) {
        this.canEventListener = canEventListener;
    }

    public ICANEventListener getCANEventListener() {
        return canEventListener;
    }

    public void setRudderType(int type) {
        isLeftRudder = type == 0;
    }

    /**
     * DataType 0x03
     * Length   0x07
     * Data0    基本状态
     * bit7 空调开关 0-off 1-on
     * bit6 AC     0-off 1-on
     * bit5 内外循环 0-外循环 1-内循环
     * bit4 模式自动 0-off 1-on
     * bit3 风力自动 0-off 1-on
     * bit2 DUAL   0-off 1-on
     * bit1 max-front 0-off 1-on
     * bit0 rear       0-off 1- on
     * Data1    模式 + 风力
     * BIT7：前窗送风开关指示 0b：OFF；1b：ON
     * BIT6：平行送风开关指示 0b：OFF；1b：ON
     * BIT5：向下送风开关指示  0b：OFF；1b：ON
     * BIT4：保留
     * BIT3：风力 0.5 级指示 0b：无；1b：0.5 级风力
     * BIT2~0：风力整数值
     * 0x00：空调关闭
     * 0x01~0x07：1~7 级风力
     * Data2    左侧温度
     * Data3    右侧温度
     * 0x7F：无此项显示 0x00：LO  0x1F：HI 0x01~0x1E：15.5°C~30°C，步进 0.5°C
     * Data4    其他V1.05
     * <p>
     * Data5    右模式+风力V1.05
     * Data6    预留
     */
    private void analyzeACTable(byte[] buffer) {
        LogCat.i(TAG + " analyzeACTable " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
        ACTable mAcTableInfo = new ACTable();
        byte buffer3 = buffer[3];
        mAcTableInfo.setAirOpen(((buffer3 >> 7) & 0x1) == 1);
        mAcTableInfo.setAcOpen(((buffer3 >> 6) & 0x1) == 1);
        mAcTableInfo.setInnerLoop(((buffer3 >> 5) & 0x1) == 0);
        if (!CommonConfig.isSmartpie) {
            mAcTableInfo.setRightWindAuto(((buffer3 >> 4) & 0x1) == 1);
            mAcTableInfo.setLeftWindAuto(((buffer3 >> 3) & 0x1) == 1);
        } else {
            mAcTableInfo.setLeftWindAuto(((buffer3 >> 4) & 0x1) == 1);
        }
        mAcTableInfo.setDual(((buffer3 >> 2) & 0x1) == 1);
        mAcTableInfo.setMaxFrontDemist(((buffer3 >> 1) & 0x1) == 1);
        mAcTableInfo.setRear((buffer3 & 0x1) == 1);
        mAcTableInfo.setRearDemist((buffer3 & 0x1) == 1);

        byte buffer4 = buffer[4]; //左模式 + 风力
        boolean isFrontWindDireLeft = ((buffer4 >> 7) & 0x1) == 1;
        boolean isFlatWindDireLeft = ((buffer4 >> 6) & 0x1) == 1;
        boolean isDownWindDireLeft = ((buffer4 >> 5) & 0x1) == 1;
        mAcTableInfo.setLeftWindLevel((buffer4 & 0x07));
        if (isFrontWindDireLeft) {
            mAcTableInfo.setLeftWindDire(WindDireType.FRONT.ordinal());
        } else if (isFlatWindDireLeft) {
            mAcTableInfo.setLeftWindDire(WindDireType.PARALLEL.ordinal());
        } else if (isDownWindDireLeft) {
            mAcTableInfo.setLeftWindDire(WindDireType.DOWN.ordinal());
        }

        //左侧设定温度
        int leftTemp = buffer[5];
        //右侧设定温度
        int rightTemp = buffer[6];
        if (isLeftRudder) {
            // 左舵车
            mAcTableInfo.setLeftTemp(15f + leftTemp * 0.5f);
            mAcTableInfo.setRightTemp(15f + rightTemp * 0.5f);
        } else {
            // 右舵车
            mAcTableInfo.setRightTemp(15f + leftTemp * 0.5f);
            mAcTableInfo.setLeftTemp(15f + rightTemp * 0.5f);
        }

        boolean iAirMenu = ((buffer[7] >> 1) & 0x1) == 1;
        if (this.iAirMenu != iAirMenu) {
            canEventListener.onHandleIdriver(EIdriverEnum.HOME, EBtnStateEnum.BTN_DOWN);
        }

        byte buffer8 = buffer[8]; //右模式 + 风力
        boolean isFrontWindDireRight = ((buffer8 >> 7) & 0x1) == 1;
        boolean isFlatWindDireRight = ((buffer8 >> 6) & 0x1) == 1;
        boolean isDownWindDireRight = ((buffer8 >> 5) & 0x1) == 1;
        mAcTableInfo.setRightWindLevel((buffer8 & 0x07));

        if (isFrontWindDireRight) {
            mAcTableInfo.setRightWindDire(WindDireType.FRONT.ordinal());
        } else if (isFlatWindDireRight) {
            mAcTableInfo.setRightWindDire(WindDireType.PARALLEL.ordinal());
        } else if (isDownWindDireRight) {
            mAcTableInfo.setRightWindDire(WindDireType.DOWN.ordinal());
        }

        //预留
        byte data = buffer[9];
        canEventListener.onACTable(mAcTableInfo);
    }

    private void analyzeCarBasePro(byte[] buffer) {
        byte b1 = buffer[3];
        boolean iPowerOn = ((b1 >> 4) & 0x01) == 1;
        boolean iInP = ((b1 >> 3) & 0x01) == 1;
        boolean iInRevering = ((b1 >> 2) & 0x01) == 1;
        boolean iNearLightOpen = ((b1 >> 1) & 0x01) == 1;
        boolean iAccOpen = (b1 & 0x01) == 1;

        baseInfo.setiPowerOn(iPowerOn);
        baseInfo.setiInP(iInP);
        baseInfo.setiInRevering(iInRevering);
        baseInfo.setiNearLightOpen(iNearLightOpen);
        baseInfo.setiAccOpen(iAccOpen);

        int curSpeed = (int) (buffer[7] & 0xFF);
        if ((buffer[7] & 0xFF) != 0xFF) {
            runInfo.setCurSpeed(curSpeed);
        }
        baseInfo.setiFlash(false);
        canEventListener.onCarBaseInfo(baseInfo);
        canEventListener.onCarRunningInfo(runInfo);
    }

    private void analyzeCarBasePro1(byte[] buffer) {
        byte b1 = buffer[3];
        boolean iRightFrontOpen = ((b1 >> 7) & 0x01) == 1;
        boolean iLeftFrontOpen = ((b1 >> 6) & 0x01) == 1;
        boolean iRightBackOpen = ((b1 >> 5) & 0x01) == 1;
        boolean iLeftBackOpen = ((b1 >> 4) & 0x01) == 1;
        boolean iBack = ((b1 >> 3) & 0x01) == 1;
        boolean iFront = ((b1 >> 2) & 0x01) == 1;
        boolean iSafetyBelt = ((b1 >> 1) & 0x01) == 1;
        boolean iSafetyBelt1 = (b1 & 0x01) == 1;
        if (isLeftRudder) {
            baseInfo.setiRightFrontOpen(iRightFrontOpen);
            baseInfo.setiLeftFrontOpen(iLeftFrontOpen);
            baseInfo.setiRightBackOpen(iRightBackOpen);
            baseInfo.setiLeftBackOpen(iLeftBackOpen);
        } else {
            baseInfo.setiRightFrontOpen(iLeftFrontOpen);
            baseInfo.setiLeftFrontOpen(iRightFrontOpen);
            baseInfo.setiRightBackOpen(iLeftBackOpen);
            baseInfo.setiLeftBackOpen(iRightBackOpen);
        }
        baseInfo.setiBack(iBack);
        baseInfo.setiFront(iFront);
        if (!CommonConfig.isSmartpie) {
            baseInfo.setiSafetyBelt(iSafetyBelt || iSafetyBelt1);
        }
        baseInfo.setiFlash(true);
        canEventListener.onCarBaseInfo(baseInfo);
    }

    /**
     * XBS车辆动态信息
     */
    private void analyzeCarRunningInfoPro1(byte[] buffer) {
        int curSpeed = (int) (buffer[3] & 0xFF);
        runInfo.setCurSpeed(curSpeed);
        int value = buffer[4] & 0xFF;
        int revolutions = (value << 8) + (buffer[5] & 0xFF);
        runInfo.setRevolutions(revolutions);
        canEventListener.onCarRunningInfo(runInfo);
    }

    /**
     * ZMYT车辆动态信息
     *
     * @param buffer
     */
    private void analyzeCarRunningInfoPro2(byte[] buffer) {

        int curSpeed = (int) (buffer[5] & 0xFF);
        runInfo.setCurSpeed(curSpeed);
        int value = buffer[3] & 0xFF;
        int revolutions = (value << 8) + (buffer[4] & 0xFF);
        runInfo.setRevolutions(revolutions);
        canEventListener.onCarRunningInfo(runInfo);
    }

    /**
     * S级面板信息
     *
     * @param buffer
     */
    private void analyzeSCarPanelInfo(byte[] buffer) {
        SCarPanelInfo info = new SCarPanelInfo();
        info.radarOff = (buffer[3] >> 6) & 0x03;
        info.passAirBag = (buffer[3] >> 4) & 0x03;
        info.sport = (buffer[3] >> 2) & 0x03;
        info.carHang = buffer[3] & 0x03;
        info.espOff = (buffer[4] >> 6) & 0x03;
        canEventListener.onSCarPanelInfo(info);
    }

    private void analyzeCanBoxPro(byte[] buffer) {
        int length = (int) buffer[2];
        byte[] infoByte = Arrays.copyOfRange(buffer, 3, length + 3);
        Charset cs = Charset.forName("US-ASCII");
        ByteBuffer bb = ByteBuffer.allocate(length);
        bb.put(infoByte);
        bb.flip();
        CharBuffer cb = cs.decode(bb);
        char[] cChar = cb.array();
        String info = new String(cChar);
        canEventListener.onCanboxInfo(info);
    }

    private void analyzeIdriverPro(byte[] buffer) {
        if (buffer.length < 6) {
            return;
        }
        EIdriverEnum idriverValue = EIdriverEnum.NONE;
        byte b = buffer[3];
        for (EIdriverEnum value : EIdriverEnum.values()) {
            if (b == value.getCode()) {
                idriverValue = value;
                break;
            }
        }
        EBtnStateEnum btnValue = EBtnStateEnum.BTN_DOWN;
        if (buffer[4] == 0x00) {
            btnValue = EBtnStateEnum.BTN_UP;
        } else if (buffer[4] == 0x02) {
            btnValue = EBtnStateEnum.BTN_LONG_PRESS;
            if (PubSysConst.isDecoder) {
                return;
            }

        }
        canEventListener.onHandleIdriver(idriverValue, btnValue);
    }

    private void analyzeTimePro(byte[] buffer) {
        int year = 2000 + (int) buffer[3];
        int month = (int) buffer[4];
        int day = buffer[5];
        int timeFormat = (((buffer[6] >> 7) & 0x01) == 1) ? 12 : 24;
        int time = buffer[6] & 0x7F;
        int min = buffer[7];
        // int sec = buffer[8];
        canEventListener.onTime(year, month, day, timeFormat, time, min, 0);
    }


    private void analyzeOriginalCarPro(byte[] buffer) {
        boolean iOriginal = true;
        byte isAux = buffer[4];
        byte type = buffer[3];
        switch (type) {
            case 0x01:// 请求进入加装
                iOriginal = false;
                break;
            case 0x02:// 请求进入原装
                iOriginal = true;
                break;
            default:
                break;
        }
        EControlSource source = EControlSource.RADIO;
        for (EControlSource temp : EControlSource.values()) {
            if (temp.getCode() == isAux) {
                source = temp;
                break;
            }
        }
        canEventListener.onOriginalCarView(source, iOriginal);
    }

    private void analyzeCarRunningInfoPro(byte[] buffer) {
        byte b = buffer[3];
        boolean foglight = ((b & 0x01) == 1) ? true : false;
        boolean iNearLightOpen = (((b >> 1) & 0x01) == 1) ? true : false;
        boolean iDistantLightOpen = (((b >> 2) & 0x01) == 1) ? true : false;
        //baseInfo.ichange = false;
        baseInfo.setFoglight(foglight);
        baseInfo.setiNearLightOpen(iNearLightOpen);
        baseInfo.setiDistantLightOpen(iDistantLightOpen);

        boolean iBrake = ((buffer[4] & 0x01) == 1) ? true : false;
        baseInfo.setiBrake(iBrake);

        boolean iFootBrake = ((buffer[4] >> 1) & 0x01) == 1 ? true : false;
        baseInfo.setiFootBrake(iFootBrake);

        int value = buffer[5] & 0xFF;
        int mileage = (value << 8) + (buffer[6] & 0xFF);
        value = buffer[7] & 0xFF;
        int revolutions = (value << 8) + (buffer[8] & 0xFF);
        runInfo.setMileage(mileage);
        if ((buffer[7] & 0xFF) != 0xFF || (buffer[8] & 0xFF) != 0xFF) {
            runInfo.setRevolutions(revolutions);
        }

        int temp = buffer[9] & 0xFF;
        int data1 = buffer[10] & 0xFF;
        int data2 = buffer[11] & 0xFF;
        int data3 = buffer[12] & 0xFF;
        int data4 = buffer[13] & 0xFF;

        long totalMileage = (data1 << 24) + (data2 << 16) + (data3 << 8) + data4;

        runInfo.setOutsideTemp(temp / 2.0f - 40.0f);
        runInfo.setTotalMileage(totalMileage);

        baseInfo.setiFlash(false);
        canEventListener.onCarBaseInfo(baseInfo);
        canEventListener.onCarRunningInfo(runInfo);
    }

    private void analyzeAUXStatus(byte[] buffer) {
        byte type = buffer[3];
        EAUXStutas source = EAUXStutas.ACTIVATING;
        for (EAUXStutas temp : EAUXStutas.values()) {
            if (temp.getCode() == type) {
                source = temp;
                break;
            }
        }
        String str = Utils.byteArrayToHexString(buffer).substring(4);
        canEventListener.onAUXActivateStutas(source, str);
    }

    private void analyzeUpgradeStatePro(byte[] buffer) {
        byte b = buffer[3];
        ECanboxUpgrade value = ECanboxUpgrade.ERROR;
        for (ECanboxUpgrade e : ECanboxUpgrade.values()) {
            if (e.getCode() == b) {
                value = e;
                break;
            }
        }
        canEventListener.onCanboxUpgradeState(value);
    }

    private void analyzeUpgradeDateByindexPro(byte[] buffer) {
        int l = buffer[4] & 0xFF;
        int h = buffer[3] & 0xFF;
        int index = (h << 8) + l;
        canEventListener.onCanboxUpgradeForGetDataByIndex(index);
    }

    /**
     * 打印canbox信息
     *
     * @param str
     */
    private void logcatCanbox(String str) {
        canEventListener.logcatCanbox(str);
    }

    /**
     * 原车触摸面板
     */
    private void analyzeCarTouchBroad(byte[] buffer) {
        boolean isTouch = buffer[3] == 1 ? true : false;
        int value = buffer[4] & 0xFF;
        int x = (value << 8) + (buffer[5] & 0xFF);
        value = buffer[6] & 0xFF;
        int y = (value << 8) + (buffer[7] & 0xFF);
        canEventListener.onCarTouchBroad(isTouch, x, y);
    }

    private void analyzeRadarInfo(byte[] buffer) {
        CarRadarInfo radarInfo = new CarRadarInfo();
        if (buffer[1] == 0x04) {//后雷达
            radarInfo.rearLeftRadar = getRadarLevel(buffer[3]);
            radarInfo.rearLeftMidRadar = getRadarLevel(buffer[4]);
            radarInfo.rearRightMidRadar = getRadarLevel(buffer[5]);
            radarInfo.rearRightRadar = getRadarLevel(buffer[6]);
        } else {
            radarInfo.frontLeftRadar = getRadarLevel(buffer[3]);
            radarInfo.frontLeftMidRadar = getRadarLevel(buffer[4]);
            radarInfo.frontRightMidRadar = getRadarLevel(buffer[5]);
            radarInfo.rearRightRadar = getRadarLevel(buffer[6]);
        }
        canEventListener.onCarRadarInfo(radarInfo);
    }

    private int getRadarLevel(byte value) {
        int level = 12;
        int temp = value & 0xFF;
        if (temp % 15 == 0) {
            level = temp / 15;
        } else {
            level = temp / 15 + 1;
        }
        return level;
    }

    BackupBean backupBean = new BackupBean();
    int b1, b2;

    public void analyzeBackup(byte[] buffer) {
        if (buffer.length < 6) {
            return;
        }
        backupBean.iBack = ((buffer[3] & 0x80) == 0x80);
        backupBean.iTurnLeftLight = ((buffer[3] & 0x01) == 0x01);
        backupBean.iTurnRightLight = ((buffer[3] & 0x02) == 0x02);
        backupBean.p_key = ((buffer[3] & 0x04) == 0x04);
        backupBean.autoPack = ((buffer[3] & 0x08) == 0x08);
        backupBean.iwheelAngle = buffer[4] & 0xFF;
        b1 = buffer[5] & 0x03;
        b2 = buffer[5] >> 2 & 0x03;
        backupBean.radar_lq = b1 >= b2 ? b1 : b2;
        b1 = buffer[5] >> 4 & 0x03;
        b2 = buffer[5] >> 6 & 0x03;
        backupBean.radar_rq = b1 >= b2 ? b1 : b2;
        b1 = buffer[6] & 0x03;
        b2 = buffer[6] >> 2 & 0x03;
        backupBean.radar_lh = b1 >= b2 ? b1 : b2;
        b1 = buffer[6] >> 4 & 0x03;
        b2 = buffer[6] >> 6 & 0x03;
        backupBean.radar_rh = b1 >= b2 ? b1 : b2;
        backupBean.gear = buffer[7];
        canEventListener.onRadarInfo(backupBean);
    }

    public void analyzeVSHBackupRadar(byte[] buffer) {
        b1 = buffer[7] & 0xFF;
        b2 = buffer[8] & 0xFF;
        backupBean.radar_lq = b1 <= b2 ? b1 : b2;
        b1 = buffer[9] & 0xFF;
        b2 = buffer[10] & 0xFF;
        backupBean.radar_rq = b1 <= b2 ? b1 : b2;
        b1 = buffer[3] & 0xFF;
        b2 = buffer[4] & 0xFF;
        backupBean.radar_lh = b1 <= b2 ? b1 : b2;
        b1 = buffer[5] & 0xFF;
        b2 = buffer[6] & 0xFF;
        backupBean.radar_rh = b1 <= b2 ? b1 : b2;

        canEventListener.onRadarInfo(backupBean);

    }

    public void analyzeVSHBackupGuiji(byte[] buffer) {
        backupBean.iwheelDirectionLeft = ((buffer[3] & 0x00) == 0x00);
        backupBean.iwheelAngle = buffer[4] << 8 + buffer[5] & 0xFF;

        canEventListener.onRadarInfo(backupBean);

    }


}
