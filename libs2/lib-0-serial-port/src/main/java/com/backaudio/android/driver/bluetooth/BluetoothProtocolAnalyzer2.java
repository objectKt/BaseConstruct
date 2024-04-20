package com.backaudio.android.driver.bluetooth;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.Locale;

import android.text.TextUtils;

import com.backaudio.android.driver.Utils;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.CallOutResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.CallingOutProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.ConnectedDeviceProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.DeviceNameProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.DeviceSwitchedProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.EMediaPlayStatus;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.EPhoneStatus;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.EnterPairingModeResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.HangUpPhoneResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.IncomingCallProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.MediaInfoProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.MediaPlayStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PhoneStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.VersionProtocol;
import com.dc.android.launcher.util.HUtils;
import com.drake.logcat.LogCat;

public class BluetoothProtocolAnalyzer2 implements IBluetoothProtocolAnalyzer {

    private static final String TAG = "tag-bt2";
    private IBluetoothEventHandler eventHandler = null;
    private byte mNextExpectedValue = -1;
    private boolean mValidProtocol = false;
    private ByteArrayOutputStream baos = new ByteArrayOutputStream();
    public FileOutputStream out = null;
    public static SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss", Locale.US);

    public void setEventHandler(IBluetoothEventHandler eventHandler) {
        LogCat.d(TAG + "IBluetoothEventHandler");
        this.eventHandler = eventHandler;
    }

    public BluetoothProtocolAnalyzer2() {
        try {
            String path = "/data/data/com.android.launcher/bluetooth2.txt";
            LogCat.i(TAG + " " + path);
            // out = new FileOutputStream("/data/data/com.touchus.benchilauncher/bluetooth2.txt", true);
            out = new FileOutputStream(path, true);
        } catch (Exception e) {
            LogCat.e("BluetoothProtocolAnalyzer2() " + e.getMessage());
        }
    }

    public void push(byte[] buffer) {
        int len = buffer.length;
        for (int i = 0; i < len; i++) {
            if (buffer[i] == 0x0d) {
                mNextExpectedValue = 0x0a;
            } else if (buffer[i] == 0x0a) {
                if (mNextExpectedValue == 0x0a) {
                    mValidProtocol = true;
                }
            } else {
                mNextExpectedValue = -1;
            }
            if (buffer[i] == -1) {
                buffer[i] = '\n';// 替换0xff，方便后续解析
            }
            baos.write(buffer, i, 1);
            if (mValidProtocol) {
                try {
                    analyze(baos.toByteArray());
                } catch (Exception e) {
                    LogCat.d(TAG + "analyze error" + e);
                } finally {
                    reset();
                }
            }
        }
    }

