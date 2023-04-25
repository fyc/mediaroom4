package com.sunmnet.mediaroom.brand.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.text.TextUtils;

import com.sunmnet.mediaroom.brand.api.ApiManager;
import com.sunmnet.mediaroom.brand.bean.response.ObjectResponse;
import com.sunmnet.mediaroom.brand.common.base.DeviceApp;
import com.sunmnet.mediaroom.brand.common.utils.DownloadUtil;
import com.sunmnet.mediaroom.brand.data.database.face.FaceInfo;
import com.sunmnet.mediaroom.brand.data.database.face.UserInfo;
import com.sunmnet.mediaroom.brand.rx.SingleTaskObserver;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.sphinx.face.entity.FacePoint;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import okhttp3.Response;

public class FaceInfoSyncManager {


    //人脸信息同步线程池
    private ThreadPoolExecutor threadPoolExecutor;
    private static FaceInfoSyncManager mInstance;

    public static FaceInfoSyncManager getInstance() {//管理器初始化
        if (mInstance == null) {
            synchronized (FaceInfoSyncManager.class) {
                if (mInstance == null) {
                    mInstance = new FaceInfoSyncManager();
                }
            }
        }
        return mInstance;
    }

    private SyncFinishWorker finishWorker;


    public boolean isSyncing() {
        return threadPoolExecutor != null && threadPoolExecutor.getActiveCount() > 0;
    }

