package module.module_db.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import java.util.List;

import module.module_db.AppDatabase;
import module.module_db.dao.CarSpeedDAO;
import module.module_db.dao.CarTravelDAO;
import module.module_db.entity.CarSpeedTable;
import module.module_db.entity.CarTravelTable;

/**
* @description:
* @createDate: 2023/5/29
*/
public class CarTravelRepository {

    private static final String TAG = CarTravelRepository.class.getSimpleName();

    private CarTravelRepository() {}

    private static class SingletonHolder {
        private static final CarTravelRepository instance = new CarTravelRepository();
    }

    public static CarTravelRepository getInstance() {
        return SingletonHolder.instance;
    }

    public synchronized void saveData(Context context, CarTravelTable table){
        try {
            if(table!=null){
//                Log.i(TAG, "saveData---CarTravelTable="+table.toString());
                CarTravelDAO dao = AppDatabase.getDatabase(context).carTravelDAO();
                dao.insert(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized void updateData(Context context, CarTravelTable table){
        try {
            if(table!=null){
//                Log.i(TAG, "updateData---CarTravelTable="+table.toString());
                CarTravelDAO dao = AppDatabase.getDatabase(context).carTravelDAO();
                dao.update(table);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllData(Context context, String deviceId){
        try {
            Log.i(TAG, "deleteAllData---deviceId="+deviceId);
            CarTravelDAO dao = AppDatabase.getDatabase(context).carTravelDAO();
            dao.deleteAllByDeviceId(deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public synchronized CarTravelTable getData(Context context, String deviceId){
        try {
            if(!TextUtils.isEmpty(deviceId)){
//                Log.i(TAG,"getData---deviceId="+deviceId);
                CarTravelDAO dao = AppDatabase.getDatabase(context).carTravelDAO();
                List<CarTravelTable> tables = dao.queryAll(deviceId);
//                Log.i(TAG,"getData---tables="+tables);
                if(tables!=null && tables.size() > 0){
                    return tables.get(0);
                }else{
                    CarTravelTable travelTable = new CarTravelTable();
                    travelTable.setDeviceId(deviceId);
                    travelTable.setStartComputeTime(System.currentTimeMillis());
                    saveData(context, travelTable);
                    return travelTable;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
