package com.sunmnet.mediaroom.brand.fragment.dialog;


import android.Manifest;
import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v4.content.PermissionChecker;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.sunmnet.mediaroom.brand.R;
import com.sunmnet.mediaroom.brand.data.database.face.UserInfo;
import com.sunmnet.mediaroom.brand.utils.DisplayUtil;
import com.sunmnet.mediaroom.brand.utils.FaceHelper;
import com.sunmnet.mediaroom.brand.view.CvCameraView;
import com.sunmnet.mediaroom.common.tools.RunningLog;
import com.sunmnet.mediaroom.common.tools.ToastUtil;
import com.sunmnet.mediaroom.common.tools.TypeUtil;
import com.sunmnet.sphinx.face.entity.FacePoint;

import org.opencv.android.Utils;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

public abstract class AbstractFaceRecogDialogFragment extends DialogFragment implements CvCameraView.CvCameraViewListener {

    protected static final String TAG = "FaceRecogDialog";

    protected CvCameraView cvCameraView;
    //识别线程
    protected ScheduledExecutorService recogScheduledExecutor;


    protected Bitmap detectCacheBitmap;
    protected BlockingQueue<Mat> recogQueue = new LinkedBlockingQueue<>(1);
    protected BlockingQueue<Mat> cacheQueue = new LinkedBlockingQueue<>(1);

    protected boolean cameraReady = false;
    protected float mRelativeFaceSize = 0.3f;
    protected int mAbsoluteFaceSize = 0;

    protected float minSimilarity = 0.88f;
    protected float bioThreshold = 0.5f;

    protected AtomicBoolean enableRecog = new AtomicBoolean(true);

