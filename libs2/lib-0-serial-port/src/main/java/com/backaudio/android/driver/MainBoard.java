package com.backaudio.android.driver;

import android.os.Handler;
import android.os.Process;

import com.backaudio.android.driver.can.CanBoxParse;
import com.backaudio.android.driver.can.ICANEventListener;
import com.backaudio.android.driver.manage.PublicEnum.EIdriverEnum;
import com.backaudio.android.driver.manage.PublicEnum.EIdriverEnum1;
import com.backaudio.android.driver.mcu.IMCUEventListener;
import com.backaudio.android.driver.mcu.InitMCUSerialPort;
import com.backaudio.android.driver.mcu.MCUParse;
import com.drake.logcat.LogCat;

/**
 * 底版驱动管理类,负责底版通讯，事件生成等
 *
 * @author hknaruto
 * @date 2014-1-16 上午10:34:42
 */
public class MainBoard {

    private static final String TAG = MainBoard.class.getSimpleName();
    private static MainBoard instance = null;

    /**
     * 初始化后立刻建立与
     */
    private MainBoard() {
        LogCat.d(TAG + "MainBoard Init " + Process.myPid());
        MCUParse.getInstance();
        CanBoxParse.getInstance();
        InitMCUSerialPort.getInstance();// MCU 转发 CAN
        if (CommonConfig.isSmartpie) {
            updateEIDriverEnum();
        }
    }

    public static MainBoard getInstance() {
        if (instance == null) {
            synchronized (MainBoard.class) {
                if (instance == null) {
                    instance = new MainBoard();
                }
            }
        }
        return instance;
    }

    /**
     * 设置触摸handle
     */
    public void addHandler(Handler handler) {
        if (handler == null) {
            return;
        }
        CanBoxParse.getInstance().setEventHandler(handler);
    }

    /**
     * 设置 CAN 监听器
     */
    public void setCANEventListener(ICANEventListener canEventListener) {
        CanBoxParse.getInstance().setCANEventListener(canEventListener);
    }

    /**
     * 设置 MCU 监听器
     */
    public void setMCUEventListener(IMCUEventListener mcuEventListener) {
        MCUParse.getInstance().setMCUEventListener(mcuEventListener);
    }

    /**
     * 设置主驾驶位置
     */
    public void setRudderType(int type) {
        CanBoxParse.getInstance().setRudderType(type);
    }

    ///////////////// 回调监听器 /////////////////

