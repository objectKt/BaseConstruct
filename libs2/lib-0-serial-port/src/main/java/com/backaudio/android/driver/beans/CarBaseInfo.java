package com.backaudio.android.driver.beans;

import android.os.Parcel;
import android.os.Parcelable;

public class CarBaseInfo implements Parcelable{
	//大灯（远光灯）是否打开
	private boolean iDistantLightOpen = false;
	//Power是否开屏
	private boolean iPowerOn = false;
	//acc是否打开
	private boolean iAccOpen = false;
	//手刹
	private boolean iBrake = false;
	//小灯（近光灯）是否打开
	private boolean iNearLightOpen = false;
	//p车档
	private boolean iInP = false;
	//倒车
	private boolean iInRevering = false;
	//右后门
	private boolean iRightBackOpen = false;
	//左后门
	private boolean iLeftBackOpen = false;
	//右前门
	private boolean iRightFrontOpen = false;
	//左前门
	private boolean iLeftFrontOpen = false;
	//安全带
	private boolean iSafetyBelt = false;
	//脚刹
	private boolean iFootBrake = false;
	//前盖
	private boolean iFront = false;
	//尾箱
	private boolean iBack = false;
	//灯光亮度
	private int iLightValue = 0;
	//雾灯
	private boolean foglight = false;
	//是否更新车门信息
	private boolean iFlash = false;
	//数据是否有更新
	public boolean ichange = false;
	// 前左胎压
	public int psi_fl = 260;
	// 右前胎压
	public int psi_fr = 260;
	// 后左胎压
	public int psi_rl = 260;
	// 右后胎压
	public int psi_rr = 260;
	
	public  CarBaseInfo(){}

	protected CarBaseInfo(Parcel in) {
		iDistantLightOpen = in.readByte() != 0;
		iPowerOn = in.readByte() != 0;
		iAccOpen = in.readByte() != 0;
		iBrake = in.readByte() != 0;
		iNearLightOpen = in.readByte() != 0;
		iInP = in.readByte() != 0;
		iInRevering = in.readByte() != 0;
		iRightBackOpen = in.readByte() != 0;
		iLeftBackOpen = in.readByte() != 0;
		iRightFrontOpen = in.readByte() != 0;
		iLeftFrontOpen = in.readByte() != 0;
		iFront = in.readByte() != 0;
		iBack = in.readByte() != 0;
		iFlash = in.readByte() != 0;
		iSafetyBelt = in.readByte() != 0;
		iFootBrake = in.readByte() != 0;
		psi_fl = in.readInt();
		psi_fr = in.readInt();
		psi_rl = in.readInt();
		psi_rr = in.readInt();
	}

	public static final Creator<CarBaseInfo> CREATOR = new Creator<CarBaseInfo>() {
		@Override
		public CarBaseInfo createFromParcel(Parcel in) {
			return new CarBaseInfo(in);
		}

		@Override
		public CarBaseInfo[] newArray(int size) {
			return new CarBaseInfo[size];
		}
	};
	public boolean isIchange() {
		boolean mischange = ichange;
		ichange = false;
		return mischange;
	}

	public boolean isiDistantLightOpen() {
		return iDistantLightOpen;
	}

	public void setiDistantLightOpen(boolean iDistantLightOpen) {
		this.iDistantLightOpen = iDistantLightOpen;
	}

	public boolean isiAccOpen() {
		return iAccOpen;
	}

	public void setiAccOpen(boolean iAccOpen) {
		this.iAccOpen = iAccOpen;
	}

	public boolean isiNearLightOpen() {
		return iNearLightOpen;
	}

	public void setiNearLightOpen(boolean iNearLightOpen) {
		this.iNearLightOpen = iNearLightOpen;
	}

	public boolean isiInP() {
		return iInP;
	}

	public void setiInP(boolean iInP) {
		this.iInP = iInP;
	}

	public boolean isiInRevering() {
		return iInRevering;
	}

	public void setiInRevering(boolean iInRevering) {
		this.iInRevering = iInRevering;
	}

	public boolean isiRightBackOpen() {
		return iRightBackOpen;
	}

	public void setiRightBackOpen(boolean iRightBackOpen) {
		ichange = ichange |(this.iRightBackOpen != iRightBackOpen);
		this.iRightBackOpen = iRightBackOpen;
	}

	public boolean isiLeftBackOpen() {
		return iLeftBackOpen;
	}

	public void setiLeftBackOpen(boolean iLeftBackOpen) {
		ichange = ichange |(this.iLeftBackOpen != iLeftBackOpen);
		this.iLeftBackOpen = iLeftBackOpen;
	}

	public boolean isiRightFrontOpen() {
		return iRightFrontOpen;
	}

	public void setiRightFrontOpen(boolean iRightFrontOpen) {
		ichange = ichange |(this.iRightFrontOpen != iRightFrontOpen);
		this.iRightFrontOpen = iRightFrontOpen;
	}

	public boolean isiLeftFrontOpen() {
		return iLeftFrontOpen;
	}

	public void setiLeftFrontOpen(boolean iLeftFrontOpen) {
		ichange = ichange |(this.iLeftFrontOpen != iLeftFrontOpen);
		this.iLeftFrontOpen = iLeftFrontOpen;
	}

	public boolean isiFront() {
		return iFront;
	}

