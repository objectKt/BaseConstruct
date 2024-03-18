package com.android.launcher.cruisecontrol;

public abstract class CanSenderBase {

    // MCU主体协议格式：引导码 0XAA +长度 + 帧ID + Data + checksum
    protected static final String DATA_HEAD = "AA0000";
    protected boolean isRunnable = false;
    protected boolean isPause = false;

    public abstract void send();

    public void release() {
        isRunnable = false;
        isPause = false;
    }

    public void setPause(boolean pause) {
        isPause = pause;
    }
}