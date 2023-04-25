package com.sunmnet.mediaroom.brand.utils;

import android.content.Context;

import com.sunmnet.mediaroom.brand.data.database.BrandOpenHelper;
import com.sunmnet.mediaroom.brand.data.database.gen.DaoMaster;
import com.sunmnet.mediaroom.brand.data.database.gen.DaoSession;

import org.greenrobot.greendao.database.Database;

public class DaoManager {

    private static final String DB_NAME = "brand-db";

    //多线程中要被共享的使用volatile关键字修饰
    private volatile static DaoManager manager;

    private static DaoSession daoSession;

    /**
     * 单例模式获得操作数据库对象
     *
     * @return
     */
    public synchronized static DaoManager getInstance() {
        if (manager == null) {
            manager = new DaoManager();
        }
        return manager;
    }

    public DaoSession getDaoSession(Context context) {
        synchronized (DaoManager.class) {
            if (daoSession == null && context != null) {
                Database db = new BrandOpenHelper(context, DB_NAME).getWritableDb();
                DaoMaster daoMaster = new DaoMaster(db);
                daoSession = daoMaster.newSession();
            }
        }
        return daoSession;
    }

    public void close() {
        if (daoSession != null) {
            daoSession.clear();
            daoSession.getDatabase().close();
            daoSession = null;
        }
    }
}
