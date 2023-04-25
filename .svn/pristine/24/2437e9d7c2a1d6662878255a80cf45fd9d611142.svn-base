package com.sunmnet.mediaroom.brand.view;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ImageFormat;
import android.graphics.Rect;
import android.graphics.SurfaceTexture;
import android.graphics.drawable.Drawable;
import android.hardware.Camera;
import android.os.Build;
import android.support.v4.content.PermissionChecker;
import android.util.AttributeSet;
import android.util.Log;
import android.view.TextureView;

import org.opencv.android.Utils;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.imgproc.Imgproc;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.Semaphore;
import java.util.concurrent.TimeUnit;

public class CvCameraView extends TextureView {

    protected static final String TAG = "CvCameraView";

    public CvCameraView(Context context) {
        super(context);
        init();
    }

    public CvCameraView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CvCameraView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private final SurfaceTextureListener mSurfaceTextureListener
            = new SurfaceTextureListener() {

        @Override
        public void onSurfaceTextureAvailable(SurfaceTexture texture, int width, int height) {
            surfaceAvailed = true;
            Log.d(TAG, "SurfaceTextureAvailable");
            checkCameraState();
        }

        @Override
        public void onSurfaceTextureSizeChanged(SurfaceTexture texture, int width, int height) {
        }

        @Override
        public boolean onSurfaceTextureDestroyed(SurfaceTexture texture) {
            surfaceAvailed = false;
            Log.d(TAG, "SurfaceTextureDestroyed");
            checkCameraState();
            return true;
        }

        @Override
        public void onSurfaceTextureUpdated(SurfaceTexture texture) {
        }
    };

