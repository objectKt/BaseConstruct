package com.backaudio.android.driver.beans;

//汽车面板信息
public class SCarPanelInfo {
	//雷达开关
	public int radarOff = 0;
	//安全气囊
	public int passAirBag = 0;
	//驾驶模式
	public int sport = 0;
	//底盘升降
	public int carHang = 0;
	//ESP开关
	public int espOff = 0;
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SCarPanelInfo [radarOff=");
		builder.append(radarOff);
		builder.append(", passAirBag=");
		builder.append(passAirBag);
		builder.append(", sport=");
		builder.append(sport);
		builder.append(", carHang=");
		builder.append(carHang);
		builder.append(", espOff=");
		builder.append(espOff);
		builder.append("]");
		return builder.toString();
	}
	
	public boolean carHangKeyPressed(){
		return carHang == 1;
	}

	public boolean espOffKeyPressed(){
		return espOff == 1;
	}

	public boolean radarOffKeyPressed(){
		return espOff == 1;
	}
}
