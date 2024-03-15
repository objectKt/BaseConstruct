package module.module_db.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import module.module_db.entity.CarTravelTable;

@Dao
public interface CarTravelDAO {

    @Query("SELECT * FROM TRAVEL_TABLE WHERE deviceId= :deviceId")
    List<CarTravelTable> queryAll(String deviceId);

    @Insert
    void insert(CarTravelTable table);

    @Query("DELETE FROM TRAVEL_TABLE WHERE deviceId= :deviceId")
    void deleteAllByDeviceId(String deviceId);

    @Update
    void update(CarTravelTable table);
}
