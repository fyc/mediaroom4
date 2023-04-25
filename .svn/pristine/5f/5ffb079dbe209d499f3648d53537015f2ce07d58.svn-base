package com.sunmnet.mediaroom.brand.data.database.face;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Keep;
import org.greenrobot.greendao.annotation.ToOne;
import com.sunmnet.mediaroom.brand.data.database.gen.DaoSession;
import com.sunmnet.mediaroom.brand.data.database.gen.UserInfoDao;
import com.sunmnet.mediaroom.brand.data.database.gen.FaceInfoDao;

@Entity
public class FaceInfo {

    @Id
    private String faceId;
    private String id;

    @ToOne(joinProperty = "id")
    private UserInfo userInfo;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 866319006)
    private transient FaceInfoDao myDao;

    @Generated(hash = 1003586454)
    public FaceInfo() {
    }

    @Generated(hash = 235474564)
    public FaceInfo(String faceId, String id) {
        this.faceId = faceId;
        this.id = id;
    }

    public String getFaceId() {
        return this.faceId;
    }

    public void setFaceId(String faceId) {
        this.faceId = faceId;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String code) {
        this.id = code;
    }

    @Generated(hash = 1652967691)
    private transient String userInfo__resolvedKey;

    /** To-one relationship, resolved on first access. */
    @Generated(hash = 216351626)
    @Keep
    public UserInfo getUserInfo() {
        String __key = this.id;
        if (userInfo__resolvedKey == null || !userInfo__resolvedKey.equals(__key)) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UserInfoDao targetDao = daoSession.getUserInfoDao();
            UserInfo userInfoNew = targetDao.queryBuilder().where(UserInfoDao.Properties.Id.eq(__key)).build().unique();
            synchronized (this) {
                userInfo = userInfoNew;
                userInfo__resolvedKey = __key;
            }
        }
        return userInfo;
    }


    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#delete(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 128553479)
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.delete(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#refresh(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 1942392019)
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.refresh(this);
    }

    /**
     * Convenient call for {@link org.greenrobot.greendao.AbstractDao#update(Object)}.
     * Entity must attached to an entity context.
     */
    @Generated(hash = 713229351)
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }
        myDao.update(this);
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1399724684)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getFaceInfoDao() : null;
    }

    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 1185749663)
    public void setUserInfo(UserInfo userInfo) {
        synchronized (this) {
            this.userInfo = userInfo;
            id = userInfo == null ? null : userInfo.getId();
            userInfo__resolvedKey = id;
        }
    }

}
