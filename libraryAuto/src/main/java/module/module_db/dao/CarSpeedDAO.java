package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import module.module_db.entity.CarSpeedTable;

@Dao
public interface CarSpeedDAO {

    @Query("SELECT * FROM SPEED_TABLE WHERE deviceId= :deviceId")
    List<CarSpeedTable> queryAll(String deviceId);

    @Insert
    void insert(CarSpeedTable table);

    @Query("DELETE FROM SPEED_TABLE WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);
}
