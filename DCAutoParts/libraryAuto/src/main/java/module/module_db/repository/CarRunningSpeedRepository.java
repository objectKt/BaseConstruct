package module.module_db.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import module.module_db.AppDatabase;
import module.module_db.dao.CarRunningSpeedDAO;
import module.module_db.dao.CarSpeedDAO;
import module.module_db.entity.CarRunningSpeedTable;
import module.module_db.entity.CarSpeedTable;

/**
* @description:
* @createDate: 2023/5/29
*/
public class CarRunningSpeedRepository {

    private static final String TAG = CarRunningSpeedRepository.class.getSimpleName();

    private CarRunningSpeedRepository() {}

    private static class SingletonHolder {
        private static final CarRunningSpeedRepository instance = new CarRunningSpeedRepository();
    }

    public static CarRunningSpeedRepository getInstance() {
        return SingletonHolder.instance;
    }

    public void saveData(Context context, CarRunningSpeedTable carSpeedTable){
        try {
            if(carSpeedTable!=null){
                Log.i(TAG, "saveData---CarRunningSpeedTable="+carSpeedTable.toString());
                CarRunningSpeedDAO dao = AppDatabase.getDatabase(context).carRunningSpeedDAO();
                dao.insert(carSpeedTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllData(Context context, String deviceId){
        try {
            Log.i(TAG, "deleteAllData---deviceId="+deviceId);
            CarRunningSpeedDAO dao = AppDatabase.getDatabase(context).carRunningSpeedDAO();
            dao.deleteAllByDeviceId(deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CarRunningSpeedTable> getData(Context context, String deviceId){
        try {
            if(!TextUtils.isEmpty(deviceId)){
                CarRunningSpeedDAO dao = AppDatabase.getDatabase(context).carRunningSpeedDAO();
                List<CarRunningSpeedTable> carSpeedTables = dao.queryAll(deviceId);
                if(carSpeedTables !=null){
                    Log.i(TAG,"getData---deviceId="+deviceId+", carSpeedTables.size="+carSpeedTables.size());
                }
               return carSpeedTables;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
