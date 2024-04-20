package com.backaudio.android.driver.beans;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Administrator on 2016/5/31.
 */
public class CarRunInfo implements Parcelable {
    /**
     *平均车速，单位:0.1KM， 最小值 0
     */
    private int averSpeed = 0;

    /**续航里程
     *
     */
    private int mileage = 0;

    /**
     *总里程
     */
    private long totalMileage = 0;

    /**
     *车外温度
     */
    private float outsideTemp = 0;
    /**
     *温度单位
     */
    private boolean isCelsius = true;

    /**
     *发动机转数
     */
    private int revolutions = 0;
    
    /**
     *油耗
     */
    private int oilConsumption = 0;

    /**
     *瞬时速度
     */
    private int curSpeed = 0;

	public CarRunInfo(){}

    protected CarRunInfo(Parcel in) {
        averSpeed = in.readInt();
        mileage = in.readInt();
        totalMileage = in.readLong();
        outsideTemp = in.readFloat();
        isCelsius = in.readByte() == 0;
        revolutions = in.readInt();
        curSpeed = in.readInt();
        oilConsumption = in.readInt();
    }

    public static final Creator<CarRunInfo> CREATOR = new Creator<CarRunInfo>() {
        @Override
        public CarRunInfo createFromParcel(Parcel in) {
            return new CarRunInfo(in);
        }

        @Override
        public CarRunInfo[] newArray(int size) {
            return new CarRunInfo[size];
        }
    };

    public int getAverSpeed() {
        return averSpeed;
    }

    public void setAverSpeed(int averSpeed) {
        this.averSpeed = averSpeed;
    }

    public int getMileage() {
        return mileage;
    }

    public void setMileage(int mileage) {
        this.mileage = mileage;
    }

    public long getTotalMileage() {
        return totalMileage;
    }

    public void setTotalMileage(long totalMileage) {
        this.totalMileage = totalMileage;
    }

    public float getOutsideTemp() {
        return outsideTemp;
    }

    public void setOutsideTemp(float outsideTemp) {
        this.outsideTemp = outsideTemp;
    }

    public int getRevolutions() {
        return revolutions;
    }

    public void setRevolutions(int revolutions) {
        this.revolutions = revolutions;
    }

    public int getCurSpeed() {
        return curSpeed;
    }

    public void setCurSpeed(int curSpeed) {
        this.curSpeed = curSpeed;
    }
    public boolean isCelsius() {
 		return isCelsius;
 	}

 	public void setCelsius(boolean isCelsius) {
 		this.isCelsius = isCelsius;
 	}

    public int getOilConsumption() {
		return oilConsumption;
	}

	public void setOilConsumption(int oilConsumption) {
		this.oilConsumption = oilConsumption;
	}

	@Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(averSpeed);
        dest.writeInt(mileage);
        dest.writeLong(totalMileage);
        dest.writeDouble(outsideTemp);
        dest.writeByte((byte) (isCelsius?0:1));
        dest.writeInt(revolutions);
        dest.writeInt(curSpeed);
        dest.writeInt(oilConsumption);
    }

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CarRunInfo [averSpeed=");
		builder.append(averSpeed);
		builder.append(", mileage=");
		builder.append(mileage);
		builder.append(", totalMileage=");
		builder.append(totalMileage);
		builder.append(", outsideTemp=");
		builder.append(outsideTemp);
		builder.append(", isCelsius=");
		builder.append(isCelsius);
		builder.append(", revolutions=");
		builder.append(revolutions);
		builder.append(", oilConsumption=");
		builder.append(oilConsumption);
		builder.append(", curSpeed=");
		builder.append(curSpeed);
		builder.append("]");
		return builder.toString();
	}

	
    
}
