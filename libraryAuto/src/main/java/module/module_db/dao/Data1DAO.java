package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import module.module_db.entity.CarSpeedTable;
import module.module_db.entity.DataTable1;

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
