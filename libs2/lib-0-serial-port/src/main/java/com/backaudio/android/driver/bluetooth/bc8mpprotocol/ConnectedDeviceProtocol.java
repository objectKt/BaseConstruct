package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 当前连接的设备
 * 
 * @author hknaruto
 * 
 */
public class ConnectedDeviceProtocol extends AbstractLineProtocol {

	private String deviceAddress;
	private String deviceName;

	public ConnectedDeviceProtocol(String key, String value) {
		super(key, value);
		deviceAddress = value;
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

    public String getDeviceName() {
        return deviceName;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

}
