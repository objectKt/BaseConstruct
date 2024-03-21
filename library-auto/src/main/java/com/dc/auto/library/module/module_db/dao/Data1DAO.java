package com.dc.auto.library.module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dc.auto.library.module.module_db.entity.DataTable1;

import java.util.List;

@Dao
public interface Data1DAO {

    @Query("SELECT * FROM data1_table WHERE deviceId= :deviceId")
    List<DataTable1> queryAll(String deviceId);

    @Insert
    void insert(DataTable1 table);

    @Query("DELETE FROM data1_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable1 table);
}
