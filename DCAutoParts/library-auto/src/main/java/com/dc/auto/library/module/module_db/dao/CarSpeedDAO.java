package com.dc.auto.library.module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.dc.auto.library.module.module_db.entity.CarSpeedTable;

import java.util.List;

@Dao
public interface CarSpeedDAO {

    @Query("SELECT * FROM SPEED_TABLE WHERE deviceId= :deviceId")
    List<CarSpeedTable> queryAll(String deviceId);

    @Insert
    void insert(CarSpeedTable table);

    @Query("DELETE FROM SPEED_TABLE WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);
}
