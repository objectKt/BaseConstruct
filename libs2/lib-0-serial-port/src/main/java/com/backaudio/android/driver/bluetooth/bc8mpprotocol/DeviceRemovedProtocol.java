package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 设备被删除协议
 * 
 * @author hknaruto
 * 
 */
public class DeviceRemovedProtocol extends AbstractLineProtocol {

	private String address;

	public DeviceRemovedProtocol(String key, String value) {
		super(key, value);
		try {
			address = value.substring(',');
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getAddress() {
		return address;
	}

}
