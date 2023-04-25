package com.sunmnet.mediaroom.brand.data.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.sunmnet.mediaroom.brand.data.database.gen.DaoMaster;
import com.sunmnet.mediaroom.brand.data.database.gen.FaceInfoDao;
import com.sunmnet.mediaroom.brand.data.database.gen.UserInfoDao;
import com.sunmnet.mediaroom.brand.utils.FaceHelper;

import org.greenrobot.greendao.database.Database;

public class BrandOpenHelper extends DaoMaster.OpenHelper {
    public BrandOpenHelper(Context context, String name) {
        super(context, name);
    }

    public BrandOpenHelper(Context context, String name, SQLiteDatabase.CursorFactory factory) {
        super(context, name, factory);
    }

    @Override
    public void onUpgrade(Database db, int oldVersion, int newVersion) {
        Log.i("greenDAO", "Upgrading schema from version " + oldVersion + " to " + newVersion + " by dropping all tables");
        // 修改了 userinfo表和faceInfo表 还需要清空人脸数据
        if (oldVersion < 5) {
            upgradeVersion4(db);
        } else {
            DaoMaster.dropAllTables(db, true);
            DaoMaster.createAllTables(db, true);
        }
    }

    private void upgradeVersion4(Database db) {
        UserInfoDao.dropTable(db, true);
        UserInfoDao.createTable(db, true);
        FaceInfoDao.dropTable(db, true);
        FaceInfoDao.createTable(db, true);
        FaceHelper.getInstance().deleteAllFace();
    }
}