    protected abstract FaceRecogListener getFaceRecogListener();


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        initCameraView(view);
    }

    protected abstract CvCameraView getCameraView(View view);

    protected void initCameraView(View view) {
        cvCameraView = getCameraView(view);
        cvCameraView.setCameraViewListener(this);
        cvCameraView.setOpaque(false);
    }

    @SuppressLint("WrongConstant")
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
            ToastUtil.show(getContext(), "人脸识别功能不支持4.4及以下系统");
            RunningLog.run("人脸识别功能不支持4.4及以下系统，关闭人脸登录窗口");
            dismiss();
            return;
        }
        if (!FaceHelper.getInstance().isInited()) {
            ToastUtil.show(getContext(), "人脸识别模块未加载完成");
            RunningLog.run("人脸识别模块未加载完成，关闭人脸登录窗口");
            dismiss();
            return;
        }
        if (PermissionChecker.checkSelfPermission(getActivity(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED) {
            requestPermissions(new String[]{Manifest.permission.CAMERA}, 1);
        } else {
            cameraReady = true;
        }

        float f = TypeUtil.objToFloatDef(getResources().getString(R.string.minSimilarity), 0.88f);
        if (f >= 0) {
            minSimilarity = f;
        }
        float f2 = TypeUtil.objToFloatDef(getResources().getString(R.string.bioThreshold), 0.5f);
        if (f2 >= 0) {
            bioThreshold = f2;
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        if (requestCode == 1 && grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            cameraReady = true;
        } else {
            showAlert();
        }
    }

    private void showAlert() {
        AlertDialog ad = new AlertDialog.Builder(getContext()).create();
        ad.setCancelable(false);
        ad.setMessage(getContext().getResources().getString(R.string.msg_connect_camera_fail));
        ad.setButton(DialogInterface.BUTTON_NEUTRAL, "OK", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
                dismiss();
            }
        });
        ad.show();
    }

    @Override
    public void onResume() {
        super.onResume();
        DisplayUtil.hideNavigationBar(getDialog().getWindow());
        if (cameraReady) {
            cvCameraView.enableView();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        if (cameraReady)
            cvCameraView.disableView();
    }


    protected void startRecogThread() {
        if (recogScheduledExecutor != null) {
            stopRecogThread();
        }
        recogScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        RecogFaceWorker recogFaceWorker = new RecogFaceWorker(detectCacheBitmap, recogQueue, cacheQueue, minSimilarity, bioThreshold);
        recogFaceWorker.setAbsoluteFaceSize(mAbsoluteFaceSize);
        recogFaceWorker.setFaceRecogListener(getFaceRecogListener());
        recogScheduledExecutor.scheduleWithFixedDelay(recogFaceWorker, 20, 10, TimeUnit.MILLISECONDS);
    }

    protected void stopRecogThread() {
        if (recogScheduledExecutor != null) {
            recogScheduledExecutor.shutdown();
            try {
                recogScheduledExecutor.awaitTermination(500, TimeUnit.MILLISECONDS);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            recogScheduledExecutor = null;
        }
    }


    @Override
    public void onCameraViewStarted(int width, int height) {
        if (mAbsoluteFaceSize == 0) {
            int minLength = Math.min(width, height);
            if (Math.round(minLength * mRelativeFaceSize) > 0) {
                mAbsoluteFaceSize = Math.round(minLength * mRelativeFaceSize);
            }
        }
        detectCacheBitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Mat cacheMat = new Mat();
        cacheQueue.offer(cacheMat);
        startRecogThread();
    }

    @Override
    public void onCameraViewStopped() {
        stopRecogThread();
        detectCacheBitmap.recycle();
        Mat mat = cacheQueue.poll();
        if (mat != null) {
            mat.release();
        }
        mat = recogQueue.poll();
        if (mat != null) {
            mat.release();
        }
    }

    @Override
    public Mat onCameraFrame(CvCameraView.CvCameraViewFrame inputFrame) {
        Mat rgba = inputFrame.rgba();
        if (enableRecog.get()) {
            Mat mat = cacheQueue.poll();
            if (mat != null) {
                rgba.copyTo(mat);
                recogQueue.offer(mat);
            }
        }
        return rgba;
    }

    public interface FaceRecogListener {

        /**
         * 检测到的人脸数
         * @param count 人脸数
         */
        void recogCount(int count);

        void recogSuccess(UserInfo userInfo);

        void recogFail();
    }

    static class RecogFaceWorker implements Runnable {
        DecimalFormat df = new DecimalFormat("0.000000000");

        FaceRecogListener listener;
        Bitmap detectCacheBitmap;
        BlockingQueue<Mat> recogQueue;
        BlockingQueue<Mat> cacheQueue;
        float minSimilarity;
        int mAbsoluteFaceSize;
        float bioThreshold;

        public RecogFaceWorker(Bitmap detectCacheBitmap, BlockingQueue<Mat> recogQueue, BlockingQueue<Mat> cacheQueue, float minSimilarity, float bioThreshold) {
            this.detectCacheBitmap = detectCacheBitmap;
            this.recogQueue = recogQueue;
            this.cacheQueue = cacheQueue;
            this.minSimilarity = minSimilarity;
            this.mAbsoluteFaceSize = 80;
            this.bioThreshold = bioThreshold;
        }

        public void setFaceRecogListener(FaceRecogListener listener) {
            this.listener = listener;
        }

        public void setMinSimilarity(float minSimilarity) {
            this.minSimilarity = minSimilarity;
        }

        public void setAbsoluteFaceSize(int mAbsoluteFaceSize) {
            this.mAbsoluteFaceSize = mAbsoluteFaceSize;
        }

        @Override
        public void run() {
            if (Thread.interrupted())
                return;
            Mat cacheMat;
            while ((cacheMat = recogQueue.poll()) != null) {
                if (detectCacheBitmap != null && !detectCacheBitmap.isRecycled()) {
                    Utils.matToBitmap(cacheMat, detectCacheBitmap);
                    long start = System.currentTimeMillis();
                    Log.d(TAG, "开始检测");
                    List<FacePoint> facePointList = FaceHelper.getInstance().faceDetect(detectCacheBitmap, mAbsoluteFaceSize);
                    long detect = System.currentTimeMillis() - start;
                    int recogCount = facePointList == null ? 0 : facePointList.size();
                    Log.d(TAG, "检测完毕,数量: " + recogCount + " ,耗时: " + detect + "ms");
                    listener.recogCount(recogCount);
                    if (facePointList != null && facePointList.size() > 0) {
                        List<Bitmap> bitmapList = new ArrayList<>();
                        Mat liveCache = new Mat();
                        Imgproc.cvtColor(cacheMat, liveCache, Imgproc.COLOR_RGB2BGR);
                        for (FacePoint facePoint : facePointList) {
                            float f = FaceHelper.getInstance().isLive(liveCache, facePoint);
                            String s = "活体检测结果: " + df.format(f);
                            RunningLog.debug(s);
                            if (f < bioThreshold) {
                                RunningLog.debug("活体检测不通过");
                                break;
                            }
                            RunningLog.debug("通过活体检测");
                            Bitmap cache = Bitmap.createBitmap(cacheMat.cols(), cacheMat.rows(), Bitmap.Config.ARGB_8888);
                            Utils.matToBitmap(cacheMat, cache);
                            // Bitmap bitmap = FaceHelper.getInstance().alignFace(facePoint, cache);
                            // bitmapList.add(bitmap);
                            // cache.recycle();

                            Map<String, Float> map = FaceHelper.getInstance().searchFace(cache, facePoint, 1, minSimilarity);
                            RunningLog.debug("人脸识别结果：" + map.toString());
                            cache.recycle();
                            UserInfo recogUser = null;
                            for (String key : map.keySet()) {
                                UserInfo info = FaceHelper.getInstance().getUserByFaceId(key);
                                if (info != null) {
                                    RunningLog.debug("人脸识别成功：" + info.getName() + ", " + info.getCode() + ", similarity: " + map.get(key));
                                    recogUser = info;
                                    break;
                                }
                            }
                            if (recogUser != null) {
                                if (listener != null) {
                                    listener.recogSuccess(recogUser);
                                }
                            } else {
                                if (listener != null) {
                                    listener.recogFail();
                                }
                            }
                        }
                        liveCache.release();
                        long end = System.currentTimeMillis() - start;
                        Log.d(TAG, "总耗时: " + end + "ms");
                        /*
                        for (Bitmap bitmap : bitmapList) {
                            Map<String, Float> map = FaceHelper.getInstance().searchFace(bitmap, 1, minSimilarity);
                            RunningLog.debug("人脸识别结果：" + map.toString());
                            bitmap.recycle();
                            UserInfo recogUser = null;
                            for (String key : map.keySet()) {
                                UserInfo info = FaceHelper.getInstance().getUserByFaceId(key);
                                if (info != null) {
                                    RunningLog.debug("人脸识别成功：" + info.getName() + ", " + info.getCode() + ", similarity: " + map.get(key));
                                    recogUser = info;
                                    break;
                                }
                            }
                            if (recogUser != null) {
                                if (listener != null) {
                                    listener.recogSuccess(recogUser);
                                }
                            } else {
                                if (listener != null) {
                                    listener.recogFail();
                                }
                            }
                        }
                         */
                    }
                }
                cacheQueue.offer(cacheMat);
            }
        }

    }

}
