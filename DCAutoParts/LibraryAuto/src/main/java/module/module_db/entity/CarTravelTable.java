package module.module_db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import module.module_db.DBTableNames;

/**
* @description: 行程
 *
 * 平均油耗 = 总消耗的燃料量 / 总行驶距离
 * 平均时速 = 总行驶距离 / 总行驶时间
* @createDate: 2023/8/28
*/
@Entity(tableName = DBTableNames.TB_TRAVEL)
public class CarTravelTable {

    @PrimaryKey
    @NonNull
    private String deviceId = "";

    //用户行驶的里程，可以重置为0
    private float userMile = 0.0f;
    //总行驶的里程
    private float totalMile = 0.0f;
    //小计里程，达到一百公里，计算油耗，把值累加到totalMile和userMile，然后重置为0
    private float subtotalMile = 0.0f;


    //小计消耗的汽油，计算一次油耗，把值累加到userConsumeOil和totalConsumeOil，然后重置
    private float subtotalConsumeOil = 0.0f;
    //用户消耗的汽油量
    private float userConsumeOil = 0f;
    //总的消耗的汽油
    private float totalConsumeOil = 0f;

    //小计平均时速, 每一百公里更新一次值
    private float subtotalAverageSpeed = 0.0f;
    private float userAverageSpeed = 0.0f;
    private float totalAverageSpeed = 0.0f;


    private long totalRunTime = 0L;
    private long userRunTime = 0L;
    //小计，汽车运行时间，达到一百公里后，重置为0
    private long subtotalRuntime = 0L;


    private long startComputeTime = 0L;

    //当前油耗
    private float currentQtrip = 0.0f;
    //用户平均油耗
    private float userAverageQtrip = 0.0f;
    //总的平均油耗
    private float totalAverageQtrip = 0.0f;

    private String data1 = "";
    private String data2 = "";
    private String data3 = "";


    @NonNull
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    public float getUserMile() {
        return userMile;
    }

    public void setUserMile(float userMile) {
        this.userMile = userMile;
    }

    public float getSubtotalMile() {
        return subtotalMile;
    }

    public void setSubtotalMile(float subtotalMile) {
        this.subtotalMile = subtotalMile;
    }

    public float getSubtotalConsumeOil() {
        return subtotalConsumeOil;
    }

    public void setSubtotalConsumeOil(float subtotalConsumeOil) {
        this.subtotalConsumeOil = subtotalConsumeOil;
    }

    public long getUserRunTime() {
        return userRunTime;
    }

    public void setUserRunTime(long userRunTime) {
        this.userRunTime = userRunTime;
    }

    public float getUserConsumeOil() {
        return userConsumeOil;
    }

    public void setUserConsumeOil(float userConsumeOil) {
        this.userConsumeOil = userConsumeOil;
    }

    public float getTotalMile() {
        return totalMile;
    }

    public void setTotalMile(float totalMile) {
        this.totalMile = totalMile;
    }

    public long getTotalRunTime() {
        return totalRunTime;
    }

    public void setTotalRunTime(long totalRunTime) {
        this.totalRunTime = totalRunTime;
    }

    public float getTotalConsumeOil() {
        return totalConsumeOil;
    }

    public void setTotalConsumeOil(float totalConsumeOil) {
        this.totalConsumeOil = totalConsumeOil;
    }

    public long getStartComputeTime() {
        return startComputeTime;
    }

    public void setStartComputeTime(long startComputeTime) {
        this.startComputeTime = startComputeTime;
    }

    public float getCurrentQtrip() {
        return currentQtrip;
    }

    public void setCurrentQtrip(float currentQtrip) {
        this.currentQtrip = currentQtrip;
    }

    public float getUserAverageQtrip() {
        return userAverageQtrip;
    }

    public void setUserAverageQtrip(float userAverageQtrip) {
        this.userAverageQtrip = userAverageQtrip;
    }

    public float getTotalAverageQtrip() {
        return totalAverageQtrip;
    }

    public void setTotalAverageQtrip(float totalAverageQtrip) {
        this.totalAverageQtrip = totalAverageQtrip;
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

    public float getSubtotalAverageSpeed() {
        return subtotalAverageSpeed;
    }

    public void setSubtotalAverageSpeed(float subtotalAverageSpeed) {
        this.subtotalAverageSpeed = subtotalAverageSpeed;
    }

    public float getUserAverageSpeed() {
        return userAverageSpeed;
    }

    public void setUserAverageSpeed(float userAverageSpeed) {
        this.userAverageSpeed = userAverageSpeed;
    }

    public float getTotalAverageSpeed() {
        return totalAverageSpeed;
    }

    public void setTotalAverageSpeed(float totalAverageSpeed) {
        this.totalAverageSpeed = totalAverageSpeed;
    }

    public long getSubtotalRuntime() {
        return subtotalRuntime;
    }

    public void setSubtotalRuntime(long subtotalRuntime) {
        this.subtotalRuntime = subtotalRuntime;
    }

    @Override
    public String toString() {
        return "CarTravelTable{" +
                "deviceId='" + deviceId + '\'' +
                ", userMile=" + userMile +
                ", totalMile=" + totalMile +
                ", subtotalMile=" + subtotalMile +
                ", subtotalConsumeOil=" + subtotalConsumeOil +
                ", userConsumeOil=" + userConsumeOil +
                ", totalConsumeOil=" + totalConsumeOil +
                ", subTotalAverageSpeed=" + subtotalAverageSpeed +
                ", userAverageSpeed=" + userAverageSpeed +
                ", totalAverageSpeed=" + totalAverageSpeed +
                ", totalRunTime=" + totalRunTime +
                ", userRunTime=" + userRunTime +
                ", subtotalRuntime=" + subtotalRuntime +
                ", startComputeTime=" + startComputeTime +
                ", currentQtrip=" + currentQtrip +
                ", userAverageQtrip=" + userAverageQtrip +
                ", totalAverageQtrip=" + totalAverageQtrip +
                ", data1='" + data1 + '\'' +
                ", data2='" + data2 + '\'' +
                ", data3='" + data3 + '\'' +
                '}';
    }
}
