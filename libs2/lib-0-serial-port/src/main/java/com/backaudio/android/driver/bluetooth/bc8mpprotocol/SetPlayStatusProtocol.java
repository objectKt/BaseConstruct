package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

public class SetPlayStatusProtocol extends AbstractLineProtocol {

	private static final String PAUSE = "PP";
	private static final String STOP = "STOP";
	private static final String PLAY_NEXT = "FWD";
	private static final String PLAY_PREV = "BACK";

	private ESetPlayStatus playStatus = null;
	private boolean isSuccess = false;

	public SetPlayStatusProtocol(String key, String value) {
		super(key, value);

		isSuccess = value.equals("0");

		if (key.equals(PAUSE)) {
			playStatus = ESetPlayStatus.PALY_OR_PAUSE;
			return;
		}
		if (key.equals(STOP)) {
			playStatus = ESetPlayStatus.STOP;
			return;
		}
		if (key.equals(PLAY_NEXT)) {
			playStatus = ESetPlayStatus.PLAY_NEXT;
			return;
		}
		if (key.equals(PLAY_PREV)) {
			playStatus = ESetPlayStatus.PLAY_PREV;
			return;
		}
	}

	public ESetPlayStatus getPlayStatus() {
		return playStatus;
	}

	public boolean isSuccess() {
		return isSuccess;
	}

}
