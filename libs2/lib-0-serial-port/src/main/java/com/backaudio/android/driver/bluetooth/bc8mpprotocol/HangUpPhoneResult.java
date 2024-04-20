package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 挂断电话结果
 * 
 * @author hknaruto
 * 
 */
public class HangUpPhoneResult extends AbstractLineProtocol {

	private boolean isSuccess = false;

	public HangUpPhoneResult(String key, String value) {
		super(key, value);
		if (value.equals("0")) {
			isSuccess = true;
		}
	}

	public boolean isSuccess() {
		return isSuccess;
	}

}
