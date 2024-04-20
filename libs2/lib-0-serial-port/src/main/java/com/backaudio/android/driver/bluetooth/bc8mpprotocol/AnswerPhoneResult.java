package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 接听电话结果
 * 
 * @author hknaruto
 * 
 */
public class AnswerPhoneResult extends AbstractLineProtocol {

	private boolean isSuccess = false;

	public AnswerPhoneResult(String key, String value) {
		super(key, value);
		if (value.equals("0")) {
			isSuccess = true;
		}
	}

	public boolean isSuccess() {
		return isSuccess;
	}

}
