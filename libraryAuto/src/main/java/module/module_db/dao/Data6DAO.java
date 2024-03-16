package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import module.module_db.entity.DataTable5;
import module.module_db.entity.DataTable6;

@Dao
public interface Data6DAO {

    @Query("SELECT * FROM data6_table WHERE deviceId= :deviceId")
    List<DataTable6> queryAll(String deviceId);

    @Insert
    void insert(DataTable6 table);

    @Query("DELETE FROM data6_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable6 table);
}
