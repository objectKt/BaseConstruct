package com.backaudio.android.driver.mcu;

import android.util.Log;

import com.backaudio.android.driver.CommonConfig;
import com.backaudio.android.driver.can.CanBoxParse;
import com.backaudio.android.driver.manage.PublicEnum.EACCTime;
import com.backaudio.android.driver.manage.PublicEnum.ECarLayer;
import com.backaudio.android.driver.manage.PublicEnum.EUpgrade;

import lib.dc.protocol.ttl.TTLParse;

import com.drake.logcat.LogCat;

import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import lib.dc.utils.UtilByteArray;

/**
 * MCU 解析
 *
 * @author liuguojia
 */
public class MCUParse {

    private final String TAG = "MCUParse";
    ByteArrayOutputStream protocolBuffer = new ByteArrayOutputStream();
    int mcuProtocolLength = 0;
    int mcuCurrentProtocolLength = 0;
    private static MCUParse instance;

    public static MCUParse getInstance() {
        if (instance == null) {
            synchronized (MCUParse.class) {
                if (instance == null) {
                    instance = new MCUParse();
                }
            }
        }
        return instance;
    }

    private MCUParse() {

    }

    private synchronized void analyzeMcu(byte[] buf, int off, int len) {
        for (int i = off, l = 0; i < buf.length && l < len; i++, l++) {
            try {
                byte b = buf[i];
                switch (mcuCurrentProtocolLength) {
                    case 0:
                        // 还没有数据
                        if (b == (byte) 0xAA) {
                            protocolBuffer.write(new byte[]{b}, 0, 1);// 协议第一个字节，期待第二个字节为0x55
                            mcuCurrentProtocolLength++;
                        } else {
                            String errString = "mcu: require 0xAA but " + Integer.toHexString(b) + "\n";
                            CommonConfig.errNum++;
                            //Log.d(TAG, errString+",CommonConfig.errNum = "+CommonConfig.errNum);
                        }
                        break;
                    case 1:
                        if (b == 0x55) {
                            // 第二个字节对应ok
                            protocolBuffer.write(new byte[]{b}, 0, 1);
                            mcuCurrentProtocolLength++;
                        } else {
                            // 第二个协议字节不对，协议错误，重置
                            String errString = "require 0x55 but " + Integer.toHexString(b) + "\n";
                            LogCat.e(TAG + errString);
                            protocolBuffer.reset();
                        }
                        break;
                    case 2:
                        // 收到第三个字节，为长度位
                        mcuProtocolLength = b;
                        if (mcuProtocolLength < 0) {
                            mcuProtocolLength += 256;
                        }
                        mcuProtocolLength += 4;// 协议头2字节，长度1字节，校验1字节
                        protocolBuffer.write(new byte[]{b}, 0, 1);
                        mcuCurrentProtocolLength++;
                        break;
                    default:
                        // 已经获取完整协议头，长度
                        if (mcuCurrentProtocolLength < mcuProtocolLength) {
                            protocolBuffer.write(new byte[]{b}, 0, 1);
                            mcuCurrentProtocolLength++;
                            if (mcuCurrentProtocolLength == mcuProtocolLength) {
                                try {
                                    mcuProtocol(protocolBuffer.toByteArray());
                                } catch (Exception e) {
                                    LogCat.e(TAG + " mcuProtocol(protocolBuffer.toByteArray()) " + e.getMessage());
                                } finally {
                                    protocolBuffer.reset();
                                    mcuCurrentProtocolLength = 0;
                                    mcuProtocolLength = 0;
                                }
                            }
                        } else {
                            if (mcuCurrentProtocolLength > mcuProtocolLength) {
                                String errString = "mcu: shit:" + Integer.toHexString(b) + "\n";
                                LogCat.e(TAG + errString);
                            }
                        }
                        break;
                }
            } catch (Exception e) {
                LogCat.e(TAG + " analyzeMcu " + e.getMessage());
            }
        }
    }

    public void pushMcu(final byte[] buffer, final int index, final int len) {
        ///String temp = "mcuThread mcu:receive:<[" + Utils.byteArrayToHexString(buffer, index, len) + "]>";
        ///LogCat.d(TAG + "pushMcu----" + temp);
        analyzeMcu(buffer, index, len);
    }

