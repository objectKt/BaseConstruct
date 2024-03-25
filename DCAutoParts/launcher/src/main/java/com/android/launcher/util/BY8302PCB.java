package com.android.launcher.util;

import dc.library.utils.logcat.LogCat;

/**
 * BY8302-16P语音板 (连续发送两条命令之间间隔在 20MS 以上，组合播放功能两条命令在 6MS 以内)
 */
public class BY8302PCB {

    private static final String TAG = BY8302PCB.class.getSimpleName();

    //转向灯是否正在播放
    public static boolean leftTurnPlay = false;
    public static boolean rightTurnPlay = false;
    //双闪
    public static boolean doubleFlashPlay = false;
    //雷达
    public static boolean radarPlay = false;
    //安全带
    public static boolean safetyBeltPlay = false;

    private static boolean isPlaying = false;

    /**
     * @description: 播放
     * @createDate: 2023/8/26
     */
    public static synchronized void play(SoundPlayer.Type type, MusicType musicType) {
        LogCat.d("play---type=" + type.name() + ", index=" + musicType.ordinal() + "leftTurnPlay=" + leftTurnPlay +
                ", rightTurnPlay=" + rightTurnPlay + ", doubleFlashPlay=" + doubleFlashPlay + ", safetyBeltPlay=" + safetyBeltPlay + ", radarPlay=" + radarPlay + ", isPlaying=" + isPlaying);
        if (type == SoundPlayer.Type.LEFT_TURN) {
            if (doubleFlashPlay) {
                leftTurnPlay = true;
                return;
            }
            if (!leftTurnPlay) {
                selectMusic(musicType.ordinal());
                setLeftTurnPlayState();
            }
        } else if (type == SoundPlayer.Type.RIGHT_TURN) {
            if (doubleFlashPlay) {
                rightTurnPlay = true;
                return;
            }
            if (!rightTurnPlay) {
                selectMusic(musicType.ordinal());
                setRightTurnPlayState();
            }
        } else if (type == SoundPlayer.Type.DOUBLE_FLASH) { //双闪优先级最高
            selectMusic(musicType.ordinal());
            setDoubleFlashPlayState();
        } else {
            if (!turnSignalOpen()) {
                //双闪第一优先级， 转向灯第二优先级
                if (type == SoundPlayer.Type.SAFETY_BELT && !radarPlay) { //安全带优先级最低
                    selectMusic(musicType.ordinal());
                    isPlaying = true;
                    safetyBeltPlay = true;
                } else if (type == SoundPlayer.Type.RADAR) {
                    selectMusic(musicType.ordinal());
                    isPlaying = true;
                    radarPlay = true;
                }
            }
        }
    }

    /**
     * 双闪播放状态
     */
    private static void setDoubleFlashPlayState() {
        isPlaying = true;
        doubleFlashPlay = true;
        radarPlay = false;
        safetyBeltPlay = false;
    }

    /**
     * 右转向播放状态
     */
    private static void setRightTurnPlayState() {
        isPlaying = true;
        rightTurnPlay = true;
        leftTurnPlay = false;
        doubleFlashPlay = false;
        radarPlay = false;
        safetyBeltPlay = false;
    }

    /**
     * 左转向播放状态
     */
    private static void setLeftTurnPlayState() {
        isPlaying = true;
        leftTurnPlay = true;
        rightTurnPlay = false;
        doubleFlashPlay = false;
        radarPlay = false;
        safetyBeltPlay = false;
    }


    /**
     * 暂停
     */
    public static synchronized void pause() {
        new Thread(() -> {
            String sendCode = "7E030201EF";
            SerialHelperttlLd3.sendHex(sendCode);

        }).start();
    }

    /**
     * 音量加 芯片有 30 级音量可调
     */
    public static synchronized void volumePlus() {
        new Thread(() -> {
            String sendCode = "7E030506EF";
            SerialHelperttlLd3.sendHex(sendCode);
        }).start();
    }

    /**
     * 音量减 芯片有 30 级音量可调
     */
    public static synchronized void volumeDown() {
        new Thread(() -> {
            String sendCode = "7E030605EF";
            SerialHelperttlLd3.sendHex(sendCode);
        }).start();
    }

