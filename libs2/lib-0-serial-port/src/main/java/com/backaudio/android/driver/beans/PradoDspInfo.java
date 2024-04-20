package com.backaudio.android.driver.beans;

public class PradoDspInfo {
	public boolean isMute;
	public int vol = 0;
	public int bal = 16;
	public int fader = 16;
	public int low = 16;
	public int mid = 16;
	public int high = 16;
	public boolean autoVol;
	public boolean surround;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PradoDspInfo [isMute=");
		builder.append(isMute);
		builder.append(", vol=");
		builder.append(vol);
		builder.append(", bal=");
		builder.append(bal);
		builder.append(", fader=");
		builder.append(fader);
		builder.append(", low=");
		builder.append(low);
		builder.append(", mid=");
		builder.append(mid);
		builder.append(", high=");
		builder.append(high);
		builder.append(", autoVol=");
		builder.append(autoVol);
		builder.append(", surround=");
		builder.append(surround);
		builder.append("]");
		return builder.toString();
	}

}