    private void mcuProtocol(byte[] buffer) {
        if (buffer == null || buffer.length < 5) {
            LogCat.e(TAG + " mcu: ProtocolAnalyzer invalid mcu buffer, drop");
            return;
        }

        if (!checkMcu(buffer)) {
            LogCat.e(TAG + " checkMcu failed");
            return;
        }

        String hexBuffer = UtilByteArray.INSTANCE.toHeXLog(buffer);

        if (buffer[3] == 0x01 && buffer[4] == 0x00) {
            LogCat.i(TAG + " analyzeMcuEnterAccOrWakeup() " + hexBuffer);
            analyzeMcuEnterAccOrWakeup(buffer);
            return;
        }

        if (buffer[3] == 0x01 && buffer[4] == 0x04) {
            LogCat.i(TAG + " analyzeMcuBack() " + hexBuffer);
            analyzeMcuBack(buffer);
            return;
        }

        // 获取MCU版本号回调
        if (buffer[3] == 0x01 && buffer[4] == 0x11) {
            LogCat.i(TAG + " analyzeMcuInfoPro() " + hexBuffer);
            analyzeMcuInfoPro(buffer);
            return;
        }

        if (buffer[3] == 0x01 && buffer[4] == (byte) 0x3E) {
            LogCat.i(TAG + " -- 左前喇叭的值 " + hexBuffer);
            return;
        }

        if (buffer[3] == (byte) 0xE2) {
            LogCat.i(TAG + " analyzeMcuUpgradeStatePro() " + hexBuffer);
            analyzeMcuUpgradeStatePro(buffer);
            return;
        }
        if (buffer[3] == (byte) 0xE4) {
            LogCat.i(TAG + " analyzeMcuUpgradeDateByIndexPro() " + hexBuffer);
            analyzeMcuUpgradeDateByIndexPro(buffer);
            return;
        }
        if (buffer[3] == 0x01 && buffer[4] == 0x50) {
            LogCat.i(TAG + " pushCanBox() " + hexBuffer);
            CanBoxParse.getInstance().pushCanBox(buffer);
            return;
        }

        if (buffer[3] == 0x01 && (buffer[4] & 0xff) == 0xE3) {
            LogCat.i(TAG + " pushTTL " + hexBuffer);
            TTLParse.getInstance().pushTTL(buffer);
            return;
        }

        if (buffer[3] == 0x01 && buffer[4] == 0x0B) {
            LogCat.i(TAG + " analyzeFrequencyPoint() -- 收音，100ms发一次" + hexBuffer);
            analyzeFrequencyPoint(buffer);
            return;
        }

        if (buffer[3] == 0x01 && buffer[4] == 0x62) {
            LogCat.i(TAG + " analyzeStoreDateFromMCU() -- MCU 返回的存储数据" + hexBuffer);
            analyzeStoreDateFromMCU(buffer);
            return;
        }

        if (buffer[3] == 0x01 && buffer[4] == 0x73) {
            LogCat.i(TAG + " analyzeStoreDateFromMCU() -- DV 状态" + hexBuffer);
            analyzeDVState(buffer);
            return;
        }

        if (buffer[3] == 0x01 && buffer[4] == 0x7E) {
            LogCat.i(TAG + " analyzeStoreDateFromMCU() -- 360选项" + hexBuffer);
            analyze360Type(buffer);
            return;
        }

        if (buffer[3] == 0x01 && (buffer[4] & 0xff) == 0x81) {
            // ACC 延迟
            LogCat.i(TAG + " analyzeACCTime() " + hexBuffer);
            analyzeACCTime(buffer);
            return;
        }

        if (buffer[3] == 0x01 && buffer[4] == 0x6F) {
            LogCat.i(TAG + " analyzeLanguage() " + hexBuffer);
            analyzeLanguage(buffer);
            return;
        }

        if (buffer[3] == 0x01 && buffer[4] == 0x03) {
            // 背光亮度
            LogCat.i(TAG + " analyzeBrightnessValue() " + hexBuffer);
            analyzeBrightnessValue(buffer);
            return;
        }

        if (buffer[3] == 0x01 && (buffer[4] & 0xff) == 0x94) {
            // DSP 信息
            LogCat.i(TAG + " analyzeDSPValues() " + hexBuffer);
            analyzeDSPValues(buffer);
            return;
        }

        if (buffer[3] == 0x01 && ((buffer[4] & 0xff) == 0xDA)) {
            // 获取8836
            LogCat.i(TAG + " analyzeMCU8836() " + hexBuffer);
            analyzeMCU8836(buffer);
            return;
        }
    }

    //  语言
    //  0x00:  简体中文
    //  0x01:  繁体中文
    //  0x02:  英文
    private void analyzeLanguage(byte[] buffer) {
        if (buffer.length >= 5) {
            int language = buffer[5];
            if (mcuEventListener != null) {
                mcuEventListener.obtainLanguageType(Integer.toHexString(language));
            }
        }
    }

    /**
     * @noinspection UnusedReturnValue
     */ /*
     * 搜索时返回频点，确定进度条进度。
     * */
    private boolean analyzeFrequencyPoint(byte[] buffer) {
        if (buffer.length < 6) {
            LogCat.d(TAG + "analyzeFrequencyPoint--buffer too short");
            return false;
        }
        return true;
    }

    private boolean checkMcu(byte[] buffer) {
        if (buffer.length < 4) {
            Log.e(TAG, "mcuParse_checkMcu buffer too short");
            return false;
        }
        if (buffer[2] != (byte) (buffer.length - 4)) {
            return false;
        }
        byte checksum = buffer[2];
        for (int i = 3; i < buffer.length - 1; i++) {
            checksum = (byte) (checksum + buffer[i]);
        }
        return checksum == buffer[buffer.length - 1];
    }

