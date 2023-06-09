/* DO NOT EDIT THIS FILE - it is machine generated */
#include <jni.h>
/* Header for class com_sunmnet_mediaroom_facedetect_DetectionTracker */

#ifndef _Included_com_sunmnet_mediaroom_facedetect_DetectionTracker
#define _Included_com_sunmnet_mediaroom_facedetect_DetectionTracker
#ifdef __cplusplus
extern "C" {
#endif
/*
 * Class:     com_sunmnet_mediaroom_facedetect_DetectionTracker
 * Method:    nativeCreateObject
 * Signature: (Ljava/lang/String;I)J
 */
JNIEXPORT jlong JNICALL Java_com_sunmnet_mediaroom_facedetect_DetectionTracker_nativeCreateObject
  (JNIEnv *, jclass, jstring, jint);

/*
 * Class:     com_sunmnet_mediaroom_facedetect_DetectionTracker
 * Method:    nativeDestroyObject
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_sunmnet_mediaroom_facedetect_DetectionTracker_nativeDestroyObject
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_sunmnet_mediaroom_facedetect_DetectionTracker
 * Method:    nativeStart
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_sunmnet_mediaroom_facedetect_DetectionTracker_nativeStart
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_sunmnet_mediaroom_facedetect_DetectionTracker
 * Method:    nativeStop
 * Signature: (J)V
 */
JNIEXPORT void JNICALL Java_com_sunmnet_mediaroom_facedetect_DetectionTracker_nativeStop
  (JNIEnv *, jclass, jlong);

/*
 * Class:     com_sunmnet_mediaroom_facedetect_DetectionTracker
 * Method:    nativeSetFaceSize
 * Signature: (JII)V
 */
JNIEXPORT void JNICALL Java_com_sunmnet_mediaroom_facedetect_DetectionTracker_nativeSetFaceSize
  (JNIEnv *, jclass, jlong, jint, jint);

/*
 * Class:     com_sunmnet_mediaroom_facedetect_DetectionTracker
 * Method:    nativeDetect
 * Signature: (JJJ)V
 */
JNIEXPORT void JNICALL Java_com_sunmnet_mediaroom_facedetect_DetectionTracker_nativeDetect
  (JNIEnv *, jclass, jlong, jlong, jlong);

/*
 * Class:     com_sunmnet_mediaroom_facedetect_DetectionTracker
 * Method:    nativeSetScaleFactor
 * Signature: (JF)V
 */
JNIEXPORT void JNICALL Java_com_sunmnet_mediaroom_facedetect_DetectionTracker_nativeSetScaleFactor
  (JNIEnv *, jclass, jlong, jfloat);

/*
 * Class:     com_sunmnet_mediaroom_facedetect_DetectionTracker
 * Method:    nativeSetMaxTrackLifetime
 * Signature: (JI)V
 */
JNIEXPORT void JNICALL Java_com_sunmnet_mediaroom_facedetect_DetectionTracker_nativeSetMaxTrackLifetime
  (JNIEnv *, jclass, jlong, jint);

#ifdef __cplusplus
}
#endif
#endif
