package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import module.module_db.entity.DataTable2;
import module.module_db.entity.DataTable3;

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
