package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 手机通话状态协议
 * 
 * @author hknaruto
 * 
 */
public class PhoneStatusProtocol extends AbstractLineProtocol {

	private EPhoneStatus phoneStatus;

	public PhoneStatusProtocol(String key, String value) {
		super(key, value);
		if (value.equals("0")) {
			phoneStatus = EPhoneStatus.UNCONNECT;
		} else if (value.equals("1")) {
			phoneStatus = EPhoneStatus.CONNECTING;
		} else if (value.equals("2")) {
			phoneStatus = EPhoneStatus.CONNECTED;
		} else if (value.equals("3")) {
			phoneStatus = EPhoneStatus.INCOMING_CALL;
		} else if (value.equals("4")) {
			phoneStatus = EPhoneStatus.CALLING_OUT;
		} else if (value.equals("5")) {
			phoneStatus = EPhoneStatus.TALKING;
		} else if (value.equals("6")) {
			phoneStatus = EPhoneStatus.MULTI_TALKING;
		}
	}

	public void setPhoneStatus(String value) {
		if (value == null) {
			phoneStatus = EPhoneStatus.UNCONNECT;
			return;
		}
		if (value.equals("0")) {
			phoneStatus = EPhoneStatus.INITIALIZING;
		} else if (value.equals("1")) {
			phoneStatus = EPhoneStatus.UNCONNECT;
		} else if (value.equals("2")) {
			phoneStatus = EPhoneStatus.CONNECTING;
		} else if (value.equals("3")) {
			phoneStatus = EPhoneStatus.CONNECTED;
		} else if (value.equals("4")) {
			phoneStatus = EPhoneStatus.CALLING_OUT;
		} else if (value.equals("5")) {
			phoneStatus = EPhoneStatus.INCOMING_CALL;
		} else if (value.equals("6")) {
			phoneStatus = EPhoneStatus.TALKING;
		} else {
			phoneStatus = EPhoneStatus.UNCONNECT;
		}
	}
	
	public void setPhoneStatus(EPhoneStatus phoneStatus){
		this.phoneStatus = phoneStatus;
	}

	public EPhoneStatus getPhoneStatus() {
		return phoneStatus;
	}

}
