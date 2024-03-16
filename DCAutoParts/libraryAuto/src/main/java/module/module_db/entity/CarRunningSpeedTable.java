package module.module_db.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

import module.module_db.DBTableNames;

/**
* @description: 启动后的速度（汽车启动到最后熄火这段时间的）
* @createDate: 2023/8/27
*/
@Entity(tableName = DBTableNames.TB_RUNNING_SPEED)
public class CarRunningSpeedTable {

    @PrimaryKey
    @NonNull
    private String id = UUID.randomUUID().toString();

    private String deviceId = "";

    private float speed = 0.0f;

    private String data1 = "";
    private String data2 = "";


    @NonNull
    public String getId() {
        return id;
    }

    public void setId(@NonNull String id) {
        this.id = id;
    }

    @NonNull
    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(@NonNull String deviceId) {
        this.deviceId = deviceId;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
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


    @Override
    public String toString() {
        return "CarRunningSpeedTable{" +
                "id='" + id + '\'' +
                ", deviceId='" + deviceId + '\'' +
                ", speed=" + speed +
                ", data1='" + data1 + '\'' +
                ", data2='" + data2 + '\'' +
                '}';
    }
}