    @Override
    public void setBackgroundDrawable(Drawable background) {
        // 解决安卓7以上的异常
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N && background != null) {
            super.setBackgroundDrawable(background);
        }
    }

    protected void init() {
        setSurfaceTextureListener(mSurfaceTextureListener);
    }

    private Semaphore mCameraOpenCloseLock = new Semaphore(1);
    private boolean surfaceAvailed;
    private boolean enabled;
    private static final int STOPPED = 0;
    private static final int STARTED = 1;
    private int mState = STOPPED;


    private SurfaceTexture mSurfaceTexture;

    private Camera mCamera;

    protected int mFrameWidth;
    protected int mFrameHeight;

    private int mPreviewFormat = ImageFormat.NV21;

    private Bitmap cacheBitmap;
    private byte[] mBuffer;

    public void setCameraViewListener(CvCameraViewListener mCameraViewListener) {
        this.mCameraViewListener = mCameraViewListener;
    }

    private CvCameraViewListener mCameraViewListener;

    //渲染线程
    private ScheduledExecutorService scheduledExecutorService;

    //预览缓冲队列
    protected BlockingQueue<CvCameraViewFrame> renderingQueue;
    protected BlockingQueue<CvCameraViewFrame> cacheQueue;

    public void enableView() {
        enabled = true;
        Log.d(TAG, "enable CvCameraView");
        checkCameraState();
    }

    public void disableView() {
        enabled = false;
        Log.d(TAG, "disable CvCameraView");
        checkCameraState();
    }

    private synchronized void checkCameraState() {
        try {
            mCameraOpenCloseLock.acquire();
            if (enabled && surfaceAvailed) {
                if (STARTED != mState) {
                    openCamera(getWidth(), getHeight());
                    mState = STARTED;
                    if (mCameraViewListener != null) {
                        mCameraViewListener.onCameraViewStarted(mFrameWidth, mFrameHeight);
                    }
                }
            } else {
                if (STOPPED != mState) {
                    closeCamera();
                    mState = STOPPED;
                    if (mCameraViewListener != null) {
                        mCameraViewListener.onCameraViewStopped();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            mCameraOpenCloseLock.release();
        }
    }

    private void openCamera(int width, int height) {
        if (PermissionChecker.checkSelfPermission(getContext(), Manifest.permission.CAMERA)
                != PackageManager.PERMISSION_GRANTED || Camera.getNumberOfCameras() == 0) {
            return;
        }
        mCamera = Camera.open();
        if(mCamera == null && Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            boolean connected = false;
            for (int camIdx = 0; camIdx < Camera.getNumberOfCameras(); ++camIdx) {
                Log.d(TAG, "Trying to open camera with new open(" + Integer.valueOf(camIdx) + ")");
                try {
                    mCamera = Camera.open(camIdx);
                    connected = true;
                } catch (RuntimeException e) {
                    Log.e(TAG, "Camera #" + camIdx + "failed to open: " + e.getLocalizedMessage());
                }
                if (connected) break;
            }
        }
        if (mCamera == null) {
            Log.e(TAG, "open camera error");
            return;
        }
        Camera.Size previewSize = chooseOptimalSize(mCamera.getParameters().getSupportedPreviewSizes(), width, height, width, height);

        mFrameWidth = previewSize.width;
        mFrameHeight = previewSize.height;

        Log.d(TAG, "preview size, width: " + mFrameWidth + " ,height: " + mFrameHeight);

        Camera.Parameters params = mCamera.getParameters();
        params.setPreviewSize(previewSize.width, previewSize.height);

        params.setRecordingHint(true);
        List<String> FocusModes = params.getSupportedFocusModes();
        if (FocusModes != null && FocusModes.contains(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO)) {
            params.setFocusMode(Camera.Parameters.FOCUS_MODE_CONTINUOUS_VIDEO);
        }
        if (Build.FINGERPRINT.startsWith("generic")
                || Build.FINGERPRINT.startsWith("unknown")
                || Build.MODEL.contains("google_sdk")
                || Build.MODEL.contains("Emulator")
                || Build.MODEL.contains("Android SDK built for x86")
                || Build.MANUFACTURER.contains("Genymotion")
                || (Build.BRAND.startsWith("generic") && Build.DEVICE.startsWith("generic"))
                || "google_sdk".equals(Build.PRODUCT))
            params.setPreviewFormat(ImageFormat.YV12);  // "generic" or "android" = android emulator
        else
            params.setPreviewFormat(ImageFormat.NV21);

        mPreviewFormat = params.getPreviewFormat();

        mCamera.setParameters(params);
        cacheBitmap = Bitmap.createBitmap(mFrameWidth, mFrameHeight, Bitmap.Config.ARGB_8888);

        renderingQueue = new LinkedBlockingQueue<>(2);
        cacheQueue = new LinkedBlockingQueue<>(2);

        Mat cacheMat1 = new Mat(mFrameHeight + (mFrameHeight / 2), mFrameWidth, CvType.CV_8UC1);
        Mat cacheMat2 = new Mat(mFrameHeight + (mFrameHeight / 2), mFrameWidth, CvType.CV_8UC1);
        CvCameraViewFrame cameraViewFrame1 = new CvCameraViewFrame(cacheMat1, mFrameWidth, mFrameHeight);
        CvCameraViewFrame cameraViewFrame2 = new CvCameraViewFrame(cacheMat2, mFrameWidth, mFrameHeight);
        cacheQueue.offer(cameraViewFrame1);
        cacheQueue.offer(cameraViewFrame2);
        mSurfaceTexture = new SurfaceTexture(2);
        try {
            mCamera.setPreviewTexture(mSurfaceTexture);
        } catch (IOException e) {
            e.printStackTrace();
        }
        int size = mFrameWidth * mFrameHeight;
        size = size * ImageFormat.getBitsPerPixel(params.getPreviewFormat()) / 8;
        mBuffer = new byte[size];

        mCamera.setPreviewCallbackWithBuffer(new Camera.PreviewCallback() {
            @Override
            public void onPreviewFrame(byte[] data, Camera camera) {
                CvCameraViewFrame frame = cacheQueue.poll();
                if (frame != null) {
                    frame.put(data);
                    renderingQueue.offer(frame);
                }
                if (mCamera != null)
                    mCamera.addCallbackBuffer(mBuffer);
            }
        });
        mCamera.addCallbackBuffer(mBuffer);
        mCamera.startPreview();

        scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new RenderingWorker(), 20,
                10, TimeUnit.MILLISECONDS);

    }

    private void closeCamera() {
        try {
            if (scheduledExecutorService != null) {
                scheduledExecutorService.shutdown();
                scheduledExecutorService.awaitTermination(500, TimeUnit.MILLISECONDS);
            }

            if (null != mCamera) {
                mCamera.setPreviewTexture(null);
                mCamera.setPreviewCallback(null);
                mCamera.stopPreview();
                mCamera.release();
                mCamera = null;
            }
            if (cacheBitmap != null) {
                cacheBitmap.recycle();
                cacheBitmap = null;
            }

            if (cacheQueue != null) {
                CvCameraViewFrame frame;
                while ((frame = cacheQueue.poll()) != null) {
                    frame.release();
                }
                cacheQueue = null;
            }
            if (renderingQueue != null) {
                CvCameraViewFrame frame;
                while ((frame = renderingQueue.poll()) != null) {
                    frame.release();
                }
                renderingQueue = null;
            }
            if (mSurfaceTexture != null) {
                mSurfaceTexture.release();
                mSurfaceTexture = null;
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("Interrupted while trying to lock camera closing.", e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected Camera.Size chooseOptimalSize(List<Camera.Size> choices, int textureViewWidth,
                                                 int textureViewHeight, int maxWidth, int maxHeight) {
        List<Camera.Size> bigEnough = new ArrayList<>();
        List<Camera.Size> notBigEnough = new ArrayList<>();
        for (Camera.Size option : choices) {
            if (option.width <= maxWidth && option.height <= maxHeight) {
                if (option.width >= textureViewWidth && option.height >= textureViewHeight) {
                    bigEnough.add(option);
                } else {
                    notBigEnough.add(option);
                }
            }
        }
        if (bigEnough.size() > 0) {
            return Collections.min(bigEnough, new CompareSizesByArea());
        } else if (notBigEnough.size() > 0) {
            return Collections.max(notBigEnough, new CompareSizesByArea());
        } else {
            Log.e(TAG, "Couldn't find any suitable preview size");
            return choices.get(0);
        }
    }

    static class CompareSizesByArea implements Comparator<Camera.Size> {

        @Override
        public int compare(Camera.Size lhs, Camera.Size rhs) {
            return Long.signum((long) lhs.width * lhs.height - (long) rhs.width * rhs.height);
        }
    }

    class RenderingWorker implements Runnable {

        @Override
        public void run() {
            CvCameraViewFrame frame = renderingQueue.poll();
            if (frame == null) {
                return;
            }
            Mat rgb;
            if (mCameraViewListener != null) {
                rgb = mCameraViewListener.onCameraFrame(frame);
            } else {
                rgb = frame.rgba();
            }
            cacheQueue.offer(frame);
            Utils.matToBitmap(rgb, cacheBitmap);
            Canvas canvas = lockCanvas();
            canvas.drawBitmap(cacheBitmap, new Rect(0, 0, cacheBitmap.getWidth(), cacheBitmap.getHeight()),
                    new Rect((canvas.getWidth() - cacheBitmap.getWidth()) / 2,
                            (canvas.getHeight() - cacheBitmap.getHeight()) / 2,
                            (canvas.getWidth() - cacheBitmap.getWidth()) / 2 + cacheBitmap.getWidth(),
                            (canvas.getHeight() - cacheBitmap.getHeight()) / 2 + cacheBitmap.getHeight()), null);
            unlockCanvasAndPost(canvas);

        }
    }

    public class CvCameraViewFrame {

        public Mat gray() {
            return mYuvFrameData.submat(0, mHeight, 0, mWidth);
        }

        public Mat rgba() {
            if (mPreviewFormat == ImageFormat.NV21)
                Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGB_NV21, 4);
            else if (mPreviewFormat == ImageFormat.YV12)
                Imgproc.cvtColor(mYuvFrameData, mRgba, Imgproc.COLOR_YUV2RGB_I420, 4);  // COLOR_YUV2RGBA_YV12 produces inverted colors
            else
                throw new IllegalArgumentException("Preview Format can be NV21 or YV12");
            return mRgba;
        }

        public CvCameraViewFrame(Mat Yuv420sp, int width, int height) {
            mWidth = width;
            mHeight = height;
            mYuvFrameData = Yuv420sp;
            mRgba = new Mat();
        }

        public void release() {
            mRgba.release();
            mYuvFrameData.release();
        }

        public void put(byte[] data) {
            mYuvFrameData.put(0, 0, data);
        }

        private Mat mYuvFrameData;
        private Mat mRgba;
        private int mWidth;
        private int mHeight;
    }

    public interface CvCameraViewListener {

        public void onCameraViewStarted(int width, int height);

        public void onCameraViewStopped();

        public Mat onCameraFrame(CvCameraViewFrame inputFrame);
    }
}
