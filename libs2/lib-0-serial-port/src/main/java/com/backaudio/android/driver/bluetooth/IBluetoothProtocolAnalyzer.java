package com.backaudio.android.driver.bluetooth;


/**
 * 蓝牙通讯协议封装，读取原始字节流，分析出返回内容并触发事件
 * 
 * @author hknaruto
 * @date 2014-1-15 下午2:56:27
 */
public interface IBluetoothProtocolAnalyzer {
    /**
     * 设置事件处理器列表
     * 
     * @param handler
     * @author hknaruto
     * @date 2014-1-15 下午2:58:52
     */
    public void setEventHandler(IBluetoothEventHandler handler);

    public void push(byte[] buffer, int off, int len);

    /**
     * 重置缓冲区，監聽器
     * 
     * @author hknaruto
     * @date 2014-1-15 下午3:32:20
     */
    public void reset();

}
