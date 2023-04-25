package com.sunmnet.mediaroom.brand.utils;

import android.os.Environment;

import java.io.File;
import java.util.concurrent.TimeUnit;

import static com.sunmnet.mediaroom.brand.data.file.FileConstant.*;

public class FileResourceUtil {

    public static String ROOT_PATH = Environment.getExternalStorageDirectory().getAbsolutePath();


    public static void setRootPath(String rootPath) {
        ROOT_PATH = rootPath;
    }

    public static String getTempFolderPath() {
        return getAppRootFolderPath() + "/" + TEMP_FOLDER + "/";
    }

    public static String getProgramFolderPath() {
        return getAppRootFolderPath() + "/" + PROGRAM_FOLDER + "/";
    }

    public static String getExamFolderPath() {
        return getAppRootFolderPath() + "/" + EXAM_FOLDER + "/";
    }

    public static String getCourseFilePath() {
        return getAppRootFolderPath() + "/" + COURSE_FILE_NAME;
    }

    public static String getAppRootFolderPath() {
        return ROOT_PATH + "/" + APP_ROOT_FOLDER;
    }

    public static String getExamTimetableFileName(String examTimetableId) {
        if (examTimetableId == null)
            return null;
        return "exam_" + examTimetableId + ".json";
    }

    public static boolean isExpired(File file, long time, TimeUnit timeUnit) {
        if (file.exists()) {
            long modifiedMillis = file.lastModified();
            long current = System.currentTimeMillis();
            if (current - modifiedMillis > timeUnit.toMillis(time)) {
                return true;
            } else
                return false;
        }
        return true;
    }

    public static String getProgramResourcePath(String programId, String resourceMd5) {
        return getProgramFolderPath() + resourceMd5 + "/" + programId + "/";
    }

    public static String getConfigFolderPath() {
        return getAppRootFolderPath() + "/" + CONFIG_FOLDER + "/";
    }

    public static String getCustomConfigFolderPath() {
        return getConfigFolderPath() + CUSTOM_CONFIG_FOLDER + "/";
    }
}
