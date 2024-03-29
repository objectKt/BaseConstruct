package com.android.launcher.dao;

import java.util.Map;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.AbstractDaoSession;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.identityscope.IdentityScopeType;
import org.greenrobot.greendao.internal.DaoConfig;

import com.android.launcher.entity.CheckBoxCarInfo;
import com.android.launcher.entity.CheckBoxEntity;
import com.android.launcher.entity.Mile;
import com.android.launcher.entity.MileSet;
import com.android.launcher.entity.MileStart;
import com.android.launcher.entity.Param;
import com.android.launcher.entity.Song;

import com.android.launcher.dao.CheckBoxCarInfoDao;
import com.android.launcher.dao.CheckBoxEntityDao;
import com.android.launcher.dao.MileDao;
import com.android.launcher.dao.MileSetDao;
import com.android.launcher.dao.MileStartDao;
import com.android.launcher.dao.ParamDao;
import com.android.launcher.dao.SongDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.

/**
 * {@inheritDoc}
 * 
 * @see org.greenrobot.greendao.AbstractDaoSession
 */
public class DaoSession extends AbstractDaoSession {

    private final DaoConfig checkBoxCarInfoDaoConfig;
    private final DaoConfig checkBoxEntityDaoConfig;
    private final DaoConfig mileDaoConfig;
    private final DaoConfig mileSetDaoConfig;
    private final DaoConfig mileStartDaoConfig;
    private final DaoConfig paramDaoConfig;
    private final DaoConfig songDaoConfig;

    private final CheckBoxCarInfoDao checkBoxCarInfoDao;
    private final CheckBoxEntityDao checkBoxEntityDao;
    private final MileDao mileDao;
    private final MileSetDao mileSetDao;
    private final MileStartDao mileStartDao;
    private final ParamDao paramDao;
    private final SongDao songDao;

    public DaoSession(Database db, IdentityScopeType type, Map<Class<? extends AbstractDao<?, ?>>, DaoConfig>
            daoConfigMap) {
        super(db);

        checkBoxCarInfoDaoConfig = daoConfigMap.get(CheckBoxCarInfoDao.class).clone();
        checkBoxCarInfoDaoConfig.initIdentityScope(type);

        checkBoxEntityDaoConfig = daoConfigMap.get(CheckBoxEntityDao.class).clone();
        checkBoxEntityDaoConfig.initIdentityScope(type);

        mileDaoConfig = daoConfigMap.get(MileDao.class).clone();
        mileDaoConfig.initIdentityScope(type);

        mileSetDaoConfig = daoConfigMap.get(MileSetDao.class).clone();
        mileSetDaoConfig.initIdentityScope(type);

        mileStartDaoConfig = daoConfigMap.get(MileStartDao.class).clone();
        mileStartDaoConfig.initIdentityScope(type);

        paramDaoConfig = daoConfigMap.get(ParamDao.class).clone();
        paramDaoConfig.initIdentityScope(type);

        songDaoConfig = daoConfigMap.get(SongDao.class).clone();
        songDaoConfig.initIdentityScope(type);

        checkBoxCarInfoDao = new CheckBoxCarInfoDao(checkBoxCarInfoDaoConfig, this);
        checkBoxEntityDao = new CheckBoxEntityDao(checkBoxEntityDaoConfig, this);
        mileDao = new MileDao(mileDaoConfig, this);
        mileSetDao = new MileSetDao(mileSetDaoConfig, this);
        mileStartDao = new MileStartDao(mileStartDaoConfig, this);
        paramDao = new ParamDao(paramDaoConfig, this);
        songDao = new SongDao(songDaoConfig, this);

        registerDao(CheckBoxCarInfo.class, checkBoxCarInfoDao);
        registerDao(CheckBoxEntity.class, checkBoxEntityDao);
        registerDao(Mile.class, mileDao);
        registerDao(MileSet.class, mileSetDao);
        registerDao(MileStart.class, mileStartDao);
        registerDao(Param.class, paramDao);
        registerDao(Song.class, songDao);
    }
    
    public void clear() {
        checkBoxCarInfoDaoConfig.clearIdentityScope();
        checkBoxEntityDaoConfig.clearIdentityScope();
        mileDaoConfig.clearIdentityScope();
        mileSetDaoConfig.clearIdentityScope();
        mileStartDaoConfig.clearIdentityScope();
        paramDaoConfig.clearIdentityScope();
        songDaoConfig.clearIdentityScope();
    }

    public CheckBoxCarInfoDao getCheckBoxCarInfoDao() {
        return checkBoxCarInfoDao;
    }

    public CheckBoxEntityDao getCheckBoxEntityDao() {
        return checkBoxEntityDao;
    }

    public MileDao getMileDao() {
        return mileDao;
    }

    public MileSetDao getMileSetDao() {
        return mileSetDao;
    }

    public MileStartDao getMileStartDao() {
        return mileStartDao;
    }

    public ParamDao getParamDao() {
        return paramDao;
    }

    public SongDao getSongDao() {
        return songDao;
    }

}
