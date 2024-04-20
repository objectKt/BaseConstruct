package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 电话本控制状态协议
 * 
 * @author hknaruto
 * 
 */
public class PhoneBookCtrlStatusProtocol extends AbstractLineProtocol {

	private EPhoneBookCtrlStatus phoneBookCtrlStatus;

	public PhoneBookCtrlStatusProtocol(String key, String value) {
		super(key, value);
		if (value.equals("0")) {
			phoneBookCtrlStatus = EPhoneBookCtrlStatus.UNCONNECT;
			return;
		}
		if (value.equals("1")) {
			phoneBookCtrlStatus = EPhoneBookCtrlStatus.CONECTING;
			return;
		}
		if (value.equals("2")) {
			phoneBookCtrlStatus = EPhoneBookCtrlStatus.CONNECTED;
			return;
		}
		if (value.equals("3")) {
			phoneBookCtrlStatus = EPhoneBookCtrlStatus.DOWNLOADING;
			return;
		}

	}

	public EPhoneBookCtrlStatus getPhoneBookCtrlStatus() {
		return phoneBookCtrlStatus;
	}

}
