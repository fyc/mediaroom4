package com.sunmnet.mediaroom.brand.data.database.face;

import org.greenrobot.greendao.DaoException;
import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Generated;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.ToMany;
import org.greenrobot.greendao.annotation.Unique;

import java.util.List;
import com.sunmnet.mediaroom.brand.data.database.gen.DaoSession;
import com.sunmnet.mediaroom.brand.data.database.gen.FaceInfoDao;
import com.sunmnet.mediaroom.brand.data.database.gen.UserInfoDao;

@Entity
public class UserInfo {

    @Id
    private String id;
    @Unique
    private String code;
    @Unique
    private String username;
    private String name;
    private Long modify;
    private String iconUrl;
    @ToMany(referencedJoinProperty = "id")
    private List<FaceInfo> faceInfoList;
    /** Used to resolve relations */
    @Generated(hash = 2040040024)
    private transient DaoSession daoSession;
    /** Used for active entity operations. */
    @Generated(hash = 437952339)
    private transient UserInfoDao myDao;
    @Generated(hash = 1821611494)
    public UserInfo(String id, String code, String username, String name, Long modify, String iconUrl) {
        this.id = id;
        this.code = code;
        this.username = username;
        this.name = name;
        this.modify = modify;
        this.iconUrl = iconUrl;
    }
    @Generated(hash = 1279772520)
    public UserInfo() {
    }
    public String getId() {
        return this.id;
    }
    public void setId(String id) {
        this.id = id;
    }
    public String getCode() {
        return this.code;
    }
    public void setCode(String code) {
        this.code = code;
    }
    public String getName() {
        return this.name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getModify() {
        return this.modify;
    }
    public void setModify(Long modify) {
        this.modify = modify;
    }
    /**
     * To-many relationship, resolved on first access (and after reset).
     * Changes to to-many relations are not persisted, make changes to the target entity.
     */
    @Generated(hash = 237216221)
    public List<FaceInfo> getFaceInfoList() {
        if (faceInfoList == null) {
            final DaoSession daoSession = this.daoSession;
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            FaceInfoDao targetDao = daoSession.getFaceInfoDao();
            List<FaceInfo> faceInfoListNew = targetDao._queryUserInfo_FaceInfoList(id);
            synchronized (this) {
                if (faceInfoList == null) {
                    faceInfoList = faceInfoListNew;
                }
            }
        }
        return faceInfoList;
    }
    /** Resets a to-many relationship, making the next get call to query for a fresh result. */
    @Generated(hash = 729977792)
    public synchronized void resetFaceInfoList() {
        faceInfoList = null;
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
    public String getIconUrl() {
        return this.iconUrl;
    }
    public void setIconUrl(String iconUrl) {
        this.iconUrl = iconUrl;
    }
    /** called by internal mechanisms, do not call yourself. */
    @Generated(hash = 821180768)
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getUserInfoDao() : null;
    }
    public String getUsername() {
        return this.username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
}
