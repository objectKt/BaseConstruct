package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

import java.io.ByteArrayOutputStream;

public class AbstractPlayLoadProtocol extends AbstractLineProtocol {

	private int needDataLength = 0;
	private ByteArrayOutputStream playload;

	public AbstractPlayLoadProtocol(String key, String value) {
		super(key, value);
		try {
			needDataLength = Integer.parseInt(value);
			playload = new ByteArrayOutputStream(needDataLength);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public int getNeedDataLength() {
		return needDataLength;
	}

	public void push(byte[] buffer, int off, int len) {

		// if (needDataLength > 0 && needDataLength <= len) {
		playload.write(buffer, off, len);
		needDataLength -= len;
		// } else {
		// System.out.println("error, needDataLength=" + needDataLength + "\t"
		// + "len=" + len);
		// }

	}

	public byte[] getPlayload() {
		return playload.toByteArray();
	}

	public void reset() {
		this.playload.reset();
	}
}