    public static void updateEIDriverEnum() {
        EIdriverEnum.NONE.setCode(EIdriverEnum1.NONE.getCode());
        EIdriverEnum.VOL_ADD.setCode(EIdriverEnum1.VOL_ADD.getCode());
        EIdriverEnum.VOL_DES.setCode(EIdriverEnum1.VOL_DES.getCode());
        EIdriverEnum.PREV.setCode(EIdriverEnum1.PREV.getCode());
        EIdriverEnum.MODE.setCode(EIdriverEnum1.MODE.getCode());
        EIdriverEnum.MUTE.setCode(EIdriverEnum1.MUTE.getCode());
        EIdriverEnum.BT.setCode(EIdriverEnum1.BT.getCode());
        EIdriverEnum.PICK_UP.setCode(EIdriverEnum1.PICK_UP.getCode());
        EIdriverEnum.HANG_UP.setCode(EIdriverEnum1.HANG_UP.getCode());
        EIdriverEnum.UP.setCode(EIdriverEnum1.UP.getCode());
        EIdriverEnum.DOWN.setCode(EIdriverEnum1.DOWN.getCode());
        EIdriverEnum.HOME.setCode(EIdriverEnum1.HOME.getCode());
        EIdriverEnum.PRESS.setCode(EIdriverEnum1.PRESS.getCode());
        EIdriverEnum.RIGHT.setCode(EIdriverEnum1.RIGHT.getCode());
        EIdriverEnum.LEFT.setCode(EIdriverEnum1.LEFT.getCode());
        EIdriverEnum.BACK.setCode(EIdriverEnum1.BACK.getCode());
        EIdriverEnum.K_VOL_ADD.setCode(EIdriverEnum1.K_VOL_ADD.getCode());
        EIdriverEnum.K_VOL_DES.setCode(EIdriverEnum1.K_VOL_DES.getCode());
        EIdriverEnum.RADIO.setCode(EIdriverEnum1.RADIO.getCode());
        EIdriverEnum.NAVI.setCode(EIdriverEnum1.NAVI.getCode());
        EIdriverEnum.MEDIA.setCode(EIdriverEnum1.MEDIA.getCode());
        EIdriverEnum.POWER_OFF.setCode(EIdriverEnum1.POWER_OFF.getCode());
        EIdriverEnum.SPEECH.setCode(EIdriverEnum1.SPEECH.getCode());
        EIdriverEnum.ESC.setCode(EIdriverEnum1.ESC.getCode());
        EIdriverEnum.K_HOME.setCode(EIdriverEnum1.K_HOME.getCode());
        EIdriverEnum.SPEAKOFF.setCode(EIdriverEnum1.SPEAKOFF.getCode());
        EIdriverEnum.STAR_BTN.setCode(EIdriverEnum1.STAR_BTN.getCode());
        EIdriverEnum.CARSET.setCode(EIdriverEnum1.CARSET.getCode());
        EIdriverEnum.CALL_HELP.setCode(EIdriverEnum1.CALL_HELP.getCode());
        EIdriverEnum.CALL_SOS.setCode(EIdriverEnum1.CALL_SOS.getCode());
        EIdriverEnum.CALL_FIX.setCode(EIdriverEnum1.CALL_FIX.getCode());
        EIdriverEnum.MEDIA_PREV.setCode(EIdriverEnum1.MEDIA_PREV.getCode());
        EIdriverEnum.MEDIA_NEXT.setCode(EIdriverEnum1.MEDIA_NEXT.getCode());
        EIdriverEnum.MEDIA_PLAY_PAUSE.setCode(EIdriverEnum1.MEDIA_PLAY_PAUSE.getCode());
        EIdriverEnum.TURN_RIGHT.setCode(EIdriverEnum1.TURN_RIGHT.getCode());
        EIdriverEnum.TURN_LEFT.setCode(EIdriverEnum1.TURN_LEFT.getCode());
        EIdriverEnum.T_PRESS.setCode(EIdriverEnum1.T_PRESS.getCode());
        EIdriverEnum.VOL_Enter.setCode(EIdriverEnum1.VOL_Enter.getCode());
        EIdriverEnum.C_DYNAMIC_DN.setCode(EIdriverEnum1.C_DYNAMIC_DN.getCode());
        EIdriverEnum.C_DYNAMIC_UP.setCode(EIdriverEnum1.C_DYNAMIC_UP.getCode());
        EIdriverEnum.C_tel.setCode(EIdriverEnum1.C_tel.getCode());
        EIdriverEnum.K_UP.setCode(EIdriverEnum1.K_UP.getCode());
        EIdriverEnum.K_DOWN.setCode(EIdriverEnum1.K_DOWN.getCode());
        EIdriverEnum.K_MEDIA.setCode(EIdriverEnum1.K_MEDIA.getCode());
        EIdriverEnum.K_ENTER.setCode(EIdriverEnum1.K_ENTER.getCode());
        EIdriverEnum.K_NAV.setCode(EIdriverEnum1.K_NAV.getCode());
        EIdriverEnum.K_LEFT.setCode(EIdriverEnum1.K_LEFT.getCode());
        EIdriverEnum.K_RIGHT.setCode(EIdriverEnum1.K_RIGHT.getCode());
        EIdriverEnum.K_RETURN.setCode(EIdriverEnum1.K_RETURN.getCode());
        EIdriverEnum.K_OK.setCode(EIdriverEnum1.K_OK.getCode());
        EIdriverEnum.NEXT.setCode(EIdriverEnum1.NEXT.getCode());
        EIdriverEnum.C_pre_next.setCode(EIdriverEnum1.C_pre_next.getCode());
        EIdriverEnum.K_SOURCE.setCode(EIdriverEnum1.K_SOURCE.getCode());

        EIdriverEnum.P_FM.setCode(EIdriverEnum1.P_FM.getCode());
        EIdriverEnum.P_AUX.setCode(EIdriverEnum1.P_AUX.getCode());
        EIdriverEnum.P_SEEK_ADD.setCode(EIdriverEnum1.P_SEEK_ADD.getCode());
        EIdriverEnum.P_SEEK_DEC.setCode(EIdriverEnum1.P_SEEK_DEC.getCode());
        EIdriverEnum.P_DISC.setCode(EIdriverEnum1.P_DISC.getCode());
        EIdriverEnum.P_AUTO_P.setCode(EIdriverEnum1.P_AUTO_P.getCode());
        EIdriverEnum.P_SCAN.setCode(EIdriverEnum1.P_SCAN.getCode());
        EIdriverEnum.P_1.setCode(EIdriverEnum1.P_1.getCode());
        EIdriverEnum.P_2.setCode(EIdriverEnum1.P_2.getCode());
        EIdriverEnum.P_3.setCode(EIdriverEnum1.P_3.getCode());
        EIdriverEnum.P_4.setCode(EIdriverEnum1.P_4.getCode());
        EIdriverEnum.P_5.setCode(EIdriverEnum1.P_5.getCode());
        EIdriverEnum.P_6.setCode(EIdriverEnum1.P_6.getCode());
        EIdriverEnum.P_VOL_ADD.setCode(EIdriverEnum1.P_VOL_ADD.getCode());
        EIdriverEnum.P_VOL_DEC.setCode(EIdriverEnum1.P_VOL_DEC.getCode());
        EIdriverEnum.P_VOL_ENTER.setCode(EIdriverEnum1.P_VOL_ENTER.getCode());
        EIdriverEnum.P_TURN_ADD.setCode(EIdriverEnum1.P_TURN_ADD.getCode());
        EIdriverEnum.P_TURN_DEC.setCode(EIdriverEnum1.P_TURN_DEC.getCode());
        EIdriverEnum.P_TURN_ENTER.setCode(EIdriverEnum1.P_TURN_ENTER.getCode());
        EIdriverEnum.C_P_SCREEN.setCode(EIdriverEnum1.C_P_SCREEN.getCode());
    }
}
