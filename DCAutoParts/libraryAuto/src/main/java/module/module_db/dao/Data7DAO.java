package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import module.module_db.entity.DataTable6;
import module.module_db.entity.DataTable7;

@Dao
public interface Data7DAO {

    @Query("SELECT * FROM data7_table WHERE deviceId= :deviceId")
    List<DataTable7> queryAll(String deviceId);

    @Insert
    void insert(DataTable7 table);

    @Query("DELETE FROM data7_table WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);


    @Update
    void update(DataTable7 table);
}
