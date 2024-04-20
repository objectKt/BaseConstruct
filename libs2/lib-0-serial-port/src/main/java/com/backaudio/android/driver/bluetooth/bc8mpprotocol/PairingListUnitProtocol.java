package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 匹配地址列表的一行数据
 * 
 * @author hknaruto
 * 
 */
public class PairingListUnitProtocol extends AbstractLineProtocol {

	private String deviceAddress;
	private String deviceName;

	public PairingListUnitProtocol(String key, String value) {
		super(key, value);
		try {
			deviceAddress = value.substring(value.indexOf(',') + 1,
					value.lastIndexOf(','));
			deviceName = value.substring(value.lastIndexOf(",\"") + 2,
					value.lastIndexOf('\"'));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getDeviceAddress() {
		return deviceAddress;
	}

	public String getDeviceName() {
		return deviceName;
	}

}
