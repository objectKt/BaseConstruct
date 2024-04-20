package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 单行协议抽象封装
 * 
 * @author hknaruto
 * 
 */
public abstract class AbstractLineProtocol {

	private String key;
	private String value;

	public AbstractLineProtocol(String key, String value) {
		this.key = key;
		this.value = value;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}


	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}

	@Override
	public String toString() {
		return "AbstractLineProtocol{" +
				"key='" + key + '\'' +
				", value='" + value + '\'' +
				'}';
	}
}