    /**
     * 音量设置 音量为 0-30 级可调，该指令可以实时修改调节音量，音量可以掉电记忆
     */
    public static synchronized void volumeSetup(int value) {
        new Thread(() -> {
            try {
                String volume = Integer.toHexString(value);
                if (volume.length() == 1) {
                    volume = "0" + volume;
                }
                String checkCode = HexDecimalUtils.xor(HexDecimalUtils.xor("04", "31"), volume);
                String sendCode = "7E0431" + volume + checkCode + "EF";
                LogCat.d("volumeSetup----value=" + value + ", sendCode=" + sendCode.toUpperCase());
                SerialHelperttlLd3.sendHex(sendCode.toUpperCase());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }).start();
    }

    /**
     * 单曲循环
     */
    public static synchronized void singleLoopMode() {
        new Thread(() -> {
            String sendCode = "7E04330235EF";
            SerialHelperttlLd3.sendHex(sendCode);
        }).start();
    }

    public static synchronized void playStatus() {
        new Thread(() -> {
            String sendCode = "7E031013EF";
            SerialHelperttlLd3.sendHex(sendCode);
        }).start();
    }

    /**
     * @description: 单曲循环
     * @createDate: 2023/8/26
     */
    public static synchronized void stop() {
        new Thread(() -> {
            String sendCode = "7E030E0DEF";
            SerialHelperttlLd3.sendHex(sendCode);
        }).start();
    }


    /**
     * @description: 1：转向灯， 2：雷达， 3：安全带
     * @createDate: 2023/8/26
     */
    public static synchronized void selectMusic(int index) {
        new Thread(() -> {
            String indexHex = Integer.toHexString(index);
            if (indexHex.length() == 1) {
                indexHex = "0" + indexHex;
            }
            String checkCode = HexDecimalUtils.xor("05", "41");
            checkCode = HexDecimalUtils.xor(checkCode, "00");
            checkCode = HexDecimalUtils.xor(checkCode, indexHex);

            String sendCode = "7E054100" + indexHex + checkCode + "EF";

            LogCat.d("selectMusic----index=" + index + ", sendCode=" + sendCode.toUpperCase());
            SerialHelperttlLd3.sendHex(sendCode.toUpperCase());

            try {
                Thread.sleep(20);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            SerialHelperttlLd3.sendHex(sendCode.toUpperCase());
        }).start();
    }


    public static void stopLeftTurn() {
        if (leftTurnPlay) {
            stop();
            leftTurnPlay = false;
        }
    }

    public static void stopRightTurn() {
        if (rightTurnPlay) {
            stop();
            rightTurnPlay = false;
        }
    }

    /**
     * @description: 停止雷达
     * @createDate: 2023/5/22
     */
    public static void stopRadar() {
        LogCat.d("stopRadar----radarPlay=" + radarPlay);
        if (radarPlay && !turnSignalOpen()) {
            stop();
            radarPlay = false;
        }
    }

    /**
     * @description: 停止双闪
     * @createDate: 2023/5/22
     */
    public static void stopDoubleFlash() {
        if (doubleFlashPlay) {
            stop();
            doubleFlashPlay = false;
        }
    }

    public static void stopSafetyBelt() {
        if (!isPlaying) {
            return;
        }
        new Thread(() -> {
            try {
                LogCat.d("stopSafetyBelt----radarPlay=" + radarPlay + ", turnSignalOpen=" + turnSignalOpen());
                if (!radarPlay && !turnSignalOpen()) {
                    isPlaying = false;
                    stop();
                }
                safetyBeltPlay = false;
            } catch (Exception e) {
                e.printStackTrace();
            }

        }).start();
    }

    /**
     * 转向，双闪是否开启
     */
    public static boolean turnSignalOpen() {
        return leftTurnPlay || rightTurnPlay || doubleFlashPlay;
    }

    public enum MusicType {
        NONE,
        TURN_SIGNAL, //转向灯
        RADAR, //雷达
        SAFETY_BELT //安全带
    }
}
