package com.dc.auto.library.module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dc.auto.library.module.module_db.entity.DataTable3;

import java.util.List;

@Dao
public interface Data3DAO {

    @Query("SELECT * FROM data3_table WHERE deviceId= :deviceId")
    List<DataTable3> queryAll(String deviceId);

    @Insert
    void insert(DataTable3 table);

    @Query("DELETE FROM data3_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable3 table);
}
