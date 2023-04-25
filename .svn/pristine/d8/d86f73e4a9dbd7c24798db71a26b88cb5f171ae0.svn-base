package com.sunmnet.mediaroom.brand.utils;

import android.util.Log;

import com.sunmnet.mediaroom.common.tools.RunningLog;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import java.util.zip.ZipFile;

/**
 * Created by liufang on 2017/4/14.
 */
public class UnZipUtil {
    /**
     * 2     * 解压缩功能.
     * 3     * 将zipFile文件解压到folderPath目录下.
     * 4     * @throws Exception
     * 5
     */
    private static final String TAG = "UnZipUtil";
    public static Boolean upZipFile(File zipFile, String folderPath) throws ZipException, IOException {
        Boolean flag=true;
        try {
            ZipFile zfile = new ZipFile(zipFile);
            File dire=new File(folderPath);
            if (!dire.exists()) {
                dire.mkdirs();
            }
            if (dire.isDirectory()) {
                StringBuffer buffer=new StringBuffer();
                String total=(dire.getTotalSpace()/1024.0/1024.0)+"MB";
                String usable=(dire.getUsableSpace()/1024.0/1024.0)+"MB";
                buffer.append(folderPath);
                buffer.append("-->总容量:");
                buffer.append(total);
                buffer.append(",可用空间:");
                buffer.append(usable);
                RunningLog.debug(buffer.toString());
            }
            Enumeration zList = zfile.entries();
            ZipEntry ze = null;
            byte[] buf = new byte[1024];
            while (zList.hasMoreElements()) {
                ze = (ZipEntry) zList.nextElement();
                if (ze.isDirectory()) {
                    RunningLog.run("ze.getName() = " + ze.getName());
                    String dirstr = folderPath + ze.getName();
                    //dirstr.trim();
                    dirstr = new String(dirstr.getBytes("utf-8"), "GB2312");
                    Log.d("upZipFile", "str = " + dirstr);
                    File f = new File(dirstr);
                    f.mkdir();
                    continue;
                }
                RunningLog.run("ze.getName() = " + ze.getName());
                OutputStream os = new BufferedOutputStream(new FileOutputStream(getRealFileName(folderPath, ze.getName())));
                InputStream is = new BufferedInputStream(zfile.getInputStream(ze));
                int readLen = 0;
                while ((readLen = is.read(buf, 0, 1024)) != -1) {
                    os.write(buf, 0, readLen);
                }
                is.close();
                os.close();
            }
            zfile.close();
        } catch (Exception e) {
            e.printStackTrace();
            flag=false;
            RunningLog.error( "电子班牌的资源文件解压失败");
            RunningLog.error(e);
        }
        return flag;
    }

    public static File getRealFileName(String baseDir, String absFileName) {
        String[] dirs = absFileName.split("/");
        String lastDir = baseDir;
        if (dirs.length > 1) {
            for (int i = 0; i < dirs.length - 1; i++) {
                lastDir += (dirs[i] + "/");
                File dir = new File(lastDir);
                if (!dir.exists()) {
                    dir.mkdirs();
                    RunningLog.run( "create dir = " + (lastDir + "/" + dirs[i]));
                }
            }
            File ret = new File(lastDir, dirs[dirs.length - 1]);
            RunningLog.run( "2ret = " + ret);
            return ret;
        } else {
            return new File(baseDir, absFileName);
        }
    }
}