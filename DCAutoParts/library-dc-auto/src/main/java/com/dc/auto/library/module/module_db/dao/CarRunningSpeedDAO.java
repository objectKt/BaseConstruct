package com.dc.auto.library.module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import com.dc.auto.library.module.module_db.entity.CarRunningSpeedTable;

@Dao
public interface CarRunningSpeedDAO {

    @Query("SELECT * FROM RUNNING_SPEED_TABLE WHERE deviceId= :deviceId")
    List<CarRunningSpeedTable> queryAll(String deviceId);

    @Insert
    void insert(CarRunningSpeedTable table);

    @Query("DELETE FROM RUNNING_SPEED_TABLE WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);
}
