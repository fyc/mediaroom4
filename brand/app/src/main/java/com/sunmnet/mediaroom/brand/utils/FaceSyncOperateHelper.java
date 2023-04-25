package com.sunmnet.mediaroom.brand.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sunmnet.mediaroom.brand.bean.face.SyncFaceAccount;
import com.sunmnet.mediaroom.brand.bean.face.SyncFaceInfo;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.data.database.face.FaceInfo;
import com.sunmnet.mediaroom.brand.data.database.face.UserInfo;
import com.sunmnet.mediaroom.brand.data.database.gen.FaceInfoDao;
import com.sunmnet.mediaroom.brand.data.database.gen.UserInfoDao;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;

import org.greenrobot.greendao.query.QueryBuilder;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;

public class FaceSyncOperateHelper {

    /**
     * 清除无效人脸数据
     */
    public void clearInvalidFaceData(Collection<String> ids) {
        FaceInfoDao faceInfoDao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getFaceInfoDao();
        QueryBuilder<FaceInfo> faceInfoQueryBuilder = faceInfoDao.queryBuilder();
        // 总人脸列表
        List<FaceInfo> faceInfoList = faceInfoQueryBuilder.build().list();
        if (!ids.isEmpty()) {
            // 找出不存在用户id数据
            /** 不用 List<FaceInfo> faceInfoList = faceInfoQueryBuilder.where(FaceInfoDao.Properties.Id.notIn(ids)).build().list();
             *  的原因是，它会引起android.database.sqlite.SQLiteException: too many SQL variables (code 1) 异常。
             */
            // 需要删除的人脸列表
            List<FaceInfo> toDeleteFaceInfoList = new ArrayList<>();
            if (faceInfoList != null && !faceInfoList.isEmpty()) {
                for (FaceInfo faceInfo : faceInfoList) {
                    boolean needDelete = true;
                    for (String id : ids) {
                        if (faceInfo.getId().equals(id)) {
                            needDelete = false;
                            break;
                        }
                    }
                    if (needDelete) {
                        toDeleteFaceInfoList.add(faceInfo);
                    }
                }
            }
            // List<FaceInfo> faceInfoList = faceInfoQueryBuilder.where(FaceInfoDao.Properties.Id.notIn(ids)).build().list();
            if (!toDeleteFaceInfoList.isEmpty()) {
                for (FaceInfo faceInfo : toDeleteFaceInfoList) {
                    //删除faceDb中的数据
                    FaceHelper.getInstance().deleteFace(faceInfo.getFaceId());
                }
                //删除所有faceInfo数据
                faceInfoDao.deleteInTx(toDeleteFaceInfoList);
            }
        } else {
            for (FaceInfo faceInfo : faceInfoList) {
                //删除faceDb中的数据
                FaceHelper.getInstance().deleteFace(faceInfo.getFaceId());
            }
            //删除所有faceInfo数据
            faceInfoDao.deleteInTx(faceInfoList);
        }
    }


    /**
     * 清除无效用户数据
     */
    public void clearInvalidUserData(Collection<String> ids) {
        UserInfoDao userInfoDao = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getUserInfoDao();
        // 用户信息列表
        List<UserInfo> list = userInfoDao.queryBuilder().list();
        if (!ids.isEmpty()) {
            /** 不用 List<UserInfo> list = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Id.notIn(ids)).build().list();
             *  的原因是，它会引起android.database.sqlite.SQLiteException: too many SQL variables (code 1) 异常。
             */
            // 要删除的用户信息列表
            List<UserInfo> toDeleteUserInfoList = new ArrayList<>();
            if (!list.isEmpty()) {
                for (UserInfo userInfo : list) {
                    boolean needDelete = true;
                    for (String id : ids) {
                        if (userInfo.getId().equals(id)) {
                            needDelete = false;
                            break;
                        }
                    }
                    if (needDelete) {
                        toDeleteUserInfoList.add(userInfo);
                    }
                }
            }
            // List<UserInfo> list = userInfoDao.queryBuilder().where(UserInfoDao.Properties.Id.notIn(ids)).build().list();
            if (!toDeleteUserInfoList.isEmpty()) {
                userInfoDao.deleteInTx(toDeleteUserInfoList);
            }
        } else {
            userInfoDao.deleteInTx(list);
        }
    }

    /**
     * 同步人脸数据
     */
    public void syncFace(@NonNull SyncFaceInfo syncFaceInfo) {
        List<FaceInfoSyncManager.SyncFaceTask> syncFaceTasks = new ArrayList<>();
        HashSet<String> ids = new HashSet<>();
        for (SyncFaceAccount account : syncFaceInfo.getAccountList()) {
            ids.add(account.getId());
            syncFaceTasks.add(new FaceInfoSyncManager.SyncFaceTask(account.getId(), account.getCode(), account.getUsername(), account.getNameCn(), account.getFaceUrl(), account.getModifyTime().getTime()));
        }
        ids.remove(null);
        ids.remove("");
        clearInvalidFaceData(ids);
        clearInvalidUserData(ids);
        FaceInfoSyncManager.getInstance().startSyncTask(syncFaceTasks, syncFaceInfo.getModifyTime());
    }

    public long getRemoteModify() {
        SharedPreferences preferences = DeviceApp.getApp().getSharedPreferences("faceSync", Context.MODE_PRIVATE);
        //已同步人脸信息的修改时间
        return preferences.getLong("remoteModify", 0);
    }

    public long getCurrentModify() {
        SharedPreferences preferences = DeviceApp.getApp().getSharedPreferences("faceSync", Context.MODE_PRIVATE);
        //正在使用的人脸信息的修改时间
        return preferences.getLong("currentModify", 0);
    }


    @Nullable
    public SyncFaceInfo getFaceSyncInfo() {
        SharedPreferences preferences = DeviceApp.getApp().getSharedPreferences("faceSync", Context.MODE_PRIVATE);
        String dataStr = preferences.getString("data", null);
        if (dataStr == null)
            return null;
        return JacksonUtil.jsonStrToBean(dataStr, SyncFaceInfo.class);
    }

    public void saveFaceSyncInfo(String id, String json, long modify) {
        SharedPreferences preferences = DeviceApp.getApp().getSharedPreferences("faceSync", Context.MODE_PRIVATE);
        preferences.edit().putString("data", json).putLong("remoteModify", modify).putString("id", id).apply();
    }
}