    /**
     * --------------解析---------------------
     **/
    private IMCUEventListener mcuEventListener = null;

    public void setMCUEventListener(IMCUEventListener mcuEventListener) {
        this.mcuEventListener = mcuEventListener;
    }

    public IMCUEventListener getMCUEventListener() {
        return mcuEventListener;
    }

    public void analyzeMcuEnterAccOrWakeup(byte[] buffer) {
        if (buffer[5] == 0x01) {
            mcuEventListener.onEnterStandbyMode();
            SendMCUData.getInstance().soundValue = new StringBuffer();
        } else if (buffer[5] == 0x02) {
            ECarLayer eCarLayer = ECarLayer.ANDROID;
            byte b = buffer[6];
            for (ECarLayer value : ECarLayer.values()) {
                if (b == value.getCode()) {
                    eCarLayer = value;
                    break;
                }
            }
            CommonConfig.mECarLayer = eCarLayer;
            mcuEventListener.onWakeUp(eCarLayer);
        }
    }

    public void analyzeMcuBack(byte[] buffer) {
        // AA 55 03 01 04 01 09
        // AA 55 03 01 04 00 08
        boolean isBack = buffer[5] == (byte) 0x01;
        mcuEventListener.onMcuBackcar(isBack);
    }

    public void analyzeMcuInfoPro(byte[] buffer) {
        int infoLenght = buffer[2] - 2;
        byte[] infoByte = Arrays.copyOfRange(buffer, 5, infoLenght + 5);
        String versionNumber = new String(infoByte, StandardCharsets.UTF_8);
        mcuEventListener.obtainVersionNumber(versionNumber);
    }

    public void analyzeMcuUpgradeStatePro(byte[] buffer) {
        byte b = buffer[4];
        EUpgrade value = EUpgrade.ERROR;
        for (EUpgrade e : EUpgrade.values()) {
            if (e.getCode() == b) {
                value = e;
                break;
            }
        }
        if (mcuEventListener != null) {
            mcuEventListener.onMcuUpgradeState(value);
        }
    }

    public void analyzeHornSoundValue(byte[] buffer) {
        byte b = buffer[4];
        int fLeft = buffer[5];
        int fRight = buffer[6];
        int rLeft = buffer[7];
        int rRight = buffer[8];
        mcuEventListener.onHornSoundValue(fLeft, fRight, rLeft, rRight);
    }

    public void analyzeMcuUpgradeDateByIndexPro(byte[] buffer) {
        int l = buffer[5] & 0xFF;
        int h = buffer[4] & 0xFF;
        int index = (h << 8) + l;
        mcuEventListener.onMcuUpgradeForGetDataByIndex(index);
    }

    public void analyzeBrightnessValue(byte[] buffer) {
        int index = buffer[5];
        mcuEventListener.obtainBrightness(index);
    }

    public void analyze360Type(byte[] buffer) {
        int value = buffer[5];
        mcuEventListener.obtain360Type(value);
    }

    public void analyzeACCTime(byte[] buffer) {
        int b = buffer[5];
        EACCTime value = EACCTime.MIN_2;
        for (EACCTime e : EACCTime.values()) {
            if (e.getCode() == b) {
                value = e;
                break;
            }
        }
        mcuEventListener.obtainACCTime(value);
    }

    public void analyzeMCU8836(byte[] buffer) {
        String value = Integer.toHexString(buffer[7]);
        String addr = Integer.toHexString(buffer[5]) + Integer.toHexString(buffer[6]);
        Log.d("2255", "<<<	obtainMCU8836: r " + addr + " " + value);
        mcuEventListener.obtainMCU8836(addr, value);
    }

    /**
     * 获取MCU存储的数据
     */
    public void analyzeStoreDateFromMCU(byte[] buffer) {
        List<Byte> datalist = new ArrayList<Byte>();
        for (int i = 5; i < buffer.length - 1; i++) {
            datalist.add(buffer[i]);
        }
        mcuEventListener.obtainStoreData(datalist);
    }

    /**
     * 获取 MCU dv 状态
     */
    public void analyzeDVState(byte[] buffer) {
        boolean isPlaying = (buffer[5] == 1);
        mcuEventListener.obtainDVState(isPlaying);
    }

    /**
     * 获取触摸坐标
     */
    public void obtainTouchPoi(int x, int y) {
        mcuEventListener.obtainTouchPoi(x, y);
    }

    /**
     * 获取DSP
     */
    public void analyzeDSPValues(byte[] buffer) {
        int[] values = {
                12, 12, 12, 12,
                12, 12, 12, 12,
                12, 12, 12, 12,
                12, 12, 12, 12};
        for (int i = 0; i < values.length; i++) {
            values[i] = buffer[i + 5];
        }
        mcuEventListener.obtainDSPValues(values);
    }
}