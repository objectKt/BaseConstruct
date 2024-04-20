package com.backaudio.android.driver.beans;

import java.util.Arrays;

public class CarMediaInfo {
	public boolean isST;
	public boolean isASL;
	public int mediaIndex;
	public boolean[] CDIndexState = new boolean[6];
	public int currentCDIndex;
	public String mediaContent;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarMediaInfo [isST=");
		builder.append(isST);
		builder.append(", isASL=");
		builder.append(isASL);
		builder.append(", mediaIndex=");
		builder.append(mediaIndex);
		builder.append(", CDIndexState=");
		builder.append(Arrays.toString(CDIndexState));
		builder.append(", currentCDIndex=");
		builder.append(currentCDIndex);
		builder.append(", mediaContent=");
		builder.append(mediaContent);
		builder.append("]");
		return builder.toString();
	}
	
}
