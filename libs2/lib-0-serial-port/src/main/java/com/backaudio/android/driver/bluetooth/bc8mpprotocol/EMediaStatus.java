package com.backaudio.android.driver.bluetooth.bc8mpprotocol;

/**
 * 媒体状态，作为EPlayStatus的补充， 只有处于连接状态下才可以控制媒体播放
 * 
 * @author hknaruto
 * 
 */
public enum EMediaStatus {
	UNCONNECT, CONNECTIING, CONNECTED, PLAYING;
}
