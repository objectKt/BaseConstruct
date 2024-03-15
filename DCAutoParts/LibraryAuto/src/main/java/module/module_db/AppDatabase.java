package module.module_db;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.migration.Migration;
import androidx.sqlite.db.SupportSQLiteDatabase;

import module.module_db.dao.CarRunningSpeedDAO;
import module.module_db.dao.CarSpeedDAO;
import module.module_db.dao.CarTravelDAO;
import module.module_db.dao.Data10DAO;
import module.module_db.dao.Data1DAO;
import module.module_db.dao.Data2DAO;
import module.module_db.dao.Data3DAO;
import module.module_db.dao.Data4DAO;
import module.module_db.dao.Data5DAO;
import module.module_db.dao.Data6DAO;
import module.module_db.dao.Data7DAO;
import module.module_db.dao.Data8DAO;
import module.module_db.dao.Data9DAO;
import module.module_db.entity.CarRunningSpeedTable;
import module.module_db.entity.CarSpeedTable;
import module.module_db.entity.CarTravelTable;
import module.module_db.entity.DataTable1;
import module.module_db.entity.DataTable10;
import module.module_db.entity.DataTable2;
import module.module_db.entity.DataTable3;
import module.module_db.entity.DataTable4;
import module.module_db.entity.DataTable5;
import module.module_db.entity.DataTable6;
import module.module_db.entity.DataTable7;
import module.module_db.entity.DataTable8;
import module.module_db.entity.DataTable9;

@Database(entities = {CarSpeedTable.class, CarRunningSpeedTable.class, CarTravelTable.class,DataTable1.class, DataTable2.class, DataTable3.class, DataTable4.class, DataTable5.class, DataTable6.class, DataTable7.class, DataTable8.class, DataTable9.class, DataTable10.class}, exportSchema = false, version = 3)
public abstract class AppDatabase extends RoomDatabase {
    public abstract CarSpeedDAO carSpeedDAO();
    public abstract CarTravelDAO carTravelDAO();
    public abstract CarRunningSpeedDAO carRunningSpeedDAO();
    public abstract Data1DAO data1DAO();
    public abstract Data2DAO data2DAO();
    public abstract Data3DAO data3DAO();
    public abstract Data4DAO data4DAO();
    public abstract Data5DAO data5DAO();
    public abstract Data6DAO data6DAO();
    public abstract Data7DAO data7DAO();
    public abstract Data8DAO data8DAO();
    public abstract Data9DAO data9DAO();
    public abstract Data10DAO data10DAO();

    private static AppDatabase INSTANCE = null;

    public static AppDatabase getDatabase(Context context) {
        if (INSTANCE == null) {
            synchronized (AppDatabase.class) {
                INSTANCE = Room.databaseBuilder(
                        context.getApplicationContext(),
                        AppDatabase.class,
                        "db_car223_left"
                ).fallbackToDestructiveMigration().build();
            }
        }
        return INSTANCE;
    }

}
