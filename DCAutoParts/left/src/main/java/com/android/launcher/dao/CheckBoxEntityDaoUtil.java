package com.android.launcher.dao;

import android.content.Context;

import com.android.launcher.entity.CheckBoxEntity;

import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "SONG".
*/
public class CheckBoxEntityDaoUtil {

    private static final String TAG = CheckBoxEntityDao.class.getSimpleName();

    private DaoManager mManager;

    public CheckBoxEntityDaoUtil(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成记录的插入，如果表未创建，先创建表
     * @return
     */
    public boolean insert(CheckBoxEntity checkBoxEntity){
        boolean flag = false;
        flag = mManager.getDaoSession().insertOrReplace(checkBoxEntity) == -1 ? false : true;
        return flag;
    }

    /**
     *
     * 插入多条数据，在子线程操作
     *
     * @return
     */
    public boolean insertMult(final List<CheckBoxEntity> checkBoxEntities) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (CheckBoxEntity checkBoxEntity : checkBoxEntities) {
//                        Log.i("aaaaa",song+"=======================");
                        mManager.getDaoSession().insert(checkBoxEntity);
                    }
                }
            });
            flag = true;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return flag;
    }


    /**
     * 删除所有记录
     */
    public void deleteAll(){
        boolean flag = false;
        try {
            //按照id删除
            mManager.getDaoSession().deleteAll(CheckBoxEntity.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<CheckBoxEntity> queryAll(){
        return mManager.getDaoSession().loadAll(CheckBoxEntity.class);
    }


//
//    /**
//     * 查询所有记录
//     * @return
//     */
//    public Mile queryMile(){
//        return mManager.getDaoSession().queryBuilder(Mile.class).where(MileDao.Properties.CurrentStatus.eq("Y")).unique() ;
//    }
}
