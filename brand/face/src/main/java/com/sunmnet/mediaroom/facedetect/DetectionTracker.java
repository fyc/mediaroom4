package com.sunmnet.mediaroom.facedetect;

import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;

public class DetectionTracker
{
    public DetectionTracker(String cascadeName, int minFaceSize) {
        mNativeObj = nativeCreateObject(cascadeName, minFaceSize);
    }

    public void start() {
        nativeStart(mNativeObj);
    }

    public void stop() {
        nativeStop(mNativeObj);
    }

    public void setMinFaceSize(int size) {
        nativeSetFaceSize(mNativeObj, size, 0);
    }

    public void setFaceSize(int sizeMin, int sizeMax) {
        nativeSetFaceSize(mNativeObj, sizeMin, sizeMax);
    }

    public void detect(Mat imageGray, MatOfRect faces) {
        nativeDetect(mNativeObj, imageGray.getNativeObjAddr(), faces.getNativeObjAddr());
    }

    public void release() {
        nativeDestroyObject(mNativeObj);
        mNativeObj = 0;
    }

    public void setScaleFactor(float scaleFactor) {
        nativeSetScaleFactor(mNativeObj, scaleFactor);
    }

    public void setMaxTrackLifetime(int period) {
        nativeSetMaxTrackLifetime(mNativeObj, period);
    }

    private long mNativeObj = 0;

    private static native long nativeCreateObject(String cascadeName, int minFaceSize);
    private static native void nativeDestroyObject(long thiz);
    private static native void nativeStart(long thiz);
    private static native void nativeStop(long thiz);
    private static native void nativeSetFaceSize(long thiz, int sizeMin,int sizeMax);
    private static native void nativeDetect(long thiz, long inputImage, long faces);
    private static native void nativeSetScaleFactor(long thiz, float scaleFactor);
    private static native void nativeSetMaxTrackLifetime(long thiz, int maxTime);
}