    public boolean cancel() {
        if (threadPoolExecutor != null && !threadPoolExecutor.isTerminated()) {
            RunningLog.run("停止同步人脸信息任务");
            ToastUtil.show(DeviceApp.getInstance(), "停止同步人脸信息任务");
            threadPoolExecutor.shutdownNow();
            if (finishWorker != null && (finishWorker.isAlive() || !finishWorker.isInterrupted())) {
                finishWorker.interrupt();
            }
            try {
                return threadPoolExecutor.awaitTermination(10, TimeUnit.SECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return false;
        } else {
            return true;
        }
    }

    public void startSyncTask(List<SyncFaceTask> taskList, long modify) {
        cancel();
        if (!FaceHelper.getInstance().isInited()) {
            RunningLog.run("人脸模型未初始化，不进行人脸同步操作");
            ToastUtil.show(DeviceApp.getInstance(), "人脸模型未初始化，不进行人脸同步操作");
            return;
        }
        if (threadPoolExecutor == null || threadPoolExecutor.isShutdown()) {
            threadPoolExecutor = new ThreadPoolExecutor(0, 5, 30, TimeUnit.SECONDS, new LinkedBlockingQueue<>());
        }
        CountDownLatch syncTaskCountDownLatch = new CountDownLatch(taskList.size());
        finishWorker = new SyncFinishWorker(threadPoolExecutor, syncTaskCountDownLatch, modify);
        finishWorker.start();
        RunningLog.run("开始同步人脸信息，数量：" + taskList.size());
        ToastUtil.show(DeviceApp.getInstance(), "开始同步人脸信息，数量：" + taskList.size());
        for (SyncFaceTask task : taskList) {
            task.setFinishWorker(finishWorker);
            threadPoolExecutor.execute(task);
        }
    }


    static class SyncFinishWorker extends Thread {

        CountDownLatch countDownLatch;
        long modify;
        ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();

        int modifyCount = 0;

        private List<SyncFaceTask> errorTaskList;
        private ThreadPoolExecutor threadPoolExecutor;

        public SyncFinishWorker(ThreadPoolExecutor thread, CountDownLatch countDownLatch, long modify) {
            this.countDownLatch = countDownLatch;
            this.modify = modify;
            this.threadPoolExecutor = thread;
        }

        @Override
        public void run() {
            try {
                countDownLatch.await();
                int roundCount = 0;
                //处理失败任务
                while (roundCount < 30 && errorTaskList != null && errorTaskList.size() > 0) {
                    roundCount++;
                    RunningLog.run("同步人脸失败任务数：" + errorTaskList.size());
                    RunningLog.run("等待5分钟后重新开始失败任务");
                    Thread.sleep(TimeUnit.MINUTES.toMillis(5));
                    List<SyncFaceTask> temp = errorTaskList;
                    errorTaskList = null;
                    countDownLatch = new CountDownLatch(temp.size());
                    for (SyncFaceTask task : temp) {
                        task.setFinishWorker(this);
                        threadPoolExecutor.execute(task);
                    }
                    countDownLatch.await();
                }
                if (roundCount > 0) {
                    RunningLog.run("处理失败任务循环次数： " + roundCount);
                }
                //写入处理完成数据
                SharedPreferences preferences = DeviceApp.getApp().getSharedPreferences("faceSync", Context.MODE_PRIVATE);
                preferences.edit().putLong("currentModify", modify).apply();
                ReentrantReadWriteLock.ReadLock readLock = readWriteLock.readLock();
                readLock.lock();
                RunningLog.run("人脸信息同步完成, 本次修改数量: " + modifyCount);
                ToastUtil.show(DeviceApp.getInstance(), "人脸信息同步完成, 本次修改数量: " + modifyCount);
                readLock.unlock();
                ApiManager.getSysApi().syncFaceResult(preferences.getString("id", null), 1)
                        .subscribe(new SingleTaskObserver<ObjectResponse>() {
                            @Override
                            public void onNext(ObjectResponse response) {
                                if (response != null) {
                                    RunningLog.run("反馈人脸同步结果，success: " + response.isSuccess() + " msg:" + response.isSuccess());
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                RunningLog.run("反馈人脸同步结果失败 " + e.getMessage());
                            }
                        });
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        public synchronized void errorTask(SyncFaceTask task) {
            if (errorTaskList == null) {
                errorTaskList = new LinkedList<>();
            }
            errorTaskList.add(task);
        }

        public void countDown() {
            countDownLatch.countDown();
        }

        public void count() {
            try {
                ReentrantReadWriteLock.WriteLock writeLock = readWriteLock.writeLock();
                writeLock.lockInterruptibly();
                modifyCount += 1;
                writeLock.unlock();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static class SyncFaceTask implements Runnable {

        String id;
        String code;
        String username;
        String name;
        String imgUrl;
        long modify;

        SyncFinishWorker finishWorker;

        public void setFinishWorker(SyncFinishWorker finishWorker) {
            this.finishWorker = finishWorker;
        }

        public SyncFaceTask(String id, String code, String username, String name, String imgUrl, long modify) {
            this.id = id;
            this.code = code;
            this.username = username;
            this.name = name;
            this.imgUrl = imgUrl;
            this.modify = modify;
        }

        @Override
        public void run() {
            Response response = null;
            InputStream is = null;
            ByteArrayOutputStream os = null;
            UserInfo userInfo = DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getUserInfoDao().load(id);
            if (TextUtils.isEmpty(id) || TextUtils.isEmpty(imgUrl) || (userInfo != null && userInfo.getModify() >= modify)) {
                if (finishWorker != null) {
                    finishWorker.countDown();
                }
                return;
            }
            try {
                String url;
                if (imgUrl.startsWith("http://")) {
                    url = imgUrl;
                } else if (imgUrl.startsWith("/") || imgUrl.startsWith("\\")) {
                    url = DeviceApp.getApp().getPlServerAddr() + imgUrl;
                } else {
                    url = DeviceApp.getApp().getPlServerAddr_Slash() + imgUrl;
                }
                url = url.replaceAll("\\\\", "/");
                response = DownloadUtil.getInstance().downloadFile(url);
                if (response != null && response.code() == HttpURLConnection.HTTP_OK && response.body() != null) {
                    is = response.body().byteStream();
                    os = new ByteArrayOutputStream();
                    byte[] b = new byte[1024];
                    int len;
                    while ((len = is.read(b)) > 0) {
                        os.write(b, 0, len);
                    }
                    byte[] imgBytes = os.toByteArray();
                    if (userInfo != null) {
                        if (userInfo.getFaceInfoList() != null && userInfo.getFaceInfoList().size() > 0) {
                            ArrayList<String> faceIds = new ArrayList<>();
                            for (FaceInfo faceInfo : userInfo.getFaceInfoList()) {
                                FaceHelper.getInstance().deleteFace(faceInfo.getFaceId());
                                faceIds.add(faceInfo.getFaceId());
                            }
                            DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getFaceInfoDao().deleteByKeyInTx(faceIds);
                        }
                        userInfo.setCode(code);
                        userInfo.setUsername(username);
                        userInfo.setName(name);
                        userInfo.setModify(modify);
                        userInfo.setIconUrl(imgUrl);
                    } else {
                        userInfo = new UserInfo(id, code, username, name, modify, imgUrl);
                    }
                    Bitmap bitmap = BitmapFactory.decodeByteArray(imgBytes, 0, imgBytes.length);
                    if (!FaceHelper.getInstance().isInited()) {
                        FaceHelper.getInstance().init();
                    }
                    List<FacePoint> pointList = FaceHelper.getInstance().faceDetect(bitmap, 50);
                    if (pointList == null || pointList.size() == 0) {
                        RunningLog.debug("图片检测不到人脸, url: " + imgUrl);
                    } else {
                        FacePoint facePoint = null;
                        RunningLog.debug("检测完毕，id: " + id);
                        if (pointList.size() == 1) {
                            facePoint = pointList.get(0);
                        } else {
                            RunningLog.debug("人脸数量不唯一, 选其中最大人脸，url: " + imgUrl);
                            float lastArea = 0;
                            for (FacePoint point : pointList) {
                                float width = Math.abs(point.getRight() - point.getLeft());
                                float height = Math.abs(point.getRight() - point.getLeft());
                                float area = width * height;
                                if (facePoint == null || area > lastArea) {
                                    lastArea = area;
                                    facePoint = point;
                                }
                            }
                        }
                        RunningLog.debug("准备添加人脸数据");
                        // Bitmap temp = FaceHelper.getInstance().alignFace(facePoint, bitmap);
                        // String faceId = FaceHelper.getInstance().addFace(id, temp);
                        // temp.recycle();

                        float[] faceVector = FaceHelper.getInstance().extract(bitmap, facePoint);
                        if (faceVector == null) {
                            RunningLog.error("id:" + id + " 人脸特征值提取失败");
                        } else {
                            String faceId = FaceHelper.getInstance().addFace(id, faceVector);
                            if (!TextUtils.isEmpty(faceId)) {
                                finishWorker.count();
                                RunningLog.debug("用户id: " + id + " 添加人脸成功,faceId:" + faceId);
                                DaoManager.getInstance().getDaoSession(DeviceApp.getApp()).getUserInfoDao().insertOrReplace(userInfo);
                            } else {
                                RunningLog.error("添加人脸失败，id: " + id);
                                finishWorker.errorTask(this);
                            }
                        }
                    }
                    bitmap.recycle();
                }
            } catch (IOException e) {
                e.printStackTrace();
                RunningLog.error("人脸同步任务出错: " + e.getMessage());
                finishWorker.errorTask(this);
            } finally {
                try {
                    if (is != null) {
                        is.close();
                    }
                    if (os != null) {
                        os.close();
                    }
                    if (response != null && response.body() != null) {
                        response.body().close();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (finishWorker != null) {
                finishWorker.countDown();
            }
        }
    }

}
