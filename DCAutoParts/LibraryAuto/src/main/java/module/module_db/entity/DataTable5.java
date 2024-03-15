package module.module_db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import module.module_db.DBTableNames;

/**
* @description:
* @createDate: 2023/8/27
*/
@Entity(tableName = DBTableNames.TB_5)
public class DataTable5 {

    @PrimaryKey
    @NonNull
    private String deviceId = "";

    private String data1 = "";
    private String data2 = "";
    private String data3 = "";
    private String data4 = "";
    private String data5 = "";
    private String data6 = "";
    private String data7 = "";
    private String data8 = "";
    private String data9 = "";
    private String data10 = "";

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    public String getData1() {
        return data1;
    }

    public void setData1(String data1) {
        this.data1 = data1;
    }

    public String getData2() {
        return data2;
    }

    public void setData2(String data2) {
        this.data2 = data2;
    }

    public String getData3() {
        return data3;
    }

    public void setData3(String data3) {
        this.data3 = data3;
    }

    public String getData4() {
        return data4;
    }

    public void setData4(String data4) {
        this.data4 = data4;
    }

    public String getData5() {
        return data5;
    }

    public void setData5(String data5) {
        this.data5 = data5;
    }

    public String getData6() {
        return data6;
    }

    public void setData6(String data6) {
        this.data6 = data6;
    }

    public String getData7() {
        return data7;
    }

    public void setData7(String data7) {
        this.data7 = data7;
    }

    public String getData8() {
        return data8;
    }

    public void setData8(String data8) {
        this.data8 = data8;
    }

    public String getData9() {
        return data9;
    }

    public void setData9(String data9) {
        this.data9 = data9;
    }

    public String getData10() {
        return data10;
    }

    public void setData10(String data10) {
        this.data10 = data10;
    }

    @Override
    public String toString() {
        return "DataTable5{" +
                "deviceId='" + deviceId + '\'' +
                ", data1='" + data1 + '\'' +
                ", data2='" + data2 + '\'' +
                ", data3='" + data3 + '\'' +
                ", data4='" + data4 + '\'' +
                ", data5='" + data5 + '\'' +
                ", data6='" + data6 + '\'' +
                ", data7='" + data7 + '\'' +
                ", data8='" + data8 + '\'' +
                ", data9='" + data9 + '\'' +
                ", data10='" + data10 + '\'' +
                '}';
    }
}
