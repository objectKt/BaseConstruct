package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import module.module_db.entity.DataTable8;
import module.module_db.entity.DataTable9;

@Dao
public interface Data9DAO {

    @Query("SELECT * FROM data9_table WHERE deviceId= :deviceId")
    List<DataTable9> queryAll(String deviceId);

    @Insert
    void insert(DataTable9 table);

    @Query("DELETE FROM data9_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable9 table);
}
