package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

public class DeviceNameProtocol extends AbstractLineProtocol {

	private boolean success = false;

	private String deviceName;
	private String PIN;
	private String MACAddr;

	public DeviceNameProtocol(String key, String value) {
		super(key, value);
		if(value == null || value.isEmpty()){
			return;
		}
		try {
			if (value.charAt(0) == '0') {
				deviceName = value.substring(value.indexOf(',') + 1);
				success = true;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public boolean isSuccess() {
		return success;
	}

	public String getDeviceName() {
		return deviceName;
	}
	public String getPIN() {
		return PIN;
	}

	public String getMACAdress() {
		return MACAddr;
	}
	public void setDeviceName(String deviceName) {
		this.success = true;
		this.deviceName = deviceName;
	}

	public void setPIN(String pIN) {
		PIN = pIN;
	}

	public void setMACAddr(String MACAddr) {
		this.MACAddr = MACAddr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DeviceNameProtocol [deviceName=");
		builder.append(deviceName);
		builder.append(", PIN=");
		builder.append(PIN);
		builder.append(", MACAddr=");
		builder.append(MACAddr);
		builder.append("]");
		return builder.toString();
	}

	

}
