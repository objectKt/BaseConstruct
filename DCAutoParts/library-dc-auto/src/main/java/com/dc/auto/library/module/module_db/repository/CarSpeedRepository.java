package com.dc.auto.library.module.module_db.repository;

import android.content.Context;
import android.text.TextUtils;
import android.util.Log;

import com.dc.auto.library.module.module_db.AppDatabase;
import com.dc.auto.library.module.module_db.dao.CarSpeedDAO;
import com.dc.auto.library.module.module_db.entity.CarSpeedTable;

import java.util.List;

/**
* @description:
* @createDate: 2023/5/29
*/
public class CarSpeedRepository {

    private static final String TAG = CarSpeedRepository.class.getSimpleName();

    private CarSpeedRepository() {}

    private static class SingletonHolder {
        private static final CarSpeedRepository instance = new CarSpeedRepository();
    }

    public static CarSpeedRepository getInstance() {
        return SingletonHolder.instance;
    }

    public void saveData(Context context, CarSpeedTable carSpeedTable){
        try {
            if(carSpeedTable!=null){
                Log.i(TAG, "saveData---carSpeedTable="+carSpeedTable.toString());
                CarSpeedDAO dao = AppDatabase.getDatabase(context).carSpeedDAO();
                dao.insert(carSpeedTable);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void deleteAllData(Context context, String deviceId){
        try {
            Log.i(TAG, "deleteAllData---deviceId="+deviceId);
            CarSpeedDAO dao = AppDatabase.getDatabase(context).carSpeedDAO();
            dao.deleteAllByDeviceId(deviceId);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<CarSpeedTable> getData(Context context, String deviceId){
        try {
            if(!TextUtils.isEmpty(deviceId)){
                CarSpeedDAO dao = AppDatabase.getDatabase(context).carSpeedDAO();
                List<CarSpeedTable> carSpeedTables = dao.queryAll(deviceId);
                Log.i(TAG,"getData---deviceId="+deviceId+", carSpeedTables="+carSpeedTables);
               return carSpeedTables;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
