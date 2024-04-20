package com.backaudio.android.driver.beans;

public class BackupBean {
	public boolean iBack;
	public boolean iTurnLeftLight;
	public boolean iTurnRightLight;
	public boolean iwheelDirectionLeft;
	public int iwheelAngle;
	public int radar_lh;
	public int radar_lq;
	public int radar_rq;
	public int radar_rh;
	public boolean p_key;
	public boolean autoPack;
	public int gear;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("BackupBean [iBack=");
		builder.append(iBack);
		builder.append(", iTurnLeftLight=");
		builder.append(iTurnLeftLight);
		builder.append(", iTurnRightLight=");
		builder.append(iTurnRightLight);
		builder.append(", iwheelAngle=");
		builder.append(iwheelAngle);
		builder.append(", radar_lh=");
		builder.append(radar_lh);
		builder.append(", radar_lq=");
		builder.append(radar_lq);
		builder.append(", radar_rq=");
		builder.append(radar_rq);
		builder.append(", radar_rh=");
		builder.append(radar_rh);
		builder.append(", p_key=");
		builder.append(p_key);
		builder.append(", autoPack=");
		builder.append(autoPack);
		builder.append(", gear=");
		builder.append(gear);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
