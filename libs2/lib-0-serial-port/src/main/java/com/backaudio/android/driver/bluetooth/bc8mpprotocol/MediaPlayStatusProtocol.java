package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 媒体播放状态协议
 * 
 * @author hknaruto
 * 
 */
public class MediaPlayStatusProtocol extends AbstractLineProtocol {

	private EMediaPlayStatus mediaPlayStatus;

	public MediaPlayStatusProtocol(String key, String value) {
		super(key, value);
		if (key.equals("A2DPSTAT")) {
			if (value.equals("0")) {
				mediaPlayStatus = EMediaPlayStatus.STOP;
				return;
			}
			if (value.equals("3")) {
				mediaPlayStatus = EMediaPlayStatus.PLAYING;
				return;
			}
			if (value.equals("2")) {
				mediaPlayStatus = EMediaPlayStatus.PAUSE;
				return;
			}
			if (mediaPlayStatus == null) {
				mediaPlayStatus = EMediaPlayStatus.STOP;
			}
			return;
		}
		if (key.equals("PLAYSTAT")) {
			if (value.equals("0")) {
				mediaPlayStatus = EMediaPlayStatus.STOP;
				return;
			}
			if (value.equals("1")) {
				mediaPlayStatus = EMediaPlayStatus.PLAYING;
				return;
			}
			if (value.equals("2")) {
				mediaPlayStatus = EMediaPlayStatus.PAUSE;
				return;
			}
			return;
		}

	}
	
	public void setMediaPlayStatus(EMediaPlayStatus mediaPlayStatus){
		this.mediaPlayStatus = mediaPlayStatus;
	}

	public EMediaPlayStatus getMediaPlayStatus() {
		return mediaPlayStatus;
	}

}