	public void setiFront(boolean iFront) {
		ichange = ichange |(this.iFront != iFront);
		this.iFront = iFront;
	}

	public boolean isiBack() {
		return iBack;
	}

	public void setiBack(boolean iBack) {
		ichange = ichange |(this.iBack != iBack);
		this.iBack = iBack;
	}

	@Override
	public int describeContents() {
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		dest.writeByte((byte) (iDistantLightOpen ? 1 : 0));
		dest.writeByte((byte) (iPowerOn ? 1 : 0));
		dest.writeByte((byte) (iAccOpen ? 1 : 0));
		dest.writeByte((byte) (iBrake ? 1 : 0));
		dest.writeByte((byte) (iNearLightOpen ? 1 : 0));
		dest.writeByte((byte) (iInP ? 1 : 0));
		dest.writeByte((byte) (iInRevering ? 1 : 0));
		dest.writeByte((byte) (iRightBackOpen ? 1 : 0));
		dest.writeByte((byte) (iLeftBackOpen ? 1 : 0));
		dest.writeByte((byte) (iRightFrontOpen ? 1 : 0));
		dest.writeByte((byte) (iLeftFrontOpen ? 1 : 0));
		dest.writeByte((byte) (iFront ? 1 : 0));
		dest.writeByte((byte) (iBack ? 1 : 0));
		dest.writeByte((byte) (iFlash ? 1 : 0));
		dest.writeByte((byte) (iSafetyBelt ? 1 : 0));
		dest.writeByte((byte) (iFootBrake ? 1 : 0));
		dest.writeInt(psi_fl);
		dest.writeInt(psi_fr);
		dest.writeInt(psi_rl);
		dest.writeInt(psi_rr);
	}

	

	public boolean isiBrake() {
		return iBrake;
	}

	public void setiBrake(boolean iBrake) {
		this.iBrake = iBrake;
	}

	public int getiLightValue() {
		return iLightValue;
	}

	public void setiLightValue(int iLightValue) {
		this.iLightValue = iLightValue;
	}

	public boolean isiPowerOn() {
		return iPowerOn;
	}

	public void setiPowerOn(boolean iPowerOn) {
		this.iPowerOn = iPowerOn;
	}

	public boolean isFoglight() {
		return foglight;
	}

	public int getPsi_fl() {
		return psi_fl;
	}

	public void setPsi_fl(int psi_fl) {
		this.psi_fl = psi_fl;
	}

	public int getPsi_fr() {
		return psi_fr;
	}

	public void setPsi_fr(int psi_fr) {
		this.psi_fr = psi_fr;
	}

	public int getPsi_rl() {
		return psi_rl;
	}

	public void setPsi_rl(int psi_rl) {
		this.psi_rl = psi_rl;
	}

	public int getPsi_rr() {
		return psi_rr;
	}

	public void setPsi_rr(int psi_rr) {
		this.psi_rr = psi_rr;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarBaseInfo [iDistantLightOpen=");
		builder.append(iDistantLightOpen);
		builder.append(", iPowerOn=");
		builder.append(iPowerOn);
		builder.append(", iAccOpen=");
		builder.append(iAccOpen);
		builder.append(", iBrake=");
		builder.append(iBrake);
		builder.append(", iNearLightOpen=");
		builder.append(iNearLightOpen);
		builder.append(", iInP=");
		builder.append(iInP);
		builder.append(", iInRevering=");
		builder.append(iInRevering);
		builder.append(", iRightBackOpen=");
		builder.append(iRightBackOpen);
		builder.append(", iLeftBackOpen=");
		builder.append(iLeftBackOpen);
		builder.append(", iRightFrontOpen=");
		builder.append(iRightFrontOpen);
		builder.append(", iLeftFrontOpen=");
		builder.append(iLeftFrontOpen);
		builder.append(", iSafetyBelt=");
		builder.append(iSafetyBelt);
		builder.append(", iFootBrake=");
		builder.append(iFootBrake);
		builder.append(", iFront=");
		builder.append(iFront);
		builder.append(", iBack=");
		builder.append(iBack);
		builder.append(", iLightValue=");
		builder.append(iLightValue);
		builder.append(", foglight=");
		builder.append(foglight);
		builder.append(", iFlash=");
		builder.append(iFlash);
		builder.append(", ichange=");
		builder.append(ichange);
		builder.append(", psi_fl=");
		builder.append(psi_fl);
		builder.append(", psi_fr=");
		builder.append(psi_fr);
		builder.append(", psi_rl=");
		builder.append(psi_rl);
		builder.append(", psi_rr=");
		builder.append(psi_rr);
		builder.append("]");
		return builder.toString();
	}

	public void setFoglight(boolean foglight) {
		this.foglight = foglight;
	}

	public boolean isiFlash() {
		return iFlash;
	}

	public void setiFlash(boolean iFlash) {
		this.iFlash = iFlash;
	}

	public boolean isiSafetyBelt() {
		return iSafetyBelt;
	}

	public void setiSafetyBelt(boolean iSafetyBelt) {
		this.iSafetyBelt = iSafetyBelt;
	}

	public boolean isiFootBrake() {
		return iFootBrake;
	}

	public void setiFootBrake(boolean iFootBrake) {
		this.iFootBrake = iFootBrake;
	}

}
