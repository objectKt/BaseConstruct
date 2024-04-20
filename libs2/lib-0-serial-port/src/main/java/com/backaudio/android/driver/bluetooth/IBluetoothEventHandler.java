package com.backaudio.android.driver.bluetooth;

import com.backaudio.android.driver.bluetooth.bc8mpprotocol.AnswerPhoneResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.CallOutResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.CallingOutProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.ConnectedDeviceProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.DeviceNameProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.DeviceRemovedProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.DeviceSwitchedProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.EnterPairingModeResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.HangUpPhoneResult;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.IncomingCallProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.MediaInfoProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.MediaPlayStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.MediaStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PairingListProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PhoneBookCtrlStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PhoneBookListProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.PhoneStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.SetPlayStatusProtocol;
import com.backaudio.android.driver.bluetooth.bc8mpprotocol.VersionProtocol;

/**
 * 蓝牙事件处理器
 *
 * @author hknaruto
 * @date 2014-1-15 下午2:23:00
 */
public interface IBluetoothEventHandler {

    /**
     * 蓝牙芯片响应版本
     *
     * @param versionProtocol
     */
    void onVersion(VersionProtocol versionProtocol);

    /**
     * 蓝牙芯片响应已配对列表
     *
     * @param pairingListProtocol
     */
    void onPairingList(PairingListProtocol pairingListProtocol);

    /**
     * 蓝牙芯片响应进入配对结果
     *
     * @param enterPairingModeResult
     */
    void onPairingModeResult(EnterPairingModeResult enterPairingModeResult);

    /**
     * 播放状态设置修改反馈
     *
     * @param playStatusProtocol
     */
    void onSetPlayStatus(SetPlayStatusProtocol playStatusProtocol);

    /**
     * 来电
     *
     * @param incomingCallProtocol
     */
    void onIncomingCall(IncomingCallProtocol incomingCallProtocol);

    /**
     * @param phoneStatusProtocol
     */
    void onPhoneStatus(PhoneStatusProtocol phoneStatusProtocol);

    void onAnswerPhone(AnswerPhoneResult answerPhoneResult);

    void onHangUpPhone(HangUpPhoneResult hangUpPhoneResult);

    void onCallOut(CallOutResult callOutResult);

    void onMediaStatus(MediaStatusProtocol mediaStatusProtocol);

    void onMediaPlayStatus(MediaPlayStatusProtocol mediaPlayStatusProtocol);

    void onPhoneBookCtrlStatus(PhoneBookCtrlStatusProtocol phoneBookCtrlStatusProtocol);

    void onMediaInfo(MediaInfoProtocol mediaInfoProtocol);

    void onPhoneBookList(PhoneBookListProtocol phoneBookListProtocol);

    void onConnectedDevice(ConnectedDeviceProtocol connectedDeviceProtocol);

    void onDeviceRemoved(DeviceRemovedProtocol deviceRemovedProtocol);

    void ondeviceSwitchedProtocol(DeviceSwitchedProtocol deviceSwitchedProtocol);

    /**
     * 返回蓝牙芯片当前设备名称
     *
     * @param deviceNameProtocol
     */
    void onDeviceName(DeviceNameProtocol deviceNameProtocol);

    void onPairedDevice(String deviceAddress, String deviceName);

    void onPhoneCallingOut(CallingOutProtocol callingOutProtocol);

    void onPhoneBook(String name, String number);

    void onFinishDownloadPhoneBook();

    void onPairingModeEnd();

    void onNetVersion(boolean isActivate, String code);//是否激活

    //当前音乐播放进度， 单位毫秒
    void onCurrentPlayProgress(long progress);
}
