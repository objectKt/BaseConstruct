package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 电话本协议，直接返回的vcard原始数据流
 * 
 * @author hknaruto
 * 
 */
public class PhoneBookListProtocol extends AbstractPlayLoadProtocol {

	public PhoneBookListProtocol(String key, String value) {
		super(key, value);

	}

	public void addUnit(AbstractPlayLoadProtocol phoneBookUnit) {
		super.push(phoneBookUnit.getPlayload(), 0,
				phoneBookUnit.getPlayload().length);
	}

}
