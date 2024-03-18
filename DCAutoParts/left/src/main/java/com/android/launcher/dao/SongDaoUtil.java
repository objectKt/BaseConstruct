package com.android.launcher.dao;

import android.content.Context;

import com.android.launcher.entity.Song;

import java.util.List;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * DAO for table "SONG".
*/
public class SongDaoUtil  {

    private static final String TAG = SongDao.class.getSimpleName();

    private DaoManager mManager;

    public SongDaoUtil(Context context){
        mManager = DaoManager.getInstance();
        mManager.init(context);
    }

    /**
     * 完成记录的插入，如果表未创建，先创建表
     * @return
     */
//    public boolean insert(Song song){
//        boolean flag = false;
//        flag = mManager.getDaoSession().get().insert(song) == -1 ? false : true;
//        //   Log.i(TAG, "insert TestOrder :" + flag + "-->" + TestOrder.toString());
//        return flag;
//    }

    /**
     *
     * 插入多条数据，在子线程操作
     *
     * @return
     */
    public boolean insertMult(final List<Song> songs) {
        boolean flag = false;
        try {
            mManager.getDaoSession().runInTx(new Runnable() {
                @Override
                public void run() {
                    for (Song song : songs) {
//                        Log.i("aaaaa",song+"=======================");
                        mManager.getDaoSession().insertOrReplace(song);
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
            mManager.getDaoSession().deleteAll(Song.class);
            flag = true;
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查询所有记录
     * @return
     */
    public List<Song> queryAll(){
        return mManager.getDaoSession().loadAll(Song.class);
    }






}