package com.backaudio.android.driver.beans;

public class CarRadarInfo {
	public int rearLeftRadar = 12;
	public int rearLeftMidRadar = 12;
	public int rearRightRadar = 12;
	public int rearRightMidRadar = 12;
	public int frontLeftRadar = 12;
	public int frontLeftMidRadar = 12;
	public int frontRightRadar = 12;
	public int frontRightMidRadar = 12;
	public boolean iHide() {
		if (frontLeftRadar < 7 || frontLeftMidRadar < 7
				|| frontRightRadar < 7 || frontRightMidRadar < 7){
			return false;
		}else{
			return true;
		}
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarRadarInfo [rearLeftRadar=");
		builder.append(rearLeftRadar);
		builder.append(", rearLeftMidRadar=");
		builder.append(rearLeftMidRadar);
		builder.append(", rearRightRadar=");
		builder.append(rearRightRadar);
		builder.append(", rearRightMidRadar=");
		builder.append(rearRightMidRadar);
		builder.append(", frontLeftRadar=");
		builder.append(frontLeftRadar);
		builder.append(", frontLeftMidRadar=");
		builder.append(frontLeftMidRadar);
		builder.append(", frontRightRadar=");
		builder.append(frontRightRadar);
		builder.append(", frontRightMidRadar=");
		builder.append(frontRightMidRadar);
		builder.append("]");
		return builder.toString();
	}
	
}