package com.dc.auto.library.module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import com.dc.auto.library.module.module_db.entity.DataTable8;

@Dao
public interface Data8DAO {

    @Query("SELECT * FROM data8_table WHERE deviceId= :deviceId")
    List<DataTable8> queryAll(String deviceId);

    @Insert
    void insert(DataTable8 table);

    @Query("DELETE FROM data8_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable8 table);
}
