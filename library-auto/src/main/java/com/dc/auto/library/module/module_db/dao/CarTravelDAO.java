package com.dc.auto.library.module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dc.auto.library.module.module_db.entity.CarTravelTable;

import java.util.List;

@Dao
public interface CarTravelDAO {

    @Query("SELECT * FROM TRAVEL_TABLE WHERE deviceId= :deviceId")
    List<CarTravelTable> queryAll(String deviceId);

    @Insert
    void insert(CarTravelTable table);

    @Query("DELETE FROM TRAVEL_TABLE WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);

    @Update
    void update(CarTravelTable table);
}
