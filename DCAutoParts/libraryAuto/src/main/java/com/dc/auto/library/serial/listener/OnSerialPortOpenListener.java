package com.dc.auto.library.serial.listener;

import java.io.File;

/**
 * 串口打开监听器
 */
public interface OnSerialPortOpenListener {

    void onSuccess(File device);

    void onFail(File device, Status status);

    enum Status {
        WITHOUT_READ_WRITE_PERMISSION,
        OPEN_FAIL
    }

}
