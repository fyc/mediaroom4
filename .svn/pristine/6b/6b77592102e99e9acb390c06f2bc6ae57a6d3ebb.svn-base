package com.sunmnet.mediaroom.brand.utils;

import android.graphics.Bitmap;
import android.os.Build;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Base64;

import com.sunmnet.mediaroom.brand.bean.face.FaceModel;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.data.database.face.FaceInfo;
import com.sunmnet.mediaroom.brand.data.database.face.UserInfo;
import com.sunmnet.mediaroom.common.tools.JacksonUtil;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.mediaroom.util.MD5Util;
import com.sunmnet.sphinx.face.dao.FaceDao;
import com.sunmnet.sphinx.face.entity.Face;
import com.sunmnet.sphinx.face.entity.FacePoint;
import com.sunmnet.sphinx.face.interfaces.FaceEngine;
import com.sunmnet.sphinx.face.utils.FaceDbUtil;

import org.opencv.core.Mat;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Semaphore;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class FaceHelper {

    private volatile AtomicBoolean inited = new AtomicBoolean(false);
    private volatile FaceEngine faceEngine;
    private static FaceHelper instance;
    private Semaphore detectLock = new Semaphore(1);

    public static String hostInfoName = "host.info";
    public static String hostKeyName = "host.key";

    private ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public static String getModelPath() {
        return Environment.getExternalStorageDirectory() + "/Sphinx/";
    }

    public static File getModelPathFile() {
        return new File(getModelPath());
    }

    private static boolean checkModel() {
        File root = new File(getModelPath());
        List<String> copyFiles = null;
        FaceModel faceModel = null;
        try {
            InputStream is = DeviceApp.getApp().getAssets().open("face/md5.json");
            byte[] bytes = new byte[is.available()];
            is.read(bytes);
            is.close();
            String s = new String(bytes);
            faceModel = JacksonUtil.jsonStrToBean(s, FaceModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (faceModel == null)
            return false;
        if (root.exists() && root.isDirectory()) {
            for (FaceModel.ModelFile modelFile : faceModel.getModelFile()) {
                File file = new File(root, modelFile.getName());
                if (!file.exists() || !file.isFile() || !file.canRead()) {
                    if (copyFiles == null) {
                        copyFiles = new ArrayList<>();
                    }
                    copyFiles.add(modelFile.getName());
                } else {
                    String fileMD5String = null;
                    try {
                        fileMD5String = MD5Util.getFileMD5String(file);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    if (fileMD5String == null || !fileMD5String.equalsIgnoreCase(modelFile.getMd5())) {
                        if (copyFiles == null) {
                            copyFiles = new ArrayList<>();
                        }
                        copyFiles.add(modelFile.getName());
                    }
                }
            }
        } else {
            copyFiles = new ArrayList<>();
            for (FaceModel.ModelFile modelFile : faceModel.getModelFile()) {
                copyFiles.add(modelFile.getName());
            }
        }
        if (copyFiles == null)
            return true;
        for (String fileName : copyFiles) {
            InputStream is = null;
            FileOutputStream fos = null;
            try {
                is = DeviceApp.getApp().getAssets().open("face/" + fileName);
                File outFile = new File(root, fileName);
                if (!outFile.getParentFile().exists())
                    outFile.getParentFile().mkdirs();
                else if (outFile.exists())
                    outFile.delete();
                outFile.createNewFile();
                fos = new FileOutputStream(outFile);
                byte[] temp = new byte[1024];
                int i = 0;
                while ((i = is.read(temp)) > 0) {
                    fos.write(temp, 0, i);
                }
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            } finally {
                if (is != null) {
                    try {
                        is.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                if (fos != null) {
                    try {
                        fos.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return true;
    }

    public static FaceHelper getInstance() {
        if (instance == null) {
            instance = new FaceHelper();
        }
        return instance;
    }

    public synchronized void init() {
        long count = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getFaceInfoDao().count();
        RunningLog.run("当前人脸库数量大小：" + count);
        int size = TypeUtil.objToInt(count);
        size = (5000 * (size / 5000 + 1));
        if (size < 25000) {
            size = 25000;
        }
        RunningLog.run("初始化人脸识别索引大小：" + size);
        init(size);
    }

    public synchronized void init(int size) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            return;
        }
        if (!inited.get() && checkModel()) {
            if (faceEngine == null) {
                faceEngine = FaceEngine.getInstance(getModelPath(), size);
                RunningLog.run("人脸初始化,faceEngine:" + faceEngine);
            }
            if (faceEngine != null) {
                inited.compareAndSet(false, true);
            }
        }
        RunningLog.run("人脸初始化,inited:" + inited.get());
    }

    public boolean isInited() {
        return inited.get();
    }

    public List<FacePoint> faceDetect(Bitmap bitmap, float faceSizeRate) {
        try {
            detectLock.acquire();
            if (faceEngine != null) {
                readWriteLock.readLock().lock();
                List<FacePoint> list = faceEngine.maxfaceDetect(bitmap, faceSizeRate);
                readWriteLock.readLock().unlock();
                detectLock.release();
                return list;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public List<FacePoint> faceDetect(Bitmap bitmap, int faceSize) {
        try {
            detectLock.acquire();
            if (isInited() && faceEngine != null) {
                readWriteLock.readLock().lock();
                List<FacePoint> list = faceEngine.faceDetect(bitmap, faceSize);
                readWriteLock.readLock().unlock();
                detectLock.release();
                return list;
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;

    }

    public String addFace(String id, Bitmap bitmap) {
        if (isInited() && faceEngine != null) {
            readWriteLock.writeLock().lock();
            float[] faceVector = faceEngine.featureExtraction(bitmap);
            String faceId = null;
            try {
                faceId = faceEngine.addToDB(id, faceVector);
            } catch (Throwable t) {
                RunningLog.error("添加人脸出错, uid: " + id + "\n" + t.getMessage());
                RunningLog.error(t);
                try {
                    FaceDao faceDao = FaceDbUtil.getInstances().getDaoSession().getFaceDao();
                    List<Face> list = faceDao.queryBuilder().where(FaceDao.Properties.Name.eq(id)).build().list();
                    for (Face face : list) {
                        faceEngine.deleteFromDB(face.getFaceId());
                    }
                    RunningLog.error("回滚人脸数据, count: " + list.size());
                } catch (Throwable t2) {
                    RunningLog.error("回滚删除人脸数据出错, uid: " + id);
                    RunningLog.error(t2);
                }
            }
            if (!TextUtils.isEmpty(faceId)) {
                DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getFaceInfoDao().insert(new FaceInfo(faceId, id));
            }
            readWriteLock.writeLock().unlock();
            return faceId;
        }
        return null;
    }

    /**
     * 添加人脸特征值到数据库
     * @param id
     * @param faceVector
     * @return
     */
    public String addFace(String id, float[] faceVector) {
        if (isInited() && faceEngine != null) {
            readWriteLock.writeLock().lock();
            String faceId = null;
            try {
                faceId = faceEngine.addToDB(id, faceVector);
            } catch (Throwable t) {
                RunningLog.error("添加人脸出错, uid: " + id + "\n" + t.getMessage());
                RunningLog.error(t);
                try {
                    FaceDao faceDao = FaceDbUtil.getInstances().getDaoSession().getFaceDao();
                    List<Face> list = faceDao.queryBuilder().where(FaceDao.Properties.Name.eq(id)).build().list();
                    for (Face face : list) {
                        faceEngine.deleteFromDB(face.getFaceId());
                    }
                    RunningLog.error("回滚人脸数据, count: " + list.size());
                } catch (Throwable t2) {
                    RunningLog.error("回滚删除人脸数据出错, uid: " + id);
                    RunningLog.error(t2);
                }
            }
            if (!TextUtils.isEmpty(faceId)) {
                DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getFaceInfoDao().insert(new FaceInfo(faceId, id));
            }
            readWriteLock.writeLock().unlock();
            return faceId;
        }
        return null;
    }

    public Map<String, Float> searchFace(Bitmap bitmap, int maxNum) {
        if (isInited() && faceEngine != null) {
            readWriteLock.readLock().lock();
            float[] faceVector = faceEngine.featureExtraction(bitmap);
            Map<String, Float> result = faceEngine.searchFace(faceVector, maxNum);
            readWriteLock.readLock().unlock();
            return result;
        }
        return null;
    }

    public Map<String, Float> searchFace(Bitmap bitmap, FacePoint facePoint, int maxNum) {
        if (isInited() && faceEngine != null) {
            readWriteLock.readLock().lock();
            float[] faceVector = faceEngine.Extract(bitmap, facePoint.getPoints());
            Map<String, Float> result = faceEngine.searchFace(faceVector, maxNum);
            readWriteLock.readLock().unlock();
            return result;
        }
        return null;
    }

    public Map<String, Float> searchFace(Bitmap bitmap, int maxNum, float similarity) {
        if (isInited() && faceEngine != null) {
            readWriteLock.readLock().lock();
            float[] faceVector = faceEngine.featureExtraction(bitmap);
            Map<String, Float> result = faceEngine.searchFace(faceVector, maxNum, similarity);
            readWriteLock.readLock().unlock();
            return result;
        }
        return null;
    }

    /**
     * 比对人脸
     * @param bitmap
     * @param facePoint
     * @param maxNum
     * @param similarity
     * @return
     */
    public Map<String, Float> searchFace(Bitmap bitmap, FacePoint facePoint, int maxNum, float similarity) {
        if (isInited() && faceEngine != null) {
            Map<String, Float> result = new LinkedHashMap<>();
            readWriteLock.readLock().lock();
            float[] faceVector = faceEngine.Extract(bitmap, facePoint.getPoints());
            Map<String, Float> searchResult = faceEngine.searchFace(faceVector, maxNum);
            for (Map.Entry<String, Float> entry : searchResult.entrySet()) {
                double sqrtResult = Math.sqrt(Math.sqrt(entry.getValue()));
                if (sqrtResult > similarity) {
                    result.put(entry.getKey(), (float) sqrtResult);
                }
            }
            readWriteLock.readLock().unlock();
            return result;
        }
        return null;
    }

    /**
     * @param mat       BGR格式，摄像头原始数据
     * @param facePoint 人脸检测结果
     */
    public float isLive(Mat mat, FacePoint facePoint) {
        if (isInited() && faceEngine != null) {
            return faceEngine.isLive(mat, (int) facePoint.getLeft(), (int) facePoint.getTop(), (int) facePoint.getRight(), (int) facePoint.getBottom());
        }
        return 0;
    }

    public UserInfo getUserByFaceId(String faceId) {
        FaceInfo faceInfo = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getFaceInfoDao().load(faceId);
        if (faceInfo != null)
            return faceInfo.getUserInfo();
        return null;
    }


    public void deleteFace(String faceId) {
        if (isInited() && faceEngine != null) {
            readWriteLock.writeLock().lock();
            faceEngine.deleteFromDB(faceId);
            readWriteLock.writeLock().unlock();
        }
    }

    public void deleteAllFace() {
        readWriteLock.writeLock().lock();
        FaceDbUtil.getInstances().getDaoSession().getFaceDao().deleteAll();
        if (isInited() && faceEngine != null) {
            faceEngine.deleteAllItems();
        }
        readWriteLock.writeLock().unlock();
    }

    public Bitmap alignFace(FacePoint facePoint, Bitmap bitmap) {
        return inited.get() && faceEngine != null ? faceEngine.align(bitmap, facePoint.getPoints(), (int) facePoint.getLeft(), (int) facePoint.getTop(), bitmap.getWidth(), bitmap.getHeight()) : null;
    }

    public String getHostInfoBase64() {
        File file = new File(FaceHelper.getModelPath(), FaceHelper.hostInfoName);
        if (!file.exists() || !file.canRead())
            return null;
        String infoBase64 = null;
        FileInputStream fileInputStream = null;
        try {
            fileInputStream = new FileInputStream(file);
            byte[] bytes = new byte[fileInputStream.available()];
            fileInputStream.read(bytes);
            infoBase64 = Base64.encodeToString(bytes, Base64.NO_WRAP);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fileInputStream != null) {
                try {
                    fileInputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return infoBase64;
    }

    /**
     * 人脸特征提取
     * @param bitmap
     * @param facePoint
     * @return
     */
    public float[] extract(Bitmap bitmap, FacePoint facePoint) {
        return inited.get() && faceEngine != null ? faceEngine.Extract(bitmap, facePoint.getPoints()) : null;
    }
}
