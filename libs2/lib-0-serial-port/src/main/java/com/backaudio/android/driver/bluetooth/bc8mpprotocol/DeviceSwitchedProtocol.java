package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 设备切换结果
 * 
 * @author hknaruto
 * 
 */
public class DeviceSwitchedProtocol extends AbstractLineProtocol {

	private EConnectedDevice connectedDevice;

	public DeviceSwitchedProtocol(String key, String value) {
		super(key, value);
		if (value.equals("0")) {
			this.connectedDevice = EConnectedDevice.LOCAL;
		} else {
			this.connectedDevice = EConnectedDevice.REMOTE;
		}
	}

	public EConnectedDevice getConnectedDevice() {
		return connectedDevice;
	}

}
