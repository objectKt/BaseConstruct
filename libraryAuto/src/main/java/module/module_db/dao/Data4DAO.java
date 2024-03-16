package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import module.module_db.entity.DataTable3;
import module.module_db.entity.DataTable4;

@Dao
public interface Data4DAO {

    @Query("SELECT * FROM data4_table WHERE deviceId= :deviceId")
    List<DataTable4> queryAll(String deviceId);

    @Insert
    void insert(DataTable4 table);

    @Query("DELETE FROM data4_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable4 table);
}
