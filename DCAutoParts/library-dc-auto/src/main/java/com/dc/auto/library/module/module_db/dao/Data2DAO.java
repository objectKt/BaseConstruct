package com.dc.auto.library.module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import com.dc.auto.library.module.module_db.entity.DataTable2;

import java.util.List;

@Dao
public interface Data2DAO {

    @Query("SELECT * FROM data2_table WHERE deviceId= :deviceId")
    List<DataTable2> queryAll(String deviceId);

    @Insert
    void insert(DataTable2 table);

    @Query("DELETE FROM data2_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable2 table);
}
