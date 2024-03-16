package com.dc.auto.library.serial.listener;

/**
 * 串口数据收发监听器
 */
public interface OnSerialPortDataListener {

    /**
     * 数据发送
     */
    void onDataSent(byte[] bytes);

    /**
     * 数据接收
     */
    void onDataReceived(byte[] bytes);
}