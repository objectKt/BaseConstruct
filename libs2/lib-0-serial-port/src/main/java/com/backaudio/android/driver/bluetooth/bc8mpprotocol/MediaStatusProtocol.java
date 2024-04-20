package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 媒体状态反馈
 * 
 * @author hknaruto
 * 
 */
public class MediaStatusProtocol extends AbstractLineProtocol {

	private EMediaStatus mediaStatus;

	public MediaStatusProtocol(String key, String value) {
		super(key, value);
		// if ("1".equals(value)) {
		// mediaStatus = EMediaStatus.UNCONNECT;
		// } else if ("2".equals(value)) {
		// mediaStatus = EMediaStatus.CONNECTED;
		// } else if ("3".equals(value)) {
		// mediaStatus = EMediaStatus.PLAYING;
		// }

		if ("0".equals(value)) {
			mediaStatus = EMediaStatus.UNCONNECT;
		} else if ("1".equals(value)) {
			mediaStatus = EMediaStatus.CONNECTIING;
		} else if ("2".equals(value)) {
			mediaStatus = EMediaStatus.CONNECTED;
		}
	}

	public void setMediaStatus(EMediaStatus mediaStatus) {
		this.mediaStatus = mediaStatus;
	}

	public EMediaStatus getMediaStatus() {
		return mediaStatus;
	}

}
