package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import module.module_db.entity.DataTable10;
import module.module_db.entity.DataTable9;

@Dao
public interface Data10DAO {

    @Query("SELECT * FROM data10_table WHERE deviceId= :deviceId")
    List<DataTable10> queryAll(String deviceId);

    @Insert
    void insert(DataTable10 table);

    @Query("DELETE FROM data10_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable10 table);
}
