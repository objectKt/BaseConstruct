package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import module.module_db.entity.DataTable4;
import module.module_db.entity.DataTable5;

@Dao
public interface Data5DAO {

    @Query("SELECT * FROM data5_table WHERE deviceId= :deviceId")
    List<DataTable5> queryAll(String deviceId);

    @Insert
    void insert(DataTable5 table);

    @Query("DELETE FROM data5_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable5 table);
}
