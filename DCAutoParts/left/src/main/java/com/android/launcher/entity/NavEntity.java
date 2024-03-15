package com.android.launcher.entity;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * @date： 2023/11/30
 * @author: 78495
*/
public class NavEntity implements Parcelable {

    private String latitude;
    private String longitude;
    private int strategyConvert;
    private int selectRouteId;


    public NavEntity(String latitude, String longitude, int strategyConvert, int selectRouteId) {
        this.latitude = latitude;
        this.longitude = longitude;
        this.strategyConvert = strategyConvert;
        this.selectRouteId = selectRouteId;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public int getStrategyConvert() {
        return strategyConvert;
    }

    public void setStrategyConvert(int strategyConvert) {
        this.strategyConvert = strategyConvert;
    }

    public int getSelectRouteId() {
        return selectRouteId;
    }

    public void setSelectRouteId(int selectRouteId) {
        this.selectRouteId = selectRouteId;
    }

    @Override
    public String toString() {
        return "NavEntity{" +
                "latitude='" + latitude + '\'' +
                ", longitude='" + longitude + '\'' +
                ", strategyConvert=" + strategyConvert +
                ", selectRouteId=" + selectRouteId +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.latitude);
        dest.writeString(this.longitude);
        dest.writeInt(this.strategyConvert);
        dest.writeInt(this.selectRouteId);
    }

    protected NavEntity(Parcel in) {
        this.latitude = in.readString();
        this.longitude = in.readString();
        this.strategyConvert = in.readInt();
        this.selectRouteId = in.readInt();
    }

    public static final Parcelable.Creator<NavEntity> CREATOR = new Parcelable.Creator<NavEntity>() {
        @Override
        public NavEntity createFromParcel(Parcel source) {
            return new NavEntity(source);
        }

        @Override
        public NavEntity[] newArray(int size) {
            return new NavEntity[size];
        }
    };
}