    private void analyze(byte[] buffer) {
        LogCat.i(TAG + " analyze() " + HUtils.ByteX.INSTANCE.bytesToHeXX(buffer));
        if (out != null) {
            synchronized (out) {
                try {
                    out.write(buffer);
                    out.write(("\t" + sdf.format(new Date()) + "\r\n").getBytes());
                    out.flush();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        if (eventHandler == null || buffer == null || buffer.length < 4) {
            LogCat.d(TAG + "--------invalid--------");
            return;
        }
        char ch1 = (char) buffer[0];
        char ch2 = (char) buffer[1];

        switch (ch1) {
            case 'D':
                handleStartD(ch2, buffer);
                break;
            case 'I':
                handleStartI(ch2, buffer);
                break;
            case 'M':
                handleStartM(ch2, buffer);
                break;
            case 'P':
                handleStartP(ch2, buffer);
                break;
            case 'T':
                if (ch2 == '1' || ch2 == '0') {
                    LogCat.d(TAG + "T: 车机（蓝牙/LOCAL）出声音");
                    DeviceSwitchedProtocol dsp = new DeviceSwitchedProtocol(null,
                            String.valueOf(ch2));
                    eventHandler.ondeviceSwitchedProtocol(dsp);
                }
                break;
            case 'S':
                LogCat.d(TAG + "---start S----"
                        + new String(buffer, 0, buffer.length - 2));
                // handleStartS(ch2, buffer);
                break;
            case 'H':
                LogCat.d(TAG + "---start H----"
                        + new String(buffer, 0, buffer.length - 2));
                // handleStartH(ch2, buffer);
                break;
            case 'O':
                LogCat.d(TAG + "---start O----"
                        + new String(buffer, 0, buffer.length - 2));
                // handleStartO(ch2, buffer);
                break;
            case 'N':
                LogCat.d(TAG + "---start N----"
                        + new String(buffer, 0, buffer.length - 2));
                // handleStartN(ch2, buffer);
                break;
            case 'V':
                LogCat.d(TAG + "---start V----"
                        + new String(buffer, 0, buffer.length - 2));
                handleStartV(ch2, buffer);
                break;
            default:
                break;
        }
    }

    private void handleStartV(char ch, byte[] buffer) {
        int len = buffer.length;
        String value;
        switch (ch) {
            case 'S':
                value = new String(buffer, 2, len - 4);
                if (TextUtils.equals("0", value)) {
                    eventHandler.onNetVersion(true, value);
                } else {
                    eventHandler.onNetVersion(false, value);
                }
                LogCat.d(TAG + "VS = " + value);
                break;
        }
    }

    private void handleStartD(char ch, byte[] buffer) {
        int len = buffer.length;
        String value;
        switch (ch) {
            case 'B':
                value = new String(buffer, 2, len - 4);
                dnp.setMACAddr(value);
                eventHandler.onDeviceName(dnp);
                LogCat.d(TAG + "MN: MACAddr() PIN = " + value);
                break;
        }
    }

    private void handleStartI(char ch, byte[] buffer) {
        String number;
        PhoneStatusProtocol psp;
        int len = buffer.length;
        switch (ch) {
            case 'A':
                LogCat.d(TAG + "IA: HFP断开");
                psp = new PhoneStatusProtocol(null, "");
                psp.setPhoneStatus(EPhoneStatus.UNCONNECT);
                eventHandler.onPhoneStatus(psp);
                break;
            case 'B':
                LogCat.d(TAG + "IB: HFP连接成功");
                psp = new PhoneStatusProtocol(null, "");
                psp.setPhoneStatus(EPhoneStatus.CONNECTED);
                eventHandler.onPhoneStatus(psp);
                break;
            case 'C':
                CallOutResult cor = new CallOutResult(null, "0");
                eventHandler.onCallOut(cor);
                LogCat.d(TAG + "IC: onCallOut() ");
                break;
            case 'D':
                number = new String(buffer, 2, len - 4);
                IncomingCallProtocol icp = new IncomingCallProtocol(null, number);
                eventHandler.onIncomingCall(icp);
                LogCat.d(TAG + "ID: onIncomingCall() " + number);
                break;
            case 'E':
                number = new String(buffer, 2, len - 4);
                LogCat.d(TAG + "三方来电：" + number);
                break;
            case 'F':
                HangUpPhoneResult hupr = new HangUpPhoneResult(null, "0");
                eventHandler.onHangUpPhone(hupr);
                LogCat.d(TAG + "IF: 挂机------------");
                break;
            case 'G':
                LogCat.d(TAG + "IG: 已接通-----------");
                psp = new PhoneStatusProtocol(null, "");
                psp.setPhoneStatus(EPhoneStatus.TALKING);
                eventHandler.onPhoneStatus(psp);
                break;
            case 'R':
                number = new String(buffer, 2, len - 4);
                CallingOutProtocol cop = new CallingOutProtocol(null, number);
                eventHandler.onPhoneCallingOut(cop);
                LogCat.d(TAG + "IR: onPhoneCallingOut() " + number);
                break;
            case 'V':
                LogCat.d(TAG + "IV: HFP连接中");
                psp = new PhoneStatusProtocol(null, "");
                psp.setPhoneStatus(EPhoneStatus.CONNECTING);
                eventHandler.onPhoneStatus(psp);
                break;
            case 'I':
                LogCat.d(TAG + "II: 进入配对");
                EnterPairingModeResult epmr = new EnterPairingModeResult(null, "0");
                eventHandler.onPairingModeResult(epmr);
                break;
            case 'J':
                LogCat.d(TAG + "IJ: 退出配对");
                EnterPairingModeResult epmr1 = new EnterPairingModeResult(null, "1");
                eventHandler.onPairingModeResult(epmr1);
                break;
            case 'S':
                LogCat.d(TAG + "IS: 蓝牙初始化完成");
                break;
            case 'O':
                LogCat.d(TAG + "打开/关闭咪头");
                // 打开/关闭咪头
                break;
        }
    }

    DeviceNameProtocol dnp = new DeviceNameProtocol(null, "");

    private void handleStartM(char ch, byte[] buffer) {
        int len = buffer.length;
        String value;
        switch (ch) {
            case 'A':
                LogCat.d(TAG + "MA: 音乐暂停中/连接成功");
                MediaPlayStatusProtocol mpsp = new MediaPlayStatusProtocol("", "");
                mpsp.setMediaPlayStatus(EMediaPlayStatus.PAUSE);
                eventHandler.onMediaPlayStatus(mpsp);
                break;
            case 'B':
                LogCat.d(TAG + "MB: 音乐播放中");
                MediaPlayStatusProtocol mpsp1 = new MediaPlayStatusProtocol("", "");
                mpsp1.setMediaPlayStatus(EMediaPlayStatus.PLAYING);
                eventHandler.onMediaPlayStatus(mpsp1);
                break;
            case 'C':
                LogCat.d(TAG + "MC: 车机（蓝牙/LOCAL）出声音");
                DeviceSwitchedProtocol dsp = new DeviceSwitchedProtocol(null, "0");
                eventHandler.ondeviceSwitchedProtocol(dsp);
                break;
            case 'D':
                LogCat.d(TAG + "MD: 手机（REMOTE）出声音");
                DeviceSwitchedProtocol dsp1 = new DeviceSwitchedProtocol(null, "1");
                eventHandler.ondeviceSwitchedProtocol(dsp1);
                break;
            case 'G':
                int type = buffer[2] - '0';
                value = String.valueOf(type);
                PhoneStatusProtocol psp = new PhoneStatusProtocol(null, "");
                psp.setPhoneStatus(value);
                eventHandler.onPhoneStatus(psp);
                LogCat.d(TAG + "MG: onPhoneStatus() " + psp.getPhoneStatus());
                break;
            case 'U':
                int st = buffer[2] - '0';
                LogCat.d(TAG + "MU: " + st);
                MediaPlayStatusProtocol msp3 = new MediaPlayStatusProtocol("A2DPSTAT",
                        String.valueOf(st));
                eventHandler.onMediaPlayStatus(msp3);
                break;
            case 'Y':
                LogCat.d(TAG + "MY: A2DP断开");
                MediaPlayStatusProtocol msp2 = new MediaPlayStatusProtocol("A2DPSTAT", "0");
                eventHandler.onMediaPlayStatus(msp2);
                break;
            case 'W':
                value = new String(buffer, 2, len - 4);
                VersionProtocol vp = new VersionProtocol(null, value);
                eventHandler.onVersion(vp);
                LogCat.d(TAG + "MW: onVersion() " + value);
                break;
            case 'M':
                value = new String(buffer, 2, len - 4);
                dnp.setDeviceName(value);
                eventHandler.onDeviceName(dnp);
                LogCat.d(TAG + "MM: onDeviceName() name = " + value);
                break;
            case 'N'://配对码
                value = new String(buffer, 2, len - 4);
                dnp.setPIN(value);
                eventHandler.onDeviceName(dnp);
                LogCat.d(TAG + "MN: onDeviceName() PIN = " + value);
                break;
            case 'X':
                String address = null;
                String name = null;
                int index = buffer[2] < 0 ? buffer[2] + 256 : buffer[2];
                address = new String(buffer, 3, 12);
                if (index == '0') {
                    ConnectedDeviceProtocol cdp = new ConnectedDeviceProtocol(null,
                            address);
                    cdp.setDeviceName(new String(buffer, 15, len - 17));
                    eventHandler.onConnectedDevice(cdp);
                    LogCat.d(TAG + "MX: address=" + address);
                } else {
                    name = new String(buffer, 15, len - 17);
                    eventHandler.onPairedDevice(address, name);
                    LogCat.d(TAG + "MX: address=" + address + " name=" + name);
                }
                break;
            case 'I':// 歌曲信息
                String[] info = new String(buffer, 2, len - 4).split("\n");
                int l = info.length;
                MediaInfoProtocol mip = new MediaInfoProtocol();
                mip.setAnalyzed(true);
                mip.setTitle((l > 0 ? info[0] : "UNKNOWN"));
                mip.setArtist((l > 1 ? info[1] : "UNKNOWN"));
                int time = 0,
                        number = 0,
                        total = 0;
                try {
                    time = Integer.parseInt((l > 2 ? info[2] : "-1"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    number = Integer.parseInt((l > 3 ? info[3] : "-1"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                try {
                    total = Integer.parseInt((l > 4 ? info[4] : "-1"));
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                }
                mip.setPlayTime(time);
                mip.setNumber(number);
                mip.setTotalNumber(total);
                eventHandler.onMediaInfo(mip);
                String mi = "";
                for (String s : info) {
                    mi += s + " ";
                }
                LogCat.d(TAG + "MI: -+- " + mi);
                break;
            case 'P': //当前播放进度
                info = new String(buffer, 2, len - 4).split("\n");
                if (info.length >= 2) {
                    String progress = info[1];
                    LogCat.d(TAG + "currentPlayProgress=" + progress);
                    try {
                        eventHandler.onCurrentPlayProgress(Long.parseLong(progress));
                    } catch (NumberFormatException e) {
                        e.printStackTrace();
                    }
                }
                break;
        }
    }

    private void handleStartP(char ch, byte[] buffer) {
        int len = buffer.length;
        switch (ch) {
            case '1':
                LogCat.d(TAG + "P1: onPairingModeResult() success");
                EnterPairingModeResult epmr = new EnterPairingModeResult(null, "0");
                eventHandler.onPairingModeResult(epmr);
                break;
            case '0':
                LogCat.d(TAG + "P0: onPairingModeResult() fail");
                EnterPairingModeResult lepmr = new EnterPairingModeResult(null,
                        null);
                eventHandler.onPairingModeResult(lepmr);
                break;
            case 'A':
                LogCat.d(TAG + "PA: " + new String(buffer));
                break;
            case 'B':
                String[] kv = new String(buffer, 2, len - 4).split("\n");
                if (kv == null || kv.length < 2) {
                    LogCat.d(TAG + "PB: invalid protocol name=" + kv[0]);
                    return;
                }
                String name = null;
                String number = null;
                name = kv[0];
                number = kv[1];
                LogCat.d(TAG + "PB: name=" + name + " number=" + number);
                eventHandler.onPhoneBook(name, number);
                break;
            case 'C':
                LogCat.d(TAG + "PC: onFinishDownloadPhoneBook()");
                eventHandler.onFinishDownloadPhoneBook();
                break;
            case 'D':
                String[] kv1 = new String(buffer, 2, len - 4).split("\n");
                if (kv1 == null || kv1.length < 2) {
                    LogCat.d(TAG + "PD: invalid protocol name=" + kv1[0]);
                    return;
                }
                String name1 = null;
                String number1 = null;
                name = kv1[0];
                number = kv1[1];
                eventHandler.onPhoneBook(name1, number1);
                LogCat.d(TAG + "PD: name=" + name + " number=" + number);
                break;
        }
    }

    @Override
    public void reset() {
        mNextExpectedValue = -1;
        mValidProtocol = false;
        baos.reset();
    }

    @Override
    public void push(byte[] buffer, int off, int len) {
        byte[] newbuffer = Arrays.copyOf(buffer, len);
        LogCat.d(TAG + " bluetoothprot recv::" + Utils.byteArrayToHexString(newbuffer, 0, newbuffer.length));
//        try {
//            Utils.saveLogLine(Utils.byteArrayToHexString(newbuffer, 0, newbuffer.length), true);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
        push(newbuffer);
    }

}
